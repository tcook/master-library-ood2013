import java.awt.Color;
import java.util.ArrayList;

public class Shape {
	public static ArrayList<Shape> shapes = new ArrayList<Shape>();
	static int _id=0;
	int x, y, id;
	private ArrayList<Block> blocks = new ArrayList<Block>();
	Color sh_color = new Color(0, 150, 0);
	boolean moveable = true;
	Block[][] block_sets = new Block[][] {
			{
					// Straight Line - Grey
					new Block(0, 0, new Color(0,0,0)),
					new Block(0, Block.blk_height, new Color(0,0,0)),
					new Block(0, Block.blk_height * 2, new Color(0,0,0)),
					new Block(0, Block.blk_height * 3, new Color(0,0,0)), }, {// Square
																		// -
																		// Light
																		// Blue
			new Block(0, 0, new Color(0,0,0)),
					new Block(0, Block.blk_height, new Color(0,0,0)),
					new Block(Block.blk_width, Block.blk_height, new Color(0,0,0)),
					new Block(Block.blk_width, 0, new Color(0,0,0)), }, {// L Shape -
																	// Red or
																	// Purple
			new Block(0, 0, new Color(0,0,0)),
					new Block(0, Block.blk_height, new Color(0,0,0)),
					new Block(0, Block.blk_height * 2, new Color(0,0,0)),
					new Block(Block.blk_width, Block.blk_height * 2, new Color(0,0,0)), }, {// L(flipped)
																				// Shape
																				// -
																				// Blue
			new Block(0, 0, new Color(0,0,0)),
					new Block(0, Block.blk_height, new Color(0,0,0)),
					new Block(0, Block.blk_height * 2, new Color(0,0,0)),
					new Block(-Block.blk_width, Block.blk_height * 2, new Color(0,0,0)), }, };

	public Shape(int x, int y, boolean m) {
		this.x = x;
		this.y = y;
		this.moveable = m;
		this.addBlock(new Block(x, y, color(255, 0, 0)));
		// shapes.add(this);
		loadShape((int) Math.floor(Math.random() * block_sets.length));
	}

	public Shape(int x, int y) {
		this.x = x;
		this.y = y;
		this.addBlock(new Block(x, y, color(255, 0, 0)));
		shapes.add(this);
		loadShape((int) Math.floor(Math.random() * block_sets.length));
		id = _id;
		_id++;
		//println(id);
	}

	void draw() {
		for (int i = 0; i < blocks.size(); i++)
			blocks.get(i).draw();
	}

	void loadShape(int index) {
		for (int i = 0; i < block_sets[index].length; i++) {
			block_sets[index][i].setPos(x, y);
			block_sets[index][i].blk_color = sh_color;
			blocks.add(block_sets[index][i]);
		}
	}

	/* Moves all blocks in shape */
	public void moveShape(int x, int y) {
		if (!moveable)
			return;
		for (int i = 0; i < this.blocks.size(); i++) {
			if (blocks.get(i).y + 32 >= height) {
				moveable = false;
				// println("x"+blocks.get(i).y+" a"+id);
			}
			blocks.get(i).setPos(x, y);
		}
		this.x += x;
		this.y += y;
	}

	public boolean getMoveable() {
		return moveable;
	}

	public void addBlock(Block b) {
		this.blocks.add(b);
	}

	public boolean colDetect(Shape s) {
		for (int x = 0; x < this.blocks.size(); x++) {
			for (int j = 0; j < s.blocks.size(); j++) {
				if (blocks.get(x).blkCollision(s.blocks.get(j).rectangle)) {
					moveable = false;
					return true;
				}
				if (blocks.get(x).y + 32 >= height) {
					moveable = false;
				}
			}
		}
		return false;
	}

	public boolean previewCol(int xOff, int yOff) {
		Shape demo = new Shape(this.x + xOff, this.y + yOff, false);
		demo.blocks = this.blocks;
		demo.moveShape(0, 0);
		boolean collide = false;
		// println(demo.x+" a "+demo.y);
		for (int i = 0; i < shapes.size(); i++) {
			if (Math.abs(this.x - shapes.get(i).x) < 10
					&& Math.abs(this.y - shapes.get(i).y) < 10)
				;
			else if (demo.colDetect(shapes.get(i)))
				collide = true;
		}
		return collide;
	}

	public void rotate() {

	}
}
