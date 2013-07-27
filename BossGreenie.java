class BossGreenie extends Greenie{

	@Override
	public void set(){
		setHealth(30);
		this.scaleHeight = 1;
		this.scaleWidth = 1;
		this.health = 30;
		this.fric = 0.9;
	}

	public BossGreenie(int x,int y){
		super(x,y);
	}

}