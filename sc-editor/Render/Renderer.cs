using System.Drawing;
using OpenTK;
using OpenTK.Graphics.OpenGL;

namespace sc_editor.Render
{
    public class Renderer
    {
        private GLControl glControl;
        
        private int _vertexBufferObject;
        public Shader _shader;

        public Renderer(GLControl glControl)
        {
            this.glControl = glControl;
        }

        public void Init()
        {
            _shader = new Shader("vertex.glsl", "fragment.glsl");
            GL.Enable(EnableCap.DepthTest);
            
            _vertexBufferObject = GL.GenBuffer();
            GL.BindVertexArray(_vertexBufferObject);
            GL.BindBuffer(BufferTarget.ArrayBuffer, _vertexBufferObject);
            
            GL.VertexAttribPointer(0, 3, VertexAttribPointerType.Float, false, 3 * sizeof(float), 0);
            GL.EnableVertexAttribArray(0);
            
            GL.ClearColor(Color.MidnightBlue);
        }

        public void ApplyAffineMatrix(ref float[] vertices, Matrix2x3 matrix)
        {
            for (var i = 0; i < vertices.Length / 3; i++)
            {
                var x = vertices[i*3];
                var y = vertices[i*3+1];
                vertices[i*3] = x * matrix.M11 + y * matrix.M12 + matrix.M13;  // v0, v2, v4
                vertices[i*3+1] = x * matrix.M21 + y * matrix.M22 + matrix.M23;  // v1, v3, v5
            }
            GL.BufferData(BufferTarget.ArrayBuffer, vertices.Length * sizeof(float), vertices, BufferUsageHint.StaticDraw);
        }

        public void Render(float[] vertices, Matrix2x3 affineTransform = new Matrix2x3())
        {
            GL.Clear(ClearBufferMask.ColorBufferBit | ClearBufferMask.DepthBufferBit);
            
            _shader.Use();
            var vertexColorLocation = GL.GetUniformLocation(_shader.Handle, "ourColor");
            GL.Uniform4(vertexColorLocation, 0.8f, 0.8f, 0.8f, 1);
            
            // var projection = Matrix4.CreatePerspectiveFieldOfView(
            //     MathHelper.PiOver4, 
            //     (float) glControl.Size.Width / glControl.Size.Height, 
            //     1, 
            //     64);
            // var view = Matrix4.LookAt(4, 3, 3, 0, 0, 0, 0, 1, 0);
            // var model = Matrix4.Identity;
            // var transform = projection * view * model;
            //
            // var transformLocation = GL.GetUniformLocation(_shader.Handle, "transform");
            // GL.UniformMatrix4(transformLocation, true, ref transform);
            
            GL.BufferData(BufferTarget.ArrayBuffer, vertices.Length * sizeof(float), vertices, BufferUsageHint.StaticDraw);
            GL.DrawArrays(PrimitiveType.Polygon, 0, vertices.Length/3);
            glControl.SwapBuffers();
        }
    }
}