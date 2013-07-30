import java.awt.*;

// Thoughts on making this astract/an 
// interface to build more detailed aliens??? 
abstract class Alien extends GameEntity{

	// Declare here for switch/better practice 
	Graphics2D graphics;

	double fric = 0.7;
	int scaleWidth = 2;
	int scaleHeight = 2;
	int barx = 10;
	int bary = 10;

	public abstract void set();
	public abstract String getImg();

	public Alien(int x,int y) {
	    super(x,y);
		set();
		setImage();
	    bounds.setSize(scaleWidth*getWidth(),scaleHeight*getHeight());
	}
		
	// Abstract draw. But if we do keep one class. Easier to manage with switch
	public void draw(Graphics2D g) {
    	g.drawImage(img,getX(),getY(),scaleWidth*getWidth(),scaleHeight*getHeight(),null);
	    graphics = (Graphics2D)g.create();
	    graphics.setColor(Color.red);
	    graphics.fill3DRect(getX()+this.barx,getY()-this.bary,this.health,10,true);
	    graphics.dispose();
	}

}