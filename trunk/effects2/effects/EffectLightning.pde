/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/59875*@* */
/* !do not delete the line above, required for linking your tweak if you re-upload */
// **Created by Aparna J. Nambiar
//Displays lightning strokes
class EffectLightning extends Effect {
    Lightning l1, l2; 
    int branchX;
    int branchY;
    int branchTarX;
    int branchTarY;
    int counter;
    int innerCounter;
    int direction;
    boolean init;
    Lightning[] l = new Lightning[20];
    int lightningCount=5;
    int finishCount=0, weight;
    EffectLightning(int width, int height, int weight) {
        super(width, height);
        counter=0;
        this.weight=weight;
        int rootX=(int)random(width/5, width*4/5);
        int rootY=(int)random(0, 5);
        for (int i=0;i<lightningCount;i++) {
            l[i] = new Lightning((int)random(width/5, width*4/5), (int)random(0, 5), weight, 2, 600);
        }
    }
    @Override
        void begin() {
        pg.beginDraw();
        if (!init) {
            init=true; 
            pg.background(0);
        }
        pg.loadPixels();
        pg.pushStyle();
        pg.smooth();
    }
    PImage end(boolean complement) {
        return end();
    }
    PImage end() {
        for (int i=0;i< lightningCount;i++) {
            if (l[i]!=null) {
                l[i].draw();
            }
        }
        counter++;

        if (counter>0 && counter%21==0 && lightningCount<l.length) {
            int chosen = (int) random(0, 3);
            branchX = l[chosen].rootX;
            branchY = l[chosen].rootY;

            l[lightningCount] = new Lightning(branchX, branchY, weight, (int)random(0, 2), 50);
            lightningCount++;
        }

        if (finishCount>=1) {
            pg.updatePixels();
            pg.background(0);
            for (int i=0;i<3;i++) {
                l[i] = new Lightning((int)random(width/5, width*4/5), (int)random(0, 5), weight, 2, 600);
                l[i].draw();
            }

            for (int i=3;i<lightningCount;i++) {
                l[i] = null;
            }
            lightningCount = 3;
            finishCount = 0;
        }   

        pg.endDraw();
        pg.popStyle();
        return pg;
    }

    public class Lightning {
        int rootX; 
        int rootY;
        int targetX;
        int targetY;
        int weight;
        float negDirection, posDirection;
        int localCounter;
        int maxLength;
        public Lightning (int rootx, int rooty, int strokeWeight, int direction, int length) {
            this.rootX= rootx;
            this.rootY = rooty;
            this.weight = strokeWeight;
            maxLength = length;
            localCounter =1;
            if (direction ==2) {
                negDirection =0.5;
                posDirection =0.5;
            }
            else if (direction ==1) {
                negDirection =0;
                posDirection =1;
            }
            else if (direction ==0) {
                negDirection =1;
                posDirection =0;
            }
        }

        public void draw() {
            if (localCounter< maxLength) {   
                localCounter++;
                targetX=rootX+(int)random(-8 * negDirection, 8 * posDirection);
                targetY=rootY+(int)random(0, 5);
                pg.stroke(255);
                pg.strokeWeight(weight);
                pg.line(rootX, rootY, targetX, targetY);
                rootX=targetX;
                rootY=targetY;
            }
            if (targetY>=height) {
                finishCount++;
                localCounter=0;
            }
        }
    }
}

