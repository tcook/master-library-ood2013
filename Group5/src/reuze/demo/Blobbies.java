package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.blob;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;


class Blobbies extends demoEffects implements Volume
{
	
	Blobbies(){}
	blob b0=new blob(); 
	blob b1=new blob(); 
	blob b2=new blob();   
	PVector c0=new PVector(2.f,0.5f,0.5f);
	PVector c1=new PVector(0.5f,2.f,0.5f);
	PVector c2=new PVector(0.5f,.5f,2.f);


	public void update( State cs)
	{
		cs.p0=b0.getPos(cs);
		cs.p1=b1.getPos(cs);
		cs.p2=b2.getPos(cs);

		float brad=0.6f;
		cs.bmin=minv(minv(cs.p0,cs.p1),cs.p2);
		cs.bmin.add(-brad,-brad,-brad);
		cs.bmax=maxv(maxv(cs.p0,cs.p1),cs.p2);
		cs.bmax.add(brad,brad,brad);
	}
	public vRes  sample( PVector p, vRes br, State cs )
	{
		float d0= BlobDensity(p, cs.p0, b0.radius);
		float d1=BlobDensity(p, cs.p1, b1.radius);
		float d2=BlobDensity(p, cs.p2, b2.radius);

		float d=d0+d1+d2;

		PVector col=br.c;
		col.x =c0.x*d0+c1.x*d1+c2.x*d2;
		col.y =c0.y*d0+c1.y*d1+c2.y*d2;
		col.z =c0.z*d0+c1.z*d1+c2.z*d2;
		col.mult(1.f/d);

		float namp = 0.1f;
		int numOct =5- cs.quality*3;
		float ox=57.f+cs.time*.1f;
		float nf=3.f;
		d +=  g_lattice.fbm(p.x*nf+ox,p.y*nf+513.f+ox,p.z*nf+13.f+ox,numOct,.6f)*namp;

		float a=smoothstep(0.3f,0.5f,d)-smoothstep(0.6f,.7f,d);
		br.a= a; 
		return br;
	}
	public float getRange() {
		return 1.3f;
	}
	public float[] getBGColors()
	{
		return bgGreen;
	}
	public float getRotation(State cs) {
		return cs.time*.25f;
	}

	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs )
	{
		PVector invrd=new PVector( 1.f/rd.x,1.f/rd.y,1.f/rd.z);
		return intersectBoxCZero(ro, invrd, cs.bmin, cs.bmax);
	}

}