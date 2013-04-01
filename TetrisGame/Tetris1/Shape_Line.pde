public class Shape_Line extends Shape {
  public Shape_Line() {
    super();
    this.blocks.add(new Block(width/2, 0 ,color(200,200,200),this));
    this.blocks.add(new Block(width/2, 32 ,color(200,200,200),this));
    this.blocks.add(new Block(width/2, 32 * 2 ,color(200,200,200),this));
    this.blocks.add(new Block(width/2, 32 * 3 ,color(200,200,200),this));
  }
/*Rotate Right and update position*/
  public void rotateShapeRight() {
    switch(position) {
    case 0:
      this.blocks.get(0).setPos(blk_size*2, blk_size*2);
      this.blocks.get(1).setPos(blk_size,blk_size);
      this.blocks.get(3).setPos(-blk_size, -blk_size);
      position=1;
      break;
    case 1:
      this.blocks.get(0).setPos(-blk_size*2, blk_size*2 );
      this.blocks.get(1).setPos(-blk_size, blk_size);
      this.blocks.get(3).setPos(blk_size, -blk_size);
      position=2;
      break;
    case 2:
      this.blocks.get(0).setPos(-blk_size*2, -blk_size*2 );
      this.blocks.get(1).setPos(-blk_size, -blk_size );
      this.blocks.get(3).setPos(blk_size,blk_size );
      position=3;
      break;
    case 3:
      this.blocks.get(0).setPos(blk_size*2, -blk_size*2);
      this.blocks.get(1).setPos(blk_size, -blk_size);
      this.blocks.get(3).setPos(-blk_size, blk_size);
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

