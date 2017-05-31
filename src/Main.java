
	
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import controller.Mouse;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Pistolero;
import model.Vampire;
import view.Arene;
import view.Arene2;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MenuBar menuBar= new MenuBar();
			final Menu file = new Menu("File");
			MenuItem quit = new MenuItem("Quit");
			MenuItem play = new MenuItem("Play");
			Pistolero p = new Pistolero();
			List<Vampire> v = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				v.add(new Vampire());
			}
			
			/* Quit handle event*/
			quit.setOnAction(e1 -> {
				primaryStage.close();
			});
			file.getItems().add(quit);
			Arene view = new Arene(p,v);
			play.setOnAction(e1 -> {
				view.init();
			});
			file.getItems().add(play);
			menuBar.getMenus().addAll(file);
			menuBar.setUseSystemMenuBar(true);
			view.getChildren().add(menuBar);
			Controller controller = new Controller(p, v, view);
			Mouse mouse = new Mouse(p,v,view);
			view.setController(controller);
			Scene scene = new Scene(view,Arene.SIZE,Arene.SIZE);
			//view.init();
			scene.setOnKeyPressed(controller);
			scene.setOnMouseClicked(mouse);
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
