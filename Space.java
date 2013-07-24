import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Random;
import java.util.concurrent.*;

public class Space extends Canvas implements KeyListener,Runnable{

    final static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    final static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	final int TARGET_AMOUNT = 25;
    Player player;
	Boss boss;
	Thread game;
	Random gen = new Random();
	CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
	CopyOnWriteArrayList<Alien> aliens = new CopyOnWriteArrayList<Alien>();
	CopyOnWriteArrayList<Bonus> bonuses = new CopyOnWriteArrayList<Bonus>();
	CopyOnWriteArrayList<Explosion> explosions = new CopyOnWriteArrayList<Explosion>();
	boolean transition = false;
	boolean reset = false;
	boolean leftPressed = false;
	boolean rightPressed = false;
	boolean upPressed = false;
	boolean downPressed = false;
	boolean shootPressed = false;
	boolean canShoot = false;
	boolean isPaused = false;
	boolean gameRunning = false;
	boolean gameOver = false;
	boolean levelDone = false;
	boolean youWin = false;
	boolean youLose = false;
	boolean canCreateBonus = false;
	boolean explosionHappened = false;
	boolean bossReached = false;
	boolean bossReady = false;
	long shootTime = 300;
	long lastShotTime = 0;
	long alienTime = 1000;
	long lastAlienTime = 0;
	static int score = 0;
	static int highScore = 0;
	int remEnemies = TARGET_AMOUNT;
	int bonusX,bonusY;
	int explX,explY;
	int gunState = 0;
	int bonusTime = 5;
	static int level = 1;
	BufferStrategy buffer;
	Graphics2D g;
	Image background;
	int backgroundX,backgroundY;
	
	public Space() {
	
	  JFrame window = new JFrame(" Space Shooter");
	  window.add("Center",this);
	  window.setDefaultCloseOperation(3);
	  window.setSize(WIDTH,HEIGHT);
	  window.setIconImage(new ImageIcon("images/ship.png").getImage());
	  window.setVisible(true);
	  player = new Player(384,600,100,"images/ship.png");
	  boss = new Boss(100,20,400,"images/alien.gif");
	  setIgnoreRepaint(true);
	 
	  createBufferStrategy(3);
	  buffer = getBufferStrategy();
	  
	  background = new ImageIcon("images/background.jpg").getImage();
	  
	  startGame(); 	
	}
	
	public void init() {
	   addKeyListener(this);	
	   requestFocus();
	   backgroundX = 0;
	   backgroundY = -background.getHeight(null)/4;
	   alienTime = 1000;
	   //Shouldn't gun state be on Player anyway?
	   gunState = 0;
	   player.shieldTime = 0;
	   boss.difficulty = 1;
	   score = 0;
	   level = 1;
	   reset(1);
	}
	
	public void startGame() {
	   if (game == null || !gameRunning)
	   {
	       game = new Thread(this);
		   game.start();
	   }
	}
	
	public void stopGame() {
	  removeKeyListener(this);
	  gameRunning = false;
	}
	
	public void run() {
	   init();
	   gameRunning = true; 
       while(gameRunning) {
	      if(!gameOver) {
	        loop();
		  } else {
		    gameOver();
		  }
		  
		  try{
	        Thread.sleep(20);
	      }catch(Exception ex){}
	   }

       System.exit(0);	   
	}
	
	public void loop() {
	  g = (Graphics2D) buffer.getDrawGraphics();
	  // args = image, x, y, sizeX, sizeY, obverser
	  g.drawImage(background,backgroundX,backgroundY,WIDTH,HEIGHT*2,null);
	  if(!levelDone) {
	    if(!isPaused) {
	     update();   
	    }
	  
	    render();
	  } else {
	    levelDone();
	    /*
		try{
		 Thread.sleep(2000);
		}catch(Exception e){}
	  	*/
	  }
	  
	  g.dispose();
	  buffer.show();
	}
	
	public void keyPressed(KeyEvent evt) {
	 int key = evt.getKeyCode();

      if (key == KeyEvent.VK_RIGHT)
	    rightPressed = true;

      if (key == KeyEvent.VK_LEFT)
	    leftPressed = true;
	  
	  if (key == KeyEvent.VK_UP)
	    upPressed = true;
	  
	  if (key == KeyEvent.VK_DOWN)
	    downPressed = true;
	  
	  if (key == KeyEvent.VK_SPACE)
	    shootPressed = true;		
	  
	  if (key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE)
		isPaused = !isPaused;
	  
	  if(key == KeyEvent.VK_F && evt.isAltDown()) 
	    stopGame();

      if (key == KeyEvent.VK_ENTER) 
      	if(reset) transition = true;
	  
	}
	public void keyReleased(KeyEvent evt) {
	   int key = evt.getKeyCode();

	  // Move right
      if (key == KeyEvent.VK_RIGHT)
	    rightPressed = false;
	  
	  // Move Left
      if (key == KeyEvent.VK_LEFT)
	    leftPressed = false;
	  
	  // Move Up
	  if (key == KeyEvent.VK_UP)
	    upPressed = false;
	  
	  // Move Down
	  if (key == KeyEvent.VK_DOWN)
	    downPressed = false;
	  
	  // Shoot baby, shoot!
	  if (key == KeyEvent.VK_SPACE)
	    shootPressed = false;
	
	}
	public void keyTyped(KeyEvent evt) {}
	
	public void update() {
	  // Check for gameover
	  if (player.isDead){;
	    aliens.clear();
		bullets.clear();
		youLose = true;
		gameOver = true;
	  	return;
	  // Check for level promotion
	  } else if(boss.isDead) {
	    processExplosions();
	    aliens.clear();
		bullets.clear();
		youWin = true;
		levelDone = true;
	  	return;
	  }

	  // Proceed as normal. Update some jazz.
	  player.update(this);
	  for (Bullet bullet : bullets)
	    bullet.update(this);
	 
	  for(Alien alien : aliens)
	     alien.update(this);
	  
	  for(Bonus bonus : bonuses)
	    bonus.update(this);
	 
	  
	  // If aliens make em!
	  if (remEnemies > 0)
	    createAlien();
	  else
	    bossReached = true;
	  
	  // Boss reached but are they dead yet?
	  if (bossReached && aliens.size()==0)
	    bossReady = true;
	   
	  // Let's throw in some magic
	  createBonus();
	  processExplosions();

	  // Are you ready?
	  if (bossReady)
	    boss.update(this);
	  
	  // pretty close to natural language there
	  if (shootPressed)
	    tryToShoot();
	  
	  // Be pretty cool if we could generate background instead. Notice jumping
      backgroundY+=2;
	  if(backgroundY >= 25)
		backgroundY = -background.getHeight(null)/4;
		
	}
	
	public void render() {
	  player.draw(g);
	  
	  // Drawing drawing drawing
	  for(Bullet bullet : bullets)
	    bullet.draw(g); 
	  
	  for(Alien alien : aliens)
	     alien.draw(g);
	  
	  for(Explosion explosion : explosions)
	     explosion.drawChildren(this);
	  
	  for(Bonus bonus : bonuses)
	    bonus.draw(g);
	  
	  if (bossReady)
	    boss.draw(g);
	  
	}
	
	public void tryToShoot() {
	  if (System.currentTimeMillis() - lastShotTime < shootTime)
	    return;
	  
	  // Reset lastShotTime to now
	  lastShotTime = System.currentTimeMillis();

	  // Switches are cleaner
      switch(gunState){
      	case 0:
          bullets.add(new Bullet(player.getX()+player.getWidth()/2,player.getY(),10,0,"images/shot.gif")); 	   
	 	  break;
	  	case 1:
		  for(int i = 0; i < 3; i++)
		    bullets.add(new Bullet(player.getX()+player.getWidth()/2,player.getY(),10, (i * 45)-45,"images/shot.gif"));
	      break;
		case 2:
	      for(int i = 0; i < 5; i++)  
		    bullets.add(new Bullet(player.getX()+player.getWidth()/2,player.getY(),10, (i * 22.5)-45,"images/shot.gif"));
	 	  break;  
	  }
	  
	}
	
	public void createAlien() {
	  // min amount of time before alien spawn. Throw in some randomness here? I guess it works well as is
	  if (System.currentTimeMillis() - lastAlienTime < alienTime)
	     return;
	  
	  // Reset lastAlienTime to now
	  lastAlienTime = System.currentTimeMillis();
	  
	  // add to alien list
	  for(int i = 0;i<level;i++)
	    aliens.add(new Alien(gen.nextInt(WIDTH-70),0,Alien.NORMAL,"images/alien.gif"));
	    
	  
	}
	
	public void createBonus() {
	  // Everytime an alien dies a bonus is born
	  if (canCreateBonus) {
	    int bonusId = gen.nextInt(bonusTime);
		bonuses.add(new Bonus(bonusX,bonusY,bonusId,"images/ship.gif"));
		canCreateBonus = false;
	  }
	}
	
	public void processExplosions() {
	  // The less mustaches I have to read through, the happier I am. It's a preference thing though..
	  if(!explosionHappened)
	  	return;

	  if(boss.isDead){
		for(int i = boss.getX();i<boss.getWidth();i++)
		  explosions.add(new Explosion(5,i,(int)Math.random() * boss.getHeight() + boss.getY(),boss.getWidth()/2));
		explosionHappened = false;
	  	return;
	  }

	  explosions.add(new Explosion(5,explX,explY,30));
	  explosionHappened = false;
	  
	}
	
	public void levelDone() {

	    g.setFont(new Font("Serif",Font.BOLD,50));
	    g.setColor(Color.red);
	    g.drawString("Level Complete",(WIDTH/2) - 60,HEIGHT/2);
	    g.setFont(new Font("garamond",Font.PLAIN,40));
	    g.setColor(Color.blue);
	    g.drawString("Your Score: "+ score,400,450);

	    if(!reset){
	      reset = true;
	      buffer.show();
	    }
		
		if(!transition)
			return;

		reset = false;
		transition = false;
		
		/**increase difficulty
			need to change the method of 
			increasing difficulty.
			(game gets unbeatable after a few levels)
		*/
		reset(++level);
		alienTime -= 100;
		boss.difficulty++;
		if(level%2 == 0)
		  bonusTime += 3;
	}
	
	public void gameOver() {
	  g = (Graphics2D)buffer.getDrawGraphics();
	 //messages need to be properly aligned
	  g.setFont(new Font("Serif",Font.BOLD,60));
	  g.setColor(Color.red);
	  g.drawString("YOU ARE DEAD!!!",(WIDTH/2) - 60,HEIGHT/2);
	  g.setFont(new Font("garamond",Font.PLAIN,40));
	  g.setColor(Color.blue);
	  g.drawString("Your Score: "+ score,400,450);	
	  
	  if(!reset){
	    reset = true;
	    buffer.show();
	  }

	  if(!transition)
	    return;

	  reset = false;
	  transition = false;

	  g.dispose();

	  if(score > highScore)
	    highScore = score;

	  init();

	}

	private void reset(int level){
		remEnemies = TARGET_AMOUNT + (level*5);
	    player.isDead = false;
	    boss.isDead = false;
	    isPaused = false;
	    bossReached = false;
	    bossReady = false;
		explosionHappened = false;
	    youWin = false;
	    youLose = false;
	    gameOver = false;
	    levelDone = false;
	    player.setHealth(100);
	    boss.setHealth(400);
	    player.setX(383);
	    player.setY(512);
	}

	// TODO: Move to Menu Class. When we make one.
    public static void main(String args[]){
	  new Space();	  
	}
}