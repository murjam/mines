package ee.itcollege.mines.parts;


import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends StackPane {

	public static final int SQUARE_SIZE = 30; 
	
	private Rectangle box = new Rectangle(SQUARE_SIZE - 1, SQUARE_SIZE - 1);
	private Label label = new Label();
	private boolean hasBomb = false;
	private int bombCount = 0;
	
	public Square(int x, int y) {
		setLayoutX(x * (SQUARE_SIZE));
		setLayoutY(y * (SQUARE_SIZE));
		getChildren().addAll(box, label);
		
		box.setFill(Color.GRAY);
		setCursor(Cursor.HAND);
	}
	
	public void show() {
		label.setText("*");
	}
	
	public boolean hasBomb() {
		return hasBomb;
	}
	
	public void setHasBomb(boolean hasBomb) {
		this.hasBomb = hasBomb;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}
	
}
