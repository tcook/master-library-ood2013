import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Tetris1 extends PApplet {

	Shape active;
	public void setup() {
	  size(720, 480);
	  //new Block(20,20,color(255,0,0));
	      new Shape(400, 0);
	     //  new Shape(500, 200);
	    //new Next(600, 200);
	}
	public void draw() {
	  background(0, 0, 255);
	  boolean all = false,noMov=true;
	  for (int i =0;i<Shape.shapes.size();i++) {
	    Shape curr=Shape.shapes.get(i);  
	    if(curr.moveable){
	      noMov=false;
	      active=curr; 
	    }
	    Shape.shapes.get(i).draw();
	    for (int x = 0; x < Shape.shapes.size(); x++) {
	      if (Shape.shapes.get(x)!=Shape.shapes.get(i)&&Shape.shapes.get(i).getMoveable() && 
	        !Shape.shapes.get(i).colDetect(Shape.shapes.get(x))){
	          //Shape.shapes.get(i).moveable=false;
	         // new Shape(400, 200);
	          //new Next(600, 200);
	         // break;
	        }
	  }
	   if(Shape.shapes.get(i).moveable && Shape.shapes.size()>0)
	     Shape.shapes.get(i).moveShape(0,1);
	    // else if(i==1)
	    // println("dus");
	 } 
	  //println(Shape.shapes.get(Shape.shapes.size()-1).moveable);
	  if(noMov){
	    new Shape(400,0);
	    active=Shape.shapes.get(Shape.shapes.size()-1);
	  }
	 
	 if (all == false) {
	    for (int x = 0; x< Shape.shapes.size(); x++) {
	      if (Shape.shapes.get(x) instanceof Next) {
	        Shape.shapes.remove(x);
	      }
	    }
	  }
	}
	public void keyPressed(){
	  println(active.previewCol(-32,0));
	 if(key=='a'&&!active.previewCol(-32,0)) {
	   active.moveShape(-32,0);
	 }
	 else if(key=='d'&&!active.previewCol(32,0))
	  active.moveShape(32,0);
	
	}
    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#ECE9D8", "Tetris1" });
    }
}


