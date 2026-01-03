#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    fragColor = texture(TEX_SAMPLER, texCoord);
}
