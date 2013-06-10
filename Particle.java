import java.awt.*;

public class Particle {

	double x;
	double y;
	int radius;
	double angle;
	double speed;
	double dx;
	double dy;
	Color innerColor;
	Color outerColor;
	Explosion parent;
	
	public Particle(Explosion parent,int radius,double speed) {
	    this.parent = parent;
		this.radius = radius;
		this.speed = speed;
		innerColor = new Color(1f,1f,0f,.5f);
		outerColor = new Color(1f,0f,0f,.2f);  
	}
	
	public void update() {
	    if(x<parent.bounds.x || x>parent.bounds.width || y<parent.bounds.y || y>parent.bounds.height) {
			parent.initParticle(this);
		}
		
		y += dy;
		x += dx;
	}
	
	public void draw(Graphics2D g) {
	    float[] grads = {.0f,.4f,1f};
		Color[] colors = {innerColor,innerColor,outerColor};
		RadialGradientPaint paint = new RadialGradientPaint((float)x+radius/2,(float)y+radius/2,(float)radius/2,grads,colors);
		g.setPaint(paint);
		g.fillOval((int)x,(int)y,(int)radius,(int)radius);
	}
	

}