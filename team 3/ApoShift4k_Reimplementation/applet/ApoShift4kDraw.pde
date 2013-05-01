class ApoShift4kDraw extends ApoShift4k{
	ApoShift4kDraw(){}

	void setups(){
		size(500, 600);
  		level[0][0] = -1;
  		p[8] = -1;
  		background(255);
	}

	void run(){
		drawInit();
                super.levels();
      		drawRectandLines();
		stringGameOutput();
	}

	void drawInit(){
		fill(255);
  		rect(0, 0, 500, 600);
  		rect(115, 560, 260, 39);
  		line(175, 560, 175, 600);
  		line(315, 560, 315, 600);
	}

	void drawRectandLines(){
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

	void stringGameOutput(){
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
