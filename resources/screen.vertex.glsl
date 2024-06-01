#version 330 core

layout (location = 0) in vec2 aPos;
layout (location = 1) in vec2 aTexCoords;
// TODO: remove unused layouts
layout (location = 2) in vec4 aColorMul;
layout (location = 3) in vec3 aColorAdd;

uniform mat4 pmv;

out vec2 texCoord;

void main()
{
    gl_Position = vec4(aPos, 0.0, 1.0);
    texCoord = aTexCoords;
}