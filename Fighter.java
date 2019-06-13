import java.awt.image.BufferedImage;

public class Fighter extends AnimatedSprite{
	
	protected Rectangle clickBox;
	protected int xPos;
	protected int yPos;
	protected int xZoom;
	protected int yZoom;
	protected int health;
	protected int attack;
	protected int defence;
	protected int level;
	protected int xp;
	
	public Fighter(BufferedImage[] images,int speed, int startSprite, int endSprite,int xPos, int yPos, int xZoom, int yZoom){
		super(images,speed,startSprite,endSprite);
		clickBox = new Rectangle(xPos,yPos,this.getWidth()*xZoom,this.getHeight()*yZoom);
		this.xPos = xPos;
		this.yPos = yPos;
		this.xZoom = xZoom;
		this.yZoom = yZoom;
		clickBox.generateGraphics(0xBBCCDD);
	}
	
	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}
	@Override
	// note the x and y zoom paramaters are not used here and are instantiated with the constructor
	public void render(RenderHandler renderer, int usless, int notUsed) {
		renderer.renderRectangle(this.clickBox,1,1,false);
		renderer.renderSprite(sprites[currentSprite],xPos,yPos,xZoom,yZoom,false);
	}
	public void select(boolean isSelected){
		if(isSelected){
			clickBox.generateGraphics(0x336622);
			System.out.println("I am selected");
		}else{
			clickBox.generateGraphics(0xBBCCDD);
			System.out.println("I am unselected");
		}
	}
	public void setHealth(int health){
		this.health = health;
	}
	public void subtractHealth(int subHealth){
		health -= subHealth;
	}
	public void addHealth(int addHealth){
		health += addHealth;
	}
	public void setAttack(int attack){
		this.attack = attack;
	}
	public void subtractAttack(int subAttack){
		attack -= subAttack;
	}
	public void addAttack(int addAttack){
		attack += addAttack;
	}
	public void setDefence(int defence){
		this.defence = defence;
	}
	public void addDefence(int addDefence){
		defence += addDefence;
	}
	public void subtractDefence(int subDefence){
		defence -= subDefence;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public void addLevel(int addLevel){
		level += addLevel;
	}
	public void setXP(int xp){
		this.xp = xp;
	}
	public void addXP(int addXP){
		xp += addXP;
	}
	public void resetXP(){
		xp = 0;	
	}
	public int getHealth(){
		return health;
	}
	public int getAttack(){
		return attack;
	}
	public int getDefence(){
		return defence;
	}
	public int getLevel(){
		return level;
	}
	public int getXP(){
		return xp;
	}
	public boolean isAlive(){
		if(health <= 0){
			return false;
		}
		return true;
	}
	
}