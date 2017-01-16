package ee.itcollege.mines;

import java.util.ArrayList;

import ee.itcollege.mines.parts.Square;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MineSweeper extends Application {
	
	private static int WIDTH = 10;
	private static int HEIGHT = 10;

	Square[][] field = new Square[WIDTH][HEIGHT];
	Pane layout = new Pane();
	boolean firstClick = true;

	@Override
	public void start(Stage window) throws Exception {
		Scene scene = new Scene(layout);
		
		createField();
		setMines();
		
		layout.setOnMouseClicked(click -> {
			int x = (int) (click.getSceneX() / Square.SQUARE_SIZE);
			int y = (int) (click.getSceneY() / Square.SQUARE_SIZE);
			
			Square clickedSquare = getSquare(x, y);
			
			if (MouseButton.PRIMARY.equals(click.getButton())) {
				
				if (firstClick) {
					while (clickedSquare.hasBomb() || clickedSquare.getBombCount() > 0) {
						setMines();
						clickedSquare = getSquare(x, y);
					}
				}
				
				if (clickedSquare.hasFlag()) {
					return;
				}

				if (clickedSquare.isShown()) {
					int flags = (int) getSquaresAround(clickedSquare).stream().filter(s -> s.hasFlag()).count();
					if (flags == clickedSquare.getBombCount()) {
						showSquare(clickedSquare);
						showWhileNoBomb(clickedSquare);
					}
				}
				else {
					showSquare(clickedSquare);
				}
				
				
				firstClick = false;
			}
			else {
				clickedSquare.toggleFlag();
			}
		});
		
		scene.setOnKeyPressed(event -> {
			if (KeyCode.ESCAPE.equals(event.getCode())) {
				System.exit(0);
			}
		});
		
		window.setScene(scene);
		window.setOnCloseRequest(e -> System.exit(0));
		window.show();
	}

	private void showSquare(Square square) {
		square.show();
		
		if (square.hasBomb()) {
			gameOver();
		}
		if (square.getBombCount() == 0) {
			showWhileNoBomb(square);
		}
	}
	
	private void gameOver() {
		layout.setOnMouseClicked(null); // no more clicks listened
		
		Stage window = new Stage();
		StackPane pane = new StackPane();
		Scene scene = new Scene(pane);
		Label label = new Label("GAME OVER!");
		pane.getChildren().add(label);
		
		pane.setMinSize(500, 500);
		window.setScene(scene);
		window.setOnCloseRequest(e -> System.exit(0));
		window.show();
	}

	private void showWhileNoBomb(Square square) {
		ArrayList<Square> squares = getSquaresAround(square);
		for (int i = 0; i < squares.size(); i++) {
			Square s = squares.get(i);
			getSquare(s.getX(), s.getY());
			if (!s.hasFlag()) {
				s.show();
			}
			if (s.getBombCount() == 0) {
				ArrayList<Square> aroundS = getSquaresAround(s);
				for (Square s2 : aroundS) {
					if (!squares.contains(s2)) {
						squares.add(s2);
					}
				}
			}
		}
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
	
	private void setMines() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				field[y][x].setHasBomb(false);
			}
		}
		
		int minesLeft = (int) (WIDTH * HEIGHT / 4);
		
		while (minesLeft > 0) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			Square square = field[y][x];
			if (!square.hasBomb()) {
				square.setHasBomb(true);
				minesLeft--;
			}
		}
	}
	
	private ArrayList<Square> getSquaresAround(Square square) {
		int x = square.getX();
		int y = square.getY();
		ArrayList<Square> squares = new ArrayList<Square>();
		
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				int currentX = x + dx;
				int currentY = y + dy;
				if (currentX == x && currentY == y) {
					continue;
				}
				
				if (currentX < 0 || currentY < 0
						|| currentX >= WIDTH
						|| currentY >= HEIGHT) {
					continue;
				}
				squares.add(field[currentY][currentX]);
			}
		}
		
		return squares;
	}
	
	
	/**
	 * Also counts and sets the number of bombs around that square 
	 */
	private Square getSquare(int x, int y) {
		Square square = field[y][x];
		
		//int bombCount = getSquaresAround(x, y).stream().filter(s -> s.hasBomb()).count();
		
		ArrayList<Square> squaresAround = getSquaresAround(square);
		int bombCount = 0;
		
		for (Square s : squaresAround) {
			if (s.hasBomb()) {
				bombCount++;
			}
		}
		square.setBombCount(bombCount);
		
		return square;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
