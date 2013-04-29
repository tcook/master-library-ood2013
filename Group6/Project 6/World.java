package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class World extends BaseVolume {
	public vRes sample(PVector p, vRes br, State cs) {
		float d = p.mag();
		d = d / 1.3f;
		float nf = 3.5f;
		float ox = cs.time * .1f;
		int numOct = cs.numOct;
		float a = g_lattice.fbm(p.x * nf + 57.f + ox,
				p.y * nf + 513.f + ox, p.z * nf + 13.f + ox, numOct, .6f);
		float a2 = max(a, 0.f);
		d -= a2 * 0.07f;
		d = smoothstep(1.f, 0.8f - cs.pulse * .5f, d);
		d *= d;
		d *= 6.f;

		br.c.set(0.6f, 1.f, 2.f);
		if (d > 3.f) {
			d *= 2.f;
			float sblend = constrain(a * 8 + .5f, 0.f, 1.f);
			br.c.set(sblend * a * 2.f, sblend * (a + .5f),
					1.f - sblend * 0.5f);
		}
		br.a = d;
		return br;
	}

	public float getRange() {
		return 1.3f;
	};

	public float getRotation(State cs) {
		return cs.time * .15f;
	}

	public float[] getBGColors() {
		return bgBlue;
	}

	public void update(State cs) {
		cs.numOct = 5 - cs.quality * 2;
		cs.ox = 57.f + cs.time * .1f;
	}
}