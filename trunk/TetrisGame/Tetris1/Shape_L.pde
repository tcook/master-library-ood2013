public class Shape_L extends Shape {
  public Shape_L() {
    super();
    this.blocks.add(new Block(width/2, 0,color(255,0,255),this));
    this.blocks.add(new Block(width/2, 32 ,color(255,0,255),this));
    this.blocks.add(new Block(width/2, 32 * 2 ,color(255,0,255),this));
    this.blocks.add(new Block(width/2 + 32, 32 * 2,color(255,0,255),this));
  }
  /*Rotate Right and update position*/
  public void rotateShapeRight() {
    switch(position) {
    case 0:
      this.blocks.get(0).setPos(blk_size, blk_size);
      this.blocks.get(2).setPos(-blk_size, -blk_size);
      this.blocks.get(3).setPos(-blk_size*2, 0);
      position=1;
      break;
    case 1:
      this.blocks.get(0).setPos(-blk_size, blk_size );
      this.blocks.get(2).setPos(blk_size, -blk_size);
      this.blocks.get(3).setPos(0, -blk_size*2);
      position=2;
      break;
    case 2:
      this.blocks.get(0).setPos(-blk_size, -blk_size );
      this.blocks.get(2).setPos(blk_size, blk_size );
      this.blocks.get(3).setPos(blk_size*2, 0 );
      position=3;
      break;
    case 3:
      this.blocks.get(0).setPos(blk_size, -blk_size);
      this.blocks.get(2).setPos(-blk_size, blk_size);
      this.blocks.get(3).setPos(0, blk_size*2);
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

