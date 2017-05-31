package view;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Case;
import model.Pistolero;
import model.Vampire;

public class Arene2 extends Pane{
	public static final int SIZE = 10;
	private GridPane plateau;
	private GridPane menu;
	private ProgressBar viePisBar;
	private ProgressIndicator viePisIndic;
	private ProgressBar[] vieVampBar;
	private ProgressIndicator vieVampIndic;
	private Controller controller;
	private Pistolero pistolero;
	private Circle ball;
	
	public Arene2(Pistolero p, Vampire[] vampires){
		pistolero = p;
		plateau = new GridPane();
		plateau.setMinSize(500,500);
		plateau.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		menu = new GridPane();
		menu.minWidth(500);
		//this.setMinSize(500, 500);
		ball =  new Circle(10, Color.RED);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Case c = new Case(i, j);
				
				if(i==4 && j==3){
					
					p.setPosition(i,j);
					c.recoitPistolero(p);
					//this.getChildren().add(ball);
				}
				if (i == 2 && j!=3) {
					c.setWall(true);
					c.drawWall();
				}
				//c.setLayoutX(i);
				//c.setLayoutY(j);
				//this.getChildren().add(c);
				plateau.add(c, j, i);
			}
		}
		viePisBar = new ProgressBar(1);
		Label l = new Label("Vie Pistolero: ");
		viePisBar.setMinWidth(500);
		viePisBar.progressProperty().bind(new SimpleIntegerProperty(p.getArme()));
		menu.add(l, 0, 0);
		menu.add(viePisBar, 1, 0);
		//this.setTop(menu);
		//this.setCenter(plateau);
		this.getChildren().add(plateau);
	}
	
	public void init(Pistolero p, Vampire[] v){
		
	}
	
	public void setController (Controller c){
		controller = c;
	}
	
	public Case getCase(int i, int j){
		return (Case)plateau.getChildren().get(i*SIZE+j);
	}
	
	public GridPane getPlateau(){
		return plateau;
	}
	
	public void moveUp(){
		int newPosX = pistolero.getPosX() - 1;
		((Case)plateau.getChildren().get(pistolero.getPosX()*SIZE+pistolero.getPosY())).drawInit();
		pistolero.setPosition(newPosX, pistolero.getPosY());
		((Case)plateau.getChildren().get(newPosX*SIZE+pistolero.getPosY())).drawPistolero();
	}
	
	public void moveDown(){
		int newPosX = pistolero.getPosX() + 1;
		((Case)plateau.getChildren().get(pistolero.getPosX()*SIZE+pistolero.getPosY())).drawInit();
		pistolero.setPosition(newPosX, pistolero.getPosY());
		((Case)plateau.getChildren().get(newPosX*SIZE+pistolero.getPosY())).drawPistolero();
	}
	
	public void moveLeft(){
		int newPosY = pistolero.getPosY() - 1;
		((Case)plateau.getChildren().get(pistolero.getPosX()*SIZE+pistolero.getPosY())).drawInit();
		pistolero.setPosition(pistolero.getPosX(), newPosY);
		((Case)plateau.getChildren().get(pistolero.getPosX()*SIZE+newPosY)).drawPistolero();
	}
	
	public void moveRight(){
		int newPosY = pistolero.getPosY() + 1;
		((Case)plateau.getChildren().get(pistolero.getPosX()*SIZE+pistolero.getPosY())).drawInit();
		pistolero.setPosition(pistolero.getPosX(), newPosY);
		((Case)plateau.getChildren().get(pistolero.getPosX()*SIZE+newPosY)).drawPistolero();
	}
	
	public void shootUp(){
		//getCase(pistolero.getPosX(), pistolero.getPosY()).shoot();
		Bounds bounds = getCase(pistolero.getPosX(), pistolero.getPosY()).getBoundsInLocal() ;
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1),
	            new KeyValue(ball.layoutXProperty(), bounds.getMaxX()-ball.getRadius())));
	    timeline.setCycleCount(1);
	    timeline.play();
	}
}
