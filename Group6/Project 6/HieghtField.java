package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class HieghtField extends BaseVolume {

	public vRes sample(PVector p, vRes br, State cs) {
		// PVector p =rotatex(dp, cs );
		float d = abs(p.y);

		float ox = 57.f + cs.time * .1f;
		// d+=abs(g_lattice.fbm( p.x+2057.f,p.z+1013.f, 2, .5f)*.5)-.25f;
		d += abs(g_lattice.tableNoise2d(p.x + 2057.f + ox, p.z + 1013.f
				+ ox * 4.f) * .5f)
				- cs.pulse * .4f;

		float a = smoothstep(0.45f, 0.5f, d) - smoothstep(0.5f, 0.8f, d);
		a += smoothstep(cs.pulse, 0.0f, abs(p.x))
				* smoothstep(cs.pulse, 0.0f, abs(p.z)) * 2.f;
		a += smoothstep(max(0.5f - cs.pulse, 0.001f), 0.0f, abs(p.y))
				* (1.f - cs.pulse) * .5f;
		PVector col = new PVector(1.f + d * 8.f, 6.f, 1.f);
		br.set(col, a * .1f);
		return br;
	}

	PVector m_boxmin = new PVector(-1, -.8f, -.8f);
	PVector m_boxmax = new PVector(1, 0.8f, 0.8f);

	public float getRange() {
		return 2.0f;
	};

	public float[] getBGColors() {
		return bgYellow;
	}
}