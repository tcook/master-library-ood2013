class EffectGradientLinear extends Effect {
  int color1=0xff0000, color2=0xff; boolean xaxis;
  EffectGradientLinear(int width, int height) {super(width,height);}
  void begin(color c1, color c2, boolean xaxis) {
    super.begin();
    color1=c1; color2=c2;
    this.xaxis=xaxis;
  }
  PImage end(boolean complement) {
    setGradient(color1, color2, complement);
    pg.updatePixels();
    pg.endDraw();
    pg.popStyle();
    return pg;
  }
  void setGradient(color c1, color c2, boolean complement) {
    final int x=0; final int y=0; final int w=width; final int h=height;
    // calculate differences between color components 
    float deltaR = red(c2)-red(c1);
    float deltaG = green(c2)-green(c1);
    float deltaB = blue(c2)-blue(c1);
  
    // choose axis
    if(xaxis) {
      // nested for loops set pixels
      // in a basic table structure
      // column
      for (int i=x; i<(x+w); i++){
        // row
        for (int j = y; j<(y+h); j++){
          color c = color((red(c1)+(j-y)*(deltaR/h)),(green(c1)+(j-y)*(deltaG/h)),(blue(c1)+(j-y)*(deltaB/h)) );
          if (pg.pixels[i+j*h]!=0xff000000 ^ complement)
            pg.pixels[i+j*h] = c;
          else if (!complement) pg.pixels[i+j*h]=0;
        }
      }  
    }  
    else {
      // column 
      for (int i=y; i<(y+h); i++){
        // row
        for (int j = x; j<(x+w); j++){
          color c = color((red(c1)+(j-x)*(deltaR/h)),(green(c1)+(j-x)*(deltaG/h)),(blue(c1)+(j-x)*(deltaB/h)));
          if (pg.pixels[j+i*h]!=0xff000000 ^ complement)
            pg.pixels[j+i*h] = c;
          else if (!complement) pg.pixels[j+i*h]=0;
        } // for j
      }  // for i
    }
  }
}
