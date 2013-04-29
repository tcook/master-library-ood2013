package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;


class Fire   extends demoEffects implements Volume
{
{
	float[] icanTempTable;
	float[] biasDensityTable;
	PVector incancol;
	PVector[] incanlghtCol=new PVector[1024];



	public float[] getBGColors()
	{
		return bgRed;
	}

	Fire(PVector incandensce)
	{
		incancol=incandensce;
		icanTempTable = new float[1024];
		biasDensityTable = new float[1024];
		for (int i=0;i<1024;i++)
		{
			// most of shader moved here
			float temp=(float)i/255.f;

			float biasedtemp=inputbias(temp, -0.2f);
			float incan= (1.0f - smoothstep(0.143f, 0.857f, biasedtemp));
			icanTempTable[i]=incan;
			incanlghtCol[i]=PVector.mult( incancol,incan);
			float Oi;

			float density=temp*2.f;
			float biaseddensity = inputbias(density, 0.315f);
			if (biaseddensity < 0.15f) 
				Oi = 0.9f * smoothstep(0.136f, 0.15f, biaseddensity);
			else 
				Oi = 0.9f * (1 - smoothstep(0.15f, 0.857f, biaseddensity));
			biasDensityTable[i]=Oi;
		}
	}


	PVector m_boxmin=new PVector(-.5f,-.8f,-.5f);
	PVector m_boxmax=new PVector(.5f,0.8f,.5f);

	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs )
	{
		PVector invrd=new PVector( 1.f/rd.x,1.f/rd.y,1.f/rd.z);
		return intersectBoxCZero(ro, invrd, m_boxmin, m_boxmax);
	}

	public float inputbias(float x, float bias) {
		return pow(x, -PApplet.log(0.5f + bias) / PApplet.log(2));
	}

	public vRes  sample( PVector p, vRes br, State cs)
	{
		//   PVector p=rotatea(cs.time*PI*.25,dp);
		float d =( p.x*p.x+p.z*p.z)+(1.f-p.y)*.15f;
		d = p.y<1 ? d : 1;

		//d=p.mag()*.5;
		int numOct =4- cs.quality*1;
		float ox=57.f+cs.time;

		d += abs((g_lattice.fbm(p.x+ox,p.y+513.f+ox,p.z+13.f+ox,numOct,.6f))*.6f);

		// density=constrain( density+a,0.,8.);
		// float density= smoothstep( 0.7f,0.2,d) - smoothstep(0.7,0.85f,d);
		float density= smoothstep(  0.8f,0.0f,d);// - smoothstep(0.9,0.95f,d);
		density=1.f-density;
		float temp =cs.pulse*.5f+0.f;
		float incan=icanTempTable[(int)(temp*255.f)];
		PVector incandescence = PVector.mult( incancol,incan);
		float Oi=biasDensityTable[(int)(density*128.f)];
		br.set( incandescence,Oi);
		return br;
	}
	public float getRange() {
		return 1.f;
	};

	public PVector rotatea(PVector p, State cs )
	{
		PVector rp=new PVector();
		rp.y=p.y;
		rp.x = cs.cosang*p.x-cs.sinang*p.z;
		rp.z = cs.cosang*p.z+cs.sinang*p.x;
		return rp;
	}

	@Override
	public float getRotation(State cs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(State cs) {
		// TODO Auto-generated method stub
		
	}

}
