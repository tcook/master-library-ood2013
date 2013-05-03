/*
 * Copyright (c) 2005-2013 Dirk Aporius <dirk.aporius@gmail.com>
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
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class C extends Applet implements Runnable {
	
	/**
	 * p[0] == X-Wert Maus
	 * p[1] == Y-Wert Maus
	 * p[2] == aktuelles Level
	 * p[3] == noch benoetigte Anzahl an Lines
	 * p[4] == Spiel gestartet
	 * p[5] == Combo
	 * p[6] == Punkte
	 * p[7] == Maustaste gedrückt
	 * p[8] == turns
	 * p[9] == 0 = nuescht or geclicked = 1 or delete = 2 or delFrame = 3 or swap = 4 or go through = 5
	 * p[10] == selected x
	 * p[11] == selected y
	 * p[12] == time
	 * p[13] == max combo
	 * p[14] == goodie swap
	 * p[15] == goodie go through
	 * p[16] == color change
	 */
	private final int[] p = new int[17];
	
	public void start() {
		new Thread(this).start();
	}
	
	public void run() {
		setSize(480, 640); // für den AppletViewer

		// Graphische Grundlagen für das Double Buffering
		BufferedImage screen = new BufferedImage(480,640,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = screen.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D appletGraphics = (Graphics2D)getGraphics();

		// Variablen zum Zeitmessen und genau Timen wann geupdatet werden soll
		long lastTime = System.nanoTime();
		long think = lastTime;
		final int[][] level = new int[7][7];
		final int time = 300;
		final int maxLines = 40;
		int x, y, i;
		final int[] next = new int[6];
		boolean[][] free = new boolean[7][7];
		
		// Game loop.
		while (true) {
			long now = System.nanoTime();
			long delta = now - lastTime;
			think += delta;
			int mouseX = (int)((p[0] - 2) / 68);
			int mouseY = (int)((p[1] - 162) / 68);
			
			// Update / think
			// Wenn 10 ms vergangen sind, dann denke nach
			while (think >= 10000000L) {
				think -= 10000000L;

				mouseX = (int)((p[0] - 2) / 68);
				mouseY = (int)((p[1] - 162) / 68);
				if (p[1] - 162 < 0) {
					mouseY = -1;
				}
				
				if (p[4] == 0) {
					if (p[7] != 0) {
						if ((p[1] > 285) && (p[1] < 355)) {
							p[4] = 1;
							p[3] = maxLines;
							p[2] = p[8] = p[5] = p[6] = p[13] = 0;
							p[14] = p[15] = p[9] = 2;
							p[10] = -1;
							p[16] = -1;
							for (y = 0; y < level.length; y++) {
								for (x = 0; x < level[0].length; x++) {
									level[y][x] = 0;
								}
							}
							free = new boolean[7][7];
							i = 0;
							while (i < 6) {
								next[i] = 0;
								if (p[2] + 3 > i) {
									next[i] = (int)(Math.random() * 5) + 1;
								}
								i += 1;
							}
							i = 0;
							while (i < 6) {
								if (next[i] == 0) break;
								do {
									x = (int)(Math.random() * 7);
									y = (int)(Math.random() * 7);
								} while (level[y][x] != 0);
								level[y][x] = (int)(Math.random() * 5) + 1;
								free[y][x] = true;
								i += 1;
							}
							p[12] = time;
						}
					}
				} else {
					if ((p[1] < 50) && (p[7] != 0)) {
						if (p[0] >= 430) {
							p[4] = 0;
						} else if ((p[0] >= 375) && (p[0] <= 424) && (p[14] > 0)) {
							p[9] = 4;
						} else if ((p[0] >= 320) && (p[0] <= 369) && (p[15] > 0)) {
							p[9] = 5;
						}
					} else
					if ((p[7] != 0) && (mouseY >= 0) && (mouseY < 7) && (mouseX >= 0) && (mouseX < 7) && (p[12] <= 0)) {
						if (((p[9] == 0) || (((p[9] == 4) || (p[9] == 5)) && (p[10] < 0))) && (level[mouseY][mouseX] != 0)) {
							free = new boolean[7][7];
							free[mouseY][mouseX] = true;
							if (p[9] == 0) p[9] = 1;
							i = level[mouseY][mouseX];
							level[mouseY][mouseX] = 0;
							a(level, free, mouseX, mouseY, mouseX, mouseY);
							level[mouseY][mouseX] = i;
							p[10] = mouseX;
							p[11] = mouseY;
							p[16] = i;
						} else if ((p[9] == 1) || ((p[9] == 4) || (p[9] == 5)) && (p[10] >= 0)) {
							if ((p[10] == mouseX) && (p[11] == mouseY)) {
								p[9] = 0;
								p[10] = -1;
							} else if ((free[mouseY][mouseX]) || ((p[9] == 5) && (level[mouseY][mouseX] == 0)) || ((p[9] == 4) && (level[mouseY][mouseX] != 0))) {
								int old = level[mouseY][mouseX];
								level[mouseY][mouseX] = level[p[11]][p[10]];								
								if (p[9] == 4) {
									if (!free[mouseY][mouseX]) {
										p[14] -= 1;
									}
									level[p[11]][p[10]] = old;
								} else {
									level[p[11]][p[10]] = 0;
									if ((p[9] == 5) && (!free[mouseY][mouseX])) {
										p[15] -= 1;
									}
								}
								p[9] = 1;
								p[8] += 1;
								free = new boolean[7][7];
								for (int m = 0; m < 2; m += 1) {

									// vertcial check
									for (x = 0; x < level[0].length; x++) {
										i = 1;
										int value = level[0][x];
										for (y = 1; y < level.length; y++) {
											if ((value != level[y][x]) || (y + 1 >= level.length)) {
												if (value == level[y][x]) {
													i += 1;
												}
												if (i >= 4) {
													if ((m == 1) && (p[9] != 1)) {
														p[9] = 1;
														free = new boolean[7][7];
													}
													p[6] += (20 + (i-4) * 8) * (p[2] + 1) * (p[5] + 1);
													p[5] += 1;
													p[12] = time;
													int start = 1;
													if (value == level[y][x]) start = 0;
													for (int k = start; k <= i - (1-start); k++) {
														free[y-k][x] = true;
													}
													p[3] -= 1;
												}
												value = level[y][x];
												i = 1;
											} else {
												if (level[y][x] == 0) {
													value = i = 0;
												} else {
													i += 1;
												}
											}
										}
									}
									// horizontal check
									for (y = 0; y < level.length; y++) {
										i = 1;
										int value = level[y][0];
										for (x = 1; x < level.length; x++) {
											if ((value != level[y][x]) || (x + 1 >= level[y].length)) {
												if (value == level[y][x]) {
													i += 1;
												}
												if (i >= 4) {
													if ((m == 1) && (p[9] != 1)) {
														p[9] = 1;
														free = new boolean[7][7];
													}
													p[6] += (20 + (i-4) * 8) * (p[2] + 1) * (p[5] + 1);
													p[5] += 1;
													p[12] = time;
													int start = 1;
													if (value == level[y][x]) start = 0;
													for (int k = start; k <= i - (1-start); k++) {
														free[y][x-k] = true;
													}
													p[3] -= 1;
												}
												value = level[y][x];
												i = 1;
											} else {
												if (level[y][x] == 0) {
													value = i = 0;
												} else {
													i += 1;
												}
											}
										}
									}
									
									// diagonal check
									for (x = 0; x < 4; x++) {
										for (y = 0; y < 4; y++) {
											i = 1;
											int value = level[y][x];
											for (int z = 1; z < 7; z++) {
												if ((x + z >= 7) || (y + z >= 7)) {
													break;
												}
												if ((value != level[y+z][x+z]) || (y + z + 1 >= level.length) || (x + z + 1 >= level[y].length)) {
													if (value == level[y+z][x+z]) {
														i += 1;
													}
													if (i >= 4) {
														if ((m == 1) && (p[9] != 1)) {
															p[9] = 1;
															free = new boolean[7][7];
														}
														p[6] += (20 + (i-4) * 8) * (p[2] + 1) * (p[5] + 1);
														p[5] += 1;
														p[12] = time;
														int start = 1;
														if (value == level[y+z][x+z]) start = 0;
														for (int k = start; k <= i - (1-start); k++) {
															free[y+z-k][x+z-k] = true;
														}
														p[3] -= 1;
													}
													value = level[y+z][x+z];
													i = 1;
												} else {
													if (level[y+z][x+z] == 0) {
														value = i = 0;
													} else {
														i += 1;
													}
												}
											}
										}
									}
									
									for (x = 6; x > 2; x--) {
										for (y = 0; y < 4; y++) {
											i = 1;
											int value = level[y][x];
											for (int z = 1; z < 7; z++) {
												if ((x - z < 0) || (y + z >= level.length)) {
													break;
												}
												if ((value != level[y+z][x-z]) || (y + z + 1 >= level.length) || (x - z - 1 < 0)) {
													if (value == level[y+z][x-z]) {
														i += 1;
													}
													if (i >= 4) {
														if ((m == 1) && (p[9] != 1)) {
															p[9] = 1;
															free = new boolean[7][7];
														}
														p[6] += (20 + (i-4) * 8) * (p[2] + 1) * (p[5] + 1);
														p[5] += 1;
														p[12] = time;
														int start = 1;
														if (value == level[y+z][x-z]) start = 0;
														for (int k = start; k <= i - (1-start); k++) {
															free[y+z-k][x-z+k] = true;
														}
														p[3] -= 1;
													}
													value = level[y+z][x-z];
													i = 1;
												} else {
													if (level[y+z][x-z] == 0) {
														value = i = 0;
													} else {
														i += 1;
													}
												}
											}
										}
									}
									
									if (p[5] > p[13]) {
										p[13] = p[5];
									}
									
									// no delete found
									if (p[12] <= 0) {
										p[9] = 2;
										p[5] = i = 0;
										while (i < 6) {
											if (next[i] != 0) {										
												do {
													x = (int)(Math.random() * 7);
													y = (int)(Math.random() * 7);
												} while (level[y][x] != 0);
												level[y][x] = next[i];
												free[y][x] = true;
												boolean bLose = true;
												for (x = 0; x < level[0].length; x++) {
													for (y = 0; y < level.length; y++) {
														if (level[y][x] == 0) {
															bLose = false;
															break;
														}
													}
												}
												if (bLose) {
													p[4] = 0;
													break;
												}
											}
											next[i] = 0;
											if (p[2] + 3 > i) {
												next[i] = (int)(Math.random() * 5) + 1;
											}
											i += 1;
										}
										p[12] = time;
									} else {
										break;
									}
								}
								p[10] = -1;
							} else {
								p[9] = 0;
								p[10] = -1;
							}
						} else if (p[12] <= 0) {
							p[9] = 0;
						}
					}
				}
				
				if (p[12] > 0) {
					p[12] -= 10;
					if (p[12] <= 0) {
						p[12] = 0;
						if (p[9] == 1) {
							p[9] = 0;
							for (x = 0; x < level[0].length; x++) {
								for (y = 0; y < level.length; y++) {
									if (free[y][x]) level[y][x] = 0;
								}
							}
							if (p[3] <= 0) {
								p[3] = maxLines;
								p[2] += 1;
								p[15] += 1;
								if (p[2] % 2 == 0) {
									p[14] += 1;
								}
							}
						}
						if (p[9] == 2) {
							p[9] = 0;
							
						}
						free = new boolean[7][7];
					}
				}
				p[7] = 0;
			}

			lastTime = now;

			// Renderabschnitt
			// Hintergrund malen
			g.setColor(new Color(239, 239, 239));
			g.fillRect(0, 0, 480, 640);
			g.setFont(g.getFont().deriveFont(20f).deriveFont(1));
			
			if (p[4] > 0) {
				for (y = 0; y < level.length; y++) {
					for (x = 0; x < level[0].length; x++) {
						if (level[y][x] > 0) {
							if (level[y][x] == 1) {
								g.setColor(new Color(255, 68, 68));
							}
							if (level[y][x] == 2) {
								g.setColor(new Color(51, 181, 229));
							}
							if (level[y][x] == 3) {
								g.setColor(new Color(153, 204, 0));
							}
							if (level[y][x] == 4) {
								g.setColor(new Color(170, 102, 204));
							}
							if (level[y][x] == 5) {
								g.setColor(new Color(255, 187, 51));
							}
							if ((p[12] <= 0) || (!free[y][x])) {
								g.fillRect(3 + x * 68, 163 + y * 68, 66, 66);
							} else {
								g.setColor(new Color(224,224,224));
								g.fillRect(3 + x * 68, 163 + y * 68, 66, 66);
							}
							
							if ((mouseX == x) && (mouseY == y)) {
								g.setStroke(new BasicStroke(3));
								g.setColor(Color.YELLOW);
								g.drawRect(3 + x * 68, 163 + y * 68, 66, 66);
								g.setStroke(new BasicStroke(1));
							}
						} else {
							g.setColor(new Color(224,224,224));
							g.fillRect(3 + x * 68, 163 + y * 68, 66, 66);
						}
						if ((p[10] >= 0) && (level[y][x] == 0) && (!free[y][x])) {
							g.setStroke(new BasicStroke(3));
							g.setColor(new Color(239, 239, 239));
							g.drawLine(3 + x * 68, 163 + y * 68, 3 + x * 68 + 66, 163 + y * 68 + 66);
							g.drawLine(3 + x * 68 + 66, 163 + y * 68, 3 + x * 68, 163 + y * 68 + 66);
							g.setStroke(new BasicStroke(1));
						}
						if ((p[10] == x) && (p[11] == y)) {
							g.setStroke(new BasicStroke(3));
							g.setColor(Color.RED);
							g.drawRect(3 + x * 68, 163 + y * 68, 66, 66);
							g.setStroke(new BasicStroke(1));
						}
					}
				}
				
				for (y = 0; y < level.length; y++) {
					for (x = 0; x < level[0].length; x++) {
						Composite composite = g.getComposite();
						if ((level[y][x] == 1) || ((mouseX == x) && (mouseY == y) && (p[16] == 1))) {
							g.setColor(new Color(255, 68, 68));
						}
						if ((level[y][x] == 2) || ((mouseX == x) && (mouseY == y) && (p[16] == 2))) {
							g.setColor(new Color(51, 181, 229));
						}
						if ((level[y][x] == 3) || ((mouseX == x) && (mouseY == y) && (p[16] == 3))) {
							g.setColor(new Color(153, 204, 0));
						}
						if ((level[y][x] == 4) || ((mouseX == x) && (mouseY == y) && (p[16] == 4))) {
							g.setColor(new Color(170, 102, 204));
						}
						if ((level[y][x] == 5) || ((mouseX == x) && (mouseY == y) && (p[16] == 5))) {
							g.setColor(new Color(255, 187, 51));
						}
						if (level[y][x] > 0) {
							if ((p[12] > 0) && (free[y][x])) {
								float per = (float)p[12] * 0.7f / (float)time;
								if (p[9] == 1) {
									i = (int)((0.7f - per) * 60 + 66);
									g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(per)));
									g.fillRect(36 - i/2 + x * 68, 196 - i/2 + y * 68, i, i);
								} else {
									i = (int)((per) * 66 + 66);
									g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(1 - per)));
									g.fillRect(36 - i/2 + x * 68, 196 - i/2 + y * 68, i, i);
								}
							}
						} else if ((mouseX == x) && (mouseY == y) && (p[10] >= 0) && ((free[y][x]) || (p[9] == 5))) {
							g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(0.5)));
							g.fillRect(3 + x * 68, 163 + y * 68, 66, 66);
						}

						g.setComposite(composite);
					}
				}
				
				for (y = 0; y < 2; y++) {
					for (x = 0; x < 3; x++) {
						if (next[x + y * 3] == 1) {
							g.setColor(new Color(255, 68, 68));
						}
						if (next[x + y * 3] == 2) {
							g.setColor(new Color(51, 181, 229));
						}
						if (next[x + y * 3] == 3) {
							g.setColor(new Color(153, 204, 0));
						}
						if (next[x + y * 3] == 4) {
							g.setColor(new Color(170, 102, 204));
						}
						if (next[x + y * 3] == 5) {
							g.setColor(new Color(255, 187, 51));
						}
						if (next[x + y * 3] == 0) {
							g.setColor(new Color(224,224,224));
							g.drawRect(370 + x * 32, 98 + y * 32, 30, 30);
						} else {
							g.fillRect(370 + x * 32, 98 + y * 32, 30, 30);
						}
					}
				}

				g.setColor(Color.YELLOW);
				i = (int)((maxLines-p[3]) * 300f / (float)(maxLines));
				g.fillRect(0, 0, i, 35);
				
				g.setColor(new Color(50, 50, 50));
				g.drawString("score", 5, 68);
				g.drawString("combo", 5, 128);
				g.drawString("next", 375, 95);

				g.setFont(g.getFont().deriveFont(35f).deriveFont(1));
				g.drawString(""+p[6], 5, 100);
				g.drawString(p[5]+" x", 5, 160);
				g.drawString("Level "+(p[2] + 1), 5, 30);
				
				if ((p[0] >= 429) && (p[0] <= 479) && (p[1] < 50)) {
					g.setColor(new Color(255, 187, 51));
				}
				g.drawRect(429, 0, 50, 50);
				g.drawString("x", 445, 36);
				
				g.setColor(new Color(50, 50, 50));
				g.setFont(g.getFont().deriveFont(15f).deriveFont(1));
				g.drawString(p[3]+" matches to next level", 120, 50);
				if (p[14] > 0) {
					if (p[9] == 4) {
						g.setColor(new Color(150, 150, 150));
					}
					if ((p[0] >= 374) && (p[0] <= 423) && (p[1] < 50)) {
						g.setColor(new Color(255, 187, 51));
					}
					g.drawRect(374, 0, 50, 50);
					g.drawString(p[14]+"", 395, 48);
					g.drawLine(385, 17, 415, 17);
					g.setColor(new Color(255, 187, 51));
					g.fillRect(380, 10, 15, 15);
					g.setColor(new Color(255, 68, 68));
					g.fillRect(405, 10, 15, 15);
				}
				g.setColor(new Color(50, 50, 50));
				if (p[15] > 0) {
					if (p[9] == 5) {
						g.setColor(new Color(150, 150, 150));
					}
					if ((p[0] >= 319) && (p[0] <= 368) && (p[1] < 50)) {
						g.setColor(new Color(255, 187, 51));
					}
					g.drawRect(319, 0, 50, 50);
					g.drawString(p[15]+"", 340, 48);
					
					g.drawString("x", 330, 22);
					g.setColor(new Color(153, 204, 0));
					g.fillRect(350, 10, 15, 15);
				}
				
			} else {
				g.setColor(new Color(224, 224, 224));
				g.fillRect(100, 285, 280, 70);
				g.setColor(Color.RED);
				g.fillRect(120, 290, 60, 60);
				g.setColor(new Color(239, 239, 239));
				g.fillPolygon(new int[] {135, 165, 135}, new int[] {305, 320, 335}, 3);
				g.setColor(new Color(50, 50, 50));
				g.drawString("Start the game", 190, 330);
				if ((p[0] >= 100) && (p[0] <= 380) && (p[1] >= 285) && (p[1] <= 355)) {
					g.setColor(new Color(255, 187, 51));
				}
				g.drawRect(100, 285, 280, 70);
				
				g.setColor(new Color(50, 50, 50));
				if (p[8] > 0) {
					g.setFont(g.getFont().deriveFont(35f).deriveFont(1));
					String s = "Score: "+p[6];
					g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, 40);

					g.setFont(g.getFont().deriveFont(30f).deriveFont(1));
					s = "Level: "+(p[2]+1);
					g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, 100);
					
					s = "Turns: "+p[8];
					g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, 140);
					
					s = "Max Combo: "+p[13];
					g.drawString(s, 240 - g.getFontMetrics().stringWidth(s)/2, 180);
				}
			}
			
			// Render das Ganze auf den Bildschirm
			appletGraphics.drawImage(screen, 0, 0, null);

			try {
				Thread.sleep(10);
			} catch (Exception e) { /** nicht schön aber selten */
			}
			;

			if (!isActive()) {
				return;
			}
		}
	}
	
	public final void a(int[][] level, boolean[][] free, int x, int y, int startX, int startY) {
		if ((free[y][x]) && ((x != startX) || (y != startY))) {
		} else if ((level[y][x] != 0) && ((x != startX) || (y != startY))) {
			free[y][x] = false;
			return;
		} else {
			free[y][x] = true;
			if (x - 1 >= 0) {
				a(level, free, x-1, y, startX, startY);
			}
			if (y - 1 >= 0) {
				a(level, free, x, y-1, startX, startY);
			}
			if (x + 1 < free[0].length) {
				a(level, free, x+1, y, startX, startY);
			}
			if (y + 1 < free.length) {
				a(level, free, x, y + 1, startX, startY);
			}
		}
	}
	
	public boolean handleEvent(Event e) {
		switch (e.id) {
			case Event.MOUSE_DOWN:
				p[7] = 1;
				break;
			case Event.MOUSE_UP:
				break;
			case Event.MOUSE_MOVE:
			case Event.MOUSE_DRAG:
				p[0] = e.x;
				p[1] = e.y;
				break;
		}
		return false;
	}
}