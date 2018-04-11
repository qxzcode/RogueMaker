#version 130

uniform sampler2D tex;
uniform vec3 fgColor;
uniform vec3 bgColor;

in vec2 vTexCoords;
out vec4 color;

void main() {
    color = vec4(mix(bgColor, fgColor, texture(tex, vTexCoords).r), 1.0);
}