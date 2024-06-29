#version 330 core

layout (location = 0) in vec2 aPos;
layout (location = 1) in vec2 aTexCoord;
layout (location = 2) in vec4 aColorMul;
layout (location = 3) in vec3 aColorAdd;

uniform mat4 pmv;

out vec2 texCoord;
out vec4 colorMul;
out vec3 colorAdd;

void main()
{
    gl_Position = pmv * vec4(aPos, 0.0f, 1.0f);
    texCoord = aTexCoord;
    colorMul = aColorMul;
    colorAdd = aColorAdd;
}