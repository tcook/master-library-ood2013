package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;
class CloudPuff extends demoEffects implements Volume
{
{
	PVector lpos=new PVector(1.f,-1.f,1.f);
	float shadRadius=1.f;

	PVector lcol=new PVector(1.f,0.8f,0.5f);
	PVector abcol=new PVector(0.1f,0.4f,0.6f);
	float[] translucency=new float[1024];
}CloudPuff()
	{
		//  lpos.normalize();
		for(int i=0;i<1024;i++)
			translucency[i]=PApplet.constrain( PApplet.exp(- ((float)i/1023.f)*1.f/.55f),0.f,2.f);
	}

	// to make better clouds
	public vRes  sample( PVector dp, vRes br, State cs )
	{
		// make eplisoid
		PVector p=br.c;
		p.y=dp.y;
		p.z=dp.z;
		p.x=dp.x*.82f;

		float d=p.dot(p);
		float d0=d;
		float nf = 4.f;
		float namp = 0.15f;
		int numOct =5- cs.quality*2;
		float ox=57.f+cs.time*.25f;
		float ns=g_lattice.fbm(p.x*nf+ox,p.y*nf+513.f+ox,p.z*nf+13.f+ox,numOct,.6f)*namp;

		d+=ns;
		float a = smoothstep( 1.f,0.9f,d)*4.f;

		float th=constrain(intSphereThickness( 1.f, p,d0*d0, lpos),0.f,1.f);
		float shadow = translucency[ (int)(th*1023.f)];
		PVector lgtv = PVector.mult(lcol,shadow);
		float ao=smoothstep(0.5f,1.f,d)*.6f+.4f;
		lgtv.add(PVector.mult(abcol,ao));
		br.set(lgtv, a);
		return br;
	}
	public float getRange() {
		return (1.f/.82f)+ 0.25f;
	}
	final float eppRangex=((1.f/.82f)+ 0.15f)*.92f;
	final float eppRange=(1.f+.15f)*.92f;
	final PVector m_boxmin=new PVector(-eppRangex,-eppRange,-eppRange);
	final PVector m_boxmax=new PVector(eppRangex,eppRange,eppRange);

	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs )
	{
		PVector invrd=new PVector( 1.f/rd.x,1.f/rd.y,1.f/rd.z);
		return intersectBoxCZero(ro, invrd, m_boxmin, m_boxmax);
	}
	public float getRotation(State cs) {
		return cs.time*.25f;
	}

	public float[] getBGColors()
	{
		return bgSky;
	}
	public void update(State cs) {
	}
}