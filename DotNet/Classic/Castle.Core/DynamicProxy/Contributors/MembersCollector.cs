// Copyright 2004-2010 Castle Project - http://www.castleproject.org/
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

namespace Castle.DynamicProxy.Contributors
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Reflection;
	using Castle.Core.Logging;
	using Castle.DynamicProxy.Generators;

	public abstract class MembersCollector
	{
		private const BindingFlags Flags = BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance;
		private ILogger logger = NullLogger.Instance;

		private ICollection<MethodInfo> checkedMethods = 
#if SL3
			new List<MethodInfo>();
#else
			new HashSet<MethodInfo>();
#endif
		private readonly IDictionary<PropertyInfo, MetaProperty> properties = new Dictionary<PropertyInfo, MetaProperty>();
		private readonly IDictionary<EventInfo, MetaEvent> events = new Dictionary<EventInfo, MetaEvent>();
		private readonly IDictionary<MethodInfo, MetaMethod> methods = new Dictionary<MethodInfo, MetaMethod>();
		
		protected readonly Type type;

		protected MembersCollector(Type type)
		{
			this.type = type;
		}

		public ILogger Logger
		{
			get { return logger; }
			set { logger = value; }
		}

		public IEnumerable<MetaMethod> Methods
		{
			get { return methods.Values; }
		}

		public IEnumerable<MetaProperty> Properties
		{
			get { return properties.Values; }
		}

		public IEnumerable<MetaEvent> Events
		{
			get { return events.Values; }
		}

		public virtual void CollectMembersToProxy(IProxyGenerationHook hook)
		{
			if (checkedMethods == null) // this method was already called!
			{
				throw new InvalidOperationException(
					string.Format("Can't call 'CollectMembersToProxy' method twice. This usually signifies a bug in custom {0}.",
					              typeof (ITypeContributor)));
			}
			CollectProperties(hook);
			CollectEvents(hook);
			// Methods go last, because properties and events have methods too (getters/setters add/remove)
			// and we don't want to get duplicates, so we collect property and event methods first
			// then we collect methods, and add only these that aren't there yet
			CollectMethods(hook);

			checkedMethods = null; // this is ugly, should have a boolean flag for this or something
		}

		private void CollectProperties(IProxyGenerationHook hook)
		{
			PropertyInfo[] propertiesFound = type.GetProperties(Flags);
			foreach (PropertyInfo property in propertiesFound)
			{
				AddProperty(property, hook);
			}
		}

		private void CollectEvents(IProxyGenerationHook hook)
		{
			EventInfo[] eventsFound = type.GetEvents(Flags);
			foreach (EventInfo @event in eventsFound)
			{
				AddEvent(@event, hook);
			}
		}

		private void CollectMethods(IProxyGenerationHook hook)
		{
			MethodInfo[] methodsFound = MethodFinder.GetAllInstanceMethods(type, Flags);
			foreach (MethodInfo method in methodsFound)
			{
				AddMethod(method, hook, true);
			}
		}

		private void AddProperty(PropertyInfo property, IProxyGenerationHook hook)
		{
			MetaMethod getter = null;
			MetaMethod setter = null;

			if (property.CanRead)
			{
				MethodInfo getMethod = property.GetGetMethod(true);
				getter = AddMethod(getMethod, hook, false);
			}

			if (property.CanWrite)
			{
				MethodInfo setMethod = property.GetSetMethod(true);
				setter = AddMethod(setMethod, hook, false);
			}

			if (setter==null && getter == null)
			{
				return;
			}

			var nonInheritableAttributes = AttributeUtil.GetNonInheritableAttributes(property);
			var arguments = property.GetIndexParameters();

			properties[property] = new MetaProperty(property.Name,
			                                        property.PropertyType,
			                                        property.DeclaringType,
			                                        getter,
			                                        setter,
			                                        nonInheritableAttributes,
			                                        arguments.Select(a => a.ParameterType).ToArray());
		}

		private void AddEvent(EventInfo @event, IProxyGenerationHook hook)
		{
			MethodInfo addMethod = @event.GetAddMethod(true);
			MethodInfo removeMethod = @event.GetRemoveMethod(true);
			MetaMethod adder = null;
			MetaMethod remover = null;

			if (addMethod != null)
			{
				adder = AddMethod(addMethod, hook, false);
			}

			if (removeMethod != null)
			{
				remover = AddMethod(removeMethod, hook, false);
			}

			if (adder == null && remover == null) return;

			events[@event] = new MetaEvent(@event.Name,
												 @event.DeclaringType, @event.EventHandlerType, adder, remover, EventAttributes.None);
		}

		private MetaMethod AddMethod(MethodInfo method, IProxyGenerationHook hook, bool isStandalone)
		{
			if (checkedMethods.Contains(method))
			{
				return null;
			}
			checkedMethods.Add(method);

			if (methods.ContainsKey(method))
			{
				return null;
			}
			var methodToGenerate = GetMethodToGenerate(method, hook, isStandalone);
			if (methodToGenerate != null)
			{
				methods[method] = methodToGenerate;
			}

			return methodToGenerate;
		}

		protected abstract MetaMethod GetMethodToGenerate(MethodInfo method, IProxyGenerationHook hook, bool isStandalone);

		/// <summary>
		/// Checks if the method is public or protected.
		/// </summary>
		/// <param name="method"></param>
		/// <returns></returns>
		protected bool IsAccessible(MethodBase method)
		{
			// Accessibility supported by the full framework and CoreCLR
			if (method.IsPublic || method.IsFamily || method.IsFamilyOrAssembly)
			{
				return true;
			}

#if !SILVERLIGHT
			// Accessibility not supported by the CoreCLR
			if (method.IsFamilyAndAssembly)
			{
				return true;
			}
			if (InternalsHelper.IsInternalToDynamicProxy(method.DeclaringType.Assembly) && method.IsAssembly)
			{
				return true;
			}
#else
			// Explicitly implemented interface method on class
			if (method.IsPrivate && method.IsFinal)
			{
				Logger.Debug(
					string.Format("Excluded explicitly implemented interface method {0} on type {1} because it cannot be intercepted.",
					              method.Name, method.DeclaringType.FullName));
			}
#endif
			return false;
		}

		/// <summary>
		/// Performs some basic screening and invokes the <see cref="IProxyGenerationHook"/>
		/// to select methods.
		/// </summary>
		/// <param name="method"></param>
		/// <param name="onlyVirtuals"></param>
		/// <param name="hook"></param>
		/// <returns></returns>
		protected bool AcceptMethod(MethodInfo method, bool onlyVirtuals, IProxyGenerationHook hook)
		{
			// we can never intercept a sealed (final) method
			if (method.IsFinal)
			{
				Logger.DebugFormat("Excluded sealed method {0} on {1} because it cannot be intercepted.", method.Name, method.DeclaringType.FullName);
				return false;
			}

			bool isInternalsAndNotVisibleToDynamicProxy = InternalsHelper.IsInternal(method);
			if (isInternalsAndNotVisibleToDynamicProxy)
			{
				isInternalsAndNotVisibleToDynamicProxy = InternalsHelper.IsInternalToDynamicProxy(method.DeclaringType.Assembly) ==
				                                         false;
			}

			if (isInternalsAndNotVisibleToDynamicProxy)
				return false;

			if (onlyVirtuals && !method.IsVirtual)
			{
#if !SILVERLIGHT
				if (method.DeclaringType != typeof(MarshalByRefObject))
#endif
				{
					Logger.DebugFormat("Excluded non-virtual method {0} on {1} because it cannot be intercepted.", method.Name, method.DeclaringType.FullName);
					hook.NonProxyableMemberNotification(type, method);
				}
				return false;
			}

			//can only proxy methods that are public or protected (or internals that have already been checked above)
			if ((method.IsPublic || method.IsFamily || method.IsAssembly || method.IsFamilyOrAssembly) == false)
				return false;


#if !SILVERLIGHT
			if (method.DeclaringType == typeof(MarshalByRefObject))
			{
				return false;
			}
#endif
			if (method.DeclaringType == typeof(object) && method.Name.Equals("Finalize", StringComparison.OrdinalIgnoreCase))
			{
				return false;
			}

			return hook.ShouldInterceptMethod(type, method);
		}
	}
}