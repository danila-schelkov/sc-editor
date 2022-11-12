#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec2 texCoord;
in vec4 colorMul;
in vec3 colorAdd;

void main()
{
    vec4 color = texture2D(TEX_SAMPLER, texCoord) * colorMul;
    color.rgb += colorAdd * color.a;
    gl_FragColor = color;
}
