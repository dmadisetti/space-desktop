import java.awt.*;

// Thoughts on making this astract/an 
// interface to build more detailed aliens??? 
abstract class Alien extends GameEntity{

	// Declare here for switch/better practice 
	Graphics2D graphics;

	int fric = 0.7;
	int health = 60;
	int scaleWidth = 2;
	int scaleHeight = 2;
	int barx = 10;
	int bary = 10;


	public Alien(int x,int y) {
	    super(x,y,0,image);
		setHealth(health);
	    bounds.setSize(scale*getWidth(),scale*getHeight());
	}
		
	// Abstract draw. But if we do keep one class. Easier to manage with switch
	public void draw(Graphics2D g) {
    	g.drawImage(img,getX(),getY(),scaleWidth*getWidth(),scaleHeight*getHeight(),null);
	    graphics = (Graphics2D)g.create();
	    graphics.setColor(Color.red);
	    graphics.fill3DRect(getX()+barx,getY()-bary,health,10,true);
	    graphics.dispose();
	}

}