package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class TextV   extends demoEffects
{

	PGraphics pg;
	int[]    pix;
	PFont font;
	PVector m_boxmin=new PVector(-1.8f,-1.f,-.3f);
	PVector m_boxmax=new PVector(1.8f,1.f,.3f);
	TextV( String val )
	{
		//font=loadFont("Andalus-48.vlw");

		pg = createGraphics(196, 64 /*,PConstants.P2D*/);
		pg.beginDraw();
		pg.background(0);
		PFont font;
		font = createFont("SansSerif.plain", 48); 
		textFont(font);
		pg.fill(~0);
		pg.line(20,20,40,40);
		pg.textSize(48);
		pg.text(val,0,48);
		pg.endDraw();

		pg.loadPixels();
		pix=pg.pixels;
		pg.updatePixels();

		boolean anyset=false;
		for(int i=0;i<pix.length;i++)
			if ( ((pg.pixels[i])&0xff)!=0)
				anyset=true;
		// percompute bilinear frac
		assert( anyset);
	}

	private PGraphics createGraphics(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs )
	{
		PVector invrd=new PVector( 1.f/rd.x,1.f/rd.y,1.f/rd.z);
		return intersectBoxCZero(ro, invrd, m_boxmin, m_boxmax);
	}

	public float getRotation(State cs)
	{
		return sin(cs.time*2.f*PI*.15f)*.25f;
	}

	public vRes  sample( PVector p, vRes br, State cs)
	{
		// do 3D noise on p
		PVector np = new PVector();
		np.set(p);
		//    PVector np=rotatea(p,cs);
		float ox=57.f+cs.time*.4f;
		float amp = cs.pulse*0.5f;
		//np.x += g_lattice.tableNoise2d( np.x+13.8f+ox,np.y+153.8f+ox)*amp;
		//np.y += g_lattice.tableNoise2d( np.y+13.8f+ox*2.f,np.z+153.8f+ox*2.f)*amp;
		//np.z += g_lattice.tableNoise2d( np.z+13.8f+ox,np.x+153.8f+ox)*amp;

		float x =PApplet.constrain( (np.x+1.7f)*.25f*(float)pg.width,0,pg.width-2);
		float y=PApplet.constrain((np.y+2.f)*.25f*(float)pg.height,0,pg.height-2);
		int ix=(int)x;
		int iy=(int)y;
		float u=x-(float)ix;
		float v =y-(float)iy;
		int i0=ix;
		int i1=ix+1;
		int j0=iy*pg.width ;
		int j1=(iy+1)*pg.width ;

		float a =(float)( pix[i0+j0 ]&0xff);
		float b =(float)( pix[ i1+j0]&0xff);
		float c = (float)(pix[ i0+j1 ]&0xff);
		float d =(float)( pix[ i1+j1 ]&0xff);
		float k1 =   b - a;
		float k2 =   c - a;
		float k4 =   a - b - c + d;

		float dv=a+k1*u+k2*v+k4*u*v; 
		dv *=1.f/255.f * ( 1.f-cs.pulse);

		int numOct =4- cs.quality*2;
		dv *=smoothstep(0.3f,0.25f, PApplet.abs(np.z));
		PVector incancol=new PVector(7.776f*(.5f+(cs.pulse)),20*.5f, 3.702f*(.5f+(1.f-cs.pulse)) );
		br.set( incancol,dv*(cs.pulse*.5f+.25f));
		return br;
	}
	public float getRange() {
		return 1.8f;
	};
}

