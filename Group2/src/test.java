package reuze.app;

public class test extends appGUI {
	public void setup() {
		size(600, 400);
		smooth();
		img = loadImage("../data/mearth.jpg");
		time = 0;
		running = new MinyBoolean(this, true);
		this.speed = new MinyInteger(this, 2);
		timeCaption = new MinyString(this, "0.0");
		stWidth = new MinyFloat(this, 2.0f);
		// BWB032513
		rSize2 = new MinyInteger(this, 0);
		time2=new MinyInteger(this);
		// END_BWB
		// BWB032513
		MinyInteger testint = new MinyInteger(this, 1);
		// END_BWB
		borderColor = new MinyColor(this, color(192));
		rotation = new InterpolatedFloat(this, 1);
		rotation.add(0, 0);
		rotation.add(1.5f, -PI);
		rotation.add(3.0f, PI);
		rotation.add(4.0f, 2 * PI);
		rotation.add(6.0f, 0);

		gradient = new ColorGradient(this, color(0));
		gradient.add(0, color(0, 1));
		gradient.add(0.25f, color(255, 0, 0));
		gradient.add(0.5f, color(0, 255, 0));
		gradient.add(0.75f, color(0, 0, 255));
		gradient.add(1, color(255));

		gui = new MinyGUI(this, 0, 0, 200, height);// height=400
		new PropertyButton(this, "Start", new TestButton(this)).add(gui);
		new PropertyButton(this, "Tester", new TestButton(this)).add(gui);
		new PropertyButtonImage(this, "../data/particle.png", new TestButton(this))
				.add(gui);
		new PropertyCheckBox(this, "Running", running).add(gui);
		new PropertyList(this, "Speed", speed, "slowest;slow;normal;fast;fastest")
				.add(gui);
		new PropertyDisplay(this, "Time", timeCaption).add(gui);
		new PropertyEditFloat(this, "Border width", stWidth).add(gui);
		// BWB032513
		new PropertySliderInteger(this, "Rect size", rSize2, 0, 10).add(gui);
		// END_BWB
		// BWB032513
		new PropertyProgBar(this, "Size", rSize2, 10,5).add(gui);
		new PropertyProgBar(this, "Time", time2,6,6).add(gui);
		// END_BWB
		new PropertyColorChooser(this, "Fill color", borderColor).add(gui);
		new PropertyGraph(this, "Rotation", rotation).add(gui);
		new PropertyGradient(this, "Gradient", gradient).add(gui);
		PropertyRadioButtonGroup test = new PropertyRadioButtonGroup(this, "Test Buttons");
		test.AddToGroup(new PropertyRadioButton(this, "Test"));
		test.AddToGroup(new PropertyRadioButton(this, "Test 2"));
		test.add(gui);


		gui.fg = color(0);
		gui.bg = color(255);
		gui.selectColor = color(196);
		for (Property p : gui)
			System.out.println(p.get().getString());
		// gui.drawBackground=false; //uncomment for image background
	}

}
