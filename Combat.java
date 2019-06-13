import java.awt.image.BufferedImage;
import javax.swing.JTextArea;

public class Combat{
	//Combatants
	protected Ally testAlly;
	protected Ally testAlly2;
	protected Ally testAlly3;
	protected Enemy testEnemy;
	protected Enemy testEnemy2;
	protected Enemy testEnemy3;
	protected Fighter[] allFighters;
	
	// utillities
	protected Sprite selectSprite;
	protected Main game;
	private boolean isButtonSelected = false;
	private String buttonSelectedName = "";
	private int selectedNum = 3;
	private int lastSelected = 3;
	private int whichFighterTurn = 0;
	private Text text;
	
	//Buttons
	private BattleButton attackButton;
	private BattleButton itemButton;
	
	//this constructor is for testing purposes only
	public Combat(Main game){
		System.out.println("Combat has Begun");
		this.game = game;
		
		//Load Fighter sprites
		BufferedImage[] playerAnimations = new BufferedImage[3];
		BufferedImage[] enemyAnimations = new BufferedImage[3];
		playerAnimations[0] = game.loadImage("PlayerIdle1.png");
		playerAnimations[1] = game.loadImage("PlayerIdle2.png");
		playerAnimations[2] = game.loadImage("PlayerDead.png");
		enemyAnimations[0] = game.loadImage("EnemyIdle1.png");
		enemyAnimations[1] = game.loadImage("EnemyIdle2.png");
		enemyAnimations[2] = game.loadImage("EnemyDead.png");
		
		//Load Fighters
		allFighters = new Fighter[6];
		testAlly = new Ally(playerAnimations,8,0,1,100,200,3,3,100,15,5,4,0);
		testAlly2 = new Ally(playerAnimations,12,0,1,200,200,3,3,100,15,5,4,0);
		testAlly3 = new Ally(playerAnimations,10,0,1,300,200,3,3,100,15,5,4,0);
		testEnemy = new Enemy(enemyAnimations,10,0,1,600,200,3,3,100,15,5,4,0);
		testEnemy2 = new Enemy(enemyAnimations,10,0,1,700,200,3,3,100,15,5,4,0);
		testEnemy3 = new Enemy(enemyAnimations,10,0,1,800,200,3,3,100,15,5,4,0);
		allFighters[0] = testAlly;
		allFighters[1] = testAlly2;
		allFighters[2] = testAlly3;
		allFighters[3] = testEnemy;
		allFighters[4] = testEnemy2;
		allFighters[5] = testEnemy3;
		
		//Load buttons
		Sprite attackSprite = new Sprite(game.loadImage("Attack.png"));
		attackButton = new BattleButton(attackSprite,100,400, 2, 2,false);
		
		//Load other images: select
		selectSprite = new Sprite(game.loadImage("Select.png"));
		
		// set the default selection to be the first enemy
		allFighters[lastSelected].select(true);
		
		// Load text
		text = new Text(game,"ABBAB ABABB AB ABABABABA ABBA BBA",400,330,3,3,60);
	}
	public Combat(Main game, int deleteThisLater/*bufferedImage backgroundImages, Ally[] ally, Enemy[] enemy */){
		
	}

	public boolean combatContinues(){
		return true;
	}
	// gets called from mouseEventListener if a Combat object exists in game
	public void leftClick(int x, int y){
		
		// create a rectangle where the user clicked, the x and y are from mouse event listener
		Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
		
		//Check if a button is selected, can only happen if a Fighter is selected
		if(attackButton.rect.intersects(mouseRectangle)){
			System.out.println("You clicked the attack button");
			isButtonSelected = true;
			buttonSelectedName = "attackButton";
		}
		
		// doesn't happen if the user clicks on a button
		if(!isButtonSelected){
			// keep track of what the user selected, -1 if didn't click on anything relevant
			int tempSelectedNumber = -1;
			
			// loop though all fighters
			for(int i = 0; i < allFighters.length;i++){
				
				// check if the fighter at that index exists
				if(allFighters[i] != null){
					
					// check if user clicked on the fighter in question
					if(mouseRectangle.intersects(allFighters[i].clickBox)){
						tempSelectedNumber = i;
					}
				}
			}
			// check if the user selected a fighter
			if(tempSelectedNumber != -1){
				
				// deselect any ally that was selected
				allFighters[selectedNum].select(false);
				
				// select the fighter
				allFighters[tempSelectedNumber].select(true);
				selectedNum = tempSelectedNumber;
			}

		}
	}
	// gets called from mouseEventListener if a Combat object exists in game
	public void rightClick(int x, int y){

		// create a rectangle where the user clicked, the x and y are from mouse event listener
		Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
		
	}
	public void update(){
		// update all the fighters sprite for animations
		for(int i = 0; i < allFighters.length;i++){
			if(allFighters[i] != null){
				allFighters[i].update(game);
			}
		}
		// handle turns
		while(isButtonSelected){
			isButtonSelected = false;
			if(buttonSelectedName.equals("attackButton")){
				System.out.println("Time to attack");
				dealDamage();
			}
			buttonSelectedName = "";
			
			//Move to the next Alive fighter's turn
			int turnJumper = 1;
			for(int playersLeftToCheck = 6; playersLeftToCheck >= 0; playersLeftToCheck--){
				if(allFighters[(whichFighterTurn + turnJumper) % allFighters.length] != null){
					if(allFighters[(whichFighterTurn + turnJumper) % allFighters.length].isAlive()){
						whichFighterTurn = (whichFighterTurn + turnJumper) % allFighters.length;
						break;
					}
				}
				turnJumper++;
			}
			
			//Check if Ally side has any players left
			boolean fightIsOver = true;
			if(allFighters[0] != null){
				if(allFighters[0].isAlive()){
					fightIsOver = false;
				}
			}if(allFighters[1] != null){
				if(allFighters[1].isAlive()){
					fightIsOver = false;
				}
			}if(allFighters[2] != null){
				if(allFighters[2].isAlive()){
					fightIsOver = false;
				}
			}
			if(fightIsOver){
				defeat();
			}
			
			//check if Enemy side has any players left
			fightIsOver = true;
			if(allFighters[3] != null){
				if(allFighters[3].isAlive()){
					fightIsOver = false;
				}
			}if(allFighters[4] != null){
				if(allFighters[4].isAlive()){
					fightIsOver = false;
				}
			}if(allFighters[5] != null){
				if(allFighters[5].isAlive()){
					fightIsOver = false;
				}
			}
			if(fightIsOver){
				victory();
			}
		}
		
	}
	public void render(){
		// render the fighters
		for(int i = 0; i < allFighters.length;i++){
			if(allFighters[i] != null){
				game.getRenderer().renderRectangle(allFighters[i].clickBox,1,1,false);
				game.getRenderer().renderSprite(selectSprite,allFighters[whichFighterTurn].clickBox.x,allFighters[whichFighterTurn].clickBox.y-30,3,3,false);
				game.getRenderer().renderSprite(allFighters[i].sprites[allFighters[i].currentSprite],allFighters[i].xPos,allFighters[i].yPos,allFighters[i].xZoom,allFighters[i].yZoom,false);
			}
		}
		
		// render the buttons
		attackButton.render(game.getRenderer(), 2,2);
		
		// render the text
		text.render(game.getRenderer());
	}
	public void dealDamage(){
		
		// deal the damage
		if(allFighters[whichFighterTurn].attack - allFighters[selectedNum].defence >= 1){
			allFighters[selectedNum].health -= (allFighters[whichFighterTurn].attack - allFighters[selectedNum].defence);
		}else{
			allFighters[selectedNum].health -= 1;
		}
		System.out.println(allFighters[selectedNum].health);
		//change the sprite if the fighter is dead
		if(allFighters[selectedNum].health <= 0){
			allFighters[selectedNum].setSpriteRange(2,2);
		}
	}
	public void victory(){
		System.out.println("You won the fight");
	}
	public void defeat(){
		System.out.println("You lost the fight");
	}
}