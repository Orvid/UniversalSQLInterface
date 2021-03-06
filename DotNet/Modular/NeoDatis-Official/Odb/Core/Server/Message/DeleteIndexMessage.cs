namespace NeoDatis.Odb.Core.Server.Message
{
	[System.Serializable]
	public class DeleteIndexMessage : NeoDatis.Odb.Core.Server.Layers.Layer3.Engine.Message
	{
		protected string className;

		protected string indexName;

		protected bool verbose;

		public DeleteIndexMessage(string baseId, string connectionId, string className, string
			 indexName, bool verbose) : base(NeoDatis.Odb.Core.Server.Layers.Layer3.Engine.Command
			.DeleteIndex, baseId, connectionId)
		{
			this.className = className;
			this.indexName = indexName;
			this.verbose = verbose;
		}

		public virtual string GetIndexName()
		{
			return indexName;
		}

		public virtual string GetClassName()
		{
			return className;
		}

		public override string ToString()
		{
			return "DeleteIndex";
		}

		public virtual bool IsVerbose()
		{
			return verbose;
		}

		public virtual void SetVerbose(bool verbose)
		{
			this.verbose = verbose;
		}

		public virtual void SetClassName(string className)
		{
			this.className = className;
		}

		public virtual void SetIndexName(string indexName)
		{
			this.indexName = indexName;
		}
	}
}
