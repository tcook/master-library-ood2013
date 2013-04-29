package reuze.demo;

import processing.core.*;
import reuze.demo.demoEffects.BaseVolume;
import reuze.demo.demoEffects.State;
import reuze.demo.demoEffects.blob;
import reuze.demo.demoEffects.vRes;
import reuze.demo.demoEffects.Volume;



class Scam
        {
                float corigin = 4.f;
                float   camAngle = 0;
                PVector  cacDir;

                Scam() {
                        update();
                }
                Scam(Scam other)
                {
                        corigin=other.corigin;
                        camAngle=other.camAngle;
                        update();
                }
                public void update()
                {
                        cacDir = new PVector( sin(camAngle),0.f,cos(camAngle));
                }
                public void dragged()
                {
                        float dx = pmouseX-mouseX;
                        float dy= pmouseY-mouseY;

                        corigin += dy * 0.5f/(float)height;
                        camAngle += dx * 0.5f/(float)width;
                        update();
                }
                public void AddRotation( float r)
                {
                        camAngle+=r;
                        update();
                }
                public PVector getOrigin()
                {
                        PVector  orig = new PVector(-cacDir.x,0.f,cacDir.z);
                        orig.mult(-corigin);
                        return orig;
                }
                public PVector getDir( int ppx, int ppy, PVector tcdir, int w, int h)
                {
                        // note could change to add and normalize
                        PVector  cdir = new PVector( -w/2+ppx,-h/2+ppy,w);

                        // 2D rotate
                        PVector camDir = tcdir;
                        camDir.x = cdir.x * cacDir.z - cdir.z * cacDir.x;
                        camDir.z = cdir.z * cacDir.z + cdir.x * cacDir.x;
                        camDir.y= cdir.y;  
                        camDir.normalize();
                        return camDir;
                }
        }