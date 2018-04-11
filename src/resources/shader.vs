#version 130

uniform vec2 scale;
uniform vec2 offset;
uniform vec2 texScale;
uniform vec2 texOffset;

in vec2 position;
out vec2 vTexCoords;

void main() {
    vec2 flippedPos = vec2(position.x, 1.0 - position.y);
    vTexCoords = (flippedPos + texOffset) * texScale;
    gl_Position = vec4((position + offset) * scale - vec2(1.0,1.0), 0.0, 1.0);
}