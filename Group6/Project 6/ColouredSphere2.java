package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;

class ColouredSphere2 extends demoEffects implements Volume
{
{
	public PVector col( float d )
	{
		float id = PApplet.constrain( 2.0f - d*2.f, 0.f,1.f);
		float r = id;
		float b = 1.f-PApplet.abs(id-.5f)*2.f;
		float g = id*id*id;
		float i = 2.f;
		return new PVector(r*i+.1f,g*i+.2f,b*i+.5f);
	}
	public vRes  sample( PVector p, vRes br, State cs )
	{
		float d = p.dot(p);
		float nf = 2.f;
		int numOct =6- cs.quality*2;
		float ox=57.f+cs.time;
		float hotness=(1.f-p.y)*.5f+cs.pulse*0.5f;
		d += (abs(g_lattice.fbm(p.x*nf+ox,p.y*nf*0.75f+513.f+ox*2.f,p.z*nf+13.f+ox,numOct,.6f)*.5f)-.25f)*2.f*hotness;
		float a = smoothstep( 1.f,0.9f,d);
		PVector cv=col(d*1.25f);//+(.5-hotness*.5));
		br.set(cv, a);
		return br;
	}
	public float getRange() {
		return 1.25f;
	};
	public float[] getBGColors()
	{
		return bgBlue;
	}
} 
