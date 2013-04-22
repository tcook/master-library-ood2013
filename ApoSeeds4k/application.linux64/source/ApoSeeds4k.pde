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
};

/*
 * 0 = mouseX
 * 1 = mouseY
 * 2 = mouse released
 * 3 = level
 * 4 = touches
 * 5 = game lost
 * 6 = game win
 */
private final float[] values = new float[7];

long lastTime = System.nanoTime();
long think = lastTime;

int[][] level = new int[6][5];

int changeX = 240 - 60 * level[0].length/2;
int changeY = 260 - 60 * level.length/2;

int titleWidth = 100;
boolean bRun = false;
float[] seeds = new float[160];

public void setup() {
    size(480, 480);
    level[0][0] = -1;
}

public void draw() {	

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
            bRun = false;
            String curLevel = levels[(int)values[3]];
            level = new int[6][5];
            values[4] = Integer.valueOf(curLevel.substring(30, 31));
            for (int y = 0; y < level.length; y++) {
                for (int x = 0; x < level[0].length; x++) {
                    level[y][x] = Integer.valueOf(curLevel.substring(x + y * level[0].length, 1 + x + y * level[0].length));
                }
            }				
            changeX = 240 - 60 * level[0].length/2;
            changeY = 260 - 60 * level.length/2;
            values[5] = values[6] = 0;
        } 
        else if (values[2] == 1) {
            if (values[5] > 0) {
                if (values[6] > 0) {
                    values[3] += 1;
                }
                level[0][0] = -1;
                think += 10000000L;
            }
            else if ((values[0] >= 0) && (values[1] >= 0) && (values[0] < 70) && (values[1] < 30)) {
                level[0][0] = -1;
                think += 10000000L;
            }
            else if ((values[0] >= 240 - titleWidth/2) && (values[1] >= 33) && (values[0] < 240 - titleWidth/2 + 50) && (values[1] < 66)) {
                values[3] -= 1;
                level[0][0] = -1;
                think += 10000000L;
            }
            else if ((values[0] >= 240 + titleWidth/2 - 50) && (values[1] >= 33) && (values[0] < 240 + titleWidth/2) && (values[1] < 66)) {
                values[3] += 1;
                level[0][0] = -1;
                think += 10000000L;
            }
            else if ((values[0] >= changeX) && (values[1] >= changeY) && (values[0] < changeX + 60 * level[0].length) && (values[1] < changeY + 60 * level.length) && (!bRun)) {
                int x = (int)(values[0] - changeX) / 60;
                int y = (int)(values[1] - changeY) / 60;
                if (level[y][x] > 0) {
                    level[y][x] -= 1;
                    values[4] -= 1;
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
            values[2] = 0;
        }
        else if (bRun) {
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
                    if (values[4] <= 0) {
                        values[5] = 1;
                    }
                }
            }
        }
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
            values[5] = values[6] = 1;
        }
    }
    lastTime = now;

    //Render

    ellipseMode(CORNER);

    fill(191, 255, 180);
    rect(0, 0, 480, 480);
    float w = titleWidth = 230;

    fill(140, 220, 255);
    rect(240 - w/2, 33 - 5, w, 43, 0, 0, 15, 15);
    rect(240 - 70, 33 - 5, 140, 43, 0, 0, 15, 15);
    rect(480 - 70, - 5, 85, 76, 0, 0, 15, 15);
    rect(-5, - 5, 85, 43, 0, 0, 15, 15);

    fill(140, 220, 255);
    rect(240 - w/2 - 10, -5, w + 20, 43, 0, 0, 15, 15);

    fill(0);
    String s = "ApoSeeds4k";
    w = textWidth(s);
    textSize(28);
    text(s, 156, 25);

    s = "Level: "+String.valueOf((int)(values[3] + 1))+" / "+levels.length;
    w = textWidth(s);
    textSize(15);
    text(s, 195, 58);

    text("<", 240 - titleWidth/2 + 15, 58);
    text(">", 240 + titleWidth/2 - 15 - textWidth(">"), 58);
    text("restart", 14, 25);

    s = "Clicks";
    w = textWidth(s);
    text(s, 475 - w, 25);

    s = String.valueOf((int)(values[4]));
    text(s, 447, 58);

    for (int y = 0; y < level.length; y++) {
        for (int x = 0; x < level[0].length; x++) {
            fill(93, 180, 93);
            rect(changeX + x * 60, changeY + y * 60, 60, 60);
            if ((values[0] - changeX >= x * 60) && (values[1] - changeY >= y * 60) && (values[0] - changeX < (x + 1) * 60) && (values[1] - changeY < (y + 1) * 60) && (level[y][x] > 0)) {
                if (bRun) {
                    rect(changeX + x * 60, changeY + y * 60, 59, 59);
                }
                rect(changeX + x * 60, changeY + y * 60, 59, 59);
                rect(changeX + x * 60, changeY + y * 60, 60, 60);
            }

            if (level[y][x] > 0) {
                fill(214, 45, 26);
                if (level[y][x] == 2) fill(46, 175, 187);
                if (level[y][x] == 3) fill(182, 146, 46);
                if (level[y][x] == 4) fill(187, 46, 182);

                ellipse(changeX + x * 60 + 10 + (level[y][x] - 1) * 3, changeY + y * 60 + 4 + (level[y][x] - 1) * 4, 40 - (level[y][x] - 1) * 6, 25 - (level[y][x] - 1) * 4);
                fill(231, 182, 27);
                ellipse(changeX + x * 60 + 18 + (level[y][x] - 1) * 3 - (level[y][x] - 1), changeY + y * 60 + 7 + (level[y][x] - 1)/2 + (level[y][x] - 1) * 4, 24 - (level[y][x] - 1) * 6 + (level[y][x] - 1) * 2, 15 - (level[y][x] - 1) * 3);
                fill(0);
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

    if (bRun) {
        for (int i = 0; i < seeds.length; i += 2) {
            if (seeds[i] > 0) {
                fill(214, 45, 26);
                ellipse((int)(changeX + seeds[i] - 2), (int)(changeY + seeds[i + 1] - 2), 5, 5);
            }
        }
    }

    textSize(22);
    if (values[5] > 0) {
        fill(140, 220, 255);
        rect(95, 190, 290, 100, 0, 0, 15, 15);

        fill(0);		
        if (values[6] > 0) {
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
    else {
        s = "";
        if (values[3] == 0) {
            s = "Click on a big flower and look what happens";
        }
        else if (values[3] == 1) {
            s = "Seeds will help the flowers to grow";
        }
        else if (values[3] == 2) {
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
            rect(240 - w/2 - 10, 480 - 35 + 7, w + 20, 35, 0, 0, 15, 15);
            fill(0);
            text(s, 240 - w/2, 473);
        }
    }
}//END DRAW

//Handle Mouse Events

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

