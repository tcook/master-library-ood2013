/**
 * Fire Cube demo effect
 * by luis2048.
 * 
 * A rotating wireframe cube with flames rising up the screen.
 * The fire effect has been used quite often for oldskool demos.
 * First you create a palette of 256 colors ranging from red to 
 * yellow (including black). For every frame, calculate each row 
 * of pixels based on the two rows below it: The value of each pixel, 
 * becomes the sum of the 3 pixels below it (one directly below, one 
 * to the left, and one to the right), and one pixel directly two 
 * rows below it. Then divide the sum so that the fire dies out as 
 * it rises.
 */
class EffectFire extends Effect {   
// This will contain the pixels used to calculate the fire effect
int[][] fire;

// Flame colors
final /*static*/ color[] palette= new color[255];
float angle;
int[] calc1,calc2,calc3,calc4,calc5;
/*static*/ boolean initialized;
void init() {
  pushStyle();
  colorMode(HSB);
  // Generate the palette
  for(int x = 0; x < palette.length; x++) {
    //Hue goes from 0 to 85: red to yellow
    //Saturation is always the maximum: 255
    //Lightness is 0..255 for x=0..128, and 255 for x=128..255
    palette[x] = color(x/3, 255, constrain(x*3, 0, 255));
  }
  popStyle();
  calc1 = new int[width];
  calc3 = new int[width];
  calc4 = new int[width];
  calc2 = new int[height];
  calc5 = new int[height];
  // Precalculate which pixel values to add during animation loop
  // this speeds up the effect by 10fps
  for (int x = 0; x < width; x++) {
    calc1[x] = x % width;
    calc3[x] = (x - 1 + width) % width;
    calc4[x] = (x + 1) % width;
  }
  
  for(int y = 0; y < height; y++) {
    calc2[y] = (y + 1) % height;
    calc5[y] = (y + 2) % height;
  }
  initialized=true;
}
  EffectFire(int width, int height) {
    pg = createGraphics(width, height,P2D);
    this.width=width; this.height=height;
    fire = new int[width][height];
    if (!initialized) init();
  }
  PImage end(boolean complement) {
    angle = angle + 0.05;
    // Randomize the bottom row of the fire buffer
    for(int x = 0; x < width; x++) {
      fire[x][height-1] = int(random(0,190)) ;
    }
  
    int counter = 0;
    // Do the fire calculations for every pixel, from top to bottom
    for (int y = 0; y < height; y++) {
      for(int x = 0; x < width; x++) {
        // Add pixel values around current pixel
        fire[x][y] =
            ((fire[calc3[x]][calc2[y]]
            + fire[calc1[x]][calc2[y]]
            + fire[calc4[x]][calc2[y]]
            + fire[calc1[x]][calc5[y]]) << 5) / 129;
            
        // Output everything to screen using our palette colors
        if (pg.pixels[counter]!=0xff000 ^ complement)  
            pg.pixels[counter] = palette[fire[x][y]];
        else if (!complement) pg.pixels[counter] = 0;
  
        // Extract the red value using right shift and bit mask 
        // equivalent of red(pg.pixels[x+y*w])
        if ((pg.pixels[counter++] >> 16 & 0xFF) == 128) {
          // Only map 3D cube 'lit' pixels onto fire array needed for next frame
          fire[x][y] = 128;
        }
      }
   }
    pg.updatePixels();
    pg.endDraw();
    pg.popStyle();
    return pg;
  }
}
