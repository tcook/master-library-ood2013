package reuze.demo;
import processing.core.*;
import reuze.demo.demoEffects.Volume;


class CrabNebula  extends demoEffects implements Volume
{
{
	public vRes  sample( PVector p, vRes br, State cs)
	{
		//noiseDetail(6,0.6);
		float d =p.mag();
		d *=2.f;
		float nf = 1.f;
		float namp = 6.f;
		int numOct =6- cs.quality*2;
		float ox=57.f+cs.time*.1f;
		d += (abs(g_lattice.fbm(p.x+57.f+ox,p.y+513.f+ox,p.z+13.f+ox, numOct,.6f)*.5f)-.25f)*namp;
		//  d += (abs(g_lattice.tableNoise3d(p.x*nf+57.f,p.y*nf+513.f,p.z*nf+13.f)*.5)-.25)*namp;

		//    d += (abs(noise(p.x*nf+57.f,p.y*nf+513.f,p.z*nf+13.f)-.5)-.25)*namp;
		d = PApplet.max(d,0.f);
		float s = 1.f;
		float e = 0.9f;
		float a = PApplet.constrain((d - s)/(e-s),0.f,1);

		float a2 = PApplet.constrain(1.f -d *10.f,0.f,1);

		float a3 = PApplet.constrain(1.f-PApplet.abs((d-0.3f)*10.f),0.f,1);
		a3 = a3*5.f;

		float c = a;
		a*=a;
		float da = PApplet.max(a,a2);
		da= PApplet.max(a3,da);
		float den = 0.1f;
		PVector cExterior = new PVector(4.f+a2*2.f,4.f+a2*2.f,8.f);
		PVector cInterior = new PVector(4.f,1.f,1.f);
		PVector cLine = new PVector(4.f,4.f,1.f);
		PVector cr = a > a2 ? cInterior : cExterior;
		cr = a3 > 0 ? cLine : cr;

		br.set( cr, da*den);
		return br;
	}
	public float getRotation(State cs)
	{
		return cs.time*2.f*PApplet.PI/32.f;
	}
	public float getRange() {
		return 1.15f;
	}

	public float[] getBGColors()
	{
		return bgBlue;
	}
}

