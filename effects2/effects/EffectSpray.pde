class EffectSpray extends Effect {
    int rgb, fill, stroke, blur;
    boolean fillb, strokeb;
    EffectSpray(int width, int height) {
        super(width, height);
    }
    void begin(color c1, int blurSize) {
        blur=blurSize;
        pg.beginDraw();
        pg.loadPixels();
        pg.background(255, 255, 255, 0);
        pg.pushStyle();
        pg.noSmooth();
        rgb=c1;
    }
    @Override
        void rect(int x, int y, int width, int height) {
        pg.fill(rgb>>16, (rgb>>8)&0xff, rgb&0xff);
        pg.rect(x, y, width, height);
        pg.filter(BLUR, blur);
        if (!strokeb) pg.noStroke();
        else pg.stroke(stroke>>16, (stroke>>8)&0xff, stroke&0xff);
        if (!fillb) pg.noFill();
        else pg.fill(fill>>16, (fill>>8)&0xff, fill&0xff);
        pg.rect(x, y, width, height);
    }
    PImage end() {
        /*for (int i=0; i<width*height; i++) {
         if (pg.pixels[i]==0xffffffff) pg.pixels[i]=0;
         }*/
        pg.updatePixels();
        pg.endDraw();
        pg.popStyle();
        return pg;
    }
    PImage end(boolean ignored) { 
        return end();
    }
    @Override
        void fill(int rgb) {
        fillb=true;
        fill=rgb;
    }
    @Override
        void stroke(int rgb) {
        strokeb=true; 
        stroke=rgb;
    }
    @Override
        void background(int rgb) {
        pg.background(rgb>>16, (rgb>>8)&0xff, rgb&0xff);
    }
    @Override
        void noFill() {
        fillb=false;
    }
    @Override
        void noStroke() {
        strokeb=false;
    }
    @Override
        void text(String s, int x, int y) {
            pg.colorMode(RGB, 0xff);
        pg.fill(rgb>>16, (rgb>>8)&0xff, rgb&0xff);
        pg.text(s, x, y);
        pg.filter(BLUR, blur);
        if (!strokeb) 
            pg.noStroke();
        else 
            pg.stroke(stroke>>16, (stroke>>8)&0xff, stroke&0xff);
        if (!fillb) 
            pg.noFill();
        else 
            pg.fill(fill>>16, (fill>>8)&0xff, fill&0xff); 
        pg.text(s, x, y);
    }
    @Override
        void ellipse(int x, int y, int w, int h) {
        pg.fill(rgb>>16, (rgb>>8)&0xff, rgb&0xff);
        pg.ellipse(x, y, width, height);
        pg.filter(BLUR, blur);
        if (!strokeb) pg.noStroke();
        else pg.stroke(stroke>>16, (stroke>>8)&0xff, stroke&0xff);
        if (!fillb) pg.noFill();
        else pg.fill(fill>>16, (fill>>8)&0xff, fill&0xff);
        pg.ellipse(x, y, width, height);
    }
}

