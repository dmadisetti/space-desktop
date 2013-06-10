import java.awt.*;

public class Boss extends GameEntity {

    boolean isDead = false;
	long alienTime = 200;
	long lastAlienTime = 0;
	int difficulty = 1;
	int damage;

	public Boss(int x,int y,int health,String image) {
	 super(x,y,health,image);
	 x_vel = 3;
	 fric = 1;
	 bounds.setSize(getWidth()*6,getHeight()*4);
	}

    public void update(Space space) {
	  if (x_pos <= 0) {
	    x_vel += accel;  
	  } else if (x_pos >= space.WIDTH-getWidth()*6) {
	    x_vel -= accel;
	  }
	  
	  x_pos += x_vel;
	  x_vel *= fric;
	  
	  bounds.setLocation(getX(),getY());
	  
	  if (System.currentTimeMillis() - space.lastAlienTime < space.alienTime){
	     ;
	  }else {
	  space.lastAlienTime = System.currentTimeMillis();
	  for(int i = 0;i < difficulty;i++){
	    int y = space.gen.nextInt((getHeight()*4));  
	     for(int j = 0;j < difficulty * 3;j++) {
		    space.aliens.add(new Alien(space.gen.nextInt(getWidth()*6)+getX(),y,Alien.BOSS,"images/alien.gif"));
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
	   Graphics2D graphics = (Graphics2D)g.create();
	
	  if (!isDead) {
        graphics.drawImage(img,getX(),getY(),getWidth()*6,getHeight()*4,null);	
	    graphics.setColor(Color.red);
	    graphics.fill3DRect(getX()-50,getY()-10,health,10,true);
	    graphics.dispose();
	  } 
	}

}