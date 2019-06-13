public class BattleButton extends GUIButton
{
	protected int xPos;
	protected int yPos;

	public BattleButton(Sprite sprite,int x, int y, int spriteXZoom, int spriteYZoom, boolean fixed)
	{
		super(sprite,new Rectangle(x,y,sprite.getWidth()*spriteXZoom,sprite.getHeight()*spriteYZoom),fixed);
		xPos = x;
		yPos = y;
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderSprite(sprite,xPos,yPos,xZoom,yZoom,fixed);
	}

	//Call at 60 fps rate.
	public void update(Main game) {}

	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom)
	{
		System.out.println("hi");
		if(mouseRectangle.intersects(rect)) {
			activate();
			return true;
		}

		return false;
	}

	public void activate(){
	}
	public Sprite getSprite(){
		return sprite;
	}

}