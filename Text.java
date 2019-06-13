import java.awt.image.BufferedImage;

public class Text{
	char[] textToChar;
	Sprite[] textSprite;
	int x;
	int y;
	int xZoom;
	int yZoom;
	int maxLength;
	
	// note that the maxLength must be a multiple of the sprite width to work properly
	public Text(Main game, String text, int x, int y, int xZoom, int yZoom, int maxLength){
		this.x = x;
		this.y = y;
		this.xZoom = xZoom;
		this.yZoom = yZoom;
		this.maxLength = maxLength;
		
		//Turn the user's string into a char array
		textToChar = text.toCharArray();
		
		//Load the sprites of the text
		Sprite A = new Sprite(game.loadImage("A.png"));
		Sprite B = new Sprite(game.loadImage("B.png"));
		
		// initialize the sprite array to be the same as the number of characters
		textSprite = new Sprite[textToChar.length];
		
		//Create a corrisponding sprite array that matches the char array
		for(int i = 0; i < textToChar.length;i++){
			switch(textToChar[i]){
				case 'A': textSprite[i] = A;
				break;
				case 'B': textSprite[i] = B;
				break;
				default: textSprite[i] = null;
			}
		}
		
	}
	public void render(RenderHandler renderer){
		
		int lineOffset = 0;
		int characterOffset = 0;
		int xRenderPosition = x;
		
		// for(int i = 0; i < textSprite.length;i++){
			// if(textSprite[i] != null){
				// if(xRenderPosition > x + maxLength + textSprite[i].getWidth()){
					// xRenderPosition = x;
					// characterOffset = 1;
					// lineOffset++;
				// }else{
					// xRenderPosition = (x+(characterOffset*textSprite[i].getWidth()*xZoom));
					// characterOffset++;
				// }
			
				// renderer.renderSprite(textSprite[i],xRenderPosition,y + textSprite[i].getHeight()*yZoom*lineOffset,xZoom,yZoom,false);
			// }else{
				// characterOffset++;
				// xRenderPosition = (x+(characterOffset*textSprite[0].getWidth()*xZoom));
			// }
		// }
		for(int i = 0; i < textSprite.length;i++){
			if(textSprite[i] != null){

				xRenderPosition = (x+(characterOffset*textSprite[i].getWidth()*xZoom));
				characterOffset++;
				renderer.renderSprite(textSprite[i],xRenderPosition,y + textSprite[i].getHeight()*yZoom*lineOffset,xZoom,yZoom,false);
			}else{
				if(xRenderPosition > x + maxLength + textSprite[0].getWidth()){
					xRenderPosition = x;
					characterOffset = 0;
					lineOffset++;
				}else{
					xRenderPosition = (x+(characterOffset*textSprite[0].getWidth()*xZoom));
					characterOffset++;
				}
			}
		}
	}
}