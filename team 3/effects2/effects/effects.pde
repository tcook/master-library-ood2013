/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/64121*@* */
/* !do not delete the line above, required for linking your tweak if you re-upload */
/* I am a big believer in software reuse.  I took some great examples and made them more useful.
 * The complement argument will apply the effect to any black pixel if true, non-black if false.
 * bobcook47@hotmail.com  feel free to send improvements or additions
 */
EffectFireworks pg;
EffectFire pg1;
EffectFire pg11;
EffectGradientLinear pg2;
EffectGradientLinear pg22;
EffectGradientLinear pg222;
EffectGradientRadial pg3;
EffectGradientRadial pg33;
EffectGradientWave pg4;
EffectGradientWave pg44;
EffectLightning pg5;
EffectMetaball pg6;
EffectMetaball pg66;
EffectParticles pg7;
EffectPlasma pg8;
EffectSpray pg9;
//Hi pg10;

void setup() {
  size(640, 360, P2D);
  hint(ENABLE_STROKE_PURE);
  // Create buffered image for effect
  pg = new EffectFireworks(100,100);
  pg1 = new EffectFire(100,100);
  pg11 = new EffectFire(100,100);
  pg2 = new EffectGradientLinear(100,100);
  pg22 = new EffectGradientLinear(100,100);
  pg222 = new EffectGradientLinear(100,100);
  pg3 = new EffectGradientRadial(100,100);
  pg33 = new EffectGradientRadial(100,100);
  pg4 = new EffectGradientWave(100,100);
  pg44 = new EffectGradientWave(100,100);
  pg5 = new EffectLightning(100,100,4);
  pg6 = new EffectMetaball(100,100,6,20000,1,180,1);
  pg66 = new EffectMetaball(100,100,6,20000,1,180,1);
  pg7 = new EffectParticles(100,100,200,100,18);
  pg8 = new EffectPlasma(100,100);
  pg9 = new EffectSpray(100,100);
}

void draw() {
  PImage Hi1 = loadImage("Hi1.gif");
  PImage Hi2 = loadImage("Hi2.gif");
  PImage Oval = loadImage("Oval.gif");
  // Output into a buffered image for reuse
  
  //Box 1
  pg.begin();
  pg.fill(0xff0000);
  pg.textSize(48);
  pg.text("HI",20,50); 
  // display the results
  image(pg.end(),0,0);
  
  //Box 2
  pg1.begin();
  pg1.fill(0xff0000);
  pg1.textSize(48);
  pg1.text("HI",20,50);
  // display the results
  image(pg1.end(false),100,0);
  
  //Box 3
  pg11.begin();
  //display the results
  image(pg11.end(false),200,0);
  image(Oval, 200, 0);
  
  //Box 4
  pg2.begin(0xff00,0xff,true);
  pg2.fill(0xff0000);
  pg2.textSize(50);
  pg2.text("HI",20,50); 
  // display the results
  image(pg2.end(false),300,0);
  
  //Box 5
  pg22.begin(0xff00,0xff,true);
  // display the results
  image(pg22.end(false),400,0);
  image(Hi1, 400,0);
  
  //Box 6
  pg3.begin(50,50,50,0xff00,0xff);
  // display the results
  image(pg3.end(false),500,0);
  image(Hi2,500,0);
  
  //Box 7
  pg33.begin(50,50,50,0xff00,0xff);
  pg33.fill(0xff0000);
  pg33.textSize(80);
  pg33.text("HI",11,76); 
  // display the results
  image(pg33.end(false),0,100);
  
  //Box 8
  pg4.begin();
  // display the results
  image(pg4.end(false),100,100);
  image(Hi1,100,100);
  
  //Box 9
  pg44.begin();
  pg44.fill(0xff0000);
  pg44.textSize(80);
  pg44.text("HI",20,70); 
  // display the results
  image(pg44.end(false),200,100);
  
  //Box 10
  pg5.begin();
  pg5.fill(0xff0000);
  pg5.textSize(80);
  pg5.text("HI",20,70); 
  // display the results
  image(pg5.end(),300,100);
  
  //Box 11
  pg6.begin();
  pg6.fill(0xff0000);
  pg6.textSize(80);
  pg6.text("HI",20,70); 
  // display the results
  image(pg6.end(false),400,100);
  
  //Box 12
  pg66.begin();
  // display the results
  image(pg66.end(false),500,100);
  image(Hi1, 500,100);
  
  //Box 13
  pg7.begin(50,50);
  pg7.fill(0xff0000);
  pg7.textSize(80);
  pg7.text("HI",20,70); 
  // display the results
  image(pg7.end(),0,200);
  
  //Box 14
  pg8.begin();
  pg8.fill(0xff0000);
  pg8.textSize(80);
  pg8.text("HI",20,70); 
  // display the results
  image(pg8.end(false),100,200);
  //pg8.begin();
 // pg8.fill(0xff0000);
  //pg8.textSize(80);
  //pg8.text("HI",20,70); 
  // display the results
  //image(pg8.end(false),100,200);
  
  //Box 15
  pg9.begin(0xff00,4);
  pg9.fill(0xffffff);
  pg9.textSize(80);
  pg9.text("HI",20,70); 
  // display the results
  image(pg9.end(),200,200);
}
