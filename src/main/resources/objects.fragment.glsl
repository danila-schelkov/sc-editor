#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec2 texCoord;
in vec4 colorMul;
in vec3 colorAdd;

out vec4 fragColor;

void main() {
    vec4 sample = texture(TEX_SAMPLER, texCoord);
    vec4 color = sample * colorMul;
    color.rgb += colorAdd * color.a;
    fragColor = vec4(color.rgb * colorMul.a, color.a);
}
