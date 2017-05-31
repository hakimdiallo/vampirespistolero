package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Pistolero;
import model.Vampire;

public class Arene extends Pane{
	public static final int SIZE = 600;
	private GridPane plateau;
	private GridPane menu;
	private ProgressBar viePisBar;
	private ProgressIndicator viePisIndic;
	private Controller controller;
	private Pistolero pistolero;
	private List<Vampire> vampires;
	private Media media;
	private MediaPlayer sound;
	private List<Bounds> coordinates;
	
	public Arene(Pistolero p, List<Vampire> vampires){
		pistolero = p;
		this.vampires = vampires;
		pistolero.setLayoutX(p.getPosX());
		pistolero.setLayoutY(p.getPosY());
		this.getChildren().add(pistolero);
		viePisBar = new ProgressBar(1);
		viePisBar.minWidth(SIZE);
		media = new Media(new File("src/view/shot_silencer.mp3").toURI().toString());
		sound = new MediaPlayer(media);
		coordinates = new ArrayList<>();
	}
	
	public void setController (Controller c){
		controller = c;
	}
	
	public void init(){
		coordinates.add(pistolero.getBoundsInLocal());
		for (int i = 0; i < this.vampires.size(); i++) {
			int posX, posY;
			Point2D pt;
			boolean goodPoint = false;
			Vampire v = this.vampires.get(i);
			do {
				goodPoint = false;
				posX = (int)(Math.random() * (SIZE - 100) ) +1;
				posY = (int)(Math.random() * (SIZE - 100) ) +1;
				pt = new Point2D(posX, posY);
				v.setPosition(posX, posY);
				v.setLayoutX(v.getPosX());
				v.setLayoutY(v.getPosY());
				this.getChildren().add(v);
				if (pistolero.getBoundsInParent().intersects(v.getBoundsInParent())) {
					goodPoint = true;
					this.getChildren().remove(v);
					System.out.println("pistol test");
					//break;
				}
				if (!goodPoint) {
					for (int j = 0; j < i; j++) {
						if (vampires.get(j).getBoundsInParent().intersects(v.getBoundsInParent())) {
							goodPoint = true;
							this.getChildren().remove(v);
							System.out.println("for test");
							break;
						}
						
					}
				}
				System.out.println("test");
			} while (goodPoint);
			System.out.println("after");
			//System.out.println(v.getBoundsInParent());
			coordinates.add(v.getBoundsInParent());
		}
		//this.getChildren().addAll(this.vampires);
	}
	
	public void shootDown(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX()+pistolero.getFitWidth()/2-6);
		ball.setLayoutY(pistolero.getPosY()+pistolero.getFitHeight()/2+2);
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutYProperty(), this.getScene().getHeight()*2)));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    timeline.play();
	    pistolero.tirer();
	    playShoot();
	}
	
	public void shootUp(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX()+pistolero.getFitWidth()/2+6);
		ball.setLayoutY(pistolero.getPosY()-16);
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutYProperty(), 0-this.getScene().getHeight())));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    timeline.play();
	    pistolero.tirer();
	    playShoot();
	}
	
	public void shootLeft(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX());
		ball.setLayoutY(pistolero.getPosY()+9);
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutXProperty(), 0-this.getScene().getWidth())));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    timeline.play();
	    pistolero.tirer();
	    playShoot();
	}
	
	public void shootRight(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX()+pistolero.getFitWidth()+1);
		ball.setLayoutY(pistolero.getPosY()+22);
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutXProperty(), this.getScene().getWidth()*2)));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    timeline.play();
	    pistolero.tirer();
	    playShoot();
	}
	
	public void playShoot(){
		sound.stop();
		sound.play();
	}
}
