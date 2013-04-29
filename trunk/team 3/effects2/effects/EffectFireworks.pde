
class EffectFireworks extends Effect {
    float r = random(30, 100); //sets the maximum radius
    ArrayList listA = new ArrayList();
    boolean init;
    
    EffectFireworks(int width, int height) {
        super(width, height);
    }
    
    @Override
        void begin() {
        pg.beginDraw();
        pg.loadPixels();
        if (!init) {
            init=true; 
            pg.background(0);
        }
        pg.pushStyle();
        pg.smooth();
    }
    
    PImage end(boolean complement) {
        return end();
    }
    
    PImage end() {
        pg.colorMode(HSB, 100);
        if (frameCount % 6 == 0) {//fireworks gradually dissappear
            pg.noStroke();
            pg.fill(0.0, 10.0);
            pg.rect(0, 0, width, height);
        }
        
        if (random(0, 10)>5.5) {
            listA.add(new Senrin());
        } 
        
        if (random(0, 10)>5.5) {
            listA.add(new Ring());
        } 
        
        if (random(0, 10)>5.5) {
            listA.add(new Hanabi());
        } 

        for (int counter = 0; counter < listA.size(); counter++) {
            Hanabi h = (Hanabi)(listA.get(counter));
            if (h.isAlive()) {
                h.update();
                //if(h.explosionSound()){
                //  snip = minim.loadSnippet("ban.wav");
                //  snip.play();
                //}
            }
            else {
                h.removeBoom();
                listA.remove(counter);
                counter--;
            }
        } //for   
        pg.updatePixels();
        pg.endDraw();
        pg.popStyle();
        return pg;
    }

    class Hanabi {
        float x, curY, gravity, radius, curr;  
        float v, accel;
        color rainbow;
        boolean exploding, justExploded;

        Hanabi() {
            x = random(width/6, width*5/6);
            curY = height;
            curr= 1;
            radius = random(70, 300);
            rainbow = color(random(0, 100), 0, 100); //99
            exploding = false;
            justExploded = false;

            v = -random(5, 15);
            accel = random(0, 0.2);
        }

        void update() {
            generateColor();
            v += accel;
            curY += v;
            if (v < 0 && !exploding) {
                risingSeed();
            }
            else {
                accel = 0.05;
                explosion();
                exploding = true;
            }
        }

        boolean explosionSound() {
            if (justExploded == false && exploding == true) {
                justExploded = true;
                return true;
            }
            else 
                return false;
        }

        void generateColor() {//generates gradual change in color
            float s = random(10, 240);
            float h = hue(rainbow);
            float a ;
            h+=1;
            if (exploding) {
                a = 40 -v*10;
            }
            else {
                a = v*10 + 100;
            }
            rainbow = color(h, s, 100, a);
        }

        void risingSeed() {
            drawSparkles();
        }

        void explosion() {//creates a circle of circles
            pg.pushMatrix();
            pg.translate(x, curY);
            pg.noStroke();
            pg.fill(rainbow);
            if (curr < radius) {
                curr+=2;
            }
            float sRadius = random(1, 6);
            for (int deg = 0; deg < 360; deg += 12) {
                float angle = radians(deg);
                float tempx =(cos(angle)* curr);
                float tempy = (sin(angle) * curr);
                pg.ellipse(tempx, tempy, sRadius, sRadius);
            }
            pg.popMatrix();
        }

        void drawSparkles() {//draws sparkles (for when mouse not pressed)
            pg.pushMatrix();
            pg.stroke(rainbow, 50);
            pg.translate(x, curY);
            pg.strokeWeight(1);
            pg.noFill();
            pg.ellipse(0, 0, 10, 10);
            float randX = random(-30, 30);
            float randY = random(-30, 30);
            float randL = random(1, 5);
            drawPlus(randX, randY, randL);
            pg.popMatrix();
        }

        void drawPlus(float x, float y, float l) {//draws plus signs in different angles
            pg.rotate(random(0, PI));
            pg.line(x-l, y, x+l, y);
            pg.line(x, y-l, x, y+l);
        }

        boolean isAlive() {
            if (1.0 - v * 10.0 < 0.0)
                return false;
            return true;
        }
        void removeBoom(){
            pg.pushMatrix();
            this.rainbow = color(0, 0, 0, 0);
            pg.noStroke();
            pg.noFill();
            pg.popMatrix();
        }
    }
    
    class Ring extends Hanabi {
        float angleX, angleY, velX;
        int interval;
        Ring() {
            super();
            angleX = random(0.9, 1);
            angleY = random(0.7, 1);
            curr = radius/2; 
            velX = random(-6, 6);
        }

        void update() {
            if (interval>3) {
                v += accel;
                x +=velX;
                curY += v;
                
                if (v <0 && !exploding) {
                    generateColor();
                    risingSeed();
                }
                else {
                    accel = 0.05;
                    explosion();
                    exploding = true;
                    generateColor();
                }
                
                interval = 0;
            }
            else
                interval++;
        }

        void generateColor() {
            super.generateColor();
            float a;
            
            if (exploding) {
                a =20 -v*10;
            }
            else {
                a = v*10 + 100;
            }
            
            rainbow = color(hue(rainbow), saturation(rainbow), 200, a);
        }

        void explosion() {//creates a circle of circles
            pg.pushMatrix();
            pg.translate(x, curY);
            pg.noStroke();
            pg.fill(rainbow);
            
            if (curr < radius) {
                curr = curr +(radius - curr) * 0.1;
            }
            
            float sRadius = random(8, 15);
            pg.rotate(random(-5, 5));
            
            for (int deg = 0; deg < 360; deg += 20) {
                float angle = radians(deg);
                float tempx =(cos(angle)* curr * angleX);
                float tempy = (sin(angle) * curr * angleY);
                pg.ellipse(tempx, tempy, sRadius, sRadius);
            }
            
            pg.popMatrix();
        }
    }

    class Senrin extends Hanabi {
        float[][] position;
        float myHue, overall, velX;

        Senrin() {
            super();
            overall = random(70, 150);
            position = new float[int(random(3, 10))][2];
            
            for (int r = 0; r < position.length; r++) {
                for (int s = 0; s < position[r].length ; s++) {
                    position[r][s] = random(-overall, overall);
                }
            }
            
            curr=0.1;
            radius = random(70, 120);
            myHue = random(0, 255);
            velX = random(-1.5, 1.5);
        }
        
        void update() {
            x += velX;
            super.update();

            if (exploding) {
                for (int r = 0; r < position.length; r++) {
                    for (int s = 0; s < position[r].length ; s++) {
                        explosion(position[r][0], position [r][1]);
                    }
                }
            }
        }
        
        void generateColor() {
            super.generateColor();
            float s, a;
            
            if (exploding) {
                a =20 -v*10;
                s = 200 - v*40;
            }
            else {
                s = 200;
                a = v*10 + 100;
            }
            
            rainbow = color(myHue, s, 255, a);
        }

        void explosion(float myX, float myY) {
            pg.pushMatrix();
            pg.translate(x+myX, curY+myY);
            pg.noStroke();
            pg.fill(rainbow);
            
            if (curr < radius) {
                curr = curr +(radius - curr) * 0.00000001;
            }
            
            float sRadius = random(1, 3);
            
            for (int deg = 0; deg < 360; deg += 10) {
                float angle = radians(deg);
                float tempx =(cos(angle)* curr);
                float tempy = (sin(angle) * curr);
                pg.ellipse(tempx, tempy, sRadius, sRadius);
            }
            
            pg.popMatrix();
        }
    }
}

