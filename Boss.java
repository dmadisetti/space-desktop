import java.awt.*;

public class Boss extends Alien {

    boolean isDead = false;
	long alienTime = 200;
	long lastAlienTime = 0;
	int difficulty = 1;
	int damage;
	int x_vel = 3;
	double fric = 1;
	int scaleWidth = 6;
	int scaleHeight = 4;
	int barx = 10;
	int bary = 10;
	int health = 400;

	public String getImg(){
		return "images/alien.gif";
	}

	public Boss(int x,int y) {
	 super(x,y);
	}

    public void update(Space space) {
	  if (x <= 0) {
	    x_vel += accel;  
	  } else if (x >= space.WIDTH-getWidth()*6) {
	    x_vel -= accel;
	  }
	  
	  System.out.println(isDead);

	  x += x_vel;
	  x_vel *= fric;
	  
	  bounds.setLocation(getX(),getY());
	  
	  if (System.currentTimeMillis() - space.lastAlienTime < space.alienTime){
	     ;
	  }else {
	  space.lastAlienTime = System.currentTimeMillis();
	  for(int i = 0;i < difficulty;i++){
	    int y = space.gen.nextInt((getHeight()*4));  
	     for(int j = 0;j < difficulty * 3;j++) {
		    space.aliens.add(new BossGreenie(space.gen.nextInt(getWidth()*6)+getX(),y));
		 }
	   }
	  }
	  
	  damage = 30 - (difficulty * 5);
	  if(difficulty == 6) {
	    damage = 2;
	  }
	  
	  for(Bullet bullet : space.bullets) {
	    if (bounds.intersects(bullet.bounds)){
		  if(health >= damage) {
		   health -= damage;
		  } else {
		   isDead = true; 
		   space.explosionHappened = true;
		   space.score += difficulty * 100;
		   space.bossReached = false;
		   space.bossReady = false;
		  } 
		  
		  space.bullets.remove(bullet);
		}
	  }
	  
	  if(bounds.intersects(space.player.bounds)) {
	  
		  if(space.player.shieldArmed){
		   /*if(space.player.shieldTime >= 310){
		     space.player.shieldTime -= 310;
		   }*/
		   space.player.shieldArmed = false;
		  } else {
		    if (space.player.health >= 50){
			   space.player.health -= 50;
			}
		  }
	  }
	}
	
	public void draw(Graphics2D g) {
	
	  // Keep this guy alive for everrrr
	  // Do we really want to though?
	  if (isDead) return;
      super.draw(g);
	}

}