package reuze.app;

import processing.core.PGraphics;
import reuze.app.appGUI.MinyValue;

public class PropertyDisplay extends Property
{
	/**
	 * 
	 */
	private final appGUI appGUI;
	private MinyValue _value;
	PropertyDisplay(appGUI appGUI, String name, MinyValue value)
	{
		super(appGUI, name);
		this.appGUI = appGUI;
		_value = value;
	}
	PropertyDisplay(appGUI appGUI, String name)
	{
		super(appGUI, name);
		this.appGUI = appGUI;
	}
	public MinyValue get() {return _value;}
	public void display(PGraphics pg, int y)
	{
		super.display(pg, y);  //displays the label
		if (_value!=null) {
			pg.noFill();
			pg.stroke(_parent.fg);
			pg.text(_value.getString(), _w*0.4f + 3, y, _w *0.6f - 8, 20);
		}
	}
}