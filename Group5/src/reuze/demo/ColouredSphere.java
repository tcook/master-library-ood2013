package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class ColouredSphere extends BaseVolume {
	PVector[] colTable = new PVector[256];

	ColouredSphere() {
		for (int i = 0; i < 256; i++)
			colTable[i] = col((float) i / 64.f);
	}

	public PVector col(float d) {
		float id = constrain(2.0f - d * 2.f, 0.f, 1.f);
		float r = id;
		float b = 1.f - abs(id - .5f) * 2.f;
		float g = id * id * id;
		float i = 2.f;
		return new PVector(r * i, g * i, b * i);
	}

	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs) {
		float spRad = 1.5f;// -(1.-cs.pulse)*.5;
		return intSphereBoundCZero(spRad, ro, rd);
	}

	public vRes sample(PVector p, vRes br, State cs) {
		float d = p.mag();
		float nf = 3.f;
		int numOct = 5 - cs.quality * 2;
		float ox = 57.f + cs.time * .2f;
		float hotness = cs.pulse * .25f + .75f;
		d += (abs(g_lattice.fbm(p.x * nf + ox, p.y * nf + 513.f + ox, p.z
				* nf + 13.f + ox, numOct, .6f) * .5f) - .25f)
				* 2.f * hotness;
		float a = smoothstep(1.f, 0.9f, d);
		PVector cv = col(d + (.5f - hotness * .5f));
		// cv.set(colTable[(int)max((d+(.5-hotness*.5))*64.,0.)] );
		br.set(cv, a);
		return br;
	}

	public float[] getBGColors() {
		return bgGreen;
	}

}