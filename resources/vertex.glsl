#version 330 core

layout (location = 0) in vec2 aPos;

uniform mat4 pmv;
uniform float scale;

void main()
{
    gl_Position = pmv * vec4(aPos * scale, 0.0f, 1.0);
}