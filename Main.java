import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.lang.Runnable;
import java.lang.Thread;

import javax.swing.JFrame;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.File;

public class Main extends JFrame implements Runnable
{

	public static int alpha = 0xFFFF00DD;

	private Canvas canvas = new Canvas();
	private RenderHandler renderer;

	private SpriteSheet sheet;
	private SpriteSheet playerSheet;

	private int selectedTileID = 2;

	private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);
	private BattleButton testButton;
	private Sprite testSprite;

	private GameObject[] objects;
	private Fighter animTest;
	private KeyBoardListener keyListener = new KeyBoardListener(this);
	private MouseEventListener mouseListener = new MouseEventListener(this);

	private Player player;
	
	protected Combat myCombat;
	protected int xZoom = 3;
	protected int yZoom = 3;

	public Main() 
	{
		//Make our program shutdown when we exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of our frame.
		//setBounds(0,0, 1000, 800);


		//Add our graphics component
		add(canvas);

		//Make our frame visible.
		setVisible(true);

		canvas.setBounds(0,0,1000,800);
		pack();
		
		//Put our frame in the center of the screen.
		setLocationRelativeTo(null);
		
		//Create our object for buffer strategy.
		canvas.createBufferStrategy(3);

		renderer = new RenderHandler(canvas.getWidth(), canvas.getHeight());

		//Load Assets
		// BufferedImage sheetImage = loadImage("Tiles1.png");
		// sheet = new SpriteSheet(sheetImage);
		// sheet.loadSprites(16, 16);

		// BufferedImage playerSheetImage = loadImage("Player.png");
		// playerSheet = new SpriteSheet(playerSheetImage);
		// playerSheet.loadSprites(20, 26);

		//Player Animated Sprites
		// AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 5);

		//Load Tiles
		// tiles = new Tiles(new File("Tiles.txt"),sheet);

		//Load Map
		// map = new Map(new Sprite(loadImage("Background.png")));

		//testImage = loadImage("GrassTile.png");
	//	testSprite = sheet.getSprite(4,1);

		// testRectangle.generateGraphics(2, 12234);

		//Load SDK GUI
		// GUIButton[] buttons = new GUIButton[tiles.size()];
		// Sprite[] tileSprites = tiles.getSprites();

		// for(int i = 0; i < buttons.length; i++)
		// {
			// Rectangle tileRectangle = new Rectangle(0, i*(16*xZoom + 2), 16*xZoom, 16*yZoom);

			// buttons[i] = new SDKButton(this, i, tileSprites[i], tileRectangle);
		// }

		// GUI gui = new GUI(buttons, 5, 5, true);

		// Load Objects
		// objects = new GameObject[1];
		// player = new Player(playerAnimations);
		// objects[0] = player;
		//objects[1] = gui;

		// Test
		// objects = new GameObject[2];
		// BufferedImage[] testPlayer = new BufferedImage[2];
		// testPlayer[0] = loadImage("PlayerIdle1.png");
		// testPlayer[1] = loadImage("PlayerIdle2.png");
		// BufferedImage testImage = loadImage("Attack.png");
		// testSprite = new Sprite(testImage);
		// testButton = new BattleButton(testSprite,100,600,3,3,false);
		// objects[0] = testButton;
		// animTest = new Fighter(testPlayer,60,100,100,3,3);
		// objects[1] = animTest;
		
		//Add Listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
		myCombat = new Combat(this);
		while(myCombat.combatContinues()){
			System.out.println("The fighting continues");
			break;
		}
	}

	
	public void update() 
	{
		if(myCombat != null){
			myCombat.update();
		}
		// for(int i = 0; i < objects.length; i++) 
			// objects[i].update(this);
	}


	protected BufferedImage loadImage(String path)
	{
		try 
		{
			BufferedImage loadedImage = ImageIO.read(Main.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

			return formattedImage;
		}
		catch(IOException exception) 
		{
			exception.printStackTrace();
			return null;
		}
	}

	public void handleCTRL(boolean[] keys) 
	{
		// if(keys[KeyEvent.VK_S]){
		//	map.saveMap();
		// }
	}

	public void leftClick(int x, int y)
	{

		Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
		boolean stoppedChecking = false;

		System.out.println("The mouse Clicked:" + x + "," + y);
		// if(mouseRectangle.intersects(testButton.rect)){
			// System.out.println("called");
		// }
		// if(mouseRectangle.intersects(animTest.clickBox)){
			// animTest.select(true);
		// }else{
			// animTest.select(false);
		// }
	}

	public void rightClick(int x, int y)
	{
		x = (int) Math.floor((x + renderer.getCamera().x)/(16.0 * xZoom));
		y = (int) Math.floor((y + renderer.getCamera().y)/(16.0 * yZoom));
		//map.removeTile(x, y);
	}


	public void render() 
	{
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);

			if(myCombat != null){
				myCombat.render();
			}
			//map.render(renderer, xZoom, yZoom);
			
			//renderer.renderRectangle(testRectangle,1,1,false);
			// for(int i = 0; i < objects.length; i++){
				// objects[i].render(renderer, xZoom, yZoom);
			// }
			renderer.render(graphics);

			graphics.dispose();
			bufferStrategy.show();
			renderer.clear();
	}

	public void changeTile(int tileID) 
	{
		selectedTileID = tileID;
	}

	public int getSelectedTile()
	{
		return selectedTileID;
	}

	public void run() 
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;

		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
		double changeInSeconds = 0;

		while(true) 
		{
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				update();
				changeInSeconds--;
			}

			render();
			lastTime = now;
		}

	}

	public static void main(String[] args) 
	{
		Main game = new Main();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

	public KeyBoardListener getKeyListener() 
	{
		return keyListener;
	}

	public MouseEventListener getMouseListener() 
	{
		return mouseListener;
	}

	public RenderHandler getRenderer()
	{
		return renderer;
	}
	public Map getMap(){
		return null;
	}
}