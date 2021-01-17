using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;

namespace sc_editor.SupercellSWF
{
    public abstract class SWFObject
    {
        public List<PointF[]> Polygons;
        public GraphicsPath gp;
        protected SupercellSWF SWF;
        protected string Name;
        protected ushort Id;
        
        private readonly List<SWFObject> _children;
        protected Bitmap _rendered;

        protected SWFObject(SupercellSWF swf)
        {
            _children = new List<SWFObject>();
            _rendered = null;
            
            Polygons = new List<PointF[]>();
            Id = 0;

            SWF = swf;
        }

        public virtual void Load(BinaryReader br, short tag)
        {
            
        }

        public virtual List<SWFObject> GetChildren()
        {
            return _children;
        }

        public virtual string GetDataTypeName()
        {
            return string.Empty;
        }

        public virtual sbyte GetDataType()
        {
            return -1;
        }

        public virtual string GetName()
        {
            return Name ?? GetDataTypeName() + ": " + GetId();
        }

        public ushort GetId()
        {
            return Id;
        }

        public virtual Bitmap Render(Matrix matrix = null)
        {
            return null;
        }
        
        public void SetName(string name)
        {
            if (name != string.Empty)
                Name = name;
        }
    }
}