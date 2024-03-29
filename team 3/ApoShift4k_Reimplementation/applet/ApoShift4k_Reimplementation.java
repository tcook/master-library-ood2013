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

public class ApoShift4k_Reimplementation extends PApplet {

class ApoShift4k{
  public final String[] levels = new String[] {		
      "10000010"+
      "01000000"+
      "10000100"+
      "01000011",
			
      "00001110"+
      "01100001"+
      "01100000"+
      "00000000",

      "10000001"+
      "10000110"+
      "10000111"+
      "11110001",
			
      "01102301"+
      "23321030"+
      "23323102"+
      "01102132",

      "40044000"+
      "03300004"+
      "03304330"+
      "40043034",
			
      "1000141000"+
      "1101110001"+
      "0414010101"+
      "0111001014"+
      "0000010010",
			
      "0111011011"+
      "1111111011"+
      "2323221211"+
      "1111111112"+
      "0111003301",
			
      "100001100011"+
      "101101011000"+
      "111111101100"+
      "001100110110"+
      "011110111101"+
      "010010010110",
			
      "10000011001110"+
      "00000000110001"+
      "10101010111101"+
      "11010111001100"+
      "11000111000000"+
      "11000110010111"+
      "10111011110001",
			
      "11101111131113"+
      "13303311111302"+
      "13202313013300"+
      "00000003100301"+
      "13202311202030"+
      "13303310113132"+
      "11101111010113",
			
      "00131003310001"+
      "00131000110000"+
      "11111110111101"+
      "33141333014111"+
      "11111111111000"+
      "00131000113111"+
      "00131001333013",
			
      "1000000110030122"+
      "1110011112011311"+
      "2210013301001131"+
      "2210013300000100"+
      "2210013321032111"+
      "2210013302110011"+
      "1110011122100133"+
      "1000000100033110",};
      
	public final int[] p = new int[10];

	long lastTime = System.nanoTime();
	long think = 10000000L;
	int width = 4;
	int[][] level = new int[width][width * 2];
	int realWidth = 480/width;
	int[] change = new int[4];
        int count = 0;
        boolean a,b,c;

	ApoShift4k(){

	}

	public void setups(){
  		p[8] = -1;
	}


	//called in the draw loop 
	public void run(){
		
		levels();

	}
	//ends at line 296 in orginal program
	public void levels(){
		  if (level[0][0] == -1) {
		    if (p[2] < 0) {
		      p[2] = levels.length - 1;
		    }
		    if (p[2] >= levels.length) {
		      p[2] = 0;
		    }
		    String l = levels[p[2]];
		    width = 4;
		    if (l.length() == 50) {
		      width = 5;
		    }
		    else if (l.length() == 72) {
		      width = 6;
		    }
		    else if (l.length() == 128) {
		      width = 8;
		    }
		    else if (l.length() == 98) {
		      width = 7;
		       
		    }
		    realWidth = 480 / width;
		    level = new int[width][width * 2];
		    for (int y = 0; y < width; y++) {
		      for (int x = 0; x < width * 2; x++) {
		        level[y][x] = Integer.valueOf(l.substring(y * width * 2 + x, y * width * 2 + x + 1));
		      }
		    }

		     
		    change = new int[4];
		    change[0] = -1;

		    p[7] = 0;
		    p[3] = 0;
		  } 
		  else if (p[5] > 0) {
		    if (p[7] > 0) {
		      p[2] += 1;
		      level[0][0] = -1;
		    }
		    else {
		      if ((p[0] > 115) && (p[0] < 175) && (p[1] > 560) && (p[1] < 600)) {
		        p[2] -= 1;
		        level[0][0] = -1;
		      }
		      else if ((p[0] > 175) && (p[0] < 315) && (p[1] > 560) && (p[1] < 600)) {
		        level[0][0] = -1;
		      }
		      else if ((p[0] > 315) && (p[0] < 375) && (p[1] > 560) && (p[1] < 600)) {
		        p[2] += 1;
		        level[0][0] = -1;
		      }
		      else if (change[0] >= 0) {
		        int changeX = (Math.abs(change[2]) + realWidth/2) / realWidth;
		        if (change[2] == 0) {
		          changeX = 0;
		        }
		        else if (change[2] < 0) {
		          changeX = -changeX;
		        }
		        int changeY = (Math.abs(change[3]) + realWidth/2) / realWidth;
		        if (change[3] == 0) {
		          changeY = 0;
		        }
		        else if (change[3] < 0) {
		          changeY = -changeY;
		        }
		        int newLevel[] = new int[width];
		        if (changeX != 0) {
		          p[3] += 1;
		          for (int x = 0; x < width; x++) {
		            int newValue = x + changeX;
		            while (newValue < 0) {
		              newValue += width;
		            }
		            while (newValue >= width) {
		              newValue -= width;
		            }
		            newLevel[newValue] = level[change[1]][x+width];
		          }
		          for (int x = 0; x < width; x++) {
		            level[change[1]][x+width] = newLevel[x];
		          }
		        }
		        else if (changeY != 0) {
		          p[3] += 1;
		          for (int y = 0; y < width; y++) {
		            int newValue = y + changeY;
		            while (newValue < 0) {
		              newValue += width;
		            }
		            while (newValue >= width) {
		              newValue -= width;
		            }
		            newLevel[newValue] = level[y][change[0]+width];
		          }
		          for (int y = 0; y < width; y++) {
		            level[y][change[0]+width] = newLevel[y];
		          }
		        }
		        change[0] = -1;
		        change[1] = -1;
		        boolean bWin = true;
		        for (int y = 0; y < width; y++) {
		          for (int x = 0; x < width; x++) {
		            if (level[y][x] != level[y][x+width]) {
		              bWin = false;
		            }
		          }
		        }
		        if (bWin) {
		          p[7] = 1;
		        }
		      }
		    }
		  }
		  else {
		    if ((p[0] > 10) && (p[0] < 490) && (p[1] > 60) && (p[1] < 540) && (p[8] >= 0)) {
		      int levelX = (p[0] - 10) / realWidth;
		      int levelY = (p[1] - 60) / realWidth;
		      change[0] = levelX;
		      change[1] = levelY;
		      change[3] = 0;
		      change[2] = 0;
		      if (Math.abs(p[0] - p[8]) > Math.abs(p[1] - p[9])) {
		          change[2] = p[8] - p[0];
		          change[3] = 0;
		      }
		      else if (Math.abs(p[0] - p[8]) < Math.abs(p[1] - p[9])) {
		        change[2] = 0;
		        change[3] = p[9] - p[1];
		      }
		    }
		  }

		  p[4] = 0;
		  p[5] = 0;
	}
	//ends at line 338 in orginal file 
	

