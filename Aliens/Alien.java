import java.awt.*;

// Thoughts on making this astract/an 
// interface to build more detailed aliens??? 
abstract class Alien extends GameEntity{

	// Split into own classes
    final static int NORMAL = 0;
	final static int BOSS = 1;

	// Declare here for switch/better practice 
	Graphics2D graphics;

	int id;
	int scale = 1;

	public Alien(int x,int y,int id,String image) {
	    super(x,y,0,image);
		this.id = id;
		if (id == NORMAL) {
		   scale = 2;
		   fric = 0.7;
		   setHealth(60);  
		} else if (id == BOSS) {
		   setHealth(30);
		} else {
		   id = NORMAL;
		   fric = 0.7;
		   setHealth(60);
		}

	    bounds.setSize(scale*getWidth(),scale*getHeight());
		
	}
	
	public void update(Space space) {
	   if (y_pos < space.HEIGHT) {
	      y_vel += accel;
		  if(y_pos < space.player.getY() && x_pos < space.player.getX()) {
		    x_vel += accel;
		  } else if(y_pos < space.player.getY() && x_pos > space.player.getX()) {
		    x_vel -= accel;
		  } else {
		    x_vel = 0;
		  }
	   } else {
	      space.aliens.remove(this);
	   }
	   
	   y_pos += y_vel;
	   x_pos += x_vel;
	   y_vel *= fric;
	   x_vel *= fric;
	   
	   bounds.setLocation(getX(),getY());
	   
	   for(Bullet bullet : space.bullets) {
	     if (bullet.y_pos > y_pos){
		    if(x_pos >= bullet.x_pos-200 && x_pos <= bullet.x_pos+200 && x_pos<bullet.x_pos) {
			    x_pos -= accel;
			}else if(x_pos >= bullet.x_pos-200 && x_pos <= bullet.x_pos+200 && x_pos>bullet.x_pos) {
			    x_pos += accel;
			}
		 }
	   
	      if (bullet.bounds.intersects(bounds)) {
			  space.bullets.remove(bullet);
		    if(health <= 0){
			  space.aliens.remove(this); 
			  space.score += 10;
			  space.remEnemies--;
			  space.canCreateBonus = true;
			  space.explosionHappened = true;
			  space.explX = getX() + getWidth()/2;
			  space.explY = getY() + getHeight()/2;
			  space.bonusX = getX();
			  space.bonusY = getY();
			} else {
			  health -= 30;
			}
		  }
	   }
	}
	
	// Abstract draw. But if we do keep one class. Easier to manage with switch
	public void draw(Graphics2D g) {
    	g.drawImage(img,getX(),getY(),scale*getWidth(),scale*getHeight(),null);
	    graphics = (Graphics2D)g.create();
	    graphics.setColor(Color.red);
	    graphics.fill3DRect(getX()+10,getY()-10,health,10,true);
	    graphics.dispose();
	}

}