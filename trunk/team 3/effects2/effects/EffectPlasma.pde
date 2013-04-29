/**
 * Plasma Demo Effect
 * by luis2048. 
 * extended bobcgausa
 * Cycles of changing colours warped to give an illusion 
 * of liquid, organic movement.Colors are the sum of sine 
 * functions and various formulas. Based on formula by Robert Klep. 
 */
 class EffectPlasma extends Effect {
   final int pixelSize=2;
   EffectPlasma(int width, int height) {super(width,height);}
   PImage end(boolean complement) {
    pg.colorMode(HSB,255);
    float  xc = 25;
    final float calculation1 = sin( radians(frameCount * 0.61655617));
    final float calculation2 = sin( radians(frameCount * -3.6352262));
      // Plasma algorithm
  for (int x = 0; x < pg.width; x++, xc += pixelSize)
  {
    float  yc    = 25;
    float s1 = 21 * sin(radians(xc) * calculation1 );
    int z=0;
    for (int y = 0; y < pg.height; y++, yc += pixelSize)
    {
      float s2 = 21 * sin(radians(yc) * calculation2 );
      float s3 = 21 * sin(radians((xc + yc + frameCount * 5) / 2));  
      float s  = (s1+ s2 + s3 + 64);
      if (pg.pixels[x+z]!=0xff000000 ^ complement)
      pg.pixels[x+z] = color(s+s, 255 - s, 255);
      else if (!complement) pg.pixels[x+z]=0;
      z+=pg.width;
    }
  }   
  pg.updatePixels();
  pg.endDraw();
  pg.popStyle();
  return pg;
  }
}
