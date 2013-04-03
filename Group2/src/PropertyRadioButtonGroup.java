package reuze.app;

import java.util.ArrayList;



class PropertyRadioButtonGroup extends Property{
	
	/**
	 * 
	 */
	private final appGUI appGUI;
	ArrayList<PropertyRadioButton> Group= new ArrayList<PropertyRadioButton>();

	PropertyRadioButtonGroup(appGUI appGUI, String name) {
		super(appGUI, name);
		this.appGUI = appGUI;
	}
	
	public void AddToGroup(PropertyRadioButton radbtn){
		
		int size= Group.size();
		Group.add(size, radbtn);
		radbtn.indexinGroup=new MinyInteger(this.appGUI);
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
				Group.get(i)._value=new MinyBoolean(this.appGUI);
			}
		}
	}
	
}