package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;



class Cloud  extends demoEffects 
{
	Cloud()
	{
		// generate 32x32x32 shadow cube based on range
	}
	PVector lcol=new PVector(1.f,0.8f,0.5f);
	PVector abcol=new PVector(0.2f,0.2f,0.4f);

	public vRes  sample( PVector p, vRes br, State cs )
	{
		float pm= p.mag();
		;
		float d = pm;
		float nf = 2.f;
		float namp = 1.f;
		int numOct =6- cs.quality*2;
		float ox=57.f+cs.time*.1f;
		d +=  (g_lattice.fbm(p.x*nf+ox,p.y*nf+513.f+ox,p.z*nf+13.f+ox,numOct,.7f)+(1.f-cs.pulseSqrt))*namp;

		float a = smoothstep( 1.f,0.5f, d)*2.f;
		a*=PApplet.min((1.5f-pm)*8,1.f);
		// need shadow term??

		// do approx lighting on p.y
		float lgt = PApplet.constrain( ((1.f-p.y)*d)*.5f+.5f,0.f,1.f);
		PVector lgtv = PVector.mult(lcol,lgt);
		lgtv.add(abcol);
		br.set(lgtv, a);
		return br;
	}
	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs )
	{
		float spRad = 1.5f-(1.f-cs.pulseSqrt)*.5f;
		return  intSphereBoundCZero( spRad, ro, rd);
	}

	public float getRange() {
		return 1.5f;
	}
	public float[] getBGColors()
	{
		return bgSky;
	}
}