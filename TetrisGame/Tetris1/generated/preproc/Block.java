import java.awt.Color;
import java.awt.Rectangle;
public class Block {
	public static int blk_width = 32, blk_height = 32;
	public int x, y;
	Rectangle rectangle;
	Color blk_color;

	public Block(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.blk_color = c;
		rectangle = new Rectangle((int) x, (int) y, (int) blk_width,
				(int) blk_height);
	}

	public void draw() {
		fill(blk_color);
		rect(x, y, blk_width, blk_height);
	}

	public void setPos(float x, float y) {
		this.x += x;
		this.y += y;
		rectangle = new Rectangle((int) this.x, (int) this.y, (int) blk_width,
				(int) blk_height);
	}

	public boolean blkCollision(Rectangle r) {
		// println(rectangle.intersection(r));
		return rectangle.intersects(r);
	}

	public Rectangle getSpot() {
		return rectangle.getBounds();
	}
}

