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

	
String[] levelsString = new String[] {
		"00000000"+
				"00000000"+
				"04000050"+
				"00100000"+
				"00000100"+
				"00310000"+
				"00000000"+
				"00000000",
				
				"0000000000000000000000000040000000311000000000000000000000000000",
				
				"00000000" +
				"00000000" +
				"00000000" +
				"00040000" +
				"00103000" +
				"00010000" +
				"00000000" +
				"00000000",
				
				"0000000000000000000000000013100000000000004150000000000000000000",
				
				"00000000"+
				"00000000"+
				"00031000"+
				"00041000"+
				"00015000"+
				"00016000"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00000000"+
				"00000000"+
				"00041400"+
				"00000000"+
				"00015100"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00000000"+
				"00005000"+
				"00611000"+
				"00011400"+
				"00030000"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00000000"+
				"00014100"+
				"00004000"+
				"00311130"+
				"00005000"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00000000"+
				"00016100"+
				"00041000"+
				"00001400"+
				"00006000"+
				"00000000"+
				"00000000",
				
				"00500300"+
				"00100100"+
				"00064000"+
				"00011000"+
				"04000040"+
				"03111130"+
				"00000000"+
				"00000000",
				
				"0000000000000000000000000001100000311300043113400000000000000000",
				
				"0000000000000000000110000031150000400400005003000001100000000000",
				
				"00000000"+
				"00503050"+
				"00011100"+
				"00310130"+
				"00011100"+
				"00503050"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00000000"+
				"00000000"+
				"00010500"+
				"00061000"+
				"00010500"+
				"00000000"+
				"00000000",
				
				"00000000000000000005000000040000011b1100000400000005000000000000",
				
				"00000000"+
				"00000000"+
				"00054000"+
				"00411500"+
				"00145100"+
				"00011000"+
				"00000000"+
				"00000000",
				
				"0000000000000000000300000031100000010140000015000000400000000000",
				
				"0000000000000000004131000010040000300100001415000000000000000000",
				
				"0000000000000000000400000031300004111400000100000000000000000000",
				
				"0000000000431000003110000011330003311000001130000013400000000000",
				
				"0000000000000000003b0c40000101000004430000b000b00001110000000000",
				
				"0000000000014000001315000131514004151310005131000004100000000000",
				
				"0000000000000000041814000161610008101800016161000418140000000000",
				
				"0000000000140000005100000013150000416100000014000000510000000000",
				
				"00000000"+
				"00013400"+
				"00151540"+
				"00111440"+
				"00113050"+
				"00011300"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00413160"+
				"00100010"+
				"00361030"+
				"00100010"+
				"00613140"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00000000"+
				"00050100"+
				"00001500"+
				"00015000"+
				"00100000"+
				"00000000"+
				"00005000",
				
				"00000000"+
				"01030000"+
				"00500100"+
				"00107000"+
				"06001000"+
				"00010400"+
				"00000000"+
				"00000000",
				
				"00000000"+
				"00000000"+
				"10401030"+
				"0d0e0d00"+
				"30104010"+
				"00000000"+
				"00000000"+
				"00000000",
				
				"00040000"+
				"00000000"+
				"00d1d000"+
				"301b1030"+
				"00e1e000"+
				"00000000"+
				"00040000"+
				"00000000",
	};
	
	/**
	 * p[0] == Maus losgelassen
	 * p[1] == aktuelles Level
	 * p[2] == how much dices
	 * p[3] == Level geschafft Klick
	 * p[4] == Maus X-Wert
	 * p[5] == Maus Y-Wert
	 * p[6] == aktuell gedrückter Würfel x-Wert
	 * p[7] == aktuell gedrückter Würfel y-Wert
	 * p[8] == difference gedrückter Würfel x-Wert
	 * p[9] == difference gedrückter Würfel y-Wert
	 * p[10] == cX /pressed and mouse X change
	 * p[11] == cY / pressed and mouse Y change
	 * p[12] == c / hoe much change
	 */
	private static final int[] p = new int[13];
	private static boolean bBreak = false;
	
	private static byte[][] level = new byte[16][8];
	
	private static final byte changeY = 25;
	
        long lastTime = System.nanoTime();
		long think = 10000000L;

        
	 void setup() {
		size(500,650);
                level[0][0] = -1;
		p[6] = -1;
	}
	
	public void draw() {
		
		
		
		
			long now = System.nanoTime();
			long delta = now - lastTime;
			think += delta;
			
			// Update / think
			// Wenn 10 ms vergangen sind, dann denke nach
			while (think >= 10000000L) {
				think -= 10000000L;
				
				if (level[0][0] == -1) {
					if (p[1] < 0) {
						p[1] = levelsString.length - 1;
					}
					if (p[1] >= levelsString.length) {
						p[1] = 0;
					}
					String l = levelsString[p[1]];
					
					level = new byte[16][8];
					for (int y = 0; y < 8; y += 1) {
						for (int x = 0; x < level[y].length; x += 1) {
							char c = l.charAt(y * 8 + x);
							if ((c >= 48) && (c <= 57)) {
								byte value = Byte.valueOf(l.substring(y * 8 + x, y * 8 + x + 1));
								if (value <= 1) {
									level[y][x] = value;
								} else {
									level[y + 8][x] = value;
								}
							} else {
								byte value = (byte)((int)c - 95);
								level[y][x] = 1;
								level[y + 8][x] = value;
							}
						}
					}
					
					p[3] = p[2] = p[0] = 0;
					p[6] = -1;
				} else {
					if (p[0] > 0) {
						if ((p[4] > 70) && (p[4] < 110) &&
							(p[5] > 590) && (p[5] < 630)) {
							p[1] -= 1;
							level[0][0] = -1;
						} else if ((p[4] > 370) && (p[4] < 410) &&
								(p[5] > 590) && (p[5] < 630)) {
							p[1] += 1;
							level[0][0] = -1;
						} else if ((p[4] > 200) && (p[4] < 280) &&
								(p[5] > 590) && (p[5] < 630)) {
							level[0][0] = -1;
						} else if (p[3] > 0) {
							level[0][0] = -1;
							if (p[3] == 1) {
								p[1] += 1;
							}
						} else if (p[6] >= 0) {
							if (((int)(p[4])/60 != p[6]) || ((int)(p[5] - changeY)/60 != p[7])) {
								
								if (!bBreak) {
									level[p[7] + 8][p[6]] -= 1;
									if (p[10] < 0) {
										for (int i = p[6] + p[12]; i <= p[6]; i++) {
											if (i + 1 < 8) {
												level[p[7] + 8][i] = level[p[7] + 8][i + 1];
											} else {
												level[p[7] + 8][i] = 0;
											}
										}
									} else if (p[10] > 0) {
										for (int i = p[6] + p[12] - 1; i >= p[6]; i--) {
											level[p[7] + 8][i + 1] = level[p[7] + 8][i];
										}
									}
									if (p[11] < 0) {
										for (int i = p[7] + p[12]; i <= p[7]; i++) {
											if (i + 1 < 8) {
												level[i + 8][p[6]] = level[i + 8 + 1][p[6]];
											} else {
												level[i + 8][p[6]] = 0;
											}
										}
									} else if (p[11] > 0) {
										for (int i = p[7] + p[12] - 1; i >= p[7]; i--) {
											level[i + 1 + 8][p[6]] = level[i + 8][p[6]];
										}
									}
									level[p[7] + 8][p[6]] = 0;
								}
								
								// is level solved?
								boolean bWin = true;
								boolean bLoose = true;
								for (int y = 0; y < 8; y += 1) {
									for (int x = 0; x < level[y].length; x += 1) {
										if ((level[y][x] == 1) && (level[y+8][x] != 2)) {
											bWin = false;
										}
										if (level[y+8][x] > 2) {
											bLoose = false;
										}
									}
								}
								if (bWin) {
									p[3] = 1;
								} else if (bLoose) {
									p[3] = 2;
								}
							}
							
							p[6] = -1;
						}
					} else if (p[2] > 0) {
						if (p[6] < 0) {
							int x = p[4] / 60;
							int y = (p[5] - changeY) / 60;
							if ((x >= 0) && (y >= 0) && (x < 8) && (y < 8) && (level[y+8][x] > 2)) {
								p[6] = x;
								p[7] = y;
								p[2] = 0;
								p[8] = p[4] - p[6] * 60;
								p[9] = p[5] - changeY - p[7] * 60;
							}
						}
					}
				}
				
				p[0] = 0;
			}

			lastTime = now;

                        ellipseMode(CORNER);
			fill(193,183,183);
			rect(0, 0, 480, 640);

			fill(90,90,90);
			rect(0,0,480,changeY);
			rect(0,480 + changeY,480,160);
			textSize(18);
			fill(0);
			String s = "ApoDice4k";
			float w = textWidth(s);
			text(s, 240 - w/2 + 1, 20);
			fill(255,255,255);
			text(s, 240 - w/2, 18);

			textSize(15);
			s = "Level "+(p[1] + 1)+" / "+levelsString.length;
			fill(0);
			text(s, 5 + 1, 19);
			fill(255,255,255);
			text(s, 5, 17);

			for (int y = 0; y < 8; y += 1) {
				for (int x = 0; x < level[y].length; x += 1) {
					if (level[y][x] == 1) {
						fill(93,90,90);
						rect(x * 60 + 1, changeY + y * 60 + 1, 58, 58, 6, 6);
					}
					if (level[y+8][x] > 0) {
						fill(255,255,255);
						rect(x * 60 + 5, changeY + y * 60 + 5, 50, 50, 6, 6);
						if ((p[6] == x) && (p[7] == y)) {
							fill(227,23,23);
							rect(x * 60 + 5, changeY + y * 60 + 5, 50, 50, 6, 6);
						}
						fill(0);
						if ((level[y+8][x] == 3) || (level[y+8][x] == 5) || (level[y+8][x] == 7)) {
							ellipse(x * 60 + 24, changeY + y * 60 + 24, 12, 12);
						}
						if ((level[y+8][x] == 4) || (level[y+8][x] == 5) || (level[y+8][x] == 6) || (level[y+8][x] == 7)) {
							ellipse(x * 60 + 8, changeY + y * 60 + 8, 12, 12);
							ellipse(x * 60 + 40, changeY + y * 60 + 40, 12, 12);
						}
						if ((level[y+8][x] == 6) || (level[y+8][x] == 7)) {
							ellipse(x * 60 + 40, changeY + y * 60 + 8, 12, 12);
							ellipse(x * 60 + 8, changeY + y * 60 + 40, 12, 12);
						}
					}
				}
			}
			
			if (p[6] >= 0) {
				fill(128, 128, 128, 128);
				if (((int)(p[4])/60 != p[6]) || ((int)(p[5] - changeY)/60 != p[7])) {
					if (!bBreak) {
						if (p[10] < 0) {
							for (int i = p[6] + p[12]; i < p[6]; i++) {
								rect((i) * 60 + 8, changeY + p[7] * 60 + 8, 44, 44, 6, 6);
							}
						} else if (p[10] > 0) {
							for (int i = p[6] + p[12]; i > p[6]; i--) {
								rect((i) * 60 + 8, changeY + p[7] * 60 + 8, 44, 44, 6, 6);
							}
						}
						if (p[11] < 0) {
							for (int i = p[7] + p[12]; i < p[7]; i++) {
								rect((p[6]) * 60 + 8, changeY + (i) * 60 + 8, 44, 44, 6, 6);
							}
						} else if (p[11] > 0) {
							for (int i = p[7] + p[12]; i > p[7]; i--) {
								rect((p[6]) * 60 + 8, changeY + (i) * 60 + 8, 44, 44, 6, 6);
							}
						}
					}
				}
			}
			
			
			if (p[3] > 0) {
				textSize(25);
				
				String s2 = "";
				if (p[3] == 1) {
					s2 = "Congratulation";					
					s = "Click to start the next level";
				} else if (p[3] > 1) {
					s2 = " Game Over ";					
					s = "Click to restart the level";
				}
				if (p[3] > 0) {
					w = textWidth(s2);
					fill(206,206,196);
					rect(240 - w/2 - 5, 277, w + 10, 36);
					fill(0);
					rect(240 - w/2 - 5, 277, w + 10, 36);
					text(s2, 240 - w/2 + 1, 305 + 2);
					fill(255,255,255);
					text(s2, 240 - w/2, 305);
					
					fill(0);
					w = textWidth(s);
					text(s, 240 - w/2 + 1, 587 + 2);
					fill(255,255,255);
					text(s, 240 - w/2, 587);
				}
			} else {
				fill(255,255,255);
				if (p[1] == 0) {
					s = "The number on the dice shows the count of possible moves";
					w = textWidth(s);
					text(s, 240 - w/2, 540);
					
					s = "Move each dice to a gray spot with no moves remaining";
					w = textWidth(s);
					text(s, 240 - w/2, 560);
				} else if (p[1] == 1) {
					s = "A dice can push another dice";
					w = textWidth(s);
					text(s, 240 - w/2, 560);
				}
				try {
					textSize(20);
					fill(193,183,183);
					rect(200, 590, 80, 40, 6, 6);
					rect(70, 590, 40, 40, 6, 6);
					rect(370, 590, 40, 40, 6, 6);
					fill(0);
					if ((p[4] > 70) && (p[4] < 110) &&
						(p[5] > 590) && (p[5] < 630)) {
						fill(141,142,2);
					}
					line(80, 610, 100, 600);
					line(80, 610, 100, 620);
					fill(0);
					if ((p[4] > 370) && (p[4] < 410) &&
						(p[5] > 590) && (p[5] < 630)) {
						fill(142,142,2);
					}
					line(400, 610, 380, 600);
					line(400, 610, 380, 620);
					
					fill(0);
					if ((p[4] > 200) && (p[4] < 280) &&
						(p[5] > 590) && (p[5] < 630)) {
						fill(142,142,2);
					}
					s = "restart";
					w = textWidth(s);
					text(s, 240 - w/2, 618);
				} catch (Exception ex) {}
			}
			
			// Render das Ganze auf den Bildschirm
			
		}


void mousePressed(){
p[2] = 1;


}

void mouseReleased() {
p[2] = 0;
p[0] = 1;

}
void mouseMoved() {
p[4] = mouseX;
p[5] = mouseY;

}
void mouseDragged() {
   		
  p[4] =mouseX;
				p[5] = mouseY;
				
				bBreak = false;
				/*if (p[6] < 0) {
					break;
				}*/
				p[10] = p[11] = p[12] = 0;
				if (Math.abs(p[4] - (p[6] * 60 + p[8])) > Math.abs(p[5] - changeY - (p[7]  *60 + p[9]))) {
					if (p[4] - (p[6] * 60 + p[8]) < 0) {
						p[10] = -1;
					} else {
						p[10] = 1;
					}
				} else if (p[5] - changeY - (p[7] * 60 + p[9]) != 0) {
					if (p[5] - changeY - (p[7] * 60 + p[9]) < 0) {
						p[11] = -1;
					} else {
						p[11] = 1;
					}
				}
				p[12] = p[10];
				if (p[10] != 0) {
					while ((p[6] + p[12] >= 0) && (p[6] + p[12] < 8) && (level[p[7] + 8][p[6] + p[12]] > 0)) {
						p[12] += p[10];
					}
					if ((p[6] + p[12] < 0) || (p[6] + p[12] > 7)) {
						bBreak = true;
					}
				}
				if (p[11] != 0) {
					p[12] = p[11];
					while ((p[7] + p[12] >= 0) && (p[7] + p[12] < 8) && (level[p[7] + p[12] + 8][p[6]] > 0)) {
						p[12] += p[11];
					}
					if ((p[7] + p[12] < 0) || (p[7] + p[12] > 7)) {
						bBreak = true;
					}
				}                        
		




}
		


