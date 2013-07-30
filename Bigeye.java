public class Bigeye extends Alien{

	int r = 0;

	public void set(){
		setHealth(60);
		this.fric = 0.7;
		this.barx = 10;
		this.bary = 10;
		this.y_vel = 17;
	}

	public String getImg(){
		return "images/alien2.png";
	}

	public Bigeye(int x,int y) {
	    super(x,y);
	}
	
	public void update(Space space) {
	   if (y > space.HEIGHT) {	  
	     // You're as good as dead to me!!
	     space.aliens.remove(this);
	     return;
	   }

	   double a = space.player.getY() - getY();
	   double b = getX() - space.player.getX();
	   double c = Math.pow(b,2);

	   if(a > 0){
	   	if(!(b < (2-space.player.getWidth())/2 || b > (space.player.getWidth()/2) -2)){
	   		y += 17;
	   	}else{
		   if(b > 0){
		   	b -= 1;
		   	x -= 1;
		   }
		   if(b < 0){
		    b += 1;
		    x += 1;
		   }
		   // Aim 2 infront of character for maximum death
		   double newy = space.player.getY() - (a*(Math.pow(b,2)))/c;
		   y = newy - y > 15 ? y + 12 : newy - y < 0 ? newy + 5 : newy - y > 7? newy - 5 : newy;
	    }
	   }else{
	   	y += 7;
	   }

	   bounds.setLocation(getX(),getY());
	   
	   // Move to bullet 
	   for(Bullet bullet : space.bullets) {	   
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