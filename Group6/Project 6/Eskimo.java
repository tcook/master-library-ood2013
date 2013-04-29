package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class Eskimo extends BaseVolume {
	final float radius = 1.5f;
	final float invradius = 1.f / radius;

	public vRes sample(PVector p, vRes br, State cs) {
		// a hollow sphere with slight wobble
		float d = p.mag() * invradius;

		float c = smoothstep(0.2f, 0.0f, d);

		float nf = 1.f;
		float namp = 1.f;
		int numOct = 4 - cs.quality * 2;
		float ox = 57.f + cs.time * .1f;
		d += (abs(g_lattice.fbm(p.x * nf + ox, p.y * nf * 2.f + 513.f + ox
				* 2.f, p.z * nf + 13.f + ox, numOct, .4f) * .5f) - .25f)
				* namp;

		float c3 = smoothstep(0.f, 1.1f, d) - smoothstep(1.1f, 1.3f, d);
		d = smoothstep(0.6f - cs.pulse * .2f, 0.7f, d)
				- smoothstep(0.7f, 0.8f, d);
		d += c * 2.f * (cs.pulse + 1.f);// *min(cs.pulse*4.,2.);
		PVector col = new PVector(2.f + c * 8.f, 2.f + c * 3.f, 24.f);
		col.mult(d);
		col.x += c3 * 5.f;
		// d += c3*0.1;
		br.set(col, d * .1f);
		return br;
	}

	public float getRotation(State cs) {
		return -cs.time * 2.f * PI / 32.f;
	}

	public float getRange() {
		return radius + 0.1f;
	}

	public float[] getBGColors() {
		return bgYellow;
	}
}