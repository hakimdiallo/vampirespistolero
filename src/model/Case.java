package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Case extends Canvas {

	private int ligne;
	private int colonne;
	private Pistolero pistolero;
	private Vampire vampire;
	private Image pistoleroIcon;
	private Image vampireIcon;
	private Image wallIcon;
	private boolean wall;
	private static final int LENGTH = 60;
	private final String V_IMG_URL = "/home/soul/workspace/VampiresPistolero/src/view/vamp.png";
	private final String P_IMG_URL = "/home/soul/workspace/VampiresPistolero/src/view/pis.png";
	private final String M_IMG_URL = "/home/soul/workspace/VampiresPistolero/src/view/mur_icon.png";
	
	
	public Case(int ligne, int colonne){
		super(LENGTH,LENGTH);
		this.ligne = ligne;
		this.colonne = colonne;
		pistolero = null;
		vampire = null;
		wall = false;
		try {
			pistoleroIcon = new Image(new FileInputStream(P_IMG_URL));
			vampireIcon = new Image(new FileInputStream(V_IMG_URL));
			wallIcon = new Image(new FileInputStream(M_IMG_URL));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		drawInit();
		//drawVampire();
		//drawPistolero();
	}
	
	public Case(){
		
	}
	
	public void drawInit(){
		this.getGraphicsContext2D().setFill(Color.BLACK);
		this.getGraphicsContext2D().fillRect(super.getScaleX(), super.getScaleY(), LENGTH, LENGTH);
		this.getGraphicsContext2D().setFill(Color.YELLOW);
		this.getGraphicsContext2D().fillOval(super.getScaleX()+(LENGTH/2), super.getScaleY()+(LENGTH/2), 1, 1);
	}

	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}
	
	public boolean isVide(){
		return pistolero == null && vampire == null;
	}

	public boolean isOccupe() {
		return pistolero != null || vampire != null;
	}
	
	public boolean isWall() {
		return wall;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public void recoitVampire(Vampire v){
		vampire = v;
		drawVampire();
	}
	
	public void virerVampire(){
		vampire = null;
	}
	
	public void recoitPistolero(Pistolero p){
		pistolero = p;
		drawPistolero();
	}
	
	public void virerPistolero(){
		pistolero = null;
	}
	
	public Pistolero getPistolero(){
		return pistolero;
	}
	
	public Vampire getVampire(){
		return vampire;
	}
	
	public void drawPistolero(){
		this.getGraphicsContext2D().drawImage(pistoleroIcon, super.getScaleX()+6, super.getScaleY()+6);
	}
	
	public void drawVampire(){
		this.getGraphicsContext2D().drawImage(vampireIcon, super.getScaleX()+6, super.getScaleY()+6);
	}
	
	public void drawWall(){
		this.getGraphicsContext2D().drawImage(wallIcon, super.getScaleX(), super.getScaleY());
	}
	
	public void shoot(){
		Circle ball = new Circle(10,Color.RED);
		//this.getGraphicsContext2D().setFill(Color.RED);
		//this.getGraphicsContext2D().fillOval(super.getScaleX(), super.getScaleY(), 10, 10);
		Bounds bounds = this.getBoundsInLocal();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1),
	            new KeyValue(ball.layoutXProperty(), bounds.getMaxX()-ball.getRadius())));
	    timeline.setCycleCount(1);
	    timeline.play();
	}
	
}
