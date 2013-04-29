import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class ApoSeeds4k_Reimplement extends PApplet {

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
    else if (SGD.getMouse_released() == 1) {
      SGD.mouseButtonActivity();
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
public void mouseReleased() {
  SGD.setMouse_released(1);
}
public void mouseMoved() {
  SGD.setControlHoriz(mouseX);
  SGD.setControlVert(mouseY);
}
public void mouseDragged() {
  SGD.setControlHoriz(mouseX);
  SGD.setControlVert(mouseY);
}
class SeedsGame {
  protected String[] levels = new String[] {
  	"00101"+
	"01010"+
	"00000"+
	"00100"+
	"10010"+
	"010011",
	
	"10101"+
	"20102"+
	"20102"+
	"20102"+
	"20102"+
	"101011",
	
	"01310"+
	"31013"+
	"00000"+
	"11011"+
	"11011"+
	"011101",
	
	"02320"+
	"01010"+
	"02120"+
	"02120"+
	"11011"+
	"011101",
	
	"00001"+
	"23130"+
	"21104"+
	"21104"+
	"23130"+
	"000012",
	
	"01221"+
	"40010"+
	"01121"+
	"21230"+
	"10332"+
	"001033",
	
	"40011"+
	"33301"+
	"41200"+
	"22142"+
	"11002"+
	"002102",
	
	"04010"+
	"13220"+
	"11221"+
	"24200"+
	"10332"+
	"010122",
	
	"12110"+
	"12040"+
	"00122"+
	"10000"+
	"01000"+
	"000203",
	
	"00200"+
	"10101"+
	"02320"+
	"13131"+
	"21012"+
	"100012",
	
	"23402"+
	"02211"+
	"21433"+
	"02100"+
	"12100"+
	"004132",
	
	"00000"+
	"20102"+
	"22422"+
	"12021"+
	"10001"+
	"000003",
	
	"20000"+
	"02212"+
	"10201"+
	"20011"+
	"32030"+
	"000032",
	
	"21220"+
	"24012"+
	"02140"+
	"11034"+
	"22212"+
	"001012",
	
	"14041"+
	"42024"+
	"12021"+
	"01010"+
	"02020"+
	"000003",
        
        /**************************
         New Levels Added To Game
        **************************/
        
	"00000"+
        "00300"+
	"03230"+
	"03230"+
	"00300"+
	"000009",

	"10101"+
        "00100"+
	"23032"+
	"41114"+
	"10001"+
	"111112",

	"11111"+
        "22222"+
	"11111"+
	"22222"+
	"11111"+
	"222222",

	"11111"+
        "10001"+
	"12221"+
	"12221"+
	"10001"+
	"111111",

	"11111"+
        "11111"+
	"11111"+
	"11111"+
	"11111"+
	"111111"
  };
  protected int titleWidth = 100;
  protected float[] seeds = new float[160];
  protected boolean bRun = false;
  protected String s = "";
  protected float w;
  protected int[][] level = new int[6][5];
  protected float control_Horiz, control_Vert, mouse_released, level_num, touches, game_lost, game_won;
  
  protected int changeX = 90;
  protected int changeY = 80;
  
  SeedsGame() {}
  
  public int[][] convertLevels() {
    String curLevel = levels[(int)level_num];
    level = new int[6][5];
    touches = Integer.valueOf(curLevel.substring(30,31));
    for (int y = 0; y < level.length; y++) {
      for (int x = 0; x < level[0].length; x++) {
        level[y][x] = Integer.valueOf(curLevel.substring(x + y * level[0].length,1 + x + y * level[0].length));
      }
    }
    return level;
  }
  
  public void levelDesign() {
    if (level_num < 0) {
      level_num = levels.length - 1;
    }
    if (level_num >= levels.length) {
      level_num = 0;
    }
    bRun = false;
    game_lost = 0;
    game_won  = 0;
    convertLevels();

  }
  
  public void mouseButtonActivity() {
    if (game_lost > 0) {
      if (game_won > 0) {
        level_num += 1;
      }
      level[0][0] = -1;
    }
    else if ((control_Horiz >= 0) && (control_Vert >= 0) && (control_Horiz < 70) && (control_Vert < 30)) {
      level[0][0] = -1;
    }
    else if ((control_Horiz >= 125) && (control_Vert >= 38) && (control_Horiz < 170) && (control_Vert < 70)) {
      level_num -= 1;
      level[0][0] = -1;
    }
    else if ((control_Horiz >= 310) && (control_Vert >= 38) && (control_Horiz < 355)&& (control_Vert < 70)) {
      level_num += 1;
      level[0][0] = -1;
    }
    else if ((control_Horiz >= changeX) && (control_Vert >= changeY) && (control_Horiz < changeX + 60 * level[0].length) && (control_Vert < changeY + 60 * level.length) && (!bRun)) {
      int x = (int)(control_Horiz - changeX) / 60;
      int y = (int)(control_Vert - changeY) / 60;
      if (level[y][x] > 0) {
        level[y][x] -= 1;
        touches -= 1;
        bRun = true;
        seeds = new float[160];
        if (level[y][x] <= 0) {
          for (int i = 0; i < 4; i++) {
            seeds[i*2] = x * 60 + 30;
            seeds[i*2 + 1] = y * 60 + 30;
          }
          seeds[1] -= 2f;
          seeds[2] += 2f;
          seeds[5] += 2f;
          seeds[6] -= 2f;
        }
      }
    }
    mouse_released = 0;
  }
  
  public void gameWinChk(){
    boolean bWin = true;
    for (int lY = 0; lY < level.length; lY++) {
      for (int lX = 0; lX < level[0].length; lX++) {
        if (level[lY][lX] > 0) {
          bWin = false;
          break;
        }
      }
    }
    if ((bWin) && (!bRun)) {
      game_lost = 1;
      game_won = 1;
    }
  }
  
  public void seedMovement() {
    for (int i = 0; i < seeds.length; i += 8) {
      int p = 0;
      while (p < 8) {
        int chooseX = -1, chooseY = -1;
        if ((p == 0) && (seeds[i] != 0) && (seeds[i + 1] != 0)) {
          seeds[i + 1] -= 2f;
          int newY = (int)((seeds[i+1] - 30) % 60);
          if (seeds[i+1] <= 0) {
            seeds[i] = seeds[i+1] = 0;
          }
          else if (0 == newY) {
            int oldY = (int)((seeds[i+1] - 30) / 60);
            int oldX = (int)((seeds[i] - 30) / 60);
            if (level[oldY][oldX] > 0) {
              chooseX = oldX;
              chooseY = oldY;
            }
          }
        }
        if ((p == 2) && (seeds[i+2] != 0) && (seeds[i + 3] != 0)) {
          seeds[i + 2] += 2f;
          int newX = (int)((seeds[i+2] - 30) % 60);
          if (seeds[i+2] >= level[0].length * 60) {
            seeds[i+2] = seeds[i+3] = 0;
          }
          else if (0 == newX) {
            int oldY = (int)((seeds[i+3] - 30) / 60);
            int oldX = (int)((seeds[i+2] - 30) / 60);
            if ((oldX < level[0].length) && (level[oldY][oldX] > 0)) {
              chooseX = oldX;
              chooseY = oldY;
            }
          }
        }
        if ((p == 4) && (seeds[i+4] != 0) && (seeds[i + 5] != 0)) {
          seeds[i + 5] += 2f;
          int newY = (int)((seeds[i+5] - 30) % 60);
          if (seeds[i+5] >= level.length * 60) {
            seeds[i+5] = seeds[i+4] = 0;
          }
          else if (0 == newY) {
            int oldY = (int)((seeds[i+5] - 30) / 60);
            int oldX = (int)((seeds[i+4] - 30) / 60);
            if (level[oldY][oldX] > 0) {
              chooseX = oldX;
              chooseY = oldY;
            }
          }
        }				
        if ((p == 6) && (seeds[i+6] != 0) && (seeds[i + 7] != 0)) {
          seeds[i + 6] -= 2f;
          int newX = (int)((seeds[i+6] - 30) % 60);
          if (seeds[i+6] <= 0) {
            seeds[i+6] = seeds[i+7] = 0;
          }
          else if (0 == newX) {
            int oldY = (int)((seeds[i+7] - 30) / 60);
            int oldX = (int)((seeds[i+6] - 30) / 60);
            if (level[oldY][oldX] > 0) {
              chooseX = oldX;
              chooseY = oldY;
            }
          }
        }
        if (chooseX >= 0) {
          level[chooseY][chooseX] -= 1;
          seeds[i + p] = 0;
          seeds[i + p + 1] = 0;
          if (level[chooseY][chooseX] <= 0) {
            for (int k = 0; k < seeds.length; k += 8) {
              if ((seeds[k] <= 0) && (seeds[k+2] <= 0) && (seeds[k+4] <= 0) && (seeds[k+6] <= 0)) {
                for (int m = 0; m < 4; m++) {
                  seeds[k + m*2] = chooseX * 60 + 30;
                  seeds[k + m*2 + 1] = chooseY * 60 + 30;
                }
                seeds[k + 1] -= 2f;
                seeds[k + 2] += 2f;
                seeds[k + 5] += 2f;
                seeds[k + 6] -= 2f;
                break;
              }
            }
          }
        }
        p += 2;
      }
      bRun = false;
      for (int k = 0; k < seeds.length; k += 2) {
        if (seeds[k] > 0) {
          bRun = true;
          break;
        }
      }
      if (!bRun) {
        if (touches <= 0) {
          game_lost = 1;
        }
      }
    }
  }
  
  
  public float getControlHoriz() {
    return control_Horiz;
  }

  public void setControlHoriz(float control_Horiz) {
    this.control_Horiz = control_Horiz;
  }

  public float getControlVert() {
    return control_Vert;
  }

  public void setControlVert(float control_Vert) {
    this.control_Vert = control_Vert;
  }

  public float getMouse_released() {
    return mouse_released;
  }

  public void setMouse_released(float mouse_released) {
    this.mouse_released = mouse_released;
  }

  public float getLevel_num() {
    return level_num;
  }

  public void setLevel_num(float level_num) {
    this.level_num = level_num;
  }

  public float getTouches() {
    return touches;
  }

  public void setTouches(float touches) {
    this.touches = touches;
  }

  public float getGame_lost() {
    return game_lost;
  }

  public void setGame_lost(float game_lost) {
    this.game_lost = game_lost;
  }

  public float getGame_won() {
    return game_won;
  }

  public void setGame_won(float game_won) {
    this.game_won = game_won;
  }
  
  public void setbRun(boolean bRun) {
    this.bRun = bRun;
  }
  
  public boolean getbRun() {
    return bRun;
  }
  
  public int getLevel(int a, int b) {
    return level[a][b];
  }
  
  public void setLevel(int a, int b, int c) {
    level[a][b] = c;
  }
}
class SeedsGameDraw extends SeedsGame {
  SeedsGameDraw() {}
  
  public void drawTitle() {
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
  
  public void drawFlowerTiles() {
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
  
  public void drawSeeds() {
    for (int i = 0; i < seeds.length; i += 2) {
      if (seeds[i] > 0) {
        fill(214, 45, 26);
        ellipse((int)(changeX + seeds[i] - 2), (int)(changeY + seeds[i + 1] - 2), 5, 5);
      }
    }
  }
  
  public void drawWinScreen() {
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
  
  public void drawBottomBanner() {
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
    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#F0F0F0", "ApoSeeds4k_Reimplement" });
    }
}
