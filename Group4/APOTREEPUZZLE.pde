/*
 * Copyright (c) 2005-2012 Dirk Aporius <dirk.aporius@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

  String[] levels = new String[] {
		"332"+
		"112"+
		"111"+
		"211",
		
		"540"+
		"21110"+
		"01112"+
		"21110"+
		"01112",
		
		"540"+
		"21112"+
		"01110"+
		"01110"+
		"00200",
		
		"531"+
		"01110"+
		"21112"+
		"01110",
		
		"551"+
		"41211"+
		"02121"+
		"11214"+
		"02111"+
		"11100",
		
		"551"+
		"21111"+
		"11211"+
		"21112"+
		"01211"+
		"21121",
		
		"552"+
		"21240"+
		"11112"+
		"21111"+
		"11112"+
		"21100",
		
		"551"+
		"12144"+
		"21120"+
		"01212"+
		"02111"+
		"00002",
		
		"550"+
		"00100"+
		"11311"+
		"00100"+
		"00100"+
		"01210",
		
		"660"+
		"021131"+
		"012111"+
		"011213"+
		"111121"+
		"112112"+
		"011110",
		
		"661"+
		"420010"+
		"411120"+
		"412111"+
		"131113"+
		"311121"+
		"311113",
		
		"663"+
		"100011"+
		"111121"+
		"023312"+
		"011111"+
		"123311"+
		"111120",
		
		"552"+
		"11621"+
		"30010"+
		"10060"+
		"36131"+
		"00010",
		
		"661"+
		"021163"+
		"111121"+
		"311131"+
		"161144"+
		"331440"+
		"111044",
		
		"662"+
		"600066"+
		"311626"+
		"661666"+
		"311113"+
		"166631"+
		"313131",
		
		"660"+
		"010050"+
		"110111"+
		"133011"+
		"131101"+
		"113121"+
		"000110",
		
		"661"+
		"012112"+
		"311121"+
		"121163"+
		"111241"+
		"011121"+
		"051112",
		
		"661"+
		"361113"+
		"111111"+
		"131361"+
		"111262"+
		"111151"+
		"211100",
		
		"661"+
		"310051"+
		"211112"+
		"006616"+
		"006026"+
		"311612"+
		"032656",
	};
	
	/*
	 * 0 = mouseX
	 * 1 = mouseY
	 * 2 = mouse released
	 * 3 = level
	 * 4 = destroy count
	 * 5 = game lost
	 * 6 = game win
	 */
private final float[] values = new float[7];
long lastTime = System.nanoTime();
long think = lastTime;
int[][] level = new int[1][1];
int changeX = 240 - 60 * level[0].length/2;
int changeY = 260 - 60 * level.length/2;
int titleWidth = 100;
boolean run = false;	

