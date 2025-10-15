package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;

class notSpaceInvaders extends Game {
	static int counter = 0;
	static Ship p1 = new Ship(500,500,"Blue");
	static Stars[] starList = new Stars[150];
	static boolean starsDrawn = true;
	//Polygon.fillPolygon(p1);
	//.fillPolygon(p1);


  public notSpaceInvaders() {
    super("notSpaceInvaders!",1450,850);
    this.setFocusable(true);
	this.requestFocus();
	for(int i = 0; i < starList.length - 25; i ++) {
		starList[i] = new Stars((int)(1450 * Math.random()),(int)(850 * Math.random()), "WHITE");
	}
  }
  
	public void paint(Graphics brush) {
    	brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
    	
    	
    	
    	// sample code for printing message for debugging
    	// counter is incremented and this message printed
    	// each time the canvas is repainted
    	counter++;
    	brush.setColor(Color.white);
    	brush.drawString("Counter is " + counter,10,10);
    	p1.paint(brush);
    	p1.move();
    	
    	//if (!starsDrawn) { 
    		for (int i = 0; i < starList.length - 25; i++) {
    			brush.setColor(Color.white);
    			starList[i].paint(brush, p1.getXVel(), p1.getYVel());
    			if(starList[i].position.getX() > 1450) { starList[i].position.setX(0); }
    	    	if(starList[i].position.getX() < 0) { starList[i].position.setX(1450); }
    	    	if(starList[i].position.getY() > 850) { starList[i].position.setY(0); }
    	    	if(starList[i].position.getY() < 0) { starList[i].position.setY(850); }
    		}
    		//starsDrawn = false;
    	//}
    	
    	//Map wrap-around logic
    	if(p1.position.getX() > 1450) { p1.position.setX(0); }
    	if(p1.position.getX() < 0) { p1.position.setX(1450); }
    	if(p1.position.getY() > 850) { p1.position.setY(0); }
    	if(p1.position.getY() < 0) { p1.position.setY(850); }
    	
    	//add extra stars on wrap-around

  }
  
	
	public static void main (String[] args) {
   		notSpaceInvaders a = new notSpaceInvaders();
   		a.addKeyListener(p1);
		a.repaint();
  }
	
}