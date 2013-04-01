package reuze.app;

import processing.core.PGraphics;
import reuze.app.appGUI.MinyBoolean;
import reuze.app.appGUI.MinyInteger;
import reuze.app.appGUI.MinyValue;
import reuze.app.appGUI.Property;
import reuze.app.appGUI.PropertyRadioButtonGroup;
import reuze.app.appGUI.Rect;

class PropertyRadioButton extends Property
{
	private MinyBoolean _value;
	private MinyInteger indexinGroup;
	private PropertyRadioButtonGroup Group;

	PropertyRadioButton(String name, MinyBoolean value)
	{
		super(name);
		_value = value;
	}
	PropertyRadioButton(String name)
	{
		super(name);
		_value = new MinyBoolean();
	}
	public MinyValue get() { return _value; }
	Rect getBox() { return new Rect((int)(_x + _w*0.4 + 3), _y + 8, 10, 10); }

	public void display(PGraphics pg, int y)
	{
		super.display(pg, y);

		pg.stroke(_parent.fg); pg.noFill();
		Rect b = getBox();
		b.x -= _x; b.y += y-_y;
		rect(pg, b);

		if(_value.getValue())
		{
			pg.fill(_parent.fg);
			b.grow(-2);
			rect(pg, b);
		}
	}

	public void onMousePressed()
	{
		if(overRect(getBox())){
			_value.setValue(!_value.getValue());
			Group.Changeselectedbutton(this);
		}
			
	}
}