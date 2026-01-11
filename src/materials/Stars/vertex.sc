$input a_color0, a_position
$output v_color0

#include <bgfx_shader.sh>

uniform vec4 StarsColor;
uniform float u_time;

void main() {
#ifndef INSTANCING
  vec3 pos = a_position;
  vec3 worldPos = mul(u_model[0], vec4(pos, 1.0)).xyz;

  float noise = fract(sin(dot(pos.xy * 5.0, vec2(12.9898, 78.233))) * 43758.5453);
  
  float starMask = step(0.6, noise);
  
  float brightness = pow(noise, 3.0) * starMask;

  // Color variation
  float colorNoise = fract(noise * 1.234); 
  vec3 white = vec3(1.0, 1.0, 1.0);
  vec3 blueish = vec3(0.7, 0.8, 1.0);
  vec3 starColor = mix(white, blueish, colorNoise);

  float twinkle = 0.4 + 0.6 * sin(u_time * 2.0 + pos.x * 10.0);
  
  vec4 color = a_color0;
  color.rgb = starColor * brightness * 2.0 * twinkle;
 
  v_color0 = color;
  gl_Position = mul(u_viewProj, vec4(worldPos, 1.0));
#else
  gl_Position = vec4(0.0,0.0,0.0,0.0);
#endif
}
