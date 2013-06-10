import java.awt.*;

public class Explosion{
  double x;
  double y;
  int numParticles;
  int limit;
  int counter = 20;
  Particle[] children;
  Rectangle bounds;
  
  public Explosion(int numParticles,double x,double y,int limit) {
    this.x = x;
	this.y = y;
	this.limit = limit;
	children = new Particle[numParticles];
	for(int i = 0;i < children.length;i++) {
	  children[i] = new Particle(this,50,5);
	  initParticle(children[i]);
	}
	
	bounds = new Rectangle((int)x-limit,(int)y-limit,(int)x+limit,(int)y+limit);
  }
  
  public void initParticle(Particle particle) {
     particle.x = this.x;
     particle.y = this.y;	
	 particle.speed = (Math.random() * 2) + 2;
     particle.angle = Math.toRadians(Math.random() * 360); 
	 particle.dx = particle.speed * Math.sin(particle.angle);
	 particle.dy = particle.speed * Math.cos(particle.angle);
  }
  
  public void drawChildren(Space space) {
     Graphics2D explosionGraphics = (Graphics2D)space.g.create();
	 for(Particle p : children) {
	   p.update();
	   p.draw(explosionGraphics);
	 }
	 explosionGraphics.dispose();
	 counter--;
	 if(counter <= 0) {
	   space.explosions.remove(this);
	 }
  } 
  
  
}