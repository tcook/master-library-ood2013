import java.awt.Rectangle;
static ArrayList<Block> all_blocks=new ArrayList<Block>();
static int blk_size=32;
class Block {
  color blk_color = color(0,0,0);
  int x, y;
  int blk_width = 32, blk_height = 32;
  Rectangle rectangle;
  public Shape parent;
  public Block(int x, int y, color blk_color,Shape parent) {
    this.x = x;
    this.y = y;
    this.blk_color=blk_color;
    rectangle = new Rectangle((int) x, (int) y, (int)blk_width, (int)blk_height);
    this.parent=parent;
    all_blocks.add(this);
  }
  /*Block draw method*/
  public void draw() {
    fill(blk_color);
    rect(x+4, y+4, blk_width-4, blk_height-4);
  }
  /*Changes the position of the block and instantiates a new rectangle with corresponding coordinates*/
  public void setPos(float x, float y) {
    this.x += x;
    this.y += y;
    rectangle = new Rectangle((int) this.x, (int) this.y, (int) blk_width, 
    (int) blk_height);
  }
    /*Block collision detection for the bottom of the screen */
  public boolean blkCollision() {
    return y + blk_height > height;
  }
  /*Block Collision overload that checks against a rectangle*/
  public boolean blkCollision(Rectangle r) {
    return rectangle.intersects(r)|| y + blk_height > height;
  }
  /*Block Collision that checks against all other blocks*/
  public boolean checkCollision(){
   for(int i=0;i<all_blocks.size();i++)
     if(blkCollision(all_blocks.get(i).rectangle)&&(all_blocks.get(i).x!=x||all_blocks.get(i).y!=y))
      return true; 
   return false;
  }
  /*Removes the block from the parent's shape*/
  public void remove(){
     parent.blocks.remove(this);
  }
}

