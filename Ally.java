import java.awt.image.BufferedImage;

public class Ally extends Fighter{
	protected boolean selected;
	public Ally(BufferedImage[] images, int speed, int startSprite, int endSprite, int xPos, int yPos, int xZoom, int yZoom,int health, int attack, int defence, int level, int xp){
		super(images,speed,startSprite,endSprite,xPos,yPos,xZoom,yZoom);
		this.health = health;
		this.attack = attack;
		this.defence = defence;
		this.level = level;
		this.xp = xp;
		clickBox.generateGraphics(0x67E0DE);
	}
	@Override
	public void select(boolean isSelected){
		if(isSelected){
			clickBox.generateGraphics(0x3B7CFF);
			System.out.println("I am selected");
		}else{
			clickBox.generateGraphics(0x67E0DE);
			System.out.println("I am unselected");
		}
	}
}