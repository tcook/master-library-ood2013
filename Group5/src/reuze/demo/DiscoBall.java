package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class DiscoBall extends BaseVolume {
	PVector ldir = new PVector(0.2f, 1.f, 0.f);
	PVector lpos = new PVector(0.f, -1.f, 0.f);
	PVector lcol = new PVector(1.f, 0.8f, 0.5f);
	PVector abcol = new PVector(0.2f, 0.2f, 0.4f);

	DiscoBall() {
		ldir.normalize();
	}

	public vRes sample(PVector p, vRes br, State cs) {
		PVector spdir = PVector.sub(p, lpos);
		spdir.x += cs.pulse * .5f - .5f;
		float mag2 = spdir.x * spdir.x + spdir.y * spdir.y + spdir.z
				* spdir.z;
		float invd = 1.f / sqrt(mag2);
		float l = constrain(invd * invd * (1.f + cs.pulse * 2.f), 0.f, 6.f);
		spdir.mult(invd);
		float d = spdir.dot(ldir);

		int numOct = 4 - cs.quality * 2;
		float ox = 57.f + cs.time * .1f;
		float nf = 5.f;
		float acone = smoothstep(0.4f + cs.pulse * .5f, 0.9f, d);
		float a = ((g_lattice.fbm(p.x * nf + 57.f + ox, p.y * nf + 513.f
				+ ox, p.z * nf + 13.f + ox, numOct, .6f) + 1.f) * .5f);
		a = p.y > 1.f ? 0 : a;

		PVector lgt = PVector.mult(lcol, acone * l);
		lgt.add(abcol);
		br.set(lgt, a);
		return br;
	}

	public float[] getBGColors() {
		return bgBlue;
	}

	public float getRange() {
		return 1.7f;
	};
}