import java.awt.*;

public class Bullet extends GameEntity{

	double angle;
	int speed = 10;
	String image = "images/shot.gif";

	public Bullet(int x,int y, double angle){
	  super(x,y);
	  this.angle = angle * (Math.PI/180);
	}
	
	public void update(Space space) {
	  	    
	  // Kill and get out of dodge if off screen
	  if(y<=0 || y>=space.HEIGHT || x<=0 || x>=space.WIDTH){
	    space.bullets.remove(this);
	  	return;
	  }

	  x_vel = speed * Math.sin(angle);
	  y_vel = speed * Math.cos(angle);

	  x += x_vel;
	  y -= y_vel; 
	  
	  bounds.setLocation(getX(),getY()); 
	  
    }
	
	public void draw(Graphics2D g) {
	  Graphics2D graphics = (Graphics2D)g.create();
	  graphics.rotate(angle,getx() + getWidth()/2,gety() + getHeight()/2);
	  graphics.drawImage(img,getX(),getY(),null);
	  graphics.dispose();
	}

}