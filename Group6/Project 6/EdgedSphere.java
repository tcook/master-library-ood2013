package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;

class EdgedSphere  extends BaseVolume
        {
                public vRes  sample( PVector p, vRes br, State cs )
                {
                        float d =p.mag();

                        float s = 1.f;
                        float e = 0.9f;
                        if ( d < e )
                                return  new vRes( new PVector(0.8f,0.4f,0.1f),1.f);
                        float a = constrain((d - s)/(e-s),0.f,1);
                        a*=a;
                        float den = 0.8f;
                        br.set( new PVector(1.f,1.f,4.f), a*den); 
                        return br;
                }
                public float getRange() {
                        return 1.f;
                }

                public float[] getBGColors()
                {
                        return bgSky;
                }
        }