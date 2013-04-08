package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class Nebula2 extends BaseVolume {

	final float radius = 1.5f;
	final float invradius = 1.f / radius;

	public vRes sample(PVector p, vRes br, State cs) {
		float d = p.dot(p);
		float namp = 6.f;
		int numOct = 6 - cs.quality * 2;
		float ox = 57.f + cs.time * .1f;

		d += (abs(g_lattice.fbm(p.x + 357.f + ox, p.y + 53.f + ox, p.z
				+ 13.f + ox, numOct, .5f) * .5f) - .25f)
				* namp;
		// float a3 = 1. - min(abs(d-0.5)*2.,1.);
		float a3 = smoothstep(0.25f, 0.5f, d) - smoothstep(0.5f, 0.75f, d);
		float ba = a3 * a3;
		a3 = a3 * 1.5f;

		br.c.set(2.f + ba * 2.f, 2.f + ba * 2.f, 4.f);
		br.a = a3 * 0.3f;
		return br;
	}

	public float getRotation(State cs) {
		return cs.time * 2.f * PI / 32.f;
	}

	public float getRange() {
		return 1.6f;
	}

	public float[] getBGColors() {
		return bgBlue;
	}
}