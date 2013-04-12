package reuze.app;

import processing.core.PGraphics;
import reuze.app.appGUI.MinyValue;

// //////////////////////////////////////////////////////////////////////////////////
// END_BWB///////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////
class PropertyCheckBox extends Property {
	/**
	 * 
	 */
	private final appGUI appGUI;
	private MinyBoolean _value;

	PropertyCheckBox(appGUI appGUI, String name, MinyBoolean value) {
		super(appGUI, name);
		this.appGUI = appGUI;
		_value = value;
	}

	PropertyCheckBox(appGUI appGUI, String name) {
		super(appGUI, name);
		this.appGUI = appGUI;
		_value = new MinyBoolean(this.appGUI);
	}

	public MinyValue get() {
		return _value;
	}

	Rect getBox() {
		return new Rect(this.appGUI, (int) (_x + _w * 0.4 + 3), _y + 8, 10, 10);
	}

	public void display(PGraphics pg, int y) {
		super.display(pg, y);

		pg.stroke(_parent.fg);
		pg.noFill();
		Rect b = getBox();
		b.x -= _x;
		b.y += y - _y;
		this.appGUI.rect(pg, b);

		if (_value.getValue()) {
			pg.fill(_parent.fg);
			b.grow(-2);
			this.appGUI.rect(pg, b);
		}
	}

	public void onMousePressed() {
		if (this.appGUI.overRect(getBox()))
			_value.setValue(!_value.getValue());
	}
}