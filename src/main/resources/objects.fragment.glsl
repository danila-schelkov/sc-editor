#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec2 texCoord;
in vec4 colorMul;
in vec3 colorAdd;

void main()
{
    vec4 sample = texture2D(TEX_SAMPLER, texCoord);
    vec4 color = sample * colorMul;
    color.rgb += colorAdd * color.a;
    gl_FragColor = vec4(color.rgb * colorMul.a, color.a);
}
