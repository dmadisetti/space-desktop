import java.awt.*;

public class Bullet extends GameEntity{

	double angle;
	
	public Bullet(int x,int y,int speed, double angle,String image){
	  super(x,y,0,image);
	  this.speed = speed;
	  this.angle = angle * (Math.PI/180);
	}
	
	public void update(Space space) {
	  
	    
	  x_vel = speed * Math.sin(angle);
	  y_vel = speed * Math.cos(angle);
	  
	    
	  if(y_pos<=0 || y_pos>=space.HEIGHT || x_pos<=0 || x_pos>=space.WIDTH){
	    space.bullets.remove(this);
	  }
	    x_pos += x_vel;
	    y_pos -= y_vel; 
	  
	  
	  bounds.setLocation(getX(),getY()); 
	  
    }
	
	public void draw(Graphics2D g) {
	  //g.drawImage(img,getX(),getY(),null);	
	  Graphics2D graphics = (Graphics2D)g.create();
	  graphics.rotate(angle,getX_pos() + getWidth()/2,getY_pos() + getHeight()/2);
	  graphics.drawImage(img,getX(),getY(),null);
	  graphics.dispose();
	}

}