package controller;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.Pistolero;
import model.Vampire;
import view.Arene;

public class Controller implements EventHandler<KeyEvent>{

	private Pistolero pistolero;
	private List<Vampire> vampires;
	private Arene arene;
	
	public Controller(Pistolero p, List<Vampire> v, Arene a){
		pistolero = p;
		vampires = v;
		arene = a;
	}

	@Override
	public void handle(KeyEvent event) {
		switch (event.getCode()) {
		case UP:
			//System.out.println("UP "+pistolero.getPosX()+" "+pistolero.getPosY()+" "+canMooveUp());
			pistolero.rotateToNorth();
			if (canMooveUp() ){//&& !arene.getCase(pistolero.getPosX()-1, pistolero.getPosY()).isWall()) {
				pistolero.moveUp();
			}
			break;
		case LEFT:
			//System.out.println("LEFT "+pistolero.getPosX()+" "+pistolero.getPosY()+" "+canMooveLeft());
			pistolero.rotateToWest();
			if (canMooveLeft() ){//&& !arene.getCase(pistolero.getPosX(), pistolero.getPosY()-1).isWall()) {
				pistolero.moveLeft();
			}
			break;
		case DOWN:
			//System.out.println("DOWN "+pistolero.getPosX()+" "+pistolero.getPosY()+" "+canMooveDown());
			pistolero.rotateToSouth();
			if (canMooveDown() ){//&& !arene.getCase(pistolero.getPosX()+1, pistolero.getPosY()).isWall()) {
				pistolero.moveDown();
			}
			break;
		case RIGHT:
			//System.out.println("RIGHT "+pistolero.getPosX()+" "+pistolero.getPosY()+" "+canMooveRight());
			pistolero.rotateToEst();
			if (canMooveRight() ){//&& !arene.getCase(pistolero.getPosX(), pistolero.getPosY()+1).isWall()) {
				pistolero.moveRight();
			}
			break;
		case SPACE:
			//System.out.println("SPACE "+pistolero.getPosX()+" "+pistolero.getPosY());
			if (!pistolero.armeVide()) {
				switch ((int)pistolero.getRotate()) {
				case 0:
					arene.shootRight();
					break;
				case 90:
					arene.shootDown();
					break;
				case 180:
					arene.shootLeft();
					break;
				case 270:
					arene.shootUp();
					break;
				default:
					break;
				}
			}else{
				pistolero.rechargerArme();
			}
			//System.out.println(arene.getChildren().contains(vampires));
			break;
		default:
			break;
		}	
	}
	
	
	public boolean canMooveUp(){
		return pistolero.getPosY() > 0;
	}
	
	public boolean canMooveLeft(){
		return pistolero.getPosX() > 0;
	}
	
	public boolean canMooveDown(){
		return pistolero.getPosY() < (arene.getScene().getHeight() - pistolero.getFitWidth()+12);
	}
	
	public boolean canMooveRight(){
		return pistolero.getPosX() < (arene.getScene().getWidth() - pistolero.getFitWidth());
	}
}
