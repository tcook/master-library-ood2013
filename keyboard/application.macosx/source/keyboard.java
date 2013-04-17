import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.ArrayList; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class keyboard extends PApplet {

//To change size of the letters on the keys you need 
//to press shift before pressing the font size buttons.



//Set global variables
int width = 600;
int height = 400;
int fontSize = height/25;
int fontSize2 = fontSize;
int fontSizeMax = height/13;
int fontSizeMin = fontSize;
int keyHeight = (height-100)/5;

int firstRow, secondRow, thirdRow, fourthRow, fifthRow;
int keydistx1 = (firstRow/2)+25;
int keydistx2 = (secondRow/2)+25;
int keydistx3 = (thirdRow/2)+25;
int keydistx4 = (fourthRow/2)+25;
int keydistx5 = (fifthRow/2)+25;

int bgroundColor = 100;
int letterColor = 255;

int cur;
float sw;
boolean toggleShift = false;
boolean update;

ArrayList<Character> textField = new ArrayList<Character>();
boolean colorState = true;

String s = "";
String s2 = "";

  
public void setup() {
  size(600, 400);
  cur = textField.size();
}

public void draw() {

  //Run method to fill row width variables
  rowButtonCalc();
  
  //Background
  fill(bgroundColor);
  
  //Text field
  rect(0,0,width,100);
  
  //First row of keys
  rect(0,100,firstRow,keyHeight);
  rect(firstRow,100,firstRow,keyHeight);
  rect(firstRow*2,100,firstRow,keyHeight);
  rect(firstRow*3,100,firstRow,keyHeight);
  rect(firstRow*4,100,firstRow,keyHeight);
  rect(firstRow*5,100,firstRow,keyHeight);
  rect(firstRow*6,100,firstRow,keyHeight);
  rect(firstRow*7,100,firstRow,keyHeight);
  rect(firstRow*8,100,firstRow,keyHeight);
  rect(firstRow*9,100,firstRow,keyHeight);
  rect(firstRow*10,100,firstRow,keyHeight);
  rect(firstRow*11,100,firstRow,keyHeight);
  rect(firstRow*12,100,firstRow,keyHeight);
  rect(firstRow*13,100,width-firstRow,keyHeight);
   
  //Second row of keys
  rect(0,100+keyHeight,secondRow,keyHeight);
  rect(secondRow,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*2,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*3,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*4,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*5,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*6,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*7,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*8,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*9,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*10,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*11,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*12,100+keyHeight,secondRow,keyHeight);
  rect(secondRow*13,100+keyHeight,width-secondRow,keyHeight);
  
  //Third row of keys
  rect(0,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*2,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*3,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*4,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*5,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*6,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*7,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*8,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*9,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*10,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*11,100+keyHeight*2,thirdRow,keyHeight);
  rect(thirdRow*12,100+keyHeight*2,width-thirdRow,keyHeight);
  
  //Fourth row of keys
  rect(0,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*2,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*3,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*4,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*5,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*6,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*7,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*8,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*9,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*10,100+keyHeight*3,fourthRow,keyHeight);
  rect(fourthRow*11,100+keyHeight*3,width-fourthRow,keyHeight);
  
  //Fifth row of keys
  rect(0,100+keyHeight*4,width-firstRow*4,keyHeight);
  rect(width-firstRow*4,100+keyHeight*4, firstRow,keyHeight);
  rect(width-firstRow*3,100+keyHeight*4, firstRow,keyHeight);
  rect(width-firstRow*2,100+keyHeight*4, firstRow,keyHeight);
  rect(width-firstRow,100+keyHeight*4, firstRow,keyHeight);

  
  //First row of letters on keys
  displayText("`","~",keydistx1,((height-keyHeight*5)+keyHeight/2));
  displayText("1","!",keydistx1+firstRow,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("2","@",keydistx1+firstRow*2,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("3","#",keydistx1+firstRow*3,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("4","$",keydistx1+firstRow*4,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("5","%",keydistx1+firstRow*5,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("6","^",keydistx1+firstRow*6,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("7","&",keydistx1+firstRow*7,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("8","*",keydistx1+firstRow*8,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("9","(",keydistx1+firstRow*9,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("0",")",keydistx1+firstRow*10,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("-","_",keydistx1+firstRow*11,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("=","+",keydistx1+firstRow*12,((height-keyHeight*5)+keyHeight/2)+15);
  displayText("BS","BS",keydistx1+firstRow*13,((height-keyHeight*5)+keyHeight/2)+15);
  
  //Second row of letters on keys
  displayText("Tab","Tab",keydistx2-5,((height-keyHeight*4)+keyHeight/2));
  displayText("q","Q",keydistx2+secondRow,((height-keyHeight*4)+keyHeight/2));
  displayText("w","W",keydistx2+secondRow*2,((height-keyHeight*4)+keyHeight/2));
  displayText("e","E",keydistx2+secondRow*3,((height-keyHeight*4)+keyHeight/2));
  displayText("r","R",keydistx2+secondRow*4,((height-keyHeight*4)+keyHeight/2));
  displayText("t","T",keydistx2+secondRow*5,((height-keyHeight*4)+keyHeight/2));
  displayText("y","Y",keydistx2+secondRow*6,((height-keyHeight*4)+keyHeight/2));
  displayText("u","U",keydistx2+secondRow*7,((height-keyHeight*4)+keyHeight/2));
  displayText("i","I",keydistx2+secondRow*8,((height-keyHeight*4)+keyHeight/2));
  displayText("o","O",keydistx2+secondRow*9,((height-keyHeight*4)+keyHeight/2));
  displayText("p","P",keydistx2+secondRow*10,((height-keyHeight*4)+keyHeight/2));
  displayText("[","{",keydistx2+secondRow*11,((height-keyHeight*4)+keyHeight/2));
  displayText("]","}",keydistx2+secondRow*12,((height-keyHeight*4)+keyHeight/2));
  displayText("\\","|",keydistx2+secondRow*13,((height-keyHeight*4)+keyHeight/2));
  
  //Third row of letters on keys
  displayText("Color","Color",keydistx3,((height-keyHeight*3)+keyHeight/2));
  displayText("a","A",keydistx3+thirdRow,((height-keyHeight*3)+keyHeight/2));
  displayText("s","S",keydistx3+thirdRow*2,((height-keyHeight*3)+keyHeight/2));
  displayText("d","D",keydistx3+thirdRow*3,((height-keyHeight*3)+keyHeight/2));
  displayText("f","F",keydistx3+thirdRow*4,((height-keyHeight*3)+keyHeight/2));
  displayText("g","G",keydistx3+thirdRow*5,((height-keyHeight*3)+keyHeight/2));
  displayText("h","H",keydistx3+thirdRow*6,((height-keyHeight*3)+keyHeight/2));
  displayText("j","J",keydistx3+thirdRow*7,((height-keyHeight*3)+keyHeight/2));
  displayText("k","K",keydistx3+thirdRow*8,((height-keyHeight*3)+keyHeight/2));
  displayText("l","L",keydistx3+thirdRow*9,((height-keyHeight*3)+keyHeight/2));
  displayText(";",":",keydistx3+thirdRow*10,((height-keyHeight*3)+keyHeight/2));
  displayText("\"","'",keydistx3+thirdRow*11,((height-keyHeight*3)+keyHeight/2));
  displayText("Enter","Enter",keydistx3+10+thirdRow*12-10,((height-keyHeight*3)+keyHeight/2));
  
  //Fourth row of letters on keys
  displayText("Shift","Shift",keydistx4,((height-keyHeight*2)+keyHeight/2));
  displayText("z","Z",keydistx4+fourthRow,((height-keyHeight*2)+keyHeight/2));
  displayText("x","X",keydistx4+fourthRow*2,((height-keyHeight*2)+keyHeight/2));
  displayText("c","C",keydistx4+fourthRow*3,((height-keyHeight*2)+keyHeight/2));
  displayText("v","V",keydistx4+fourthRow*4,((height-keyHeight*2)+keyHeight/2));
  displayText("b","B",keydistx4+fourthRow*5,((height-keyHeight*2)+keyHeight/2));
  displayText("n","N",keydistx4+fourthRow*6,((height-keyHeight*2)+keyHeight/2));
  displayText("m","M",keydistx4+fourthRow*7,((height-keyHeight*2)+keyHeight/2));
  displayText(",","<",keydistx4+fourthRow*8,((height-keyHeight*2)+keyHeight/2));
  displayText(".",">",keydistx4+fourthRow*9,((height-keyHeight*2)+keyHeight/2));
  displayText("/","?",keydistx4+fourthRow*10,((height-keyHeight*2)+keyHeight/2));
  displayText("Shift","Shift",keydistx4+fourthRow*11,((height-keyHeight*2)+keyHeight/2));
  
  //Fifth row of letters on keys
  displayText("Space","Space",(width-(firstRow*8)-firstRow/2),((height-keyHeight)+keyHeight/2));
  displayText("A\u2191","A\u2191",(width-(firstRow*3)-firstRow/2),((height-keyHeight)+keyHeight/2));
  displayText("A\u2193","A\u2193",(width-(firstRow*2)-firstRow/2),((height-keyHeight)+keyHeight/2));
  displayText("\u2190","\u2190",(width-(firstRow)-firstRow/2),((height-keyHeight)+keyHeight/2));
  displayText("\u2192","\u2192",(width-firstRow/2),((height-keyHeight)+keyHeight/2));
  
  textSize(fontSize2);
  if(update){
    if(!textField.isEmpty()){
      for(int i=0; i<cur;i++){
        s+=Character.toString(textField.get(i));
      }
      
      for(int i = cur; i<textField.size();i++){
        s2+=Character.toString(textField.get(i));
      }
      sw = textWidth(s);
    }
  }
  //Draws the letters and cursor.
  if(!textField.isEmpty()){text(s,10+textWidth(s)/2,50);}
  line(10+sw,0,10+sw,100);
  if(cur!=textField.size()){text(s2,10+sw+textWidth(s2)/2,50);}
  
  //Reset the strings each time.
  s="";
  s2="";
  //textSize(fontSize);
  
}//END DRAW

//Calculate width of keys for each row
public void rowButtonCalc () {
  firstRow = width/14;
  secondRow = width/14;
  thirdRow = width/13;
  fourthRow = width/12;
  fifthRow = width/8;
}

//Method to put the letters on the keys
public void displayText(String reg, String toggle, int x, int y){
  fill(letterColor);
  textSize(fontSize); 
  textAlign(CENTER);
  if(toggleShift){
    text(toggle,x,y);
    return;
  }
  text(reg,x,y);
}

//adds the character to the output
public boolean addText(char c){
  textField.add(cur,c);
  cur++;
  return true;
}

//Removes a character from the output
public boolean removeText(){
  if(cur>0){
    textField.remove(cur-1);
    cur--;
    return true;
  }
  else{
    return true;
  }
}

//Moves the cursor on the screen
public boolean curMoved(int direction){
    if(!(cur<=0) && direction == -1){
      cur += direction;
      return true;
    }
    else if(!(cur>=textField.size()) && direction == 1){
      cur += direction;
      return true;
    }
    else{
      return true;
    }
}

//Controls the change of the keyboard color
public void colorChange() {
  if (colorState == true) {
    bgroundColor = 255;
    letterColor = 0;
    colorState = false;
  }
  else {
    bgroundColor = 100;
    letterColor = 255;
    colorState = true;
  }
}

public void mousePressed() {
  
  //Top row of keys
  if (mouseX > 0 && mouseX < firstRow && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false) 
    update = addText('\'');
  if (mouseX > 0 && mouseX < firstRow && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('~');
    toggleShift = false; }
  if (mouseX > firstRow && mouseX < firstRow*2 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('1');
  if (mouseX > firstRow && mouseX < firstRow*2 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('!');
    toggleShift = false; }
  if (mouseX > firstRow*2 && mouseX < firstRow*3 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('2');
  if (mouseX > firstRow*2 && mouseX < firstRow*3 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('@');
    toggleShift = false; }
  if (mouseX > firstRow*3 && mouseX < firstRow*4 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('3');
  if (mouseX > firstRow*3 && mouseX < firstRow*4 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('#');
    toggleShift = false; }
  if (mouseX > firstRow*4 && mouseX < firstRow*5 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('4');
  if (mouseX > firstRow*4 && mouseX < firstRow*5 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('$');
    toggleShift = false; }
  if (mouseX > firstRow*5 && mouseX < firstRow*6 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('5');
  if (mouseX > firstRow*5 && mouseX < firstRow*6 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('%');
    toggleShift = false; }
  if (mouseX > firstRow*6 && mouseX < firstRow*7 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('6');
  if (mouseX > firstRow*6 && mouseX < firstRow*7 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('^');
    toggleShift = false; }
  if (mouseX > firstRow*7 && mouseX < firstRow*8 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('7');
  if (mouseX > firstRow*7 && mouseX < firstRow*8 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('&');
    toggleShift = false; }
  if (mouseX > firstRow*8 && mouseX < firstRow*9 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('8');
  if (mouseX > firstRow*8 && mouseX < firstRow*9 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('*');
    toggleShift = false; }
  if (mouseX > firstRow*9 && mouseX < firstRow*10 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('9');
  if (mouseX > firstRow*9 && mouseX < firstRow*10 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('(');
    toggleShift = false; }
  if (mouseX > firstRow*10 && mouseX < firstRow*11 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('0');
  if (mouseX > firstRow*10 && mouseX < firstRow*11 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText(')');
    toggleShift = false; }
  if (mouseX > firstRow*11 && mouseX < firstRow*12 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('-');
  if (mouseX > firstRow*11 && mouseX < firstRow*12 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('_');
    toggleShift = false; }
  if (mouseX > firstRow*12 && mouseX < firstRow*13 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == false)
    update = addText('=');
  if (mouseX > firstRow*12 && mouseX < firstRow*13 && mouseY > 100 && mouseY < keyHeight+100 && toggleShift == true) {
    update = addText('+');
    toggleShift = false; }
  if (mouseX > firstRow*13 && mouseX < firstRow*14 && mouseY > 100 && mouseY < keyHeight+100) {
    update = removeText();
    toggleShift = false;
  }
    
  //Second Row of keys
  if (mouseX > 0 && mouseX < secondRow && mouseY > keyHeight+100 && mouseY < keyHeight*2+100){
    update = addText(' ');
    update = addText(' ');
    update = addText(' ');
  }
  if (mouseX > secondRow && mouseX < secondRow*2 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('q');
  if (mouseX > secondRow && mouseX < secondRow*2 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('Q');
    toggleShift = false; }
  if (mouseX > secondRow*2 && mouseX < secondRow*3 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('w');
  if (mouseX > secondRow*2 && mouseX < secondRow*3 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('W');
    toggleShift = false; }
  if (mouseX > secondRow*3 && mouseX < secondRow*4 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('e');
  if (mouseX > secondRow*3 && mouseX < secondRow*4 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('E');
    toggleShift = false; }
  if (mouseX > secondRow*4 && mouseX < secondRow*5 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('r');
  if (mouseX > secondRow*4 && mouseX < secondRow*5 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('R');
    toggleShift = false; }
  if (mouseX > secondRow*5 && mouseX < secondRow*6 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('t');
  if (mouseX > secondRow*5 && mouseX < secondRow*6 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('T');
    toggleShift = false; }
  if (mouseX > secondRow*6 && mouseX < secondRow*7 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('y');
  if (mouseX > secondRow*6 && mouseX < secondRow*7 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('Y');
    toggleShift = false; }
  if (mouseX > secondRow*7 && mouseX < secondRow*8 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('u');
  if (mouseX > secondRow*7 && mouseX < secondRow*8 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('U');
    toggleShift = false; }
  if (mouseX > secondRow*8 && mouseX < secondRow*9 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('i');
  if (mouseX > secondRow*8 && mouseX < secondRow*9 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('I');
    toggleShift = false; }
  if (mouseX > secondRow*9 && mouseX < secondRow*10 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('o');
  if (mouseX > secondRow*9 && mouseX < secondRow*10 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('O');
    toggleShift = false; }
  if (mouseX > secondRow*10 && mouseX < secondRow*11 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('p');
  if (mouseX > secondRow*10 && mouseX < secondRow*11 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('P');
    toggleShift = false; }
  if (mouseX > secondRow*11 && mouseX < secondRow*12 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('[');
  if (mouseX > secondRow*11 && mouseX < secondRow*12 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('{');
    toggleShift = false; }
  if (mouseX > secondRow*12 && mouseX < secondRow*13 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText(']');
  if (mouseX > secondRow*12 && mouseX < secondRow*13 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('}');
    toggleShift = false; }
  if (mouseX > secondRow*13 && mouseX < secondRow*14 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == false)
    update = addText('\\');
  if (mouseX > secondRow*13 && mouseX < secondRow*14 && mouseY > keyHeight+100 && mouseY < keyHeight*2+100 && toggleShift == true) {
    update = addText('|');
    toggleShift = false; }
    
  //Third row of keys
  if (mouseX > 0 && mouseX < thirdRow && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100) {
    colorChange();
  }
  if (mouseX > thirdRow && mouseX < thirdRow*2 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('a');
  if (mouseX > thirdRow && mouseX < thirdRow*2 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('A');
    toggleShift = false; }
  if (mouseX > thirdRow*2 && mouseX < thirdRow*3 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('s');
  if (mouseX > thirdRow*2 && mouseX < thirdRow*3 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('S');
    toggleShift = false; }
  if (mouseX > thirdRow*3 && mouseX < thirdRow*4 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('d');
  if (mouseX > thirdRow*3 && mouseX < thirdRow*4 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('D');
    toggleShift = false; }
  if (mouseX > thirdRow*4 && mouseX < thirdRow*5 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('f');
  if (mouseX > thirdRow*4 && mouseX < thirdRow*5 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('F');
    toggleShift = false; }
  if (mouseX > thirdRow*5 && mouseX < thirdRow*6 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('g');
  if (mouseX > thirdRow*5 && mouseX < thirdRow*6 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('G');
    toggleShift = false; }
  if (mouseX > thirdRow*6 && mouseX < thirdRow*7 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('h');
  if (mouseX > thirdRow*6 && mouseX < thirdRow*7 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('H');
    toggleShift = false; }
  if (mouseX > thirdRow*7 && mouseX < thirdRow*8 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('j');
  if (mouseX > thirdRow*7 && mouseX < thirdRow*8 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('J');
    toggleShift = false; }
  if (mouseX > thirdRow*8 && mouseX < thirdRow*9 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('k');
  if (mouseX > thirdRow*8 && mouseX < thirdRow*9 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('K');
    toggleShift = false; }
  if (mouseX > thirdRow*9 && mouseX < thirdRow*10 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('l');
  if (mouseX > thirdRow*9 && mouseX < thirdRow*10 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('L');
    toggleShift = false; }
  if (mouseX > thirdRow*10 && mouseX < thirdRow*11 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText(';');
  if (mouseX > thirdRow*10 && mouseX < thirdRow*11 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText(':');
    toggleShift = false; }
  if (mouseX > thirdRow*11 && mouseX < thirdRow*12 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == false)
    update = addText('\'');
  if (mouseX > thirdRow*11 && mouseX < thirdRow*12 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100 && toggleShift == true) {
    update = addText('\'');
    toggleShift = false; }
  if (mouseX > thirdRow*12 && mouseX < thirdRow*13 && mouseY > keyHeight*2+100 && mouseY < keyHeight*3+100) {
    update = addText(' ');
    toggleShift = false; }
    
  //Fourth row of keys
  if (mouseX > 0 && mouseX < fourthRow && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100)
    toggleShift = true;
  if (mouseX > fourthRow && mouseX < fourthRow*2 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('z');
  if (mouseX > fourthRow && mouseX < fourthRow*2 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('Z');
    toggleShift = false; }
  if (mouseX > fourthRow*2 && mouseX < fourthRow*3 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('x');
  if (mouseX > fourthRow*2 && mouseX < fourthRow*3 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('X');
    toggleShift = false; }
  if (mouseX > fourthRow*3 && mouseX < fourthRow*4 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('c');
  if (mouseX > fourthRow*3 && mouseX < fourthRow*4 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('C');
    toggleShift = false; }
  if (mouseX > fourthRow*4 && mouseX < fourthRow*5 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('v');
  if (mouseX > fourthRow*4 && mouseX < fourthRow*5 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('V');
    toggleShift = false; }
  if (mouseX > fourthRow*5 && mouseX < fourthRow*6 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('b');
  if (mouseX > fourthRow*5 && mouseX < fourthRow*6 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('B');
    toggleShift = false; }
  if (mouseX > fourthRow*6 && mouseX < fourthRow*7 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('n');
  if (mouseX > fourthRow*6 && mouseX < fourthRow*7 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('N');
    toggleShift = false; }
  if (mouseX > fourthRow*7 && mouseX < fourthRow*8 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('m');
  if (mouseX > fourthRow*7 && mouseX < fourthRow*8 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('M');
    toggleShift = false; }
  if (mouseX > fourthRow*8 && mouseX < fourthRow*9 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText(',');
  if (mouseX > fourthRow*8 && mouseX < fourthRow*9 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('<');
    toggleShift = false; }
  if (mouseX > fourthRow*9 && mouseX < fourthRow*10 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('.');
  if (mouseX > fourthRow*9 && mouseX < fourthRow*10 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('>');
    toggleShift = false; }
  if (mouseX > fourthRow*10 && mouseX < fourthRow*11 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == false)
    update = addText('/');
  if (mouseX > fourthRow*10 && mouseX < fourthRow*11 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100 && toggleShift == true) {
    update = addText('?');
    toggleShift = false; }
  if (mouseX > fourthRow*11 && mouseX < fourthRow*12 && mouseY > keyHeight*3+100 && mouseY < keyHeight*4+100)
    toggleShift = true;
  
  //Fifth row of keys
  if (mouseX > 0 && mouseX < width - firstRow*4 && mouseY > keyHeight*4+100 && mouseY < keyHeight*5+100)
    update = addText(' ');
  if (mouseX > width - firstRow*4 && mouseX < width - firstRow*3 && mouseY > keyHeight*4+100 && mouseY < keyHeight*5+100 && toggleShift == false){
    if(fontSize2<fontSizeMax) fontSize2++;
      update = true;
  }
  if (mouseX > width - firstRow*4 && mouseX < width - firstRow*3 && mouseY > keyHeight*4+100 && mouseY < keyHeight*5+100 && toggleShift == true){
    if(fontSize<fontSizeMax) fontSize++;
      update = true; 
  }
  if (mouseX > width - firstRow*3 && mouseX < width - firstRow*2 && mouseY > keyHeight*4+100 && mouseY < keyHeight*5+100 && toggleShift == false){
    if(fontSize2>=fontSizeMin)fontSize2--;
    update = true; }
  if (mouseX > width - firstRow*3 && mouseX < width - firstRow*2 && mouseY > keyHeight*4+100 && mouseY < keyHeight*5+100 && toggleShift == true){
    if(fontSize>=fontSizeMin)fontSize--;
      update = true;
  }
  if (mouseX > width - firstRow*2 && mouseX < width - firstRow && mouseY > keyHeight*4+100 && mouseY < keyHeight*5+100)
    update = curMoved(-1);
  if (mouseX > width - firstRow*1 && mouseX < width && mouseY > keyHeight*4+100 && mouseY < keyHeight*5+100)
    update = curMoved(1);
}
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "keyboard" };
        if (passedArgs != null) {
          PApplet.main(concat(appletArgs, passedArgs));
        } else {
          PApplet.main(appletArgs);
        }
    }
}
