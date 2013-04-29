package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class DiscoBall2 extends BaseVolume {
	PVector insideCol = new PVector(1.f, 0.f, 0.25f);
	PVector outsideCol = new PVector(0.f, 0.1f, 2.f);

	DiscoBall2() {
		outsideCol.sub(insideCol);
	}

	public void update(State cs) {
		cs.numOct = 6 - cs.quality * 3;
		cs.ox = 57.f + cs.time;
	}

	public vRes sample(PVector p, vRes br, State cs) {
		float len = p.mag();
		float strength = len / 1.5f;
		float d = smoothstep(1.f, .8f, strength);
		PVector np = br.c;
		np.set(p);
		np.mult(1.f / len);

		int numOct = cs.numOct;
		float ox = cs.ox;
		float nf = 3.f;
		float ns = g_lattice.fbm(np.x * nf + 57.f + ox, np.y * nf + 513.f
				+ ox, np.z * nf + 13.f + ox, numOct, .6f);
		float cf = ns - .5f;
		cf = constrain(cf * 4.f, 0.f, 2.f);

		PVector cval = PVector.add(insideCol,
				PVector.mult(outsideCol, strength));
		float star = ((1.f - strength) + ns * .25f) * (cs.pulse2 + 1.f);
		cval.add(0.f, star, 0.f);
		br.set(cval, d * (cf + star));
		return br;
	}

	public float getRange() {
		return 1.5f;
	};

	public float[] getBGColors() {
		return bgBlue;
	}
}