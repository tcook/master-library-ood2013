class SeedsGameDraw extends SeedsGame {
  SeedsGameDraw() {}
  
  void drawTitle() {
    //Draw Window, titles, and boxes around titles.
    ellipseMode(CORNER);
    fill(191,255,180);
    rect(0,0,480,480);
    w = titleWidth = 230;
    fill(140,220,255);
    rect(240 - w/2, 33 - 5, w, 43, 0, 0, 15, 15);
    rect(240 - 70, 33 - 5, 140, 43, 0, 0, 15, 15);
    rect(480 - 70, - 5, 85, 76, 0, 0, 15, 15);
    rect(-5, -5, 85, 43, 0, 0, 0, 15); 
    fill(140,220,255);
    rect(240 - w/2 - 10, -5, w + 20, 43, 0, 0, 15, 15);
    fill(0);
    s = "ApoSeeds4k";
    w = textWidth(s);
    textSize(28);
    text(s, 156, 25);
    s = "Level: "+String.valueOf((int)(level_num + 1))+" / "+levels.length;
    w = textWidth(s);
    textSize(15);
    text(s, 195, 58);
    text("<", 240 - titleWidth/2 + 15, 58);
    text(">", 240 + titleWidth/2 - 15 - textWidth(">"), 58);
    text("restart", 14, 25);	
    s = "Clicks";
    w = textWidth(s);
    text(s, 475 - w, 25);		
    s = String.valueOf((int)touches);
    text(s, 447, 58);
  }
  
  void drawFlowerTiles() {
    for (int y = 0; y < level.length; y++) {
      for (int x = 0; x < level[0].length; x++) {
        fill(93, 180, 93);
        rect(changeX + x * 60, changeY + y * 60, 60, 60);
        if ((control_Horiz - changeX >= x * 60) && (control_Vert - changeY >= y * 60) && (control_Horiz - changeX < (x + 1) * 60) && (control_Vert - changeY < (y + 1) * 60) && (level[y][x] > 0)) {
          if (bRun) {
            rect(changeX + x * 60, changeY + y * 60, 59, 59);
            }
          rect(changeX + x * 60, changeY + y * 60, 59, 59);
          rect(changeX + x * 60, changeY + y * 60, 60, 60);
        }
        //print(level[y][x]);
        if (level[y][x] > 0) {
          fill(214, 45, 26);
          if (level[y][x] == 2) fill(46, 175, 187);
          if (level[y][x] == 3) fill(182, 146, 46);
          if (level[y][x] == 4) fill(187, 46, 182);			
          ellipse(changeX + x * 60 + 10 + (level[y][x] - 1) * 3, changeY + y * 60 + 4 + (level[y][x] - 1) * 4, 40 - (level[y][x] - 1) * 6, 25 - (level[y][x] - 1) * 4);
          fill(231, 182, 27);
          noStroke();
          ellipse(changeX + x * 60 + 18 + (level[y][x] - 1) * 3 - (level[y][x] - 1), changeY + y * 60 + 7 + (level[y][x] - 1)/2 + (level[y][x] - 1) * 4, 24 - (level[y][x] - 1) * 6 + (level[y][x] - 1) * 2, 15 - (level[y][x] - 1) * 3);
          fill(0);
          stroke(0);
          rect(changeX + x * 60 + 26, changeY + y * 60 + 10 + (level[y][x] - 1) * 4 - 1, 3, 7 - (level[y][x] - 1));
          rect(changeX + x * 60 + 31, changeY + y * 60 + 10 + (level[y][x] - 1) * 4 - 1, 3, 7 - (level[y][x] - 1));
					
          fill(166, 187, 46);
          rect(changeX + x * 60 + 27 + (level[y][x] - 1) / 2, changeY + y * 60 + 29, 6 - (level[y][x] - 1), 27 - (level[y][x] - 1) * 4);
          fill(166, 187, 46);
          ellipse(changeX + x * 60 + 8 + (level[y][x] - 1) * 2 - 1, changeY + y * 60 + 46 - (level[y][x] - 1) * 2, 20 - (level[y][x] - 1) * 2, 12 - (level[y][x] - 1) * 2);				
          fill(166, 187, 46);
          ellipse(changeX + x * 60 + 30 + (6 - (level[y][x] - 1))/2, changeY + y * 60 + 46 - (level[y][x] - 1) * 2, 20 - (level[y][x] - 1) * 2, 12 - (level[y][x] - 1) * 2);

        }
      }
    }
  }
  
  void drawSeeds() {
    for (int i = 0; i < seeds.length; i += 2) {
      if (seeds[i] > 0) {
        fill(214, 45, 26);
        ellipse((int)(changeX + seeds[i] - 2), (int)(changeY + seeds[i + 1] - 2), 5, 5);
      }
    }
  }
  
  void drawWinScreen() {
    textSize(22);
    fill(140, 220, 255);
    rect(95, 190, 290, 100, 15, 15);
    fill(0);
    if (game_won > 0) {
      s = "Congratulation";
      w = textWidth(s);
      text(s, 240 - w/2, 220);
      s = "Click to start the next level";
    }
    else {
      s = "Try again";
      w = textWidth(s);
      text(s, 240 - w/2, 220);
      s = "Click to restart the level";
    }
    textSize(17);
    w = textWidth(s);
    text(s, 240 - w/2, 282);
  }
  
  void drawBottomBanner() {
    textSize(17);
    s = "";
    if (level_num == 0) {
      s = "Click on a big flower and look what happens";
    }
    else if (level_num == 1) {
      s = "Seeds will help the flowers to grow";
    }
    else if (level_num == 2) {
      s = "Click on a smaller flower and it will grow";
    }
    if (s.length() > 0) {
      w = textWidth(s);
      int c = 1;
      while (w > 470) {
        textSize(17-c);
        w = textWidth(s);	
        c += 1;
      }
      fill(140, 220, 255);
      rect(240 - w/2 - 10, 480 - 35 + 7, w + 20, 35, 15, 15, 0, 0);
      fill(0);
      text(s, 240 - w/2, 473);
    }
  }
}
