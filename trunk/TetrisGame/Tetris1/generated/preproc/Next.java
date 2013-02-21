public class Next extends Shape{
  public Next(int x, int y){
    super(x,y , false);
  }
  
  public void moveOn(){
    new Shape(this.x, this.y);
    shapes.remove(this);
  }
}
