public class Shape_T extends Shape {
  public Shape_T() {
    super();
    this.blocks.add(new Block(width/2, 0 ,color(255,0,0),this));
    this.blocks.add(new Block(width/2, 32,color(255,0,0),this));
    this.blocks.add(new Block(width/2 + 32, 32 ,color(255,0,0),this));
    this.blocks.add(new Block(width/2 - 32, 32,color(255,0,0),this));
  }
/*Rotate Right and update position*/
  public void rotateShapeRight() {
    switch(position) {
    case 0:
      this.blocks.get(0).setPos(blk_size,blk_size);
      this.blocks.get(2).setPos(-blk_size, blk_size);
      this.blocks.get(3).setPos(blk_size, -blk_size);
      position=1;
      break;
    case 1:
      this.blocks.get(0).setPos(-blk_size, blk_size );
      this.blocks.get(2).setPos(-blk_size, -blk_size);
      this.blocks.get(3).setPos(blk_size, blk_size);
      position=2;
      break;
    case 2:
      this.blocks.get(0).setPos(-blk_size, -blk_size );
      this.blocks.get(2).setPos(blk_size, -blk_size );
      this.blocks.get(3).setPos(-blk_size, blk_size );
      position=3;
      break;
    case 3:
      this.blocks.get(0).setPos(blk_size, -blk_size);
      this.blocks.get(2).setPos(blk_size,blk_size);
      this.blocks.get(3).setPos(-blk_size, -blk_size);
      position=0;
      break;
    }
  }

   public void rotateShapeLeft() {
    rotateShapeRight();
    rotateShapeRight();
    rotateShapeRight();
  }
}

