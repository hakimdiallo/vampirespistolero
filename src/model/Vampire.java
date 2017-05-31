package model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

//import javafx.animation.KeyFrame;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.Arene;

public class Vampire extends ImageView{
	private final int WIDTH = 70;
	private final int HEIGHT = 100;
	private int vie;
	private int posX;
	private int posY;
	private String direction;
	private boolean moving;

	public Vampire(){
		moving=false;
		vie = 3;
		posX = (int)((Math.random() * (Arene.SIZE - HEIGHT) ) + 1);
		posY = (int)((Math.random() * (Arene.SIZE - HEIGHT) ) + 1);
		Image img;
		try {
			img = new Image(new FileInputStream("/home/soul/workspace/VampiresPistolero/src/view/zombie_walk.gif"));
			this.setImage(img);
			this.setFitWidth(WIDTH);
			this.setFitHeight(HEIGHT);
			this.setPreserveRatio(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//moveRandom();
	}
	
	public void moveRandom2(){
		while(posX>=0){
			this.moveLeft();
			System.out.println("Move left\n");
		}
	}
	
	public void moveRandom(){
		moving=true;
		Random generator = new Random();
		int r = generator.nextInt(3);
		//int r=2;
		TranslateTransition transition = new TranslateTransition();
		transition.setNode(this);
		//while(true){
			if(r==0){
				rotateToEst();
				transition.setToX(1000);
				//posX +=10;
			}
			else if(r==1){
				rotateToNorth();
				transition.setToY(-1000);
				//pos
			}
			else if(r==2){
				rotateToWest();
				transition.setToX(-1000);
				//posX =posX-10;
			}
			else {
				rotateToSouth();
				transition.setToY(1000);
			}
			transition.setDuration(Duration.seconds(5));
			transition.setCycleCount(100);
			transition.setOnFinished( e -> {
				moving= false;
			});
			transition.play();
		//}

	}
	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public void setVie(int v){
		vie = v;
	}
	
	public int getVie(){
		return vie;
	}
	
	public boolean isDead(){
		return (vie==0);
	}
	
	public void affaiblir(){
		if (vie > 0) {
			vie--;
		}
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public void rotateToNorth(){
		this.setRotate(270);
	}
	
	public void rotateToEst(){
		this.setRotate(0);
	}
	
	public void rotateToSouth(){
		this.setRotate(90);
	}
	
	public void rotateToWest(){
		this.setRotate(180);
	}
	
	public void moveUp(){
		setPosition(getPosX(), getPosY()-10);
		this.relocate(getPosX(), getPosY());
	}
	
	public void moveLeft(){
		setPosition(getPosX()-10, getPosY());
		this.relocate(getPosX(), getPosY());
	}
	
	public void moveDown(){
		setPosition(getPosX(), getPosY()+10);
		this.relocate(getPosX(), getPosY());
	}
	
	public void moveRight(){
		setPosition(getPosX()+10, getPosY());
		this.relocate(getPosX(), getPosY());
	}
	
	public boolean canMooveUp(){
		return posY > 0;
	}
	
	public boolean canMooveLeft(){
		return posX > 0;
	}
	
	public boolean canMooveDown(){
		return posY>Arene.SIZE;
	}
	
	public boolean canMooveRight(){
		return posX>Arene.SIZE;
	}
	
	public boolean isMoving(){
		return moving;
	}
}