	//mouse pressed
	public void toggleOnAction(float x, float y){
	  p[4] = 1;
	  p[5] = 0;
	  p[0] = (int)x;
	  p[1] = (int)y;
          //print("toggleON");
	}
	//mouse released
	public void toggleOffAction(){
	  p[4] = 0;
	  p[5] = 1;
	  p[8] = -1;
	  p[9] = -1;
          //print("toggleOFF");
	}
	//Cursor Moved
	public void cursorMoved(float x, float y){
	  p[0] = (int)y;
	  p[1] = (int)x;
	}

	//Cursor Dragged Location
	public void cursorDragged(float x, float y){
	  p[8] = (int)x;
	  p[9] = (int)y;
          //print("dragged");
	}
        public void printlvl(){
          println("level:");
          int lvl = 0;
          for(int i= 0; i<level.length; i++){
            print("|");
           for(int j = (level[i].length/2); j<level[i].length;j++){
             
             print(" "+level[i][j]+" ");
           }
           print("|\n");
          }
        }
        //Change Row Column X & Y
        public void changeRCXY(int r,int c,int x ,int y ){
          if(count == 50){
            toggleOnAction(13.00f+(realWidth*r), 60.00f+(realWidth*c));
          }
          if(count == 200){
            cursorDragged(13.00f+(realWidth*x),60.00f+(y*realWidth));
            printlvl();

          }
          if(count == 500){
            toggleOffAction();
            count = 0;
           }
           count++;
        }
}
class ApoShift4kDraw extends ApoShift4k{
	ApoShift4kDraw(){}

	public void setups(){
		size(500, 600);
  		level[0][0] = -1;
  		p[8] = -1;
  		background(255);
	}

	public void run(){
		drawInit();
                super.levels();
      		drawRectandLines();
		stringGameOutput();
	}

	public void drawInit(){
		fill(255);
  		rect(0, 0, 500, 600);
  		rect(115, 560, 260, 39);
  		line(175, 560, 175, 600);
  		line(315, 560, 315, 600);
	}

