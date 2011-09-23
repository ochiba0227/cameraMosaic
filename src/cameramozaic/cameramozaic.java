package cameramozaic;

import processing.core.*;
import processing.video.*; 
import ddf.minim.*;

@SuppressWarnings("serial")
public class cameramozaic extends PApplet {
	Capture cap;
	Minim minim;
	AudioPlayer voice;
	PImage mo;
	int x,y,gx,gy;
	int sx=0;
	int sy=0;
	int flag=0;

	public void setup(){
		textSize(60);
		background(255);
		size(1280,1024);
		minim = new Minim(this);
		voice = minim.loadFile("cameramozaic/itsuka.mp3");
		cap = new Capture(this, 1280,1024);
		frameRate(30);
	}

	public void draw(){
	  if(cap.available()) { 
	    cap.read(); 
	    image(cap, 0, 0); 
	  }
	  if(mousePressed == true&&flag==0){ 
	    noFill(); 
	    rect(x, y, sx, sy);
	    gx=mouseX;
	    gy=mouseY;
	  }
	  else if(flag==1){
	    int mx=gx-x;
	    int my=gy-y;
	    mo=get(x,y,mx,my);
	    for(int i=0;i<my;i+=my/20){
	      for(int j=0;j<mx;j+=mx/20){
	        fill(mo.pixels[j+(mx*i)]);
	        rect(x+j,y+i,mx/20,my/20);
	      }
	    }
	    fill(255);
	    text("Iキーを押してみよう！",0,60);
	  }
	  fill(255); 
	  text("Cキーでモザイク終了",0,984); 
	}
	public void mouseDragged(){
	  if(flag==0){
	    if(sx+mouseX-pmouseX<width||sx+mouseX-pmouseX>0){
	      sx+=mouseX-pmouseX;
	    }
	    if(sy+mouseY-pmouseY<height||sy+mouseY-pmouseY>0){
	      sy+=mouseY-pmouseY;
	    }
	  }
	}
	public void mousePressed(){
	  if(flag==0){
	    x=mouseX;
	    y=mouseY;
	  }
	}
	public void mouseReleased(){
	  int swap;
	  if(gx<x){
	    swap=gx;
	    gx=x;
	    x=swap;
	  }
	  if(gy<y){
	    swap=gy;
	    gy=y;
	    y=swap;
	  }
	  if(gx-x>20&&gy-y>20&&gx>0&&gx<width&&gy>0&&gy<height){
	    flag=1;
	    noStroke();
	  }    
	  else{
	    x=y=sx=sy=flag=0;
	  }   
	}
	public void keyPressed(){
	  if(key=='c'){ 
	    x=y=sx=sy=flag=0;
	    stroke(0);
	  }
	  else if(key=='s'){
	  cap.settings();
	  }
	  else if(key=='i'&&flag==1){
	    voice.rewind();
	    voice.play();
	  }
	  else if(key==ESC){
		  exit();
	  }
	}
	public void stop(){
	  voice.close();
	  minim.stop();
	  super.stop();
	} 
  public static void main(String args[]){
	    PApplet.main(new String[] {"cameramozaic.cameramozaic" });
	}
}