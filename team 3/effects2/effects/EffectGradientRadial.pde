class EffectGradientRadial extends Effect {
  float radius=20, x, y;
  int color1=0xff0000, color2=0xff;
  int temp[]=new int[width*height];
  EffectGradientRadial(int width, int height) {super(width,height);}
  void begin(float x, float y, float radius, color c1, color c2) {
    super.begin();
    pg.background(200);
    this.x=x; this.y=y; this.radius=radius; color1=c1; color2=c2;
  }
  PImage end(boolean complement) {
    float px = 0, py = 0, angle = 0;
  // calculate differences between color components 
  float deltaR = red(color2)-red(color1);
  float deltaG = green(color2)-green(color1);
  float deltaB = blue(color2)-blue(color1);
  // hack to ensure there are no holes in gradient
  // needs to be increased, as radius increases
  float gapFiller = 8.0;

  for (int i=0; i< radius; i++){
    for (float j=0; j<360; j+=1.0/gapFiller){
      px = x+cos(radians(angle))*i;
      py = y+sin(radians(angle))*i;
      angle+=1.0/gapFiller;
      int z=int(px) + pg.width*int(py);
      if (z<0 || z>=width*height) continue;
      color c = color(
      (red(color1)+(i)*(deltaR/radius)),
      (green(color1)+(i)*(deltaG/radius)),
      (blue(color1)+(i)*(deltaB/radius)) 
        );
      temp[z]=c;      
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
