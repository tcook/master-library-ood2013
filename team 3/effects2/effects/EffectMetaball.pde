/**
 * Metaball Demo Effect
 * by luis2048. 
 * 
 * Organic-looking n-dimensional objects. The technique for rendering 
 * metaballs was invented by Jim Blinn in the early 1980s. Each metaball 
 * is defined as a function in n-dimensions. 
 */
class EffectMetaball extends Effect {
  int numBlobs, blobSize,c1,c2,c3;

int[] blogPx;
int[] blogPy;

// Movement vector for each blob
int[] blogDx;
int[] blogDy;

int[][] vy,vx;
   //c1,c2,c3   two must be set to 1, the third can be 0-255 but not 1
   EffectMetaball(int width, int height, int nBlobs, int blobSize, int c1,int c2,int c3) {
     super(width,height);
     numBlobs=nBlobs;
     this.blobSize=Math.min(abs(blobSize)+1000,200000);
     this.c1=c1; this.c2=c2; this.c3=c3;
     vy = new int[numBlobs][height];
     vx = new int[numBlobs][width];
     blogPx = new int[numBlobs];
     blogPy = new int[numBlobs];
     blogDx = new int[numBlobs];
     blogDy = new int[numBlobs];
     for (int i=0; i<numBlobs; ++i) {
      blogPx[i]=i*(int)random(60);
      blogPy[i]=i*(int)random(60);
      blogDx[i]=blogDy[i]=1;
     }
   }
   PImage end(boolean complement) {
      for (int i=0; i<numBlobs; ++i) {
    blogPx[i]+=blogDx[i];
    blogPy[i]+=blogDy[i];

    // bounce across screen
    if (blogPx[i] < 0) {
      blogDx[i] = 1;
    }
    if (blogPx[i] > pg.width) {
      blogDx[i] = -1;
    }
    if (blogPy[i] < 0) {
      blogDy[i] = 1;
    }
    if (blogPy[i] > pg.height) {
      blogDy[i]=-1;
    }

    for (int x = 0; x < pg.width; x++) {
      vx[i][x] = int(sq(blogPx[i]-x));
    }

    for (int y = 0; y < pg.height; y++) {
      vy[i][y] = int(sq(blogPy[i]-y)); 
    }
  }

  // Output into a buffered image for reuse
  for (int y = 0; y < pg.height; y++) {
    for (int x = 0; x < pg.width; x++) {
      int m = 1;
      for (int i = 0; i < numBlobs; i++) {
        // Increase this number to make your blobs bigger
        m += blobSize/(vy[i][y] + vx[i][x]+1);
      }
      int c=color(c1==1?m+x:c1, c2!=1?c2:(c1==1?(x+m+y)/2:m+x), c3!=1?c3:(x+m+y)/2);
      int z=x+y*pg.width;
      if (pg.pixels[z]!=0xff000000 ^ complement)
      pg.pixels[z] = c;
      else if (!complement) pg.pixels[z]=0;
    }
  }   
  pg.updatePixels();
  pg.endDraw();
  pg.popStyle();
  return pg;
  }
}
