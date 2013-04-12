package reuze.app;

// ---------------------------------------------------
class Rect {
	/**
	 * 
	 */
	private final appGUI appGUI;
	int x, y, w, h;

	Rect(appGUI appGUI, int nx, int ny, int nw, int nh) {// 0,0,200,400
		this.appGUI = appGUI;
		x = nx;
		y = ny;
		w = nw;
		h = nh;
	}

	Rect(appGUI appGUI, Rect r) {
		this.appGUI = appGUI;
		x = r.x;
		y = r.y;
		w = r.w;
		h = r.h;
	}

	void grow(int v) {
		x -= v;
		y -= v;
		w += 2 * v;
		h += 2 * v;
	}
}