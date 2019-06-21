#version 330 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 vertexColor;
layout(location = 2) in vec3 vertexNormal;

out vec3 fragmentPosition;
out vec3 fragmentColor;
out vec3 fragmentNormal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertexPosition, 1.0);
    fragmentPosition = vec3(modelMatrix * vec4(vertexPosition, 1.0));
    fragmentColor = vertexColor;
    fragmentNormal = vertexNormal;
}