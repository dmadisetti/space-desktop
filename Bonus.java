import java.awt.*;
import javax.swing.*;

public class Bonus extends GameEntity {

    int id;
    Space space;
	double y_vel = 5;

	public Bonus(int x,int y,int id) {
	  super(x,y);
	  this.id = id;
	  setImage();
	}
	
	public void update(Space space) {
	  if (y >= space.HEIGHT){ 
	  	space.bonuses.remove(this);
	  	return;
	  }
	  	
	  y += y_vel;		
	  
	  if (img == null) space.bonuses.remove(this);
	  
	  bounds.setLocation(getX(),getY());
	}
	
	public String getImg() {
	  switch(id) {
	    case 0: 
	      image = "images/health.png";
		  break;
		case 1: 
		  image = "images/weapon_upgrade.png";
		  break;
		case 2: 
		  image = "images/shield.png";
          break;		
		default: image = "";
	  }
	  return image;
	}
     
	public void draw(Graphics2D g) {
	   g.drawImage(img,getX(),getY(),null);
	}  

}