
	
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Pistolero;
import model.Vampire;
import view.Arene;
import view.Arene2;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Pistolero p = new Pistolero();
			List<Vampire> v = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				v.add(new Vampire());
			}
			Arene view = new Arene(p,v);
			Controller controller = new Controller(p, v, view);
			view.setController(controller);
			Scene scene = new Scene(view,Arene.SIZE,Arene.SIZE);
			view.init();
			scene.setOnKeyPressed(controller);
			primaryStage.setScene(scene);
			//primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
