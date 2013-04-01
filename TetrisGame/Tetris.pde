public static ArrayList<Shape> shapes = new ArrayList<Shape>();

Shape active;
Shape preview;
boolean gameOver,pause;
int score,row;
public void setup() {
  size(720, 480);
  frame.setTitle("Tetris - Ryan Dale");
  preview = randShape();
  active = preview;
  preview = randShape();
  preview.moveShape(250, 30);
  score=0;
  row=0;
  gameOver=false;
  pause=false;
}

public void draw() {
  /*Game Over State*/
  if(gameOver){
    textAlign(CENTER, CENTER);
    fill(150,150,150);
    textSize(32);
    text("GAME OVER!\nFINAL SCORE: "+score+"\nPress ENTER to restart",375,220);
    return;
  }
  /*Pause State*/
  if(pause){
     textAlign(CENTER, CENTER);
    fill(150,150,150);
    textSize(32);
    text("PAUSED!\nPress ENTER\n to continue",375,220);
    return;
  }
  row = 0;
  for (int x = 0; x < active.blocks.size(); x++) {
    if (active.blocks.get(x).blkCollision()) {
      active = preview;
      active.moveShape(-250, -30);
      preview = randShape();
      preview.moveShape(250, 30);
    }
    for (int y = 0; y < shapes.size() - 2; y++) {
      for (int z = 0; z < shapes.get(y).blocks.size(); z++) {
        if (active.blocks.get(x).blkCollision(shapes.get(y).blocks.get(z).rectangle)) {
                for(int i=0;i<active.blocks.size();i++)
                  if(active.blocks.get(i).y<=0)
                    {
                     gameOver=true;
                     return;
                    }
          active = preview;
          active.moveShape(-250, -30);
          preview = randShape();
          preview.moveShape(250, 30);
        }
      }
    }
    checkRows();
  }

  for(int i=0;i<all_blocks.size();i++)
    if(!all_blocks.get(i).parent.moveable&&!all_blocks.get(i).checkCollision())
      all_blocks.get(i).setPos(0,1);
  background(150, 150, 150);
  fill(0, 102, 153);
  rect((width/2)-(5*32),0,(11*32),480);
  textAlign(TOP,LEFT);
  textSize(32);
  /*Concept of score text was reused from processing docuentation*/
  text("Score: "+score,40,40);
  fill(0, 102, 153, 50);
  text("Score: "+score,40,80);
  for (int i = 0; i < all_blocks.size(); i++)
    all_blocks.get(i).draw();
  //moving the active shape down one
  active.moveShape(0, 1);
}
public void checkRows(){
  int count,
      testY;
  ArrayList<Block> row;
   for(int i=0;i<all_blocks.size();i++){
     if(all_blocks.get(i).parent==active)continue;
       Block b =all_blocks.get(i);
       b.setPos(0,(int)(Math.round(b.y/32.0) * 32.0)-b.y);
     
   }
   for(int i=14;i>=0;i--){
     count=0;
    testY=i*32;
    row=new ArrayList<Block>();
    for(int j=0;j<all_blocks.size();j++){
      if(all_blocks.get(j).y==testY&&all_blocks.get(j).parent!=active){
        count++;
        row.add(all_blocks.get(j));
      }
      if(count==11){
        removeRow(row);
        score++;
        count=0;
      }
    }
   }
  
}
public void removeRow(ArrayList<Block> row){
 int testY=row.get(0).y;
 for(int i=0;i<row.size();i++){
    for(int j =0;j<all_blocks.size();j++){
        if(row.get(i).x==all_blocks.get(j).x&&row.get(i).y==all_blocks.get(j).y){
          row.get(i).remove();
          all_blocks.remove(all_blocks.get(j));
        }
    }
 }
for(int i =0;i<all_blocks.size();i++)
  if(all_blocks.get(i).y<testY)
    all_blocks.get(i).setPos(0,32);
}

public void keyPressed() {
  if ((key=='a'||keyCode==37) && active.getLeft() > (width/2)-(32*5))
  {
   active.moveShape(-32, 0); 
   if(active.myCheckCollision())   
    active.moveShape(32, 0); 
  }
  else if ((key=='d'||keyCode==39) && active.getRight() < (width/2)+(32*6))
  {
   active.moveShape(32, 0); 
   if(active.myCheckCollision())   
    active.moveShape(-32, 0);
  }else if ((key=='w'||keyCode==38)&& active.checkRotationCollision())
    active.rotateShapeRight();
  else if (key=='s'||keyCode==40)
    {
     while(!active.myCheckCollision(
     ))
       active.moveShape(0,1);
       active.moveShape(0,-1);
    }
 if(gameOver && key==ENTER){
  gameOver=false;
  shapes = new ArrayList<Shape>();
  all_blocks=new ArrayList<Block>();
  preview = randShape();
  active = preview;
  preview = randShape();
  preview.moveShape(250, 30);
  score=0;
 }
 if(!gameOver && key==' ')
   pause=!pause;
}
/*Returns a random shape from a collection of all of the shape types*/
public Shape randShape() {
  int r = (int)random(7);
  switch(r) {
  case 0:
    return new Shape_L();
  case 1:
    return new Shape_Line();
  case 2:
    return new Shape_Square();
  case 3:
    return new Shape_T();
  case 4:
    return new Shape_Z();
  case 5:
    return new Shape_Z_F();
  case 6:
    return new Shape_L_F();
  }
  return null;
}
