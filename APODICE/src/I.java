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
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class I extends Applet implements Runnable {
	
	public static final String[] levelsString = new String[] {
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
	
	public void start() {
		new Thread(this).start();
	}
	
	public void run() {
//		setSize(500, 600); // für den AppletViewer
		
		// Graphische Grundlagen für das Double Buffering
		BufferedImage screen = new BufferedImage(480,640,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = screen.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D appletGraphics = (Graphics2D)getGraphics();

		// Variablen zum Zeitmessen und genau Timen wann geupdatet werden soll
		long lastTime = System.nanoTime();
		long think = 10000000L;
		
		level[0][0] = -1;
		p[6] = -1;
		
		// Game loop.
		while (true) {
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

			// Renderabschnitt
			// Hintergrund malen
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 480, 640);

			g.setColor(Color.DARK_GRAY);
			g.fillRect(0,0,480,changeY);
			g.fillRect(0,480 + changeY,480,160);
			g.setFont(g.getFont().deriveFont(18.0f).deriveFont(1));
			g.setColor(Color.black);
			String s = "ApoDice4k";
			int w = g.getFontMetrics().stringWidth(s);
			g.drawString(s, 240 - w/2 + 1, 20);
			g.setColor(Color.white);
			g.drawString(s, 240 - w/2, 18);

			g.setFont(g.getFont().deriveFont(15.0f).deriveFont(1));
			s = "Level "+(p[1] + 1)+" / "+levelsString.length;
			g.setColor(Color.black);
			g.drawString(s, 5 + 1, 19);
			g.setColor(Color.white);
			g.drawString(s, 5, 17);

			for (int y = 0; y < 8; y += 1) {
				for (int x = 0; x < level[y].length; x += 1) {
					if (level[y][x] == 1) {
						g.setColor(Color.DARK_GRAY);
						g.fillRoundRect(x * 60 + 1, changeY + y * 60 + 1, 58, 58, 6, 6);
					}
					if (level[y+8][x] > 0) {
						g.setColor(Color.WHITE);
						g.fillRoundRect(x * 60 + 5, changeY + y * 60 + 5, 50, 50, 6, 6);
						if ((p[6] == x) && (p[7] == y)) {
							g.setColor(Color.RED);
							g.drawRoundRect(x * 60 + 5, changeY + y * 60 + 5, 50, 50, 6, 6);
						}
						g.setColor(Color.BLACK);
						if ((level[y+8][x] == 3) || (level[y+8][x] == 5) || (level[y+8][x] == 7)) {
							g.fillOval(x * 60 + 24, changeY + y * 60 + 24, 12, 12);
						}
						if ((level[y+8][x] == 4) || (level[y+8][x] == 5) || (level[y+8][x] == 6) || (level[y+8][x] == 7)) {
							g.fillOval(x * 60 + 8, changeY + y * 60 + 8, 12, 12);
							g.fillOval(x * 60 + 40, changeY + y * 60 + 40, 12, 12);
						}
						if ((level[y+8][x] == 6) || (level[y+8][x] == 7)) {
							g.fillOval(x * 60 + 40, changeY + y * 60 + 8, 12, 12);
							g.fillOval(x * 60 + 8, changeY + y * 60 + 40, 12, 12);
						}
					}
				}
			}
			
			if (p[6] >= 0) {
				g.setColor(new Color(128, 128, 128, 128));
				if (((int)(p[4])/60 != p[6]) || ((int)(p[5] - changeY)/60 != p[7])) {
					if (!bBreak) {
						if (p[10] < 0) {
							for (int i = p[6] + p[12]; i < p[6]; i++) {
								g.fillRoundRect((i) * 60 + 8, changeY + p[7] * 60 + 8, 44, 44, 6, 6);
							}
						} else if (p[10] > 0) {
							for (int i = p[6] + p[12]; i > p[6]; i--) {
								g.fillRoundRect((i) * 60 + 8, changeY + p[7] * 60 + 8, 44, 44, 6, 6);
							}
						}
						if (p[11] < 0) {
							for (int i = p[7] + p[12]; i < p[7]; i++) {
								g.fillRoundRect((p[6]) * 60 + 8, changeY + (i) * 60 + 8, 44, 44, 6, 6);
							}
						} else if (p[11] > 0) {
							for (int i = p[7] + p[12]; i > p[7]; i--) {
								g.fillRoundRect((p[6]) * 60 + 8, changeY + (i) * 60 + 8, 44, 44, 6, 6);
							}
						}
					}
				}
			}
			
			
			if (p[3] > 0) {
				g.setFont(g.getFont().deriveFont(25f).deriveFont(1));
				
				String s2 = "";
				if (p[3] == 1) {
					s2 = "Congratulation";					
					s = "Click to start the next level";
				} else if (p[3] > 1) {
					s2 = " Game Over ";					
					s = "Click to restart the level";
				}
				if (p[3] > 0) {
					w = g.getFontMetrics().stringWidth(s2);
					g.setColor(Color.GRAY);
					g.fillRect(240 - w/2 - 5, 277, w + 10, 36);
					g.setColor(Color.BLACK);
					g.drawRect(240 - w/2 - 5, 277, w + 10, 36);
					g.drawString(s2, 240 - w/2 + 1, 305 + 2);
					g.setColor(Color.WHITE);
					g.drawString(s2, 240 - w/2, 305);
					
					g.setColor(Color.BLACK);
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2 + 1, 587 + 2);
					g.setColor(Color.WHITE);
					g.drawString(s, 240 - w/2, 587);
				}
			} else {
				g.setColor(Color.WHITE);
				if (p[1] == 0) {
					s = "The number on the dice shows the count of possible moves";
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2, 540);
					
					s = "Move each dice to a gray spot with no moves remaining";
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2, 560);
				} else if (p[1] == 1) {
					s = "A dice can push another dice";
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2, 560);
				}
				try {
					g.setFont(g.getFont().deriveFont(20f).deriveFont(1));
					g.setColor(Color.LIGHT_GRAY);
					g.fillRoundRect(200, 590, 80, 40, 6, 6);
					g.fillRoundRect(70, 590, 40, 40, 6, 6);
					g.fillRoundRect(370, 590, 40, 40, 6, 6);
					g.setColor(Color.BLACK);
					if ((p[4] > 70) && (p[4] < 110) &&
						(p[5] > 590) && (p[5] < 630)) {
						g.setColor(Color.YELLOW.darker().darker());
					}
					g.drawLine(80, 610, 100, 600);
					g.drawLine(80, 610, 100, 620);
					g.setColor(Color.BLACK);
					if ((p[4] > 370) && (p[4] < 410) &&
						(p[5] > 590) && (p[5] < 630)) {
						g.setColor(Color.YELLOW.darker().darker());
					}
					g.drawLine(400, 610, 380, 600);
					g.drawLine(400, 610, 380, 620);
					
					g.setColor(Color.BLACK);
					if ((p[4] > 200) && (p[4] < 280) &&
						(p[5] > 590) && (p[5] < 630)) {
						g.setColor(Color.YELLOW.darker().darker());
					}
					s = "restart";
					w = g.getFontMetrics().stringWidth(s);
					g.drawString(s, 240 - w/2, 618);
				} catch (Exception ex) {}
			}
			
			// Render das Ganze auf den Bildschirm
			appletGraphics.drawImage(screen, 0, 0, null);

			try {
				Thread.sleep(10);
			} catch (Exception e) { /** nicht schön aber selten */
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
				p[2] = 1;
				break;
			case Event.MOUSE_UP:
				// mouse button released
				p[2] = 0;
				p[0] = 1;
				break;
			case Event.MOUSE_MOVE:
				p[4] = e.x;
				p[5] = e.y;
				break;
			case Event.MOUSE_DRAG:
				p[4] = e.x;
				p[5] = e.y;
				
				bBreak = false;
				if (p[6] < 0) {
					break;
				}
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
				break;
		}
		return false;
	}
}