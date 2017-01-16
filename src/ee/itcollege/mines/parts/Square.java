package ee.itcollege.mines.parts;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Square extends StackPane {

	public static final int SQUARE_SIZE = 50; 

	private static final Color DEFAULT_COLOR = new Color(.2, .2, .2, 1);
	
	private Rectangle box = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
	private Label label = new Label();
	
	private boolean
		hasBomb = false,
		flag = false,
		shown = false;
	
	private int bombCount = 0;
	private int x, y;
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
		
		setLayoutX(x * (SQUARE_SIZE));
		setLayoutY(y * (SQUARE_SIZE));
		getChildren().addAll(box, label);
		
		box.setStroke(new Color(.7, .7, .7, 1));
		box.setFill(Color.GRAY);
		setCursor(Cursor.HAND);
		label.setFont(new Font("Monospaced Bold", 30));
		label.setTextFill(DEFAULT_COLOR);
	}
	
	public void show() {
		shown = true;
		box.setFill(Color.LIGHTGRAY);
		if (hasBomb) {
			label.setText("*");
			label.setTextFill(Color.RED);
		}
		else {
			if (bombCount > 0) {
				label.setText(String.valueOf(bombCount));
			}
		}
	}
	
	public void toggleFlag() {
		if (!shown) {
			flag = !flag;
			label.setText(flag ? "B" : "");
			label.setTextFill(flag ? Color.ORANGE : DEFAULT_COLOR);
		}
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
	
	public boolean hasFlag() {
		return flag;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
