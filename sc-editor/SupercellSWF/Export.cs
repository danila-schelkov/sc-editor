namespace sc_editor.SupercellSWF
{
    public class Export : SWFObject
    {
        public Export(SupercellSWF swf) : base(swf) { }

        public void SetId(ushort id)
        {
            Id = id;
        }
        
        public override string GetName()
        {
            return Name;
        }

        public override sbyte GetDataType()
        {
            return 3;
        }

        public override string GetDataTypeName()
        {
            return "Exports";
        }
    }
}