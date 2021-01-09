using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Text;
using System.Windows.Forms;
using OpenTK;
using sc_editor.SupercellSWF;

namespace sc_editor
{
    public static class Extensions
    {
        public static void Populate(this TreeView treeView, List<SWFObject> list, string groupName = null)
        {
            if (list == null) throw new ArgumentNullException(nameof(list));

            foreach (var data in list)
            {
                var dataTypeKey = groupName ?? data.GetDataType().ToString();
                var dataTypeName = groupName ?? data.GetDataTypeName() + "s";
                var id = data.GetId().ToString();
                
                var node = treeView.Nodes.ContainsKey(dataTypeKey) ? 
                    treeView.Nodes[dataTypeKey] : 
                    treeView.Nodes.Add(dataTypeKey, dataTypeName);
                
                var childNode = node.Nodes.Add(id, data.GetName());
                childNode.Tag = data;
                childNode.PopulateChildren(data);
            }
        }

        private static void PopulateChildren(this TreeNode tn, SWFObject sco)
        {
            foreach (var child in sco.GetChildren())
            {
                if (child is null) continue;
                
                var node = tn.Nodes.Add(child.GetId().ToString(), child.GetName());
                node.Tag = child;
                
                node.PopulateChildren(child);
            }
        }
        
        public static Bitmap ResizeBitmap(this Bitmap bitmap, int width, int height)
        {
            var result = new Bitmap(width, height);
            
            using (var g = Graphics.FromImage(result))
            {
                g.DrawImage(bitmap, 0, 0, width, height);
            }
 
            return result;
        }
        
        public static byte ReadUnsignedChar(this BinaryReader br)
        {
            return br.ReadByte();
        }
        
        public static ushort ReadShort(this BinaryReader br)
        {
            return br.ReadUInt16();
        }
        
        public static float ReadTwip(this BinaryReader br)
        {
            return (float) br.ReadInt32() / 20;
        }
        
        public static byte[] ReadByteArray(this BinaryReader br, int count)
        {
            var array = new byte[count];
            
            for (var i = 0; i < count; i++)
            {
                array[i] = br.ReadUnsignedChar();
            }
            return array;
        }
        
        public static ushort[] ReadShortArray(this BinaryReader br, int count)
        {
            var array = new ushort[count];
            
            for (var i = 0; i < count; i++)
            {
                array[i] = br.ReadShort();
            }
            return array;
        }
        
        public static string ReadAscii(this BinaryReader br)
        {
            var length = br.ReadByte();
            
            if (length != 255) 
                return Encoding.ASCII.GetString(br.ReadBytes(length));
            return string.Empty;
        }
        
        public static Matrix ReadMatrix2x3(this BinaryReader br)
        {
            var matrix = new Matrix(
                (float) br.ReadInt32() / 1024,
                (float) br.ReadInt32() / 1024,
                (float) br.ReadInt32() / 1024,
                (float) br.ReadInt32() / 1024,
                (float) br.ReadInt32() / 20,
                (float) br.ReadInt32() / 20
            );

            return matrix;
        }
        
        public static ColorTransform ReadColorTransform(this BinaryReader br)
        {
            var colorTransform = new ColorTransform(0, 0, 0);

            br.ReadUnsignedChar();  // R
            br.ReadUnsignedChar();  // R
            br.ReadUnsignedChar();  // G
            br.ReadUnsignedChar();  // G
            br.ReadUnsignedChar();  // B
            br.ReadUnsignedChar();  // B
            br.ReadUnsignedChar();  // A
            
            return colorTransform;
        }
    }
}