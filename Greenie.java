public class Greenie extends Alien{

	public void set(){
		setHealth(60);
		this.fric = 0.7;
		this.barx = 10;
		this.bary = 10;
	}

	public String getImg(){
		return "images/alien.gif";
	}

	public Greenie(int x,int y) {
	    super(x,y);
	}
	
	public void update(Space space) {
	   if (y < space.HEIGHT) {
	      y_vel += accel;
		  
		  // Ternaries are sexy
		  // Bit of AI going on here
		  x_vel = y < space.player.getY() && x < space.player.getX() ?
		    x_vel + accel
		  : y < space.player.getY() && x > space.player.getX() 		 ?
		    x_vel - accel
		  : 0;
	  
	   // You're as good as dead to me!!
	   } else space.aliens.remove(this);
	   
	   // Maybe move to Alien
	   y += y_vel;
	   x += x_vel;
	   y_vel *= fric;
	   x_vel *= fric;
	   
	   bounds.setLocation(getX(),getY());
	   
	   // Move to bullet 
	   for(Bullet bullet : space.bullets) {
	     if (bullet.y > y){
		    if(getX() >= bullet.getX() - 200 && x <= bullet.getX() + 200 && getX() < bullet.getX()) {
			    x -= accel;
			}else if(getX() >= bullet.getX()-200 && getX() <= bullet.getX() + 200 && getX()>bullet.getX()) {
			    x += accel;
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
}