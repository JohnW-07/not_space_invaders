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
	//Polygon.fillPolygon(p1);
	//.fillPolygon(p1);


  public notSpaceInvaders() {
    super("notSpaceInvaders!",1000,1000);
    this.setFocusable(true);
	this.requestFocus();
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
    	
    	//Map wrap-around logic
    	if(p1.position.getX() > 1000) { p1.position.setX(0); }
    	if(p1.position.getX() < 0) { p1.position.setX(1000); }
    	if(p1.position.getY() > 1000) { p1.position.setY(0); }
    	if(p1.position.getY() < 0) { p1.position.setY(1000); }

  }
  
	
	public static void main (String[] args) {
   		notSpaceInvaders a = new notSpaceInvaders();
   		a.addKeyListener(p1);
		a.repaint();
  }
	
}