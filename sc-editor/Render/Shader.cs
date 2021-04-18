using System;
using System.IO;
using System.Text;
using OpenTK.Graphics.OpenGL;

namespace sc_editor.Render
{
    public class Shader
    {
        public int Handle;
        
        private int _fragmentShader;
        private int _vertexShader;

        private bool _disposedValue;
        

        public Shader(string vertexPath, string fragmentPath)
        {
            // load the source code from the individual shader files
            string VertexShaderSource;

            using (var reader = new StreamReader(vertexPath, Encoding.UTF8))
            {
                VertexShaderSource = reader.ReadToEnd();
            }

            string FragmentShaderSource;

            using (var reader = new StreamReader(fragmentPath, Encoding.UTF8))
            {
                FragmentShaderSource = reader.ReadToEnd();
            }
            
            // generate our shaders, and bind the source code to the shaders
            _vertexShader = GL.CreateShader(ShaderType.VertexShader);
            GL.ShaderSource(_vertexShader, VertexShaderSource);

            _fragmentShader = GL.CreateShader(ShaderType.FragmentShader);
            GL.ShaderSource(_fragmentShader, FragmentShaderSource);
            
            // compile the shaders and check for errors
            GL.CompileShader(_vertexShader);

            var infoLogVert = GL.GetShaderInfoLog(_vertexShader);
            if (infoLogVert != string.Empty)
                Console.WriteLine(infoLogVert);

            GL.CompileShader(_fragmentShader);

            var infoLogFrag = GL.GetShaderInfoLog(_fragmentShader);

            if (infoLogFrag != string.Empty)
                Console.WriteLine(infoLogFrag);
            
            // link shaders together into a program
            Handle = GL.CreateProgram();

            GL.AttachShader(Handle, _vertexShader);
            GL.AttachShader(Handle, _fragmentShader);

            GL.LinkProgram(Handle);
            
            // We should do a little cleanup.
            // The individual vertex and fragment shaders are useless now that they've been linked
            GL.DetachShader(Handle, _vertexShader);
            GL.DetachShader(Handle, _fragmentShader);
            GL.DeleteShader(_fragmentShader);
            GL.DeleteShader(_vertexShader);
        }
        
        public int GetAttribLocation(string attribName)
        {
            return GL.GetAttribLocation(Handle, attribName);
        }

        public void Use()
        {
            GL.UseProgram(Handle);
        }
        
        protected virtual void Dispose(bool disposing)
        {
            if (_disposedValue) return;
            GL.DeleteProgram(Handle);

            _disposedValue = true;
        }

        ~Shader() { }


        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }
    }
}