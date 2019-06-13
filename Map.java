public class Map{
	private Sprite background;
	public Map(Sprite background){
		this.background = background;
	}
	
	public void render(RenderHandler renderer, int xZoom, int yZoom){
		Rectangle camera = renderer.getCamera();
		renderer.renderSprite(background,0,0, 1,1,true,camera.x, camera.y,camera.w, camera.h);
	
	}
	public int getWidth(){
		return background.getWidth();
	}
	public int getHeight(){
		return background.getHeight();
	}
}