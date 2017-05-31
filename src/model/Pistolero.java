package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.attribute.standard.MediaPrintableArea;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.Arene;

public class Pistolero extends ImageView{
	private final int BALLES = 5;
	private final int WIDTH = 70;
	private final int HEIGHT = 100;
	private int vie;
	private int arme;
	private int posX;
	private int posY;
	private String direction;
	private Media media;
	private MediaPlayer sound;

	public Pistolero(){
		arme = BALLES;
		posX = (int)((Math.random() * (Arene.SIZE - HEIGHT) ) + 1);
		posY = (int)((Math.random() * (Arene.SIZE - HEIGHT) ) + 1);
		Image img;
		try {
			img = new Image(new FileInputStream("/home/soul/workspace/VampiresPistolero/src/view/el_pistolero.png"));
			this.setImage(img);
			this.setFitWidth(WIDTH);
			this.setFitHeight(HEIGHT);
			this.setPreserveRatio(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		media = new Media(new File("src/view/reload.mp3").toURI().toString());
		sound = new MediaPlayer(media);
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
	
	public int getVide(){
		return vie;
	}
	
	public void affaiblir(){
		if (vie > 0) {
			vie--;
		}
	}
	
	public int getArme(){
		return arme;
	}
	
	public void rechargerArme(){
		sound.stop();
		sound.play();
		arme = BALLES;
	}
	
	public void tirer(){
		if (arme > 0) {
			arme--;
		}
	}
	
	public boolean armeVide(){
		return arme == 0;
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
}