	public void drawRectandLines(){
		  rect(10, 60, 480, 480);
		  stroke(150);
		  for (int y = 0; y < width; y++) {
		    for (int x = 0; x < width; x++) {
		      if (level[y][x+width] != 0) {
		        if (level[y][x+width] == 1) fill(0);
		        if (level[y][x+width] == 2) fill(0,0,255);
		        if (level[y][x+width] == 3) fill(0,255,0);
		        if (level[y][x+width] == 4) fill(255,0,0);
		        if ((change[0] == x) && (change[3] != 0)) {
		          int changeY = change[3];
		          if (changeY + y * realWidth < 0) {
		            rect(11 + x * realWidth, 61 + y * realWidth + 480 + changeY, realWidth - 1, realWidth - 1);	
		          }
		          else if (changeY + y * realWidth > 480 - realWidth) {
		            rect(11 + x * realWidth, 61 + y * realWidth - 480 + changeY, realWidth - 1, realWidth - 1);	
		          }
		          rect(11 + x * realWidth, 61 + y * realWidth + changeY, realWidth - 1, realWidth - 1);
		        }
		        else if ((change[1] == y) && (change[2] != 0)) {
		          int changeX = change[2];
		          if (changeX + x * realWidth < 0) {
		            rect(11 + 480 + changeX + x * realWidth, 61 + y * realWidth, realWidth - 1, realWidth - 1);	
		          }
		          else if (changeX + x * realWidth > 480 - realWidth) {
		            rect(11 + x * realWidth - 480 + changeX, 61 + y * realWidth, realWidth - 1, realWidth - 1);	
		          }
		          rect(11 + x * realWidth + changeX, 61 + y * realWidth, realWidth - 1, realWidth - 1);
		        }
		        else {
		          rect(11 + x * realWidth, 61 + y * realWidth, realWidth - 1, realWidth - 1);
		        }
		      }
		    }
		  }
	    }

	public void stringGameOutput(){
	  stroke(0);
	  fill(0);
	  textSize(30);
	  String s = "ApoShift4k";
	  float w = textWidth(s);
	  text(s, 250 - w/2, 40);
	  			
	  if (p[7] > 0) {
	    fill(255);
	    rect(0, 545, 499, 55);
	    textSize(25);
	    fill(0);
	    s = "Congratulation!";
	    w = textWidth(s);
	    text(s, 250 - w/2, 570);
	    				
	    s = "Click to start the next level!";
	    w = textWidth(s);
	    text(s, 250 - w/2, 592);
	  }
	  else {
	    textSize(15);
	    s = "level: "+String.valueOf((int)(p[2] + 1))+" / "+String.valueOf(levels.length);
	    text(s, 10, 25);
	    
	    s = "moves: "+String.valueOf((int)(p[3]));
	    text(s, 10, 50);
	    					
	    s = "goal: ";
	    text(s, 445 - textWidth(s), 30);
	    
	    textSize(30);
	    s = "reset";
	    w = textWidth(s);
	    text(s, 250 - w/2, 590);
	     
	    line(135, 580, 155, 570);
	    line(135, 580, 155, 590);
	    line(355, 580, 335, 570);
	    line(355, 580, 335, 590);
	  }
	  int littleWidth = 48/width;
	  for (int y = 0; y < width; y++) {
	    for (int x = 0; x < width; x++) {
	      if (level[y][x] == 0) {fill(255);stroke(0);}
	      if (level[y][x] == 1) {fill(0);stroke(90);}
	      if (level[y][x] == 2) {fill(0,0,255);stroke(0);}
	      if (level[y][x] == 3) {fill(0,255,0);stroke(0);}
	      if (level[y][x] == 4) {fill(255,0,0);stroke(0);}
	      rect(451 + x * littleWidth, 2 + y * littleWidth, littleWidth - 1, littleWidth - 1);
	    }
	  }
	  stroke(0);
	  for (int i = 0; i < width + 1; i++) {
	    line(10 + i * realWidth, 60, 10 + i * realWidth, 480 + 60);
	    line(10, 60 + i * realWidth, 480 + 10, 60 + i * realWidth);
	  }

	}
  }

ApoShift4kDraw nshift = new ApoShift4kDraw();

public void setup(){
  nshift.setups();
}

public void draw(){
  nshift.run();
  //Uncomment next line for auto run and text output
  //nshift.changeRCXY(2,1,2,2);
}

public void mousePressed() {
  nshift.toggleOnAction(mouseX,mouseY);
}
public void mouseReleased() {
  nshift.toggleOffAction();
}
public void mouseMoved() {
  nshift.cursorMoved(mouseX,mouseY);
}
public void mouseDragged() {
  nshift.cursorDragged(mouseX, mouseY);
}

    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#F0F0F0", "ApoShift4k_Reimplementation" });
    }
}
