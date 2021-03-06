namespace NeoDatis.Odb.Core.Server.Message
{
	[System.Serializable]
	public class CommitMessageResponse : NeoDatis.Odb.Core.Server.Layers.Layer3.Engine.Message
	{
		private bool ok;

		public CommitMessageResponse(string baseId, string connectionId, string error) : 
			base(NeoDatis.Odb.Core.Server.Layers.Layer3.Engine.Command.Commit, baseId, connectionId
			)
		{
			SetError(error);
		}

		public CommitMessageResponse(string baseId, string connectionId, bool ok) : base(
			NeoDatis.Odb.Core.Server.Layers.Layer3.Engine.Command.Commit, baseId, connectionId
			)
		{
			this.ok = ok;
		}

		public virtual bool IsOk()
		{
			return ok;
		}
	}
}
