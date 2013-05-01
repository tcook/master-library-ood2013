/*
 * Copyright (c) 2005-2012 Dirk Aporius <dirk.aporius@gmail.com>
 * All rights reserved.
 */

SeedsGameDraw SGD = new SeedsGameDraw();

public void setup() {
  size(480,480);
  SGD.setLevel(0,0,-1);
  frameRate(90);
}
	
public void draw() {
  if (SGD.getLevel(0,0) < 0) {
      SGD.levelDesign();
    }
    else if (SGD.getAction() == 1) {
      SGD.update();
    }
    
    else if (SGD.getbRun()) {
      SGD.seedMovement();
    }

    //Determine win or lose level
    SGD.gameWinChk();
    
    //Draw title at top.
    SGD.drawTitle();
  
    //Draws Tiles and Flowers
    SGD.drawFlowerTiles();
  
    //When a flower releases seeds
    if (SGD.getbRun()) {
      SGD.drawSeeds();
    }
    //Win screen drawing
    if (SGD.getGame_lost() > 0) {
      SGD.drawWinScreen();
    }
  
    //Draw bottom banner
    else {
      SGD.drawBottomBanner();
    }
}

//Handle Mouse Events
void mouseReleased() {
  SGD.setAction(1);
}
void mouseMoved() {
  SGD.setControlHoriz(mouseX);
  SGD.setControlVert(mouseY);
}
void mouseDragged() {
  SGD.setControlHoriz(mouseX);
  SGD.setControlVert(mouseY);
}
