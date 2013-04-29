package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.blob;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;


class blob
        {
                float shadScale=1.f/0.3f;
                PVector p;
                PVector v;

                PVector ppos;
                float radius;
                blob()
                {
                        p=new PVector( random(-.5f,.5f),
                                        random(-.5f,.5f),
                                        random(-.5f,.5f));
                        v=new PVector( random(-.5f,.5f),
                                        random(-.5f,.5f),
                                        random(-.5f,.5f));
                        radius=0.1f;
                }
                public PVector getPos( State cs)
                {
                        return PVector.add(p,PVector.mult(v,cs.pulse*2.f));
                }
                public float density( State cs,PVector cp)
                {
                        PVector np=PVector.add(p,PVector.mult(v,cs.pulse*2.f));
                        PVector rp=PVector.sub(cp,np);
                        return radius/rp.dot(rp);
                }
                public float shadow( State cs, PVector cp, PVector ld )
                {
                        return GetShadow(cs,cp,cp.dot(cp), ld);
                }
                public float GetShadow( State cs,PVector cp, float ro2, PVector d)
                {
                        PVector np=PVector.add(p,PVector.mult(v,cs.pulse*2.f));
                        PVector o=PVector.sub(cp,np);
                        return intSphereThickness( radius, o, o.dot(o), d)*shadScale;
                }
                public void update(State cs) {
                }
        };