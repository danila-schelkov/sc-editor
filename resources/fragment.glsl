#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec2 texCoord;

out vec4 color;

void main() {
    color = texture(TEX_SAMPLER, texCoord);
//    color = vec4(texCoord, 0, 1);
}
