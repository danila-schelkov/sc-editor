using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Linq;

namespace sc_editor.SupercellSWF
{
    public class ShapeDrawBitmapCommand : SWFObject
    {
        public readonly List<PointF> SheetPoints;
        
        private byte TextureId { get; set; }

        private readonly List<PointF> _shapePoints;
        private SWFTexture _texture;

        public ShapeDrawBitmapCommand(SupercellSWF swf) : base(swf)
        {
            SheetPoints = new List<PointF>();
            _shapePoints = new List<PointF>();
        }
        
        public override void Load(BinaryReader br, short tag)
        {
            TextureId = br.ReadUnsignedChar();
            var pointsCount = tag == 4 ? (byte) 4 : br.ReadUnsignedChar();
            
            _texture = SWF.GetTexture(TextureId);

            for (var i = 0; i < pointsCount; i++)
            {
                var point = new PointF
                {
                    X = br.ReadTwip(),
                    Y = br.ReadTwip()
                };
                SheetPoints.Add(point);
            }
            for (var i = 0; i < pointsCount; i++)
            {
                var point = new PointF
                {
                    X = tag != 22 ? br.ReadShort() : (float) br.ReadShort() * _texture.Size.Width / 65535,
                    Y = tag != 22 ? br.ReadShort() : (float) br.ReadShort() * _texture.Size.Height / 65535
                };
                _shapePoints.Add(point);
            }
        }

        public override string GetDataTypeName()
        {
            return "Region";
        }
        
        public override Bitmap Render()
        {
            if (!(_rendered is null)) return _rendered;
            
            _texture = SWF.GetTexture(TextureId);
            if (_texture == null) return null;

            var bitmap = _texture.Render();

            Debug.WriteLine($"Rendering polygon image of {_shapePoints.Count} points");
            // foreach(var uv in _shapePoints)
            // {
            //     Debug.WriteLine("u: " + uv.X + ", v: " + uv.Y);
            // }

            var gp = new GraphicsPath();
            gp.AddPolygon(_shapePoints.ToArray());

            var roundedBounds = Rectangle.Round(gp.GetBounds());

            var gpWidth = roundedBounds.Width;
            gpWidth = gpWidth > 0 ? gpWidth : 1;

            var gpHeight = roundedBounds.Height;
            gpHeight = gpHeight > 0 ? gpHeight : 1;

            _rendered = new Bitmap(gpWidth, gpHeight);

            var chunkX = roundedBounds.X;
            var chunkY = roundedBounds.Y;

            using (var g = Graphics.FromImage(_rendered))
            {
                gp.Transform(new Matrix(1, 0, 0, 1, -chunkX, -chunkY));
                g.SetClip(gp);
                g.DrawImage(bitmap, -chunkX, -chunkY);
                // g.DrawPath(new Pen(Color.IndianRed, 2), gp);  // Polygon Path
            }

            return _rendered;
        }

        public void GetVertexCount()
        {
            
        }

        public void SetXY(int pointIndex, float x, float y)
        {
            SheetPoints[pointIndex] = new PointF
            {
                X = x,
                Y = y
            };
        }
        
        public void SetUV(int pointIndex, float u, float v)
        {
            _shapePoints[pointIndex] = new PointF
            {
                X = u,
                Y = v
            };
        }
        
        public float GetX(int pointIndex)
        {
            return SheetPoints[pointIndex].X;
        }
        
        public float GetY(int pointIndex)
        {
            return SheetPoints[pointIndex].Y;
        }
        
        public float GetU(int pointIndex)
        {
            return _shapePoints[pointIndex].X;
        }
        
        public float GetV(int pointIndex)
        {
            return _shapePoints[pointIndex].Y;
        }
    }
    public class Shape : SWFObject
    {
        private readonly List<SWFObject> _commands;

        public Shape(SupercellSWF swf) : base(swf)
        {
            _commands = new List<SWFObject>();
        }

        public override void Load(BinaryReader br, short tag)
        {
            Id = br.ReadShort();
            var regionsCount = br.ReadShort();

            if (tag == 18)
            {
                var pointsCount = br.ReadShort();
            }
            else
            {
                var pointsCount = 4 * regionsCount;
            }
            
            while (true)
            {
                var shapeDrawBitmapTag = br.ReadUnsignedChar();
                var shapeDrawBitmapLength = br.ReadInt32();

                switch (shapeDrawBitmapTag)
                {
                    case 0:
                        return;
                    case 6:
                        Debug.WriteLine("SupercellSWF::TAG_SHAPE_DRAW_COLOR_FILL_COMMAND not supported");
                        break;
                    case 4:
                    case 17:
                    case 22:
                        var shapeDrawBitmapCommand = new ShapeDrawBitmapCommand(SWF);
                        shapeDrawBitmapCommand.Load(br, shapeDrawBitmapTag);
                        _commands.Add(shapeDrawBitmapCommand);
                        break;
                    default:
                        br.ReadBytes(shapeDrawBitmapLength);
                        break;
                }
            }
        }
        
        public override Bitmap Render()
        {
            if (!(_rendered is null)) return _rendered;
            
            var gp = new GraphicsPath();

            foreach (var polygon in GetChildren()
                .Select(command => ((ShapeDrawBitmapCommand) command).SheetPoints.ToArray()))
            {
                gp.AddPolygon(polygon);
                Polygons.Add(polygon);
            }

            var roundedBounds = Rectangle.Round(gp.GetBounds());

            var gpWidth = roundedBounds.Width;
            gpWidth = gpWidth > 0 ? gpWidth : 1;

            var gpHeight = roundedBounds.Height;
            gpHeight = gpHeight > 0 ? gpHeight : 1;

            _rendered = new Bitmap(gpWidth, gpHeight);

            var chunkX = -roundedBounds.X;
            var chunkY = -roundedBounds.Y;

            using (var g = Graphics.FromImage(_rendered))
            {
                foreach (var command in GetChildren().Cast<ShapeDrawBitmapCommand>())
                {
                    var bitmap = command.Render().ResizeBitmap(gpWidth, gpHeight);

                    gp.Transform(new Matrix(1, 0, 0, 1, chunkX, chunkY));
                    g.SetClip(gp);
                    g.DrawImage(bitmap, 0, 0);
                }
            }

            return _rendered;
        }

        public override List<SWFObject> GetChildren()
        {
            return _commands;
        }

        public override sbyte GetDataType()
        {
            return 0;
        }

        public override string GetDataTypeName()
        {
            return "Shape";
        }
    }
}