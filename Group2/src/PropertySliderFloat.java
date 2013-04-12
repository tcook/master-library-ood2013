package reuze.app;

import reuze.app.appGUI.MinyValue;

class PropertySliderFloat extends PropertySlider {
	/**
	 * 
	 */
	private final appGUI appGUI;
	private MinyFloat _value;
	private float _mini, _maxi;

	PropertySliderFloat(appGUI appGUI, String name, MinyFloat value, float mini, float maxi) {
		super(appGUI, name);
		this.appGUI = appGUI;
		_value = value;
		_mini = mini;
		_maxi = maxi;
	}

	PropertySliderFloat(appGUI appGUI, String name, float mini, float maxi) {
		super(appGUI, name);
		this.appGUI = appGUI;
		_value = new MinyFloat(this.appGUI, mini);
		_mini = mini;
		_maxi = maxi;
	}

	public MinyValue get() {
		return _value;
	}

	float getPos() {
		return (_value.getValue() - _mini) / (_maxi - _mini);
	}

	void setPos(float v) {
		_value.setValue(appGUI.constrain(_mini + v * (_maxi - _mini), _mini, _maxi));
	}

	public void update() {
		super.update();
		_value.setValue(appGUI.constrain(_value.getValue(), _mini, _maxi));
	}
}