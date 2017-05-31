package controller;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Pistolero;
import model.Vampire;
import view.Arene;

public class Mouse implements EventHandler<MouseEvent>{
	private Pistolero pistolero;
	private List<Vampire> vampires;
	private Arene arene;
	private double x,y;
	
	public Mouse(Pistolero p, List<Vampire> v,Arene a){
		pistolero = p;
		vampires = v;
		arene = a;

	}


	@Override
	public void handle(MouseEvent event) {
		x = event.getX();
		y = event.getY();
		
		if(x>=pistolero.getLayoutX()){
			pistolero.rotateToEst();
			arene.shootRight();
		}
		else if(y>=pistolero.getLayoutY()){
			pistolero.rotateToSouth();
			arene.shootDown();
		}
		else if(y<pistolero.getLayoutY()){
			pistolero.rotateToNorth();
			arene.shootUp();
		}
		else if(x<pistolero.getLayoutX()){
			pistolero.rotateToWest();
			arene.shootLeft();
		}
	}


}
