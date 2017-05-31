package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
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
	private static Circle currentBall;
	private static boolean alreadyDie;
	
	public Arene(Pistolero p, List<Vampire> vampires) {
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
		alreadyDie = false;
	}
	
	public void setController (Controller c){
		controller = c;
	}
	
	public void init(){
		coordinates.add(pistolero.getBoundsInLocal());
		for (int i = 0; i < this.vampires.size(); i++) {
			int posX, posY;
			boolean goodPoint = false;
			Vampire v = this.vampires.get(i);
			do {
				goodPoint = false;
				posX = (int)(Math.random() * (SIZE - 100) ) +1;
				posY = (int)(Math.random() * (SIZE - 100) ) +1;

				v.setPosition(posX, posY);
				v.setLayoutX(v.getPosX());
				v.setLayoutY(v.getPosY());
				
				/* Temps */
				this.getChildren().add(v);
				if (pistolero.getBoundsInParent().intersects(v.getBoundsInParent())) {
					goodPoint = true;
					this.getChildren().remove(v);
					//break;
				}
				if (!goodPoint) {
					for (int j = 0; j < i; j++) {
						if (vampires.get(j).getBoundsInParent().intersects(v.getBoundsInParent())) {
							goodPoint = true;
							this.getChildren().remove(v);
							break;
						}
					}
				}
			} while (goodPoint);
			
			coordinates.add(v.getBoundsInParent());
		}
		for (int i = 0; i < this.vampires.size(); i++) {
			Vampire v = this.vampires.get(i);
			Timeline timeline = new Timeline();
		    timeline.setCycleCount(1);
		    timeline.getKeyFrames().add(restart(v));
	    	intersect();
	    	for(int j=0;j<vampires.size() && j!=i;j++){
		    	Vampire vbefore = vampires.get(j);
		    	if(v.getBoundsInParent().intersects(vbefore.getBoundsInParent())){
		    		System.out.println("Intersect v-v");
		    		//getChildren().remove(vbefore);
		    	}
		    }
	    	if(v.getBoundsInParent().intersects(pistolero.getBoundsInParent())){
	    		//getChildren().remove(pistolero);
	    		System.out.println("Intersec v-p");
	    	}
		    timeline.play();
		    timeline.setOnFinished(e -> {
		    	timeline.getKeyFrames().add(restart(v));
		    	timeline.play();
		    	//timeline.play();
		    });
		}
		//this.getChildren().addAll(this.vampires);
	}
	
	public KeyFrame restart(Vampire v){
		KeyValue kv;
		KeyFrame kf;
		Random g1= new Random();
		int r = g1.nextInt(3);
		if(r==0){
			v.rotateToEst();
		    kv = new KeyValue(v.layoutXProperty(), 500);
		}
		else if(r==1){
			v.rotateToNorth();
		    kv = new KeyValue(v.layoutYProperty(), 10);
		}
		else if(r==2){
			v.rotateToWest();
		    kv = new KeyValue(v.layoutXProperty(), 10);
		}
		else {
			v.rotateToSouth();
		    kv = new KeyValue(v.layoutYProperty(), 500);
		}
		kf = new KeyFrame(Duration.seconds(10), kv);
		return kf;
	}

	public void intersect(){
		for (int i = 0; i < this.vampires.size(); i++) {
			Vampire v = vampires.get(i);
			if(v.getBoundsInParent().intersects(pistolero.getBoundsInParent())){
				System.out.println("Intersec vampire pistolero");
			}
			if(pistolero.getLayoutX() == v.getLayoutX() && pistolero.getLayoutY()==v.getLayoutY()){
				System.out.println("Same v-p");
			}
		}
	}
	
	public void shootDown(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX()+pistolero.getFitWidth()/2-6);
		ball.setLayoutY(pistolero.getPosY()+pistolero.getFitHeight()/2+2);
		currentBall = ball;
		bindShoot();
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutYProperty(), this.getScene().getHeight()*2)));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    
	    for (int i = 0; i < this.vampires.size(); i++) {
	    	Vampire v = vampires.get(i);
	    	if(v.getBoundsInParent().intersects(ball.getBoundsInParent())){
	    		//this.getChildren().remove(v);
	    		System.out.println("Good shoot down!");
	    	}
	    }
	    timeline.play();
	    pistolero.tirer();
	    playShoot();
	}
	
	public void shootUp(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX()+pistolero.getFitWidth()/2+6);
		ball.setLayoutY(pistolero.getPosY()-16);
		currentBall = ball;
		bindShoot();
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutYProperty(), 0-this.getScene().getHeight())));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    for (int i = 0; i < this.vampires.size(); i++) {
	    	Vampire v = vampires.get(i);
	    	if(v.getBoundsInParent().intersects(ball.getBoundsInParent())){
	    		//this.getChildren().remove(v);
	    		System.out.println("Good shoot up!");
	    	}
	    }
	    timeline.play();
	    /* Check shoot */
	    pistolero.tirer();
	    playShoot();
	}
	
	public void shootLeft(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX());
		ball.setLayoutY(pistolero.getPosY()+9);
		currentBall = ball;
		bindShoot();
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutXProperty(), 0-this.getScene().getWidth())));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    for (int i = 0; i < this.vampires.size(); i++) {
	    	Vampire v = vampires.get(i);
	    	if(v.getBoundsInParent().intersects(ball.getBoundsInParent())){
	    		//this.getChildren().remove(v);
	    		System.out.println("Good shoot left!");
	    	}
	    }
	    timeline.play();
	    
	    pistolero.tirer();
	    playShoot();
	}
	
	public void shootRight(){
		Circle ball = new Circle(4,Color.RED);
		ball.setLayoutX(pistolero.getPosX()+pistolero.getFitWidth()+1);
		ball.setLayoutY(pistolero.getPosY()+22);
		currentBall = ball;
		bindShoot();
		this.getChildren().add(ball);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5 ),
	            new KeyValue(ball.layoutXProperty(), this.getScene().getWidth()*2)));
	    timeline.setCycleCount(1);
	    timeline.setAutoReverse(true);
	    timeline.setOnFinished(removeBall -> 
	    		getChildren().remove(ball)
	    	);
	    for (int i = 0; i < this.vampires.size(); i++) {
	    	Vampire v = vampires.get(i);
	    	if(v.getBoundsInParent().intersects(ball.getBoundsInParent())){
	    		//this.getChildren().remove(v);
	    		System.out.println("Good shoot right!");
	    	}
	    }
	    timeline.play();
	    pistolero.tirer();
	    playShoot();
	}
	
	public void playShoot(){
		sound.stop();
		sound.play();
	}
	
	public void bindShoot(){
		for (int i = 0; i < this.vampires.size(); i++) {
			Vampire v = this.vampires.get(i);
			v.boundsInParentProperty().addListener((ob,ol,ne) -> {
				if (v.getBoundsInParent().intersects(currentBall.getBoundsInParent())) {
					this.getChildren().remove(currentBall);
					this.getChildren().remove(v);
				}
			});
		}
	}
}
