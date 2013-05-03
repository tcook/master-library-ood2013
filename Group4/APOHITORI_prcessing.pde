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


	
	private final String[] levels = new String[] {
			"41434"+
			"14225"+
			"43123"+
			"42521"+
			"53314",
			
			"31142"+
			"24125"+
			"15123"+
			"55321"+
			"23514",
			
			"32313"+
			"54352"+
			"41335"+
			"25551"+
			"25143",
			
			"45132"+
			"34244"+
			"12415"+
			"13245"+
			"11533",
			
			"25542"+
			"44312"+
			"52431"+
			"31555"+
			"24135",
			
			"24523"+
			"45113"+
			"34442"+
			"52345"+
			"53241",
			
			"41212"+
			"25331"+
			"53123"+
			"22245"+
			"13455",
			
			"54144"+
			"31445"+
			"25314"+
			"22251"+
			"15233",
			
			"21453"+
			"31255"+
			"41531"+
			"54313"+
			"52324",
			
			"43132"+
			"14552"+
			"43425"+
			"51314"+
			"31523",
			
			"442631"+
			"651114"+
			"236343"+
			"524315"+
			"341362"+
			"666251",
			
			"215464"+
			"551213"+
			"546222"+
			"523635"+
			"613551"+
			"432152",
			
			"161344"+
			"511433"+
			"213656"+
			"222612"+
			"345662"+
			"446122",
			
			"615453"+
			"164345"+
			"236111"+
			"263534"+
			"325621"+
			"523161",
			
			"441622"+
			"351241"+
			"244453"+
			"162553"+
			"516134"+
			"636426",
			
			"221644"+
			"645612"+
			"262411"+
			"166635"+
			"313256"+
			"423124",
			
			"241365"+
			"142333"+
			"254641"+
			"466652"+
			"615523"+
			"163146",
			
			"366524"+
			"516324"+
			"625431"+
			"444166"+
			"153226"+
			"552413",
			
			"412436"+
			"541214"+
			"324551"+
			"432521"+
			"113565"+
			"265416",
			
			"614263"+
			"535126"+
			"625241"+
			"545642"+
			"312415"+
			"453226",
	};
	
	/**
	 * p[0] == X-Wert Maus
	 * p[1] == Y-Wert Maus
	 * p[2] == aktuelles Level
	 * p[3] == aktuelle Moveanzahl
	 * p[4] == Maus gedr�ckt
	 * p[5] == Maus losgelassen
	 * p[6] == Spiel gestartet
	 * p[7] == Level geschafft Klick
	 */
	private final int[] p = new int[8];
		long lastTime = System.nanoTime();
		long think = 10000000L;

		int width = 5;
		int[][] level = new int[width][width];
int[][] what = new int[width][width];
		int realWidth = 60;
	


	 void setup() {
		size(400,400);
                level[0][0] = -1;
                p[6] = 1;
	}
	
	 void draw() {

			long now = System.nanoTime();
			long delta = now - lastTime;
			think += delta;
			
			// Update / think
			// Wenn 10 ms vergangen sind, dann denke nach
			while (think >= 10000000L) {
				think -= 10000000L;
				
				if (level[0][0] == -1) {
					if (p[2] >= levels.length) {
						p[2] = 0;
					}
					if (p[2] < 0) {
						p[2] = levels.length - 1;
					}
					String l = levels[p[2]];
					width = 5;
					if (l.length() == 36) {
						width = 6;
					}
					realWidth = 300 / width;
					
					level = new int[width][width];
					what = new int[width][width];
					for (int y = 0; y < width; y++) {
						for (int x = 0; x < width; x++) {
							level[y][x] = Integer.valueOf(l.substring(y * width + x, y * width + x + 1));
						}
					}
							
					p[3] = 0;
					p[7] = 0;
				} else if (p[6] > 0) {
					if (p[4] > 0) {
						if (p[7] > 0) {
							p[2] += 1;
							level[0][0] = -1;
							think += 10000000L;
						} else	if ((p[0] > 110) && (p[0] < 160) &&
							(p[1] > 365) && (p[1] < 395)) {
							p[2] -= 1;
							level[0][0] = -1;
							think += 10000000L;
						} else if ((p[0] > 240) && (p[0] < 290) &&
								   (p[1] > 365) && (p[1] < 395)) {
							p[2] += 1;
							level[0][0] = -1;
							think += 10000000L;
						} else if ((p[0] > 160) && (p[0] < 240) &&
								   (p[1] > 360) && (p[1] < 395)) {
							level[0][0] = -1;
							think += 10000000L;
						} else {
							if ((p[0] > 50) && (p[0] < 350) &&
								(p[1] > 50) && (p[1] < 350)) {
								int curX = (p[0] - 50) / realWidth;
								int curY = (p[1] - 50) / realWidth;
								what[curY][curX] += 1;
								if (what[curY][curX] > 2) {
									what[curY][curX] = 0;
								}
								p[3] += 1;
								boolean bBreak = false;
								for (int y = 0; y < width; y++) {
									for (int x = 0; x < width; x++) {
										if (what[y][x] == 2) {
											if ((y - 1 >= 0) && (what[y-1][x] == 2)) {
												bBreak = true;
											} else if ((x - 1 >= 0) && (what[y][x-1] == 2)) {
												bBreak = true;
											} else if ((x + 1 < width) && (what[y][x+1] == 2)) {
												bBreak = true;
											} else if ((y + 1 < width) && (what[y+1][x] == 2)) {
												bBreak = true;
											}
										}
									}
								}
								if (!bBreak) {
									ArrayList<Integer> list = new ArrayList<Integer>();
									for (int y = 0; y < width; y++) {
										list.clear();
										for (int x = 0; x < width; x++) {
											if (what[y][x] != 2) {
												if ((list.size() == 0)) {
													list.add(level[y][x]);
												} else if (list.indexOf(level[y][x]) == -1) {
													list.add(level[y][x]);
												} else {
													bBreak = true;
												}
											}
										}
									}
									for (int x = 0; x < width; x++) {
										list.clear();
										for (int y = 0; y < width; y++) {
											if (what[y][x] != 2) {
												if ((list.size() == 0)) {
													list.add(level[y][x]);
												} else if (list.indexOf(level[y][x]) == -1) {
													list.add(level[y][x]);
												} else {
													bBreak = true;
												}
											}
										}
									}
									if (!bBreak) {
										for (int x = 0; x < width; x++) {
											if (what[0][x] != 2) {
												if (this.isOne(new boolean[width][width], new boolean[width][width], what, x, 0)) {
													for (int y = 0; y < width; y++) {
														for (int newX = 0; newX < width; newX++) {
															if (what[y][newX] == 0) {
																what[y][newX] = 1;
															}
														}
													}
													p[7] = 1;
												}
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				p[4] = 0;
				p[5] = 0;
			}

			lastTime = now;

                        ellipseMode(CORNER);

			fill(129,126,126);
			rect(0, 0, 400, 400);


			textSize(25);
			for (int y = 0; y < width; y++) {
				for (int x = 0; x < width; x++) {
					fill(255,255,255);
					rect(50 + x * realWidth, 50 + y * realWidth, realWidth, realWidth);
					fill(80,80,80,69);
					rect(50 + x * realWidth, 50 + y * realWidth, realWidth, realWidth);
					if (what[y][x] == 2) {
						rect(50 + x * realWidth + 1, 50 + y * realWidth + 1, realWidth - 1, realWidth - 1);
						fill(255,255,255);
					} else if (what[y][x] == 1) {
						ellipse(57 + x * realWidth, 57 + y * realWidth, realWidth - 10, realWidth - 10);
					}
					String s = String.valueOf(level[y][x]);
					float w = textWidth(s);
					text(String.valueOf(level[y][x]), 53 + x * realWidth + realWidth/2 - w/2, 50 + y * realWidth + realWidth - 18);
				}
			}
			
			fill(186,186,186);
			rect(0, 360, 400, 40);
			rect(0, 0, 400, 40);
			fill(80,80,80, 69);
			rect(0, 0, 399, 399);
			rect(0, 40, 399, 320);
			
                        //fill(0);
			textSize(20);
			String s = "ApoHitori4k";
			float w = textWidth(s);
			text(s, 200 - w/2, 30);
			
			/**if (p[6] == 0) {
				g.setColor(Color.WHITE);
				g.fillRect(55, 55, 290, 290);
				g.fillRect(0, 355, 400, 45);
			
				g.setColor(new Color(120, 120, 120));				
				s = "Click to start the game!";
				w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 200 - w/2, 390);
				
				g.setFont(g.getFont().deriveFont(12f).deriveFont(1));
				s = "1.) no two shaded squares may be";
				g.drawString(s, 60, 110);
				s = "directly adjacent to one another.";
				g.drawString(s, 60, 125);
				
				s = "2.) no two circled squares with matching";
				g.drawString(s, 60, 150);
				s = "digits may exist in the same row or column.";
				g.drawString(s, 60, 165);
				
				s = "3.) no circled square or group thereof may be com-";
				g.drawString(s, 60, 190);
				s = "pletely isolated from the rest by shaded squares.";
				g.drawString(s, 60, 205);
			} else */
			if (p[7] > 0) {
				textSize(15);
				s = "Congratulation!";
				w = textWidth(s);
				text(s, 200 - w/2, 377);
				
				s = "Click to start the next level!";
				w = textWidth(s);
				text(s, 200 - w/2, 396);
			} else {				
				rect(110, 365, 180, 30);
				rect(160, 365, 80, 30);
				if ((p[0] > 160) && (p[0] < 240) &&
				   (p[1] > 365) && (p[1] < 395)) {
					fill(169,175,9);
				}
				s = "reset";
				w = textWidth(s);
				text(s, 200 - w/2, 388);
				
				if ((p[0] > 110) && (p[0] < 160) &&
					(p[1] > 365) && (p[1] < 395)) {
					fill(169,175,9);
				} else {
					fill(0);
				}
				s = "<";
				w = textWidth(s);
				text(s, 135 - w/2, 388);
				
				
				if ((p[0] > 240) && (p[0] < 290) &&
					(p[1] > 365) && (p[1] < 395)) {
					fill(169,175,9);
				} else {
					fill(0);
				}
                                
				s = ">";
				w = textWidth(s);
				text(s, 265 - w/2, 388);

				fill(0);
				textSize(15);
				s = "level: "+String.valueOf((int)(p[2] + 1))+" / "+String.valueOf(levels.length);
				text(s, 10, 30);
				
				s = "clicks: "+String.valueOf((int)(p[3]));
				w = textWidth(s);
				text(s, 390 - w, 30);
			}
			
		

		}

	
	private boolean isOne(boolean[][] isCurVisited, boolean[][] isVisited, int[][] what, int px, int py) {
		isVisited[py][px] = true;
		isCurVisited[py][px] = true;
		
		if ((px - 1 >= 0) && (what[py][px-1] != 2) && (!isCurVisited[py][px-1])) {
			isOne(isCurVisited, isVisited, what, px - 1, py);
		}
		if ((px + 1 < what[0].length) && (what[py][px+1] != 2) && (!isCurVisited[py][px+1])) {
			isOne(isCurVisited, isVisited, what, px + 1, py);
		}
		if ((py - 1 >= 0) && (what[py-1][px] != 2) && (!isCurVisited[py-1][px])) {
			isOne(isCurVisited, isVisited, what, px, py - 1);
		}
		if ((py + 1 < what.length) && (what[py+1][px] != 2) && (!isCurVisited[py+1][px])) {
			isOne(isCurVisited, isVisited, what, px, py + 1);
		}
		isCurVisited[py][px] = false;
		
		for (int y = 0; y < what.length; y++) {
			for (int x = 0; x < what[0].length; x++) {
				if ((what[y][x] != 2) && (!isVisited[y][x])) {
					return false;
				}
			}
		}
		return true;
	}
	

void mousePressed(){
p[4] = 1;
p[5] = 0;

}

void mouseReleased(){
p[4] = 0;
p[5] = 1;

}

void mouseMoved(){
p[0] = mouseX;
p[1] = mouseY;
  
}

void mouseDragged(){

p[0] = mouseX;
p[1] = mouseY;


}


