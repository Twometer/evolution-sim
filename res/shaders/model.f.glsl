#version 330 core

in vec3 fragmentPosition;
in vec3 fragmentColor;
in vec3 fragmentNormal;

out vec4 color;

const vec3 lightPos = vec3(0.0f, 5.0f, 0.0f);
const vec3 lightColor = vec3(1.0, 1.0, 1.0);

void main(void) {
    float ambientStrength = 0.3;
    vec3 ambient = ambientStrength * lightColor;
    vec3 norm = normalize(fragmentNormal);
    vec3 lightDir = normalize(lightPos - fragmentPosition);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;
    vec3 result = (ambient + diffuse) * fragmentColor;
    color = vec4(result, 1.0);
}