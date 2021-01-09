using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Linq;

namespace sc_editor.SupercellSWF
{
    public class Bind
    {
        public ushort Id { get; set; }
        public byte Blend { get; set; }
        public string Name { get; set; }

        public Bind() {}
    }

    public class MovieClipFrame : SWFObject
    {
        public MovieClipFrame(SupercellSWF swf) : base(swf) { }

        public override void Load(BinaryReader br, short tag)
        {
            Id = br.ReadShort();
            Name = br.ReadAscii();
        }
    }
    
    public class MovieClip : SWFObject
    {
        private byte _fps;
        private ushort _framesCount;

        private List<ushort[]> _transforms;

        private List<SWFObject> _movieClipFrames;
        private List<SWFObject> _children;
        private List<Bind> _binds;
        
        public MovieClip(SupercellSWF swf) : base(swf)
        {
            _movieClipFrames = new List<SWFObject>();
            _transforms = new List<ushort[]>();
            _binds = new List<Bind>();

            SWF = swf;
        }
        
        public override void Load(BinaryReader br, short tag)
        {
            Id = br.ReadShort();
            _fps = br.ReadUnsignedChar();
            _framesCount = br.ReadShort();

            switch (tag)
            {
                case 14:
                    // SupercellSWF::getTimelineOffset
                    Debug.WriteLine("TAG_MOVIE_CLIP_4 no longer supported");
                    break;
                case 3:
                    Debug.WriteLine("TAG_MOVIE_CLIP no longer supported");
                    break;
                default:
                {
                    var transformsCount = br.ReadInt32();

                    for (var i = 0; i < transformsCount; i++)
                    {
                        _transforms.Add(br.ReadShortArray(3));
                    }
                    break;
                }
            }

            var bindsCount = br.ReadShort();

            var idArray = br.ReadShortArray(bindsCount);
            for (var i = 0; i < bindsCount; i++)
            {
                var bind = new Bind();
                bind.Id = idArray[i];
                _binds.Add(bind);
            }
            if (tag == 12)
            {
                var blendArray = br.ReadByteArray(bindsCount);
                for (var i = 0; i < bindsCount; i++)
                {
                    _binds[i].Blend = blendArray[i];
                }
            }
            else
            {
                for (var i = 0; i < bindsCount; i++)
                {
                    _binds[i].Blend = 0;
                }
            }
            
            for (var i = 0; i < bindsCount; i++)
            {
                _binds[i].Name = br.ReadAscii();
            }

            while (true)
            {
                byte movieClipFrameTag;
                int movieClipFrameLength;
                
                while (true)
                {
                    movieClipFrameTag = br.ReadUnsignedChar();
                    movieClipFrameLength = br.ReadInt32();
                    if (movieClipFrameLength < 0)
                    {
                        Debug.WriteLine($"Negative tag length in MovieClip. Tag {movieClipFrameTag}");
                    }

                    if (movieClipFrameTag != 11)
                        break;

                    var movieClipFrame = new MovieClipFrame(SWF);
                    movieClipFrame.Load(br, movieClipFrameTag);

                    _movieClipFrames.Add(movieClipFrame);
                }

                if (movieClipFrameTag == 0)
                    break;
                if (movieClipFrameTag == 5)
                {
                    Debug.WriteLine("TAG_MOVIE_CLIP_FRAME no longer supported");
                }
                else
                {
                    Debug.WriteLine($"Unknown tag in MovieClip, {movieClipFrameTag}");
                }

                br.ReadChars(movieClipFrameLength);
            }
        }
        
        public override Bitmap Render()
        {
            var gp = new GraphicsPath();
            
            var allowAddPolygons = Polygons.Count == 0;
            foreach (var child in GetChildren())
            {
                child.Render();

                foreach (var polygon in child.Polygons)
                {
                    gp.AddPolygon(polygon);
                    if (allowAddPolygons)
                        Polygons.Add(polygon);
                }
            }
            
            var roundedBounds = Rectangle.Round(gp.GetBounds());

            var gpWidth = roundedBounds.Width;
            gpWidth = gpWidth > 0 ? gpWidth : 1;

            var gpHeight = roundedBounds.Height;
            gpHeight = gpHeight > 0 ? gpHeight : 1;

            var _rendered = new Bitmap(gpWidth, gpHeight);

            var chunkX = -roundedBounds.X;
            var chunkY = -roundedBounds.Y;
            
            using (var g = Graphics.FromImage(_rendered))
            {
                // foreach (var transform in _transforms)
                // {
                //     var child = GetChildren()[transform[0]];
                //     var bitmap = child.Render();
                //     
                //     // Why this doesn't work?
                //     // var matrix = transform[1] == 65535 ? 
                //     //     new Matrix(-1, 0, 0, 1, chunkX, chunkY) : 
                //     //     SWF.GetMatrix(transform[1]);
                //     // gp.Transform(matrix); // Понял, надо применять не на текущий, а на gp детей
                //     
                //     gp.Transform(new Matrix(1, 0, 0, 1, chunkX, chunkY));  // Действует на полигоны
                //     g.SetClip(gp);
                //     // Я картинку подгружал очень много раз, каждый transform, так что надо вынести рисование картинки за этот цикл, пока что его отключу
                // }

                foreach (var bitmap in GetChildren().Select(child => child.Render()))
                {
                    gp.Transform(new Matrix(1, 0, 0, 1, chunkX, chunkY));  // Действует на полигоны
                    g.SetClip(gp);
                    g.DrawImage(bitmap, 0, 0); // действует только на картинку
                }
            }

            return _rendered;
        }

        public override List<SWFObject> GetChildren()
        {
            if (_children != null) return _children;
            _children = new List<SWFObject>();
            
            foreach (var bind in _binds)
            {
                SWFObject bindObject = null;

                var shape = SWF.GetShapes().FindLast(
                    swfObject => swfObject.GetId() == bind.Id
                );
                var movieClip = SWF.GetMovieClips().FindLast(
                    swfObject => swfObject.GetId() == bind.Id
                );
                var textField = SWF.GetTextFields().FindLast(
                    swfObject => swfObject.GetId() == bind.Id
                );

                if (!(shape is null))
                    bindObject = shape;
                else if (!(movieClip is null))
                    bindObject = movieClip;
                else if (!(textField is null))
                    bindObject = textField;

                if (bindObject is null) continue;

                bindObject.SetName(bind.Name);

                _children.Add(bindObject);
            }

            return _children;
        }

        public override string GetDataTypeName()
        {
            return "Movieclip";
        }
    }
}