import java.awt.image.BufferedImage;

public class Enemy extends Fighter{
	protected boolean selected;
	public Enemy(BufferedImage[] images, int speed, int startSprite, int endSprite, int xPos, int yPos, int xZoom, int yZoom,int health, int attack, int defence, int level, int xp){
		super(images,speed,startSprite,endSprite,xPos,yPos,xZoom,yZoom);
		this.health = health;
		this.attack = attack;
		this.defence = defence;
		this.level = level;
		this.xp = xp;
		clickBox.generateGraphics(0xFFEB3B);
	}
	@Override
	public void select(boolean isSelected){
		if(isSelected){
			clickBox.generateGraphics(0xFF9800);
			System.out.println("I am selected");
		}else{
			clickBox.generateGraphics(0xFFEB3B);
			System.out.println("I am unselected");
		}
	}
}