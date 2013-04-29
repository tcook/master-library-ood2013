package reuze.demo;

import processing.core.PVector;
import reuze.demo.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;

class Sphere extends BaseVolume {
	public vRes sample(PVector p, vRes br, State cs) {
		float bx = max(max(abs(p.x), abs(p.y)), abs(p.z));
		float bxl = lerp(1.f, bx, cs.pulse);

		float a = smoothstep(1.f, 0.9f, lerp(p.mag(), bx, cs.pulse2)) * (0.5f);

		br.set(new PVector(1.f + cs.pulse2 * 3, 3.f, 4.f - cs.pulse2 * 3),
				a);
		return br;
	}

	PVector m_boxmin = new PVector(-1, -1.f, -1.f);
	PVector m_boxmax = new PVector(1, 1.f, 1.f);

	public PVector getIntersectionVolume(PVector ro, PVector rd, State cs) {
		PVector invrd = new PVector(1.f / rd.x, 1.f / rd.y, 1.f / rd.z);
		return intersectBoxCZero(ro, invrd, m_boxmin, m_boxmax);
	}

	public float getRotation(State cs) {
		return cs.time * 2.f * PI / 8.f;
	}

	public float getRange() {
		return 1.7f;
	}

	public float[] getBGColors() {
		return bgRed;
	}
}