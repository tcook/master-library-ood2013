
ApoShift4kDraw nshift = new ApoShift4kDraw();

void setup(){
  nshift.setups();
}

void draw(){
  nshift.run();
  //Uncomment next line for auto run and text output
  //nshift.changeRCXY(2,1,2,2);
}

void mousePressed() {
  nshift.toggleOnAction(mouseX,mouseY);
}
void mouseReleased() {
  nshift.toggleOffAction();
}
void mouseMoved() {
  nshift.cursorMoved(mouseX,mouseY);
}
void mouseDragged() {
  nshift.cursorDragged(mouseX, mouseY);
}

