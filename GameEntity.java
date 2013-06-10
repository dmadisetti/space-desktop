import java.awt.*;
import javax.swing.*;

public abstract class GameEntity{

   double x_pos;
   double y_pos;
   Image img;
   double x_vel;
   double y_vel;
   int speed;
   double accel;
   double fric;
   Rectangle bounds;
   int health;
   
   public GameEntity(int x,int y,int health,String image) {
      x_pos = x;
	  y_pos = y;
	  x_vel = 0;
	  y_vel = 0;
	  accel = 2;
	  speed = 0;
	  fric = 0.9;
	  this.health = health;
	  setImage(image);
	  bounds = new Rectangle(getX(),getY(),getWidth(),getHeight());
   }
   
   public abstract void update(Space stage);   
   public abstract void draw(Graphics2D g);
   
   public void setImage(String image) {
	  img = new ImageIcon(this.getClass().getResource(image)).getImage();
	  
   }
   
   public double getX_vel(){
      return x_vel; 
   }
   public double getY_vel(){
      return y_vel;
   }
   public double getX_pos(){
      return x_pos;
   }
   public int getX(){
      return (int)x_pos;
   }
   public double getY_pos(){
      return y_pos;
   }
   public int getY(){
      return (int)y_pos;
   }
   public Image getImage() {
	  return img;
   }   
   public int getWidth() {
      return img.getWidth(null);
   }
   public int getHeight() {
      return img.getHeight(null);
   }
   public int getHealth() {
      return health;
   }
   public void setX(int x) {
     x_pos = x;
   }
   public void setY(int y) {
     y_pos = y; 
   }
   public void setHealth(int health) {
     this.health = health;
   }

}