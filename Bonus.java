import java.awt.*;

public class Bonus extends GameEntity {

    int id;
    Space space;
	
	public Bonus(int x,int y,int id,String image) {
	  super(x,y,0,image);
	  y_vel = 5;
	  this.id = id;
	  verifyBonus(id);
	}
	
	public void update(Space space) {
	  if (y_pos < space.HEIGHT) {
	    y_pos += y_vel;		
	  } else {
	    space.bonuses.remove(this);
	  }
	  
	  if (img == null) {
	    space.bonuses.remove(this);
	  }
	  
	  bounds.setLocation(getX(),getY());
	}
	
	public void verifyBonus(int ID) {
	  switch(ID) {
	    case 0: setImage("images/health.png");
		        break;
		case 1: setImage("images/weapon_upgrade.png");
		        break;		
		case 2: setImage("images/shield.png");
                break;		
		default: setImage("");	
	  }
	}
     
	public void draw(Graphics2D g) {
	   g.drawImage(img,getX(),getY(),null);
	}  

}