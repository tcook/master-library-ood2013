package reuze.app;

import java.util.ArrayList;

import reuze.app.appGUI.MinyBoolean;
import reuze.app.appGUI.MinyGUI;
import reuze.app.appGUI.MinyInteger;
import reuze.app.appGUI.Property;
import reuze.app.appGUI.PropertyRadioButton;

/*If you add the following lines to setup(), it will add a couple of test radio buttons to the gui.
		PropertyRadioButtonGroup test = new PropertyRadioButtonGroup("Test Buttons");
		test.AddToGroup(new PropertyRadioButton("Test"));
		test.AddToGroup(new PropertyRadioButton("Test 2"));
		test.add(gui);
*/

class PropertyRadioButtonGroup extends Property{
	
	ArrayList<PropertyRadioButton> Group= new ArrayList<PropertyRadioButton>();

	PropertyRadioButtonGroup(String name) {
		super(name);
	}
	
	public void AddToGroup(PropertyRadioButton radbtn){
		
		int size= Group.size();
		Group.add(size, radbtn);
		radbtn.indexinGroup=new MinyInteger(size);
		radbtn.Group= this;
	}
	
	public int getsize() {return Group.size();}
	
	void add(MinyGUI parent) {
		_parent = parent;
		for (int i=0; i<Group.size(); i++){
			Group.get(i).add(_parent);
		}
	}
	
	public void Changeselectedbutton(PropertyRadioButton radbtn){
		int id = Group.indexOf(radbtn);
		for (int i=0; i<Group.size(); i++){
			if(i!=id){
				Group.get(i)._value=new MinyBoolean(false);
			}
		}
	}
	
}