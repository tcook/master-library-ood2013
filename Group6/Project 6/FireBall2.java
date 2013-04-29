package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class FireBall2 extends demoEffects
{
	PVector fcol=new PVector(4.f,2.f,1.f);
	FireBall2( PVector c)
	{
		fcol=c;
	}

	public void update(State cs) 
	{
		cs.numOct=5- cs.quality*2;
		cs.ox=57.f+cs.time*.5f;

	}
	public vRes  sample( PVector p, vRes br, State cs )
	{
		float nf = 1.5f;
		float namp = cs.pulse*.75f+.25f;
		int numOct =cs.numOct;
		float ox=cs.ox;
		float ns=g_lattice.fbm(p.x*nf+ox,p.y*nf+513.f+ox,p.z*nf+13.f+ox,numOct,.6f)*namp;

		// make eplisoid
		float pyroden=PApplet.max(1.f-(p.dot(p))+PApplet.abs(ns),0);
		float a = smoothstep(0.0f,0.1f,pyroden)- smoothstep(0.1f,.3f,pyroden);
		br.setCopy(fcol, a);
		return br;
	}
	public float getRange() {
		return 1.4f;
	}  
	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs )
	{
		return intSphereBoundCZero( 1.4f, ro, rd);
	}

	public float getRotation(State cs) {
		return cs.time*.25f;
	}

	public float[] getBGColors()
	{
		return bgYellow;
	}

	public PVector minv( PVector a, PVector b)
	{
		return new PVector( PApplet.min(a.x,b.x), PApplet.min(a.y,b.y), PApplet.min(a.z,b.z));
	}
	public PVector maxv( PVector a, PVector b)
	{
		return new PVector( PApplet.max(a.x,b.x), PApplet.max(a.y,b.y), PApplet.max(a.z,b.z));
	}
}