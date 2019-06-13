import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject
{

	protected Sprite[] sprites;
	protected int currentSprite = 0;
	protected int speed;
	protected int counter = 0;

	protected int startSprite = 0;
	protected int endSprite;
	protected boolean isSheet;

	public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) 
	{
		sprites = new Sprite[positions.length];
		this.speed = speed;
		this.endSprite = positions.length - 1;
		isSheet = true;

		for(int i = 0; i < positions.length; i++)
			sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].w, positions[i].h);
	}

	public AnimatedSprite(SpriteSheet sheet, int speed) 
	{
		sprites = sheet.getLoadedSprites();
		this.speed = speed;
		this.endSprite = sprites.length - 1;
		isSheet = true;
	}

	//@param speed represents how many frames pass until the sprite changes
	public AnimatedSprite(BufferedImage[] images, int speed, int startSprite, int endSprite)
	{
		sprites = new Sprite[images.length];
		this.speed = speed;
		this.startSprite = images.length - 1;
		this.startSprite = startSprite;
		this.endSprite = endSprite;

		for(int i = 0; i < images.length; i++){
			sprites[i] = new Sprite(images[i]);
		}
		currentSprite = startSprite;
		isSheet = false;
	}

	//Render is dealt specifically with the Layer class.
	public void render(RenderHandler renderer, int xZoom, int yZoom) {}

	public void render(RenderHandler renderer, int x, int y, int xZoom, int yZoom) {
		renderer.renderSprite(sprites[currentSprite],x,y,xZoom,yZoom,false);
	}
	
	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

	//Call at 60 fps rate.
	public void update(Main game)
	{
		counter++;
		if(counter >= speed) 
		{
			counter = 0;
			if(isSheet){
				incrementSprite();
			}else{
				changeSprite();
			}
		}
	}

	public void reset()
	{
		counter = 0;
		currentSprite = startSprite;
	}

	public void setAnimationRange(int startSprite, int endSprite)
	{
		this.startSprite = startSprite;
		this.endSprite = endSprite;
		reset();
	}

	public int getWidth()
	{
		return sprites[currentSprite].getWidth();
	}

	public int getHeight()
	{
		return sprites[currentSprite].getHeight();
	}

	public int[] getPixels()
	{
		return sprites[currentSprite].getPixels();
	}

	public void incrementSprite() 
	{
		currentSprite++;
		if(currentSprite >= endSprite)
			currentSprite = startSprite;
	}
	public void changeSprite(){
		currentSprite++;
		if(currentSprite > endSprite){
			currentSprite = startSprite;
		}
	}
	public void setStartSprite(int numStart){
		startSprite = numStart;
	}
	public void setEndSprite(int numEnd){
		endSprite = numEnd;
	}
	public void setSpriteRange(int numStart, int numEnd){
		startSprite = numStart;
		endSprite = numEnd;
	}
}