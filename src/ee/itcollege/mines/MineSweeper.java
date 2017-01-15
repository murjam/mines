package ee.itcollege.mines;

import ee.itcollege.mines.parts.Square;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MineSweeper extends Application {
	
	private static int WIDTH = 10;
	private static int HEIGHT = 10;

	Square[][] field = new Square[WIDTH][HEIGHT];
	Pane layout = new Pane();

	@Override
	public void start(Stage window) throws Exception {
		Scene scene = new Scene(layout);
		
		createField();
		
		
		layout.setOnMouseClicked(click -> {
			int x = (int) (click.getSceneX() / Square.SQUARE_SIZE);
			int y = (int) (click.getSceneY() / Square.SQUARE_SIZE);
			
			Square clickedSquare = field[y][x];
			
			if (MouseButton.PRIMARY.equals(click.getButton())) {
				clickedSquare.show();
			}
		});
		
		scene.setOnKeyTyped(event -> {
			System.out.format("Key %s was hit\n", event.getCharacter());
		});
		
		window.setScene(scene);
		window.setOnCloseRequest(e -> System.exit(0));
		window.show();
	}

	private void createField() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Square square = new Square(x, y);
				field[y][x] = square;
				layout.getChildren().add(square); 
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
