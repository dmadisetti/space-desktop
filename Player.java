import java.awt.*;

public class Player extends GameEntity{
	
	int maxHealth = 100;
	boolean isDead = false;
	boolean shieldArmed = false;
	int shieldTime = 310;
	int	accel = 4;
	int health = 100;
	
	public String getImg(){
		return "images/ship.png";
	}

	public Player(int x,int y) {
	  super(x,y);
	  setImage();
	}
	
	public void update(Space space) {
	  
	  if (space.leftPressed) {
	    if(x>=0) {
		 x_vel -= accel; 
		}else if(x<=0){
		 x_vel += accel;  
		} 
	  }   
	  
	  if (space.rightPressed) {
	    if((x + img.getWidth(null))<=space.WIDTH) {
		 x_vel += accel; 
		}else if((x + img.getWidth(null))>=space.WIDTH){
		 x_vel -= accel;  
		} 
		
	  } 
	  if (space.upPressed) {
	    if(y>=0) {
		 y_vel -= accel; 
		}else if(y<=0){
		 y_vel += accel;  
		}
	  }  
	  
	  if (space.downPressed) {
	    if((y+img.getHeight(null))<=space.HEIGHT) {
		 y_vel += accel; 
		}else if(y+img.getHeight(null)>=space.HEIGHT){
		 y_vel += -accel;  
		}
	  }
	  
	  if (x<=0){
        x_vel += accel;}
	  else if(x+img.getWidth(null)>=space.WIDTH){	
	    x_vel -= accel;}
      if(y<=0){
	    y_vel += accel;}
	  else if(y+img.getHeight(null)>=space.HEIGHT){
	    y_vel += -accel;}	  
	  	
	  x += x_vel;	
	  y += y_vel;	
		
	  y_vel *= fric;
	  x_vel *= fric;
	  
	  if (Math.abs(y_vel) < 0.01){
	     y_vel = 0;
	  }	 
	  if (Math.abs(y_vel) < 0.01){
	     y_vel = 0;	 
	  }	   
	  bounds.setLocation(getX(),getY());

	  // Be better to do while checkig aliens	  
      for(Alien alien : space.aliens) {
	     if (bounds.intersects(alien.bounds)){
			space.aliens.remove(alien);
			space.score += 10;
			if(health > 25 && !shieldArmed){
			  health -= 25;
			} else if(health > 25 && shieldArmed) {
			  ;
			} else {
			  isDead = true;
			}
			
			if (space.gunState == 0 || shieldArmed) {
			  ;
			}else{
			  space.gunState--;
			}
		 }
	  }
	  
	  // Might be better to do when doing bonuses
	  for(Bonus bonus : space.bonuses) {
	    if (bounds.intersects(bonus.bounds)){
		   if (bonus.id == 0 && health < 100) {
		      health += 25;
			  space.bonuses.remove(bonus);
		   } else if(bonus.id == 1) {
		      if (space.gunState < 2){
			  space.gunState++;
			  }else if (space.gunState >= 2){
			   space.gunState = 2; 
			  }

              space.bonuses.remove(bonus);			  
		   } else if(bonus.id == 2) {
		         shieldArmed = true;
			   if (shieldTime >= 930)
			     shieldTime = 930;
			   else
			     shieldTime += 310;
			   space.bonuses.remove(bonus);
           	}
		}
	  }
      if(shieldTime > 0) {
	    shieldTime--;
	  }else {
	    shieldArmed = false;
	  }	  
	}
	
	
	
	public void draw(Graphics2D g) {
	 Graphics2D graphics = (Graphics2D)g.create();
	 
	 if (!isDead){
	 
	 Graphics2D healthGraphics = (Graphics2D)graphics.create();
	 graphics.rotate(x_vel/50,x + img.getWidth(null)/2,y + img.getHeight(null)/2);
	 
	 graphics.drawImage(img,getX(),getY(),null);
	 healthGraphics.setColor(Color.cyan);
	 healthGraphics.fill3DRect(30,30,120,70,true);
	 healthGraphics.setColor(Color.blue);
	 healthGraphics.draw3DRect(40,40,maxHealth,10,true);
	 healthGraphics.fill3DRect(40,40,health,10,true);
	 if(shieldArmed) {
	    float[] grads = {.0f,.7f,1f};
		Color[] colors = {new Color(0x00ffffff,true),new Color(0x300000ff,true),new Color(0xffffff)};
	    RadialGradientPaint paint = new RadialGradientPaint((float)getX()+getWidth()/2,(float)getY()+getHeight()/2,getWidth()/2,grads,colors);
		healthGraphics.setPaint(paint);
	    healthGraphics.fillOval(getX(),getY(),getWidth(),getHeight());
	 }
	 healthGraphics.setColor(Color.red);
	 healthGraphics.drawString("Score :"+Space.score+" Lvl :"+Space.level,40,70);
	 healthGraphics.drawString("High Score: "+Space.highScore,40,90);
	 graphics.dispose();
	 healthGraphics.dispose();
	 }  
	}

}