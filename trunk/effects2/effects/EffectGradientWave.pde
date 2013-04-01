class EffectGradientWave extends Effect {
  int temp[]=new int[width*height];
  EffectGradientWave(int width, int height) {super(width,height);}
  PImage end(boolean complement) {
  float angle = 0; 
  float px = 0, py = 0;
  float amplitude = 20;
  float frequency = 0;
  float fillGap = 2.5;
    for (int i =0; i < height; i++){
    // Reset angle to 0, so waves stack properly
    angle = 0;
    // Increasing frequency causes more gaps
    frequency+=.006;
    for (float j=0; j<width; j++){
      py = i+sin(radians(angle))*amplitude;
      angle+=frequency;
      int c =  color(abs(py-i)*255/amplitude, 255-abs(py-i)*255/amplitude, j*(255.0/(width+50)));
      if (py<0 || py>=width) continue;
      int z=int(j) + int(py)*width;
      temp[z] = c;
      // Hack to fill gaps. Raise value of fillGap if you increase frequency
      for (int filler = 0; filler<fillGap; filler++) {
        z=int(j-filler)+(int(py)-filler)*width;
        if (z>=0 && z<width*height)
        temp[z]=c;
        z=int(j+filler) + (int(py)+filler)*width;
        if (z>=0 && z<width*height)
        temp[z]=c;
      }
    }
  }
  for (int i=0; i<width*height; i++) {
      if (pg.pixels[i]!=0xff000000 ^ complement)
        pg.pixels[i] = temp[i];
      else if (!complement) pg.pixels[i]=0;
    }
    pg.updatePixels();
    pg.endDraw();
    pg.popStyle();
    return pg;
  }
}
