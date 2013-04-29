package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.blob;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;

class BaseVolume implements Volume
        {
                public vRes sample( PVector p, vRes br, State cs ) { 
                        return br;
                }
                public float getRange() { 
                        return 1.0f;
                }
                public PVector getIntersectionVolume(PVector ro, PVector rd, State cs )
                {
                        float spRad = getRange();
                        return  intSphereBoundCZero( spRad, ro, rd);
                }
                public float getRotation(State cs) { 
                        return 0;
                }
                public void update(State cs) {
                };
                public float[] getBGColors() { 
                        return bgBlue;
                }
        }
        float[] bgBlue= {
                        0.0f,0.0f,0.0f,  0.2f,0.2f,0.4f,
        };
        float[] bgRed= {
                        0.0f,0.0f,0.0f,  0.35f,0.2f,0.2f
        };
        float[] bgGreen= {
                        0.0f,0.0f,0.0f, 0.2f,0.35f,0.2f
        };
        float[] bgSky= {
                        0.1f,0.2f,0.5f, 0.3f,0.7f,1.f
        };
        float[] bgYellow= {
                        0.0f,0.0f,0.0f, 0.35f,0.35f,0.2f
        };
        public float intSphereThickness( float r2, PVector ro, float ro2, PVector dir)
        {
                float c = ro2 - r2;
                float b = ro.dot(dir);
                float t = b*b-c;
                if ( t > 0.0f) {
                        float st =sqrt(t);
                        float t0 = max(-b-st,0.f);
                        float t1=  max(-b+st,0.f);
                        return t1 -t0;
                }
                return 0.f;
        }