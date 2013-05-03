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

import java.applet.Applet;
import java.awt.Event;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class M extends Applet implements Runnable {

	private final static String[] levels = new String[] {
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
	
	public void start() {
		new Thread(this).start();
	}
	
	public void run() {		
//		setSize(640, 480); // For AppletViewer, remove later.

		// Set up the graphics stuff, double-buffering.
		BufferedImage screen = new BufferedImage(480,480,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = screen.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D appletGraphics = (Graphics2D)getGraphics();

		// Some variables to use for the fps.
		long lastTime = System.nanoTime();
		long think = lastTime;
		
		int[][] level = new int[1][1];
		level[0][0] = -1;
		
		int changeX = 240 - 60 * level[0].length/2;
		int changeY = 260 - 60 * level.length/2;
		
		int titleWidth = 100;

		// Game loop.
		while (true) {
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
								if (bWin) {
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
			g.setColor(new Color(191, 255, 180));
			g.fillRect(0, 0, 480, 480);
			g.setFont(g.getFont().deriveFont(28f).deriveFont(1));
			String s = "ApoTreePuzzle4k";
			int w = g.getFontMetrics().stringWidth(s);
			titleWidth = w;
			
			g.setColor(new Color(140, 220, 255));
			g.fillRoundRect(240 - w/2, 33 - 5, w, 43, 15, 15);
			g.fillRoundRect(480 - 70, - 5, 85, 76, 15, 15);
			g.fillRoundRect(-5, - 5, 85, 43, 15, 15);
			g.setColor(Color.BLACK);
			g.drawRoundRect(240 - 70, 33 - 5, 140, 43, 15, 15);
			g.drawRoundRect(240 - w/2, 33 - 5, w, 43, 15, 15);
			g.drawRoundRect(480 - 70, - 5, 85, 76, 15, 15);
			g.drawRoundRect(-5, - 5, 85, 43, 15, 15);
			g.setColor(new Color(140, 220, 255));
			g.fillRoundRect(240 - w/2 - 10, -5, w + 20, 43, 15, 15);

			g.setColor(Color.BLACK);
			g.drawString(s, 240 - w/2, 25);
			g.drawRoundRect(240 - w/2 - 10, -5, w + 20, 43, 15, 15);

			g.setFont(g.getFont().deriveFont(15f).deriveFont(1));
			s = "Level: "+String.valueOf((int)(values[3] + 1))+" / "+levels.length;
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 240 - w/2, 58);
			
			s = "<";
			g.drawString(s, 240 - titleWidth/2 + 15, 58);
			
			s = ">";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 240 +titleWidth/2 - 15 - w, 58);
			
			g.drawString("restart", 14, 25);
			
			s = "Shovels";
			w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 475 - w, 25);
			
			s = String.valueOf((int)(values[4]));
			g.drawString(s, 447, 58);
			
			for (int y = 0; y < level.length; y++) {
				for (int x = 0; x < level[0].length; x++) {
					if (level[y][x] > 0) {
						if ((level[y][x] == 1) || (level[y][x] == 2) || (level[y][x] == 3)) {
							g.setColor(new Color(28, 150, 12));
						} else if ((level[y][x] == 4) || (level[y][x] == 7)) {
							g.setColor(new Color(85, 165, 252));
						} else if ((level[y][x] == 5) || (level[y][x] == 8)) {
							g.setColor(new Color(255, 66, 10));
						} else if (level[y][x] == 6) {
							g.setColor(new Color(172, 109, 28));
						}
						g.fillRect(changeX + x * 60, changeY + y * 60, 60, 60);
						g.setColor(Color.BLACK);
						if ((values[0] - changeX >= x * 60) && (values[1] - changeY >= y * 60) &&
							(values[0] - changeX < (x + 1) * 60) && (values[1] - changeY < (y + 1) * 60)) {
							g.setColor(Color.WHITE);
							if ((values[4] > 0) && ((level[y][x] == 1) || (level[y][x] == 6))) {
								g.setColor(Color.YELLOW);
							}
							g.drawRect(changeX + x * 60, changeY + y * 60, 59, 59);
						}
						g.drawRect(changeX + x * 60, changeY + y * 60, 60, 60);
						
						if (level[y][x] == 2) {
							g.setColor(new Color(92, 49, 21));
							g.fillRect(changeX + x * 60 + 25, changeY + y * 60 + 20, 10, 30);
							g.setColor(Color.BLACK);
							g.drawRect(changeX + x * 60 + 25, changeY + y * 60 + 20, 10, 30);
							g.setColor(new Color(57, 211, 53));
							g.fillOval(changeX + x * 60 + 15, changeY + y * 60 - 5, 30, 30);
							g.setColor(Color.BLACK);
							g.drawOval(changeX + x * 60 + 15, changeY + y * 60 - 5, 30, 30);
						} else if (level[y][x] == 3) {
							g.setColor(new Color(92, 49, 21));
							g.fillRect(changeX + x * 60 + 27, changeY + y * 60 + 35, 6, 15);
							g.setColor(Color.BLACK);
							g.drawRect(changeX + x * 60 + 27, changeY + y * 60 + 35, 6, 15);
							g.setColor(new Color(57, 211, 53));
							g.fillOval(changeX + x * 60 + 23, changeY + y * 60 + 22, 15, 15);
							g.setColor(Color.BLACK);
							g.drawOval(changeX + x * 60 + 23, changeY + y * 60 + 22, 15, 15);
						} else if ((level[y][x] == 7) || (level[y][x] == 8)) {
							g.setColor(new Color(92, 49, 21));
							g.fillRect(changeX + x * 60 + 9, changeY + y * 60 + 25, 40, 10);
							g.setColor(Color.BLACK);
							g.drawRect(changeX + x * 60 + 9, changeY + y * 60 + 25, 40, 10);
						}
					}
				}
			}
			
			g.setFont(g.getFont().deriveFont(22f).deriveFont(1));
			
			if (values[5] > 0) {
				g.setColor(new Color(140, 220, 255));
				g.fillRoundRect(90, 190, 300, 100, 15, 15);
				g.setColor(Color.BLACK);
				g.drawRoundRect(90, 190, 300, 100, 15, 15);
				
				if (values[6] > 0) {
					s = "Congratulation";
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2, 215);
					
					s = "Click to start the next level";
				} else {
					s = "Try again";
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2, 215);
					
					s = "Save the trees";
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2, 255);
					
					s = "Click to restart the level";
				}
				g.setFont(g.getFont().deriveFont(17f).deriveFont(1));
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 240 - w/2, 285);
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
					w = g.getFontMetrics().stringWidth(s);
					int c = 1;
					while (w > 470) {
						g.setFont(g.getFont().deriveFont(17f - c).deriveFont(1));
						w = g.getFontMetrics().stringWidth(s);	
						c += 1;
					}
					g.setColor(new Color(140, 220, 255));
					g.fillRoundRect(240 - w/2 - 10, 480 - 35 + 7, w + 20, 35, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRoundRect(240 - w/2 - 10, 480 - 35 + 7, w + 20, 35, 15, 15);
					g.drawString(s, 240 - w/2, 473);
				}
			}
			
			// Draw the entire results on the screen.
			appletGraphics.drawImage(screen, 0, 0, null);

			try {
				Thread.sleep(10);
			} catch (Exception e) { /* best practice */
			};

			if (!isActive()) {
				return;
			}
		}
	}

	public boolean handleEvent(Event e) {
		switch (e.id) {
			case Event.MOUSE_DOWN:
				// mouse button pressed
				break;
			case Event.MOUSE_UP:
				// mouse button released
				values[2] = 1;
				break;
			case Event.MOUSE_MOVE:
				values[0] = e.x;
				values[1] = e.y;
				break;
			case Event.MOUSE_DRAG:
				values[0] = e.x;
				values[1] = e.y;
				break;
		}
		return false;
	}
}