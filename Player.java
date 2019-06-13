public class Player implements GameObject
{
	private Rectangle playerRectangle;

	//0 = Right, 1 = Left, 2 = Up, 3 = Down
	private int direction = 0;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	
	//Platformer movement
	private int speed = 10;
	private int jumpSpeed = 40;
	private Vector2 velocity = new Vector2();
	private float gravity = 1.5f;

	public Player(Sprite sprite)
	{
		this.sprite = sprite;

		if(sprite != null && sprite instanceof AnimatedSprite)
			animatedSprite = (AnimatedSprite) sprite;

		updateDirection();
		playerRectangle = new Rectangle(0, 720, 20, 26);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
	}

	private void updateDirection()
	{
		if(animatedSprite != null)
		{
			animatedSprite.setAnimationRange(direction * 8, (direction * 8) + 7);
		}
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom)
	{
		if(animatedSprite != null)
			renderer.renderSprite(animatedSprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		else if(sprite != null)
			renderer.renderSprite(sprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		else
			renderer.renderRectangle(playerRectangle, xZoom, yZoom, false);
	}

	//Call at 60 fps rate.
	public void update(Main game)
	{
		KeyBoardListener keyListener = game.getKeyListener();

		boolean didMove = false;
		int newDirection = direction;

		int yMin = game.getMap().getHeight() - playerRectangle.h - 54;
		
		if(playerRectangle.y < yMin){
			velocity.y += gravity;
		}else{
			velocity.y = 0;
			playerRectangle.y = yMin;
			velocity.x = 0;
		}
		
		boolean onGround = playerRectangle.y == yMin;
		
		if(keyListener.left())
		{
			newDirection = 1;
			didMove = true;
			velocity.x = -speed;
		}
		if(keyListener.right())
		{
			newDirection = 0;
			didMove = true;
			velocity.x = speed;
		}
		if(keyListener.up()) 
		{
			didMove = true;
			if(onGround){
				velocity.y -= jumpSpeed;
			}
			//newDirection = 2;	
		}
		if(keyListener.down()) 
		{
			//newDirection = 3;
			didMove = true;
		//	playerRectangle.y += speed;
		}

		if(newDirection != direction) 
		{
			direction = newDirection;
			updateDirection();
		}

		if(!didMove) {
			animatedSprite.reset();
		}
		Map map = game.getMap();
		updateCamera(map,game.getRenderer().getCamera());

		if(didMove)
			animatedSprite.update(game);
		
		int newX = (int)(playerRectangle.x + velocity.x);
		int newY = (int)(playerRectangle.y + velocity.y);
		
		if(newX < 0){
			newX = 0;
		}
		
		if(newX > map.getWidth() - playerRectangle.w * game.xZoom){
			newX = map.getWidth()- playerRectangle.w * game.xZoom;
		}
		
		if(newY < 0){
			newY = 0;
		}
		if(newY > map.getHeight() - playerRectangle.h*game.yZoom){
			newY = map.getHeight() - playerRectangle.h * game.yZoom;
		}
		
		playerRectangle.x = newX;
		playerRectangle.y = newY;
	}

	public void updateCamera(Map map, Rectangle camera) {
		int x = playerRectangle.x - (camera.w/2);
		int y = playerRectangle.y - (camera.h /2);
		if(x < 0){
			x = 0;
		}
		if(x + camera.w > map.getWidth()){
			x = map.getWidth() - camera.w;
		}
		if(y < 0){
			y = 0;
		}
		if(y + camera.h > map.getHeight()){
			y = map.getHeight() - camera.h;
		}
		camera.y = y;
		camera.x = x;
		
		//camera.x = playerRectangle.x - (camera.w / 2);
		//camera.y = playerRectangle.y - (camera.h / 2);
	}

	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
}