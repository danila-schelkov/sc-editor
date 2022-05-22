#version 330 core

layout (location = 0) in vec2 aPos;
layout (location = 1) in vec2 aTexCoord;

uniform mat4 pmv;

out vec2 texCoord;
out vec4 colorMul;
out vec3 colorAdd;

void main()
{
    gl_Position = pmv * vec4(aPos, 0.0f, 1.0f);
    texCoord = aTexCoord;
    colorMul = vec4(1.0f, 1.0f, 1.0f, 1.0f);
    colorAdd = vec3(0.0f, 0.0f, 0.0f);
}