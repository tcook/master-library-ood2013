package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

public class Hieght2 extends BaseVolume {
	Hieght2(){}
	
	public vRes sample(PVector p, vRes br, State cs) {
		float ox = cs.time * .4f;
		float nf = g_lattice.tableNoise2d(p.x + 13.8f + ox * .1f, p.z
				+ 153.8f + ox);
		float hn = nf * .8f + cs.pulse * .5f;
		float d = smoothstep(p.y, p.y - .05f, hn) * 2.f;// >hn ? 1.: 0;
		float pz = p.z * 6;
		// float grid=pz-(int)pz;
		// float gv=smoothstep( 0.4,0.5,grid)-smoothstep( 0.5,0.6,grid);
		PVector col = new PVector(3.f, 1.f, 0.5f);
		br.set(col, d);

		return br;
	}

	public float[] getBGColors() {
		return bgGreen;
	}

	PVector m_boxmin = new PVector(-1, -.8f, -1.5f);
	PVector m_boxmax = new PVector(1, 0.8f, 1.5f);

	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs) {
		PVector invrd = new PVector(1.f / rd.x, 1.f / rd.y, 1.f / rd.z);
		return intersectBoxCZero(ro, invrd, m_boxmin, m_boxmax);
	}

	public float getRotation(State cs) {
		return (cs.halfpulse * 2.f - 1.f) * PI / 8.f;
	}

	public float getRange() {
		return 1.5f;
	};
}