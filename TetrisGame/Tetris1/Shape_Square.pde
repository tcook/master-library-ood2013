public class Shape_Square extends Shape {
  public Shape_Square() {
    super();
    this.blocks.add(new Block(width/2, 0,color(0,255,255),this));
    this.blocks.add(new Block(width/2 + 32, 0,color(0,255,255),this));
    this.blocks.add(new Block(width/2, 32,color(0,255,255),this));
    this.blocks.add(new Block(width/2 + 32, 32,color(0,255,255),this));
  }

  public void rotateShapeRight() {
  }

  public void rotateShapeLeft() {
  }
}

