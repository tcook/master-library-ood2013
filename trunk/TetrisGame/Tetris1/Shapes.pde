import java.util.ArrayList;

public abstract class Shape {
  protected ArrayList<Block> blocks = new ArrayList<Block>(); 
  protected int position = 0;
  private boolean moveable;
  //constructor
  public Shape() {
    this.moveable=true;
    shapes.add(this);
  }

  //call the blocks draw method
  void draw() {
    for (int i = 0; i < blocks.size(); i++)
      blocks.get(i).draw();
  }
  //move the whole shape
  public void moveShape(int x, int y) {
    for (int i = 0; i < this.blocks.size(); i++)
      blocks.get(i).setPos(x, y);
  }

  //get the right most point to check for unit collision on the right side of the screen
  public int getRight() {
    int most = 0;
    for (int x = 0; x < this.blocks.size(); x++) {
      if (blocks.get(x).x + blocks.get(x).blk_width > most)
        most = blocks.get(x).x + blocks.get(x).blk_width;
    }
    return most;
  }

  //get the left most point of the shape for unit collision on the left side of the screen
  public int getLeft() {
    int most = width;
    for (int x = 0; x < this.blocks.size(); x++) {
      if (blocks.get(x).x < most)
        most = blocks.get(x).x;
    }
    return most;
  }
  /*Collision method with all of instance's blocks compared to all other shape's blocks*/
  public boolean checkCollision(){
    for(int x=0;x<all_blocks.size();x++){
      for (int y = 0; y < shapes.size(); y++) {
            if (blocks.get(x).blkCollision(all_blocks.get(y).rectangle))
              moveable=false;{
              return true;
            }
      }
    
    moveable=true;}
    return false;
  }
  /*Collision detection for each of the instances blocks*/
  public boolean myCheckCollision(){
    for(int i=0;i<blocks.size();i++)    
        if(blocks.get(i).checkCollision())
          return true;
    return false;
  }
  /*Test the rotation to make sure it won't collide before you actually rotate the shape*/
  public boolean checkRotationCollision(){
    rotateShapeRight();
    for(int i=0;i<blocks.size();i++)
      if(blocks.get(i).checkCollision()||blocks.get(i).x<(width/2)-(32*5)||blocks.get(i).x>(width/2)+(width/2)-(32*6)){
      rotateShapeLeft();
        return false; 
      }
    rotateShapeLeft();
    return true; 
  }
  public void moveBack() {
    for (int x = 0; x < preview.blocks.size(); x++)
      preview.blocks.get(x).setPos(-300, -30);
  }
  /*Abstract methods*/
  public abstract void rotateShapeRight();
  public abstract void rotateShapeLeft();

}