void setup()
{
size(480, 480);
level[0][0] = -1;

}
	
	public void draw() {		

		// Game loop.
		
			long now = System.nanoTime();
			long delta = now - lastTime;
			think += delta;
			
			// Update / think
			while (think >= 10000000L) {
				think -= 10000000L;
				
				if (level[0][0] < 0) {
					if (values[3] < 0) {
						values[3] = levels.length - 1;
					}
					if (values[3] >= levels.length) {
						values[3] = 0;
					}
					
					String curLevel = levels[(int)values[3]];
					level = new int[Integer.valueOf(curLevel.substring(1,2))][Integer.valueOf(curLevel.substring(0,1))];
					values[4] = Integer.valueOf(curLevel.substring(2,3));
					for (int y = 0; y < level.length; y++) {
						for (int x = 0; x < level[0].length; x++) {
							level[y][x] = Integer.valueOf(curLevel.substring(3 + x + y * level[0].length,4 + x + y * level[0].length));
						}
					}
					
					changeX = 240 - 60 * level[0].length/2;
					changeY = 260 - 60 * level.length/2;
					values[5] = values[6] = 0;
				} else if (values[2] == 1) {
					if (values[5] > 0) {
						if (values[6] > 0) {
							values[3] += 1;
						}
						level[0][0] = -1;
						think += 10000000L;
					} else if ((values[0] >= 0) && (values[1] >= 0) &&
							(values[0] < 70) && (values[1] < 30)) {
						level[0][0] = -1;
						think += 10000000L;
					} else if ((values[0] >= 240 - titleWidth/2) && (values[1] >= 33) &&
							(values[0] < 240 - titleWidth/2 + 50) && (values[1] < 66)) {
						values[3] -= 1;
						level[0][0] = -1;
						think += 10000000L;
					} else if ((values[0] >= 240 + titleWidth/2 - 50) && (values[1] >= 33) &&
							(values[0] < 240 + titleWidth/2) && (values[1] < 66)) {
						values[3] += 1;
						level[0][0] = -1;
						think += 10000000L;
					} else if ((values[0] >= changeX) && (values[1] >= changeY) &&
						(values[0] < changeX + 60 * level[0].length) && (values[1] < changeY + 60 * level.length)) {
						int x = (int)(values[0] - changeX) / 60;
						int y = (int)(values[1] - changeY) / 60;
						if (level[y][x] == 2) {
							level[y][x] = 4;
							boolean[] bBreak = new boolean[4];
							int count = 1;
							while ((!bBreak[0]) || (!bBreak[1]) || (!bBreak[2]) || (!bBreak[3])) {
								if ((!bBreak[0]) && (y - count >= 0)) {
									if (level[y-count][x] == 2) {
										level[y-count][x] = 7;
										values[6] = -1;
										values[5] = 1;
									} else if (level[y-count][x] == 1) {
										level[y-count][x] = 4;
									} else if (level[y-count][x] == 3) {
										level[y-count][x] = 2;
										bBreak[0] = true;
									} else if (level[y-count][x] != 7) {
										bBreak[0] = true;
									}
								} else {
									bBreak[0] = true;
								}
								if ((!bBreak[1]) && (x + count < level[0].length)) {
									if (level[y][x+count] == 2) {
										level[y][x+count] = 7;
										values[6] = -1;
										values[5] = 1;
									} else if (level[y][x+count] == 1) {
										level[y][x+count] = 4;
									} else if (level[y][x+count] == 3) {
										level[y][x+count] = 2;
										bBreak[1] = true;
									} else if (level[y][x+count] != 7) {
										bBreak[1] = true;
									}
								} else {
									bBreak[1] = true;
								}
								if ((!bBreak[2]) && (y + count < level.length)) {
									if (level[y+count][x] == 2) {
										level[y+count][x] = 7;
										values[6] = -1;
										values[5] = 1;
									} else if (level[y+count][x] == 1) {
										level[y+count][x] = 4;
									} else if (level[y+count][x] == 3) {
										level[y+count][x] = 2;
										bBreak[2] = true;
									} else if (level[y+count][x] != 7) {
										bBreak[2] = true;
									}
								} else {
									bBreak[2] = true;
								}
								if ((!bBreak[3]) && (x - count >= 0)) {
									if (level[y][x-count] == 2) {
										level[y][x-count] = 7;
										values[6] = -1;
										values[5] = 1;
									} else if (level[y][x-count] == 1) {
										level[y][x-count] = 4;
									} else if (level[y][x-count] == 3) {
										level[y][x-count] = 2;
										bBreak[3] = true;
									} else if (level[y][x-count] != 7) {
										bBreak[3] = true;
									}
								} else {
									bBreak[3] = true;
								}
								count += 1;
							}
							boolean[][] bLava = new boolean[level.length][level[0].length];
							for (y = 0; y < level.length; y++) {
								for (x = 0; x < level[0].length; x++) {
									if (level[y][x] == 5) {
										bLava[y][x] = true;
									}
								}
							}
							for (y = 0; y < level.length; y++) {
								for (x = 0; x < level[0].length; x++) {
									if (bLava[y][x]) {
										if ((y - 1 >= 0) && (level[y-1][x] != 0) && (level[y-1][x] != 4) && (level[y-1][x] != 6)) {
											if ((level[y-1][x] == 2) || (level[y-1][x] == 3)) {
												values[5] = level[y-1][x] = 8;
											} else if (level[y-1][x] < 6) {
												level[y-1][x] = 5;
											}
										}
										if ((x - 1 >= 0) && (level[y][x-1] != 0) && (level[y][x-1] != 4) && (level[y][x-1] != 6)) {
											if ((level[y][x-1] == 2) || (level[y][x-1] == 3)) {
												values[5] = level[y][x-1] = 8;
											} else if (level[y][x-1] < 6) {
												level[y][x-1] = 5;
											}
										}
										if ((x + 1 < level[0].length) && (level[y][x+1] != 0) && (level[y][x+1] != 4) && (level[y][x+1] != 6)) {
											if ((level[y][x+1] == 2) || (level[y][x+1] == 3)) {
												values[5] = level[y][x+1] = 8;
											} else if (level[y][x+1] < 6) {
												level[y][x+1] = 5;
											}
										}
										if ((y + 1 < level.length) && (level[y+1][x] != 0) && (level[y+1][x] != 4) && (level[y+1][x] != 6)) {
											if ((level[y+1][x] == 2) || (level[y+1][x] == 3)) {
												values[5] = level[y+1][x] = 8;
											} else if (level[y+1][x] < 6) {
												level[y+1][x] = 5;
											}
										}
									}
								}
							}
							if (values[5] <= 0) {
								boolean bWin = true;
								for (y = 0; y < level.length; y++) {
									for (x = 0; x < level[0].length; x++) {
										if ((level[y][x] == 2) || (level[y][x] == 3)) {
											bWin = false;
										}
									}
								}
								if ((bWin)&&(!run)) {
									values[5] = values[6] = 1;
								}
							}
						} else if (values[4] > 0) {
							if (level[y][x] == 1) {
								level[y][x] = 0;
								values[4] -= 1;
							} else if (level[y][x] == 6) {
								level[y][x] = 1;
								values[4] -= 1;
							}
						}
					}

                                              values[2] = 0;
                                            
                                          }
			}

			lastTime = now;

			// Render
                        ellipseMode(CORNER);
                        
                        
			fill(191, 255, 180);
			rect(0, 0, 480, 480);
			float w = titleWidth = 230;
			
			
			
			
			fill(140, 220, 255);
			rect(240 - w/2, 33 - 5, w, 43, 15, 15);
			rect(480 - 70, - 5, 85, 76, 15, 15);
			rect(-5, - 5, 85, 43, 15, 15);
                        rect(240 - 70, 33 - 5, 140, 43, 15, 15);
			rect(240 - w/2, 33 - 5, w, 43, 15, 15);
			rect(480 - 70, - 5, 85, 76, 15, 15);
			rect(-5, - 5, 85, 43, 15, 15);
			fill(140, 220, 255);
			rect(240 - w/2 - 10, -5, w + 20, 43, 15, 15);

			
			rect(240 - w/2 - 10, -5, w + 20, 43, 15, 15);

			fill(0);
                        String s = "ApoTreePuzzle4k";   
                        w = textWidth(s);
                        textSize(28);
                        text(s, 125, 25);
                        
                        s = "Level: "+String.valueOf((int)(values[3] + 1))+" / "+levels.length;
			w = textWidth(s);
                        textSize(15);
			text(s, 240 - w/2, 58);
			
                        text("<", 240 - titleWidth/2 + 15, 58);
                        text(">", 240 + titleWidth/2 - 15 - textWidth(">"), 58);
                        text("restart", 14, 25);
                        s = "Shovels";
			w = textWidth(s);
			text(s, 475 - w, 25);
			
			s = String.valueOf((int)(values[4]));
			text(s, 447, 58);
			

			
			
			
			for (int y = 0; y < level.length; y++) {
				for (int x = 0; x < level[0].length; x++) {
					if (level[y][x] > 0) {
						if ((level[y][x] == 1) || (level[y][x] == 2) || (level[y][x] == 3)) {
							fill(28, 150, 12);
						} else if ((level[y][x] == 4) || (level[y][x] == 7)) {
							fill(85, 165, 252);
						} else if ((level[y][x] == 5) || (level[y][x] == 8)) {
							fill(255, 66, 10);
						} else if (level[y][x] == 6) {
							fill(172, 109, 28);
						}
						rect(changeX + x * 60, changeY + y * 60, 60, 60);
						
						if ((values[0] - changeX >= x * 60) && (values[1] - changeY >= y * 60) &&
							(values[0] - changeX < (x + 1) * 60) && (values[1] - changeY < (y + 1) * 60)) {
							fill(255,255,255);
							if ((values[4] > 0) && ((level[y][x] == 1) || (level[y][x] == 6))) {
								fill(255,255,255);
							}
							rect(changeX + x * 60, changeY + y * 60, 59, 59);
						}
						rect(changeX + x * 60, changeY + y * 60, 60, 60);
						fill(0);
						if (level[y][x] == 2) {
							fill(92, 49, 21);
							rect(changeX + x * 60 + 25, changeY + y * 60 + 20, 10, 30);							
							rect(changeX + x * 60 + 25, changeY + y * 60 + 20, 10, 30);
							fill(57, 211, 53);
							ellipse(changeX + x * 60 + 15, changeY + y * 60 - 5, 30, 30);							
							ellipse(changeX + x * 60 + 15, changeY + y * 60 - 5, 30, 30);
                                                        fill(0);
						} else if (level[y][x] == 3) {
							fill(92, 49, 21);
							rect(changeX + x * 60 + 27, changeY + y * 60 + 35, 6, 15);							
							rect(changeX + x * 60 + 27, changeY + y * 60 + 35, 6, 15);
							fill(57, 211, 53);
							ellipse(changeX + x * 60 + 23, changeY + y * 60 + 22, 15, 15);							
							ellipse(changeX + x * 60 + 23, changeY + y * 60 + 22, 15, 15);
                                                        fill(0);
						} else if ((level[y][x] == 7) || (level[y][x] == 8)) {
							fill(92, 49, 21);
							rect(changeX + x * 60 + 9, changeY + y * 60 + 25, 40, 10);
							rect(changeX + x * 60 + 9, changeY + y * 60 + 25, 40, 10);
                                                        fill(0);
						}
					}
				}
			}
			
			textSize(21);
			
			if (values[5] > 0) {
				fill(140, 220, 255);
				rect(90, 190, 300, 100, 15, 15);
				
				fill(0);
				if (values[6] > 0) {                                        
					s = "Congratulation";
					w = textWidth(s);
					text(s, 240 - w/2, 215);
					
					s = "Click to start the next level";
				} else {
					s = "Try again";
					w = textWidth(s);
					text(s, 240 - w/2, 215);
					
					s = "Save the trees";
					w = textWidth(s);
					text(s, 240 - w/2, 255);
					
					s = "Click to restart the level";
				}
				textSize(17);
				w = textWidth(s);
				text(s, 240 - w/2, 285);
			} else {
				s = "";
				if (values[3] == 0) {
					s = "Click every tree to win";
				} else if (values[3] == 1) {
					s = "Trees turn to water";
				} else if (values[3] == 2) {
					s = "Water drowns other trees";
				} else if (values[3] == 3) {
					s = "If you have a shovel, you can destroy gras fields";
				} else if (values[3] == 8) {
					s = "Small trees will grow with water";
				} else if (values[3] == 12) {
					s = "Dirt can be destroyed with the shovel";
				} else if (values[3] == 15) {
					s = "Lava grows each click";
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
					rect(240 - w/2 - 10, 480 - 35 + 7, w + 20, 35, 15, 15);					
					rect(240 - w/2 - 10, 480 - 35 + 7, w + 20, 35, 15, 15);
                                        fill(0);
					text(s, 240 - w/2, 473);
				}
			}
			
			
		
	
}
void mouseReleased() {
    values[2] = 1;
}
void mouseMoved() {
    values[0] = mouseX;
    values[1] = mouseY;
}
void mouseDragged() {
    values[0] = mouseX;
    values[1] = mouseY;
}




	

