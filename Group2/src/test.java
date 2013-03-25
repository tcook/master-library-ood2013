package reuze.app;

import reuze.app.appGUI.*;

public class test extends appGUI {
	public void setup()
	{
		size(600, 400);
		smooth();
		img=loadImage("../data/mearth.jpg");
		time = 0;
		running = new MinyBoolean(true);this.
		speed = new MinyInteger(2);
		timeCaption = new MinyString("0.0");
		stWidth = new MinyFloat(2.0f);
		rSize = new MinyFloat(1.0f);
		borderColor = new MinyColor(color(192));
		rotation = new InterpolatedFloat(1);
		rotation.add(0, 0);
		rotation.add(1.5f, -PI);
		rotation.add(3.0f, PI);
		rotation.add(4.0f, 2*PI);
		rotation.add(6.0f, 0);

		gradient = new ColorGradient(color(0));
		gradient.add(0, color(0, 1));
		gradient.add(0.25f, color(255, 0, 0));
		gradient.add(0.5f, color(0, 255, 0));
		gradient.add(0.75f, color(0, 0, 255));
		gradient.add(1, color(255));

		gui = new MinyGUI(0, 0, 200, height);
		new PropertyButton("Start", new TestButton()).add(gui);
		new PropertyButton("Tester", new TestButton()).add(gui);
		new PropertyButtonImage("../data/particle.png", new TestButton()).add(gui);
		new PropertyCheckBox("Running", running).add(gui);
		new PropertyList("Speed", speed, "slowest;slow;normal;fast;fastest").add(gui);
		new PropertyDisplay("Time", timeCaption).add(gui);
		new PropertyEditFloat("Border width", stWidth).add(gui);
		new PropertySliderFloat("Rect size", rSize, 0.5f, 2.0f).add(gui);
		new PropertyColorChooser("Fill color", borderColor).add(gui);
		new PropertyGraph("Rotation", rotation).add(gui);
		new PropertyGradient("Gradient", gradient).add(gui);

		gui.fg = color(0);
		gui.bg = color(255);
		gui.selectColor = color(196);
		for (Property p:gui) System.out.println(p.get().getString());
		//gui.drawBackground=false;   //uncomment for image background
	}

}
