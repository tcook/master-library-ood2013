package reuze.app;

import processing.core.PGraphics;
import reuze.app.appGUI.MinyValue;



class PropertyRadioButton extends Property
{
	/**
	 * 
	 */
	private final appGUI appGUI;
	MinyBoolean _value;
	MinyInteger indexinGroup;
	PropertyRadioButtonGroup Group;

	PropertyRadioButton(appGUI appGUI, String name, MinyBoolean value)
	{
		super(appGUI, name);
		this.appGUI = appGUI;
		_value = value;
	}
	PropertyRadioButton(appGUI appGUI, String name)
	{
		super(appGUI, name);
		this.appGUI = appGUI;
		_value = new MinyBoolean(this.appGUI);
	}
	public MinyValue get() { return _value; }
	Rect getBox() { return new Rect(appGUI, (int)(_x + _w*0.4 + 3), _y + 8, 10, 10); }

	public void display(PGraphics pg, int y)
	{
		super.display(pg, y);

		pg.stroke(_parent.fg); pg.noFill();
		Rect b = getBox();
		b.x -= _x; b.y += y-_y;
		this.appGUI.rect(pg, b);

		if(_value.getValue())
		{
			pg.fill(_parent.fg);
			b.grow(-2);
			this.appGUI.rect(pg, b);
		}
	}

	public void onMousePressed()
	{
		if(this.appGUI.overRect(getBox())){
			_value.setValue(!_value.getValue());
			Group.Changeselectedbutton(this);
		}
			
	}
	public void add(MinyGUI _parent) {
		// TODO Auto-generated method stub
		
	}
}