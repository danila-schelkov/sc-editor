using System;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Imaging;
using System.Windows.Forms;
using OpenTK;
using OpenTK.Graphics.OpenGL;
using sc_editor.Render;
using sc_editor.SupercellSWF;

namespace sc_editor
{
    public partial class Form1 : Form
    {
        private int _vertexBufferObject;
        private Shader _shader;
        private SupercellSWF.SupercellSWF _decoder;
        private Renderer _renderer;

        public Form1()
        {
            InitializeComponent();

            // closeToolStripMenuItem_Click(null, EventArgs.Empty);
            _renderer = new Renderer(glControl);
        }
        
        protected override void OnLoad(EventArgs e)
        {
            base.OnLoad(e);
            
            _renderer.Init();
            glControl.SwapBuffers();
            
            Application.Idle += Application_Idle;
            glControl.Resize += glControl_Resize;
        }

        private void Application_Idle(object sender, EventArgs e)
        {
            while (glControl.IsIdle)
            {
                OnRenderFrame();
            }
        }

        private void OnRenderFrame()
        {
            // if (treeView1.SelectedNode == null) return;
            var vertices = new []
            {
                -0.5f, -0.5f, 0.0f, //Bottom-left vertex
                0.5f, -0.5f, 0.0f, //Bottom-right vertex
                0.0f,  0.5f, 0.0f  //Top vertex
            };
            _renderer.Render(vertices);
        }

        private void glControl_Resize(object sender, EventArgs e)
        {
            if (!(sender is GLControl c)) return;
            
            if (c.ClientSize.Height == 0)
                c.ClientSize = new Size(c.ClientSize.Width, 1);

            GL.Viewport(c.ClientSize);
            
            var aspectRatio = Width / (float)Height;
            var perspective = Matrix4.CreatePerspectiveFieldOfView(MathHelper.PiOver4, aspectRatio, 1, 64);
            GL.MatrixMode(MatrixMode.Projection);
            GL.LoadMatrix(ref perspective);
        }


        private void RenderMenu()
        {
            treeView1.Nodes.Clear();
            if (exportsEnabled.Checked)
                treeView1.Populate(_decoder.GetExports(), "Exports");
            if (shapesEnabled.Checked)
                treeView1.Populate(_decoder.GetShapes());
            if (movieClipsEnabled.Checked)
                treeView1.Populate(_decoder.GetMovieClips());
            if (texturesEnabled.Checked)
                treeView1.Populate(_decoder.GetTextures());
            if (textFieldsEnabled.Checked)
                treeView1.Populate(_decoder.GetTextFields());
        }

        #region File Menu
        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            var dialog = new OpenFileDialog
            {
                Filter = @"Supercell SWF (*.sc)|*.sc|All files (*.*)|*.*", 
                FilterIndex = 1, 
                RestoreDirectory = true
            };

            var result = dialog.ShowDialog();

            if (result != DialogResult.OK) return;
            var path = dialog.FileName;
            
            // TODO: сделать диалоговое окно по типу "хотите сохранить изменения в файле?"
            closeToolStripMenuItem_Click(sender, EventArgs.Empty);
            sectionsToolStripMenuItem.Enabled = true;

            _decoder.Load(path);
            RenderMenu();
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            var dialog = new SaveFileDialog();
            var result = dialog.ShowDialog();

            if (result != DialogResult.OK) return;
            
            var path = dialog.FileName;
            // Compression.Compress(
            //     _decompressed, 
            //     path, 
            //     Compression.Signature.Sc
            // );
        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            _decoder = new SupercellSWF.SupercellSWF();
            treeView1.Nodes.Clear();

            sectionsToolStripMenuItem.Enabled = false;
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            closeToolStripMenuItem_Click(sender, EventArgs.Empty);
            Application.Exit();
        }
        #endregion

        #region View Menu
        
        private void ChangeSectionsToView(object sender, EventArgs e)
        {
            RenderMenu();
        }
        
        private void saveImage_Click(object sender, EventArgs e)
        {
            throw new NotImplementedException();
            // if (pictureBox1.Image == null) return;
            //
            // var dialog = new SaveFileDialog
            // {
            //     Filter = @"Image Files(*.Png;*.JPG;*.GIF)|*.png;*.JPG;*.GIF|All files (*.*)|*.*", 
            //     FilterIndex = 1, 
            //     RestoreDirectory = true,
            //     FileName = treeView1.SelectedNode.Name
            // };
            // var result = dialog.ShowDialog();
            //
            // if (result != DialogResult.OK) return;
            //
            // var path = dialog.FileName;
            // pictureBox1.Image.Save(path);
        }

        #endregion

        private void treeView1_AfterSelect(object sender, TreeViewEventArgs e)
        {
            var node = treeView1.SelectedNode;
            var data = node.Tag;

            if (data == null) return;
            Debug.WriteLine(node.Text);

            float maxWidth = 0;
            float maxHeight = 0;
            float[] vertices = null;
            if (data.GetType() == typeof(ShapeDrawBitmapCommand))
            {
                var sheetPoints = ((ShapeDrawBitmapCommand) data).SheetPoints;
                vertices = new float[sheetPoints.Count * 3];
                for (var i = 0; i < sheetPoints.Count; i++)
                {
                    var x = sheetPoints[i].X;
                    var y = sheetPoints[i].Y;

                    if (Math.Abs(x) > maxWidth) maxWidth = Math.Abs(x);
                    if (Math.Abs(y) > maxHeight) maxHeight = Math.Abs(y);
                    
                    vertices[i * 3] = x;
                    vertices[i * 3 + 1] = y;
                    vertices[i * 3 + 2] = 0f;
                }

                var aspectRatio = (float) glControl.Size.Width / glControl.Size.Height;
                _renderer.ApplyAffineMatrix(ref vertices, new Matrix2x3(
                    1f, 0, 0,
                    0, 1f, 0
                ));
            } 
            else if (data.GetType() == typeof(SWFTexture))
            {
                vertices = new []
                {
                    0.5f,  0.5f, 0.0f,  // top right
                    0.5f, -0.5f, 0.0f,  // bottom right
                    -0.5f, -0.5f, 0.0f,  // bottom left
                    -0.5f,  0.5f, 0.0f   // top left
                };
            }

            if (vertices == null) return;
            _renderer.Render(vertices);
            glControl.SwapBuffers();
        }
    }
}