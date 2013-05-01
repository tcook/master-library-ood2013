import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

aiChess ch = new aiChess();
void setup() {
  size(602, 552);
  background(122, 122, 122);
  //aiChess ch = new aiChess();
  //ch.draw();
}

void draw(){
  ch.paint();
}

void mousePressed(){
  ch.mouseEvent();
  redraw();
}


class aiChess implements Runnable {
  private static final int EMPTY = 0;
  private static final int PAWN = 1;
  private static final int BISHOP = 2;
  private static final int KNIGHT = 3;
  private static final int ROOK = 4;
  private static final int QUEEN = 5;
  private static final int KING = 6;
  private static final int FIGURE = 15;
  private static final int BLACK = 16;
  private static final int WHITE = 32;
  private static final int COLOR = 32 + 16;
  private static final int MAX_TICK = 500;
  private static final int MAX = 100000;
  private static final int KING_VALUE = 10000;
  private static final int MAGIC = 0x953310;
  final int XOFFSET = 20;
  final int YOFFSET = 20;
  private int[][] myboard;
  private int[] myply;
  private long time;
  private int[][][] undo;
  private int[] resply;
  private int difficulty;

  aiChess() {
    init();
  }

  void init() {
    myply = new int[5];
    myply[0] = -1;
    myply[4] = QUEEN;

    undo = new int[1000][][];
    myboard = new int[10][8];
    myboard[8][4] = WHITE;

    // White
    myboard[0][0] = WHITE | ROOK;
    myboard[1][0] = WHITE | KNIGHT;
    myboard[2][0] = WHITE | BISHOP;
    myboard[3][0] = WHITE | QUEEN;
    myboard[4][0] = WHITE | KING;
    myboard[5][0] = WHITE | BISHOP;
    myboard[6][0] = WHITE | KNIGHT;
    myboard[7][0] = WHITE | ROOK;
    for (int i = 0; i < 8; i++) {
      myboard[i][1] = WHITE | PAWN;
    }

    // Black
    for (int i = 0; i < 8; i++) {
      myboard[i][6] = BLACK | PAWN;
    }
    myboard[0][7] = BLACK | ROOK;
    myboard[1][7] = BLACK | KNIGHT;
    myboard[2][7] = BLACK | BISHOP;
    myboard[3][7] = BLACK | QUEEN;
    myboard[4][7] = BLACK | KING;
    myboard[5][7] = BLACK | BISHOP;
    myboard[6][7] = BLACK | KNIGHT;
    myboard[7][7] = BLACK | ROOK;

    myboard[8][2] = -1;
  }

  boolean isCastleing(final int[][]board, final int sx, final int sy, final int dx, final int dy) {
    final int side = board[8][4];
    final int mustY = (side == WHITE) ? 0 : 7;
    final int rookX = (dx == 2) ? 0 : 7;

    if (sy != dy)
      return false;
    if (sy != mustY)
      return false;
    if (sx != 4)
      return false;
    if ((dx != 2) && (dx != 6))
      return false;

    int min, max;
    min = dx - 1; // dx == 2 ? 1 : 5; EVIL HACK
    max = dx == 2 ? 3 : 6;
    for (int i = min; i <= max; i++) {
      if (board[i][sy] != EMPTY)
        return false;
    }

    min = dx == 2 ? 2 : 4;
    max = min + 2;
    for (int i = min; i <= max; i++) {
      for (int x = 0; x < 8; x++)
        for (int y = 0; y < 8; y++) {
          final int figure = board[x][y];
          if ((figure != EMPTY) && ((figure & COLOR) != side)) {
            if ((figure & FIGURE) == PAWN) {
              if (sy == y + (side == BLACK ? 1 : -1)) {
                if (Math.abs(x - i) == 1)
                  return false;
              }
            } 
            else {
              if (isNormal(board, x, y, i, sy))
                return false;
            }
          }
        }
    }

    if ((board[8][6] & side) != 0)
      return false;
    if ((board[8][dx == 2 ? 5 : 7] & side) != 0)
      return false;

    return true;
  }

  boolean isEnPassant(int[][] board, int sx, int sy, int dx, int dy) {
    final int side = board[8][4];
    final int mustDir = side == WHITE ? 1 : -1;
    final int mustSY = side == WHITE ? 4 : 3;

    if (sy != mustSY)
      return false;
    if (dy != sy + mustDir)
      return false;
    if (Math.abs(sx - dx) != 1)
      return false;

    if (board[8][0] != dx)
      return false;
    if (board[8][1] != dy + mustDir)
      return false;
    if (board[8][2] != dx)
      return false;
    if (board[8][3] != sy)
      return false;

    if ((board[sx][sy] & FIGURE) != PAWN)
      return false;
    if ((board[dx][sy] & FIGURE) != PAWN)
      return false;
    return true;
  }

  boolean isNormal(int[][] board, int sx, int sy, int dx, int dy) {
    final int f = board[sx][sy];
    final int g = board[dx][dy];

    if ((dx == sx) && (dy == sy))
      return false;

    if (f == EMPTY)
      return false;

    if ((g != EMPTY) && ((f & FIGURE) == PAWN)) {
      if (Math.abs(sx - dx) != 1)
        return false;
      if (dy != sy + ((f & COLOR) == WHITE ? 1 : -1))
        return false;
    } 
    else {
      switch (f & FIGURE) {
      case PAWN:
        if (sx == dx) {
          if ((f & COLOR) == WHITE) {
            if (dy == sy + 1)
              break;
            if ((sy == 1) && (dy == 3))
              break;
          } 
          else {
            if (dy == sy - 1)
              break;
            if ((sy == 6) && (dy == 4))
              break;
          }
        }
        return false;
      case ROOK:
        if ((sx == dx) || (sy == dy))
          break;
        return false;
      case KNIGHT:
        if ((Math.abs(sx - dx) == 1) && (Math.abs(sy - dy) == 2))
          return true;
        if ((Math.abs(sx - dx) == 2) && (Math.abs(sy - dy) == 1))
          return true;
        return false;
      case BISHOP:
        if (sx - sy == dx - dy)
          break;
        if (sx + sy == dx + dy)
          break;
        return false;
      case QUEEN:
        if (sx - sy == dx - dy)
          break;
        if (sx + sy == dx + dy)
          break;
        if ((sx == dx) || (sy == dy))
          break;
        return false;
      case KING:
        if ((Math.abs(sx - dx) <= 1) && (Math.abs(sy - dy) <= 1))
          break;
        return false;
      }
    }

    while ( (sx != dx) || (sy != dy)) {
      if (sx < dx)
        sx++;
      if (sx > dx)
        sx--;
      if (sy < dy)
        sy++;
      if (sy > dy)
        sy--;
      if (((sx != dx) || (sy != dy)) && (board[sx][sy] != EMPTY))
        return false;
    }
    return true;
  }

  void copy(int[][] from, int[][] to) {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 8; j++) {
        to[i][j] = from[i][j];
      }
    }
  }

  boolean move(int[][] board, int[] ply) {
    final int sx = ply[0];
    final int sy = ply[1];
    final int dx = ply[2];
    final int dy = ply[3];
    final int side = board[8][4];

    final int f = board[sx][sy];
    if ((f & COLOR) != side)
      return false;
    if ((board[dx][dy] & COLOR) == side)
      return false;

    final int move = board[9][0];
    undo[move] = new int[10][8];
    copy(board, undo[move]);

    if (isCastleing(board, sx, sy, dx, dy)) {
      final int rookSrcX = (dx == 2) ? 0 : 7;
      final int g = board[rookSrcX][sy];

      board[rookSrcX][sy] = 0;
      board[sx][sy] = 0;
      board[dx][sy] = f;
      board[(dx == 2) ? 3 : 5][sy] = g;
    } 
    else if (isEnPassant(board, sx, sy, dx, dy)) {
      board[sx][sy] = EMPTY;
      board[dx][dy] = f;
      board[dx][sy] = EMPTY;
      board[9][1] = 0;
    } 
    else if (isNormal(board, sx, sy, dx, dy)) {
      if (board[dx][dy] != EMPTY)
        board[9][1] = 0;
      board[sx][sy] = EMPTY;
      board[dx][dy] = f;
      if ((f & FIGURE) == PAWN) {
        if ((dy == 0) || (dy == 7))
          board[dx][dy] = ply[4] | side;
      }
      if ((f & FIGURE) == KING)
        board[8][6] |= side;
      if ((f & FIGURE) == ROOK) {
        if (((sx == 0) || (sx == 7)) && ((sy == 0) || (sy == 7)))
          board[8][sx == 0 ? 5 : 7] |= sy == 0 ? WHITE : BLACK;
      }
    } 
    else
    {
      return false;
    }

    board[8][0] = sx;
    board[8][1] = sy;
    board[8][2] = dx;
    board[8][3] = dy;
    board[8][4] = COLOR - side;
    board[9][0]++;
    board[9][1]++;

    int inCheck = 0;
    for (int x = 0; x < 8; x++)
      for (int y = 0; y < 8; y++) {
        if (board[x][y] != EMPTY) {
          for (int tx = 0; tx < 8; tx++)
            for (int ty = 0; ty < 8; ty++) {
              int g = board[tx][ty];
              if (((g & FIGURE) == KING)
                && ((g & COLOR) != (board[x][y] & COLOR))) {
                if (isNormal(board, x, y, tx, ty))
                  inCheck |= g & COLOR;
              }
            }
        }
      }
    board[9][2] = inCheck;

    if ((inCheck & side) != 0) {
      copy(undo[move], board);
      return false;
    }

    return true;
  }

  int search(int[][] board, final int steps, final int maxsteps, int alpha, int beta) {
    resply = null;

    // draw after 50 non-taking moves
    if (board[9][1] > 50)
      return 0;

    // draw after three back-and-forth moves
    final int move = board[9][0];
    if (move > 15) {
      boolean argh = true;
      for (int i = 0; argh & (i < 8); i++) {
        final int b0 = move - 1 - i;
        final int b1 = b0 - 4;
        for (int x = 0; x < 8; x++)
          for (int y = 0; y < 8; y++)
            if (undo[b0][x][y] != undo[b1][x][y])
              argh = false;
      }
      if (argh)
        return 0;
    }

    final int side = board[8][4];

    // evaluate board if search depth is reached
    if (steps <= 0) {
      int whiteValue = 0;
      for (int dx = 0; dx < 8; dx++)
        for (int dy = 0; dy < 8; dy++) {
          // add value of piece found at dx,dy
          if (board[dx][dy] != EMPTY) {
            int figure = board[dx][dy];
            int type = figure & FIGURE;
            int value = 40 * (type == KING ? KING_VALUE
              : (MAGIC >> (4 * type)) & 0xf);
            if ((figure & COLOR) == WHITE)
              whiteValue += value;
            else
              whiteValue -= value;
          }

          float sup = 0;
          for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++) {
              int figure = board[x][y];
              if (figure != EMPTY) {
                int type = figure & FIGURE;
                int mycolor = figure & COLOR;
                float attack = 1.0f / (type == KING ? KING_VALUE
                  : (MAGIC >> (4 * type)) & 0xf);
                if (mycolor == BLACK)
                  attack = -attack;
                if (type == PAWN) {
                  if (dy == y + (mycolor == WHITE ? 1 : -1)) {
                    if (Math.abs(x - dx) == 1)
                      sup += attack;
                  }
                } 
                else {
                  if (isNormal(board, x, y, dx, dy))
                    sup += attack;
                }
              }
            }

          int value;
          if (dx < dy)
            value = dx - dy;
          else
            value = dy - dx;
          if (dx < 7 - dy)
            value += dx + dy;
          else
            value += 14 - (dx + dy);

          if (sup != 0) {
            if (sup > 0)
              whiteValue += value;
            else
              whiteValue -= value;
          }
        }

      return side == WHITE ? whiteValue : -whiteValue;
    }

    int[] bestply = null;
    int[][] save = new int[10][8];

    for (int x = 0; x < 8; x++)
      for (int y = 0; y < 8; y++) {
        int figure = board[x][y];
        int type = figure & FIGURE;
        if ((figure & COLOR) == side) {
          for (int dx = 0; dx < 8; dx++) {
            for (int dy = 0; dy < 8; dy++) {
              if (((board[dx][dy] & COLOR) != side)
                && (isEnPassant(board, x, y, dx, dy)
                || isCastleing(board, x, y, dx, dy) || isNormal(
              board, x, y, dx, dy))) {
                // System.out.println("CONSIDERING: "+x+" "+y+" -> "+dx+" "+dy);
                int newsteps = steps;
                if ((board[dx][dy] != EMPTY)
                  && (steps < maxsteps))
                  newsteps++;

                int min = QUEEN;
                if ((type == PAWN) && ((dy == 0) || (dy == 7)))
                  min = BISHOP;
                for (int k = min; k <= QUEEN; k++) {
                  int[] p = new int[] { 
                    x, y, dx, dy, k
                  };
                  copy(board, save);
                  if (move(save, p)) {
                    int val = -search(save, newsteps - 1, 
                    maxsteps - 1, -beta, -alpha);
                    if (val >= beta)
                      return beta;
                    if (val > alpha) {
                      alpha = val;
                      bestply = p;
                    }
                  }
                }
              }
            }
          }
        }
      }

    // resply must be set here
    resply = bestply;

    // if no move was found at all, the current side has lost
    return bestply == null ? -KING_VALUE : alpha;
  }

  void run() {
    //setCursor(new Cursor(Cursor.WAIT_CURSOR));
    search(myboard, (difficulty + 4) / 2, 4 * difficulty + 2, -MAX, MAX);
    if (resply != null) {
      move(myboard, resply);
      time = System.currentTimeMillis();
      search(myboard, 1, 0, -MAX, MAX);
    }

    if (resply == null) {
      if (myboard[9][2] == 0)
        myboard[8][4] = 0; // draw
      else
        myboard[8][4] = -myboard[8][4];
    }
    //setCursor(null);
    redraw();
  }

  protected void mouseEvent() {
    if (myboard[8][4] == BLACK)
      return;

    // calculate clicked field position
    int xclick = (mouseX - XOFFSET) / 64;
    int yclick = (mouseY - YOFFSET) / 64;
    if ((xclick >= 0) && (xclick <= 7) && (yclick >= 0) && (yclick <= 7)) {
      if (myply[0] == -1) {
        myply[0] = xclick;
        myply[1] = 7 - yclick;
      } 
      else {
        myply[2] = xclick;
        myply[3] = 7 - yclick;
        // create ply and move
        if (move(myboard, myply)) {
          time = System.currentTimeMillis();
          redraw();
        }

        // reset highlight
        myply[0] = -1;
      }
    } 
    else if (xclick == 8) {
      if (yclick == 0) {
        // cycle through transformation targets
        myply[4] = (myply[4] + 3) % 4 + BISHOP;
        background(122,122,122);
      } 
      else if (yclick == 1) { // undo last move
        int move = myboard[9][0];
        if (move > 0) {
          move = (move - 1) & ~1;
          copy(undo[move], myboard);
        }
      } 
      else if (yclick == 2){ // cycle through difficulty levels
        difficulty = (difficulty + 1) % 3;
        background(122,122,122);
      }
      else if (yclick == 3) // restart game
        init();
    }
    redraw();
  }

  void paint() {
    translate(XOFFSET, YOFFSET);
    int myside = myboard[8][4];
    fill(0);
    String s = "Draw";
    if (myside == WHITE)
      s = "White";
    if (myside == BLACK)
      s = "Black";
    if (myside == -WHITE)
      s = "Black wins";
    if (myside == -BLACK)
      s = "White wins";
    text(s, 0, 8*64+16);

    StringBuffer buffer = new StringBuffer();
    buffer.append((char) (myboard[8][0] + 'A'))
      .append((char) (myboard[8][1] + '1')).append('-')
        .append((char) (myboard[8][2] + 'A'))
          .append((char) (myboard[8][3] + '1'));
    if (myboard[8][2] != -1)
      text(buffer.toString(), 100, 8*64+16);

    rect(-1, -1, 8*64+1, 8*64+1);
    for (int i=0; i<8; i++) {
      for (int j=0; j<8; j++) {
        if (((i+j)%2)==0)
          fill(255, 165, 0);
        else
          fill(255, 140, 0);
        if ((i==myply[0]) && (7-j==myply[1]))
          fill(0, 0, 255);
        rect(i*64, j*64, 64, 64);
      }
    }

    fill(0);
    for (int i=0; i<8; i++) {
      text(Character.toString((char) ('8'-i)), -16, i*64+35);
      text(Character.toString((char) (i+'A')), i*64+32, -4);
    }

    smooth();

    strokeWeight(3);

    stroke(255);

    for (int i=0; i<= difficulty; i++) {
      int wx = 8 * 64 + 18 + 14 * i, wy = 2 * 64 + 32;
      translate(wx, wy);
      line(0, -6, 3, 4);
      line(3, 4, -5, -2);
      line(-5, -2, 5, -2);
      line(5, -2, -3, 4);
      line(-3, 4, 0, -6);
      translate(-wx, -wy);
    }

    {
      int wx=8*64+32, wy=64+32;
      translate(wx, wy);
      line(-16, 0, -8, -8);
      line(-8, -8, -8, -4);
      line(-8, -4, 13, -4);
      line(14, -4, 16, -2);
      line(16, -1, 16, 12);
      line(16, 12, 8, 12);
      line(8, 12, 8, 4);
      line(8, 4, -8, 4);
      line(-8, 4, -8, 8);
      line(-8, 8, -16, 0);
      translate(-wx, -wy);
    }

    {
      int wx=8*64+32, wy=3*64+32;
      translate(wx, wy);
      line(-8, 0, -16, -8);
      line(-16, -8, -8, -16);
      line(-8, -16, -8, -12);
      line(-8, -12, 13, -12);
      line(14, -12, 16, -10);
      line(16, -9, 16, 8);

      line(8, 0, 16, 8);
      line(16, 8, 8, 16);
      line(8, 16, 8, 12);
      line(8, 12, -13, 12);
      line(-14, 12, -16, 10);
      line(-16, 9, -16, -8);

      rect(-8, -4, 16, 8);
      translate(-wx, -wy);
    }

    long tickCount = System.currentTimeMillis() -time;

    for (int i = 0; i < 9; i++)
      for (int j = 0; j < 8; j++) {
        int figure = EMPTY;
        if (i < 8)
          figure = myboard[i][7 - j];
        else if (j == 0)
          figure = WHITE | myply[4];

        if ((figure & COLOR) == WHITE)
          stroke(255);
        else
          stroke(0);

        float wx = i * 64 + 32, wy = j * 64 + 32;

        if ((tickCount < MAX_TICK) && (i == myboard[8][2])
          && (7 - j == myboard[8][3])) {
          float sx = myboard[8][0] * 64 + 32;
          float sy = (7 - myboard[8][1]) * 64 + 32;
          wx = (tickCount * wx + (MAX_TICK - tickCount) * sx)
            / MAX_TICK;
          wy = (tickCount * wy + (MAX_TICK - tickCount) * sy)
            / MAX_TICK;
        }

        g.translate(wx, wy);
        switch (figure & FIGURE) {
        case PAWN:
          rect(-4, -6, 8, 8);
          rect(-8, 8, 16, 8);
          break;
        case BISHOP:
          rect(-8, -4, 16, 20);
          line(0, -18, 6, -12);
          line(6, -12, 0, -6);
          line(0, -6, -6, -12);
          line(-6, -12, 0, -18);
          break;
        case KNIGHT:
          line(14, 16, -10, 16);
          line(-10, 16, -1, -1);
          line(-1, -1, -12, 2);
          line(-12, 2, -16, -6);
          line(-16, -6, 1, -16);
          line(-3, -20, 14, -4);
          line(14, -4, 14, 16);
          ellipse(-2, -10, 4, 4);
          break;
        case QUEEN:
          rect(-8, 8, 16, 8);
          line(8, 2, -8, 2);
          line(-8, 2, -16, -10);
          line(-16, -10, -6, -7);
          line(-6, -7, 0, -17);
          line(0, -17, 6, -7);
          line(6, -7, 16, -10);
          line(16, -10, 8, 2);
          break;
        case KING:
          rect(-8, 8, 16, 8);
          line(8, 2, -8, 2);
          line(-8, 2, -16, -12);
          line(-16, -12, 16, -12);
          line(16, -12, 8, 2);
          line(-4, -17, 4, -17);
          line(0, -12, 0, -20);
          break;
        case ROOK:
          rect(-8, 8, 16, 8);
          rect(-16, -14, 32, 16);
          line(-8, -14, -8, -8);
          line(0, -14, 0, -8);
          line(8, -14, 8, -8);
          break;
        }
        translate(-wx, -wy);
      }

    // repaint if animation isn't complete
    // or invoke ai if it's black's turn
    if (tickCount < MAX_TICK)
      redraw();
    else if ((myboard[8][4] == BLACK) && (time != 0)) {
      time = 0;
      new Thread(this).start();
      // SwingUtilities.invokeLater(this);
    }
  }
}

