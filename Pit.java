import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

//Controller: Back side -> see PitPanel (Controller: Front side)
public class Pit extends JComponent implements ChangeListener
{
	public static final int DEFAULT_STONES_NUM = 3;

	private int id;
	private Board board;

	private static final int DEFAULT_WIDTH = 50;
	private static final int DEFAULT_HEIGHT = 50;
	
	private final static int DEFAULT_STONE_X = 0;
	private final static int DEFAULT_STONE_Y = 0;
	private final static int DEFAULT_STONE_SIZE = 10;

	public Pit(int id, Board board)
	{	
		this.board = board;
		this.id = id;
	}

	public void stateChanged(ChangeEvent e)
	{
		board = (Board) e.getSource();
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		int x_centered = getWidth()/2 - Pit.DEFAULT_WIDTH/2;
		int y_centered = getHeight()/2 - Pit.DEFAULT_HEIGHT/2;

		Rectangle body = new Rectangle(
			x_centered,
			y_centered,
			Pit.DEFAULT_WIDTH, 
			Pit.DEFAULT_HEIGHT
		);

		setPreferredSize(new Dimension(
			Pit.DEFAULT_WIDTH, 
			Pit.DEFAULT_HEIGHT
		));

		g2.draw(body);

		// Create the parts of this car and draw them here.
		int Xcircle = Pit.DEFAULT_STONE_X;
		int Ycircle = Pit.DEFAULT_STONE_Y;
		for(int i = 0; i < board.getNumOfStones(id); i++)
		{
			//create Pit inside the Rectangle, MAX Pit will be 36.
			Ellipse2D.Double circle = new Ellipse2D.Double ( 
				x_centered + Xcircle, 
				y_centered + Ycircle, 
				Pit.DEFAULT_STONE_SIZE, 
				Pit.DEFAULT_STONE_SIZE
			); 

			g2.fill(circle);

			Xcircle += Pit.DEFAULT_STONE_SIZE;

			if(Xcircle == Pit.DEFAULT_WIDTH)
			{
				Xcircle = x_centered;
				Ycircle += Pit.DEFAULT_STONE_SIZE;
			}
		}

		g2.drawString(String.valueOf(id), 10, 30);
	}

	public boolean contains(int x, int y)
	{
		int row = getRow();
		int col = getCol();
		int outerWidth = getWidth();
		int outerHeight = getHeight();

		int x0 = (col*outerWidth) + (outerWidth/2) - (Pit.DEFAULT_WIDTH/2);

		int y0 = ((PitPanel.DEFAULT_ROWS_NUMBER - row - 1)*outerHeight) + 
				 (outerHeight/2) - (Pit.DEFAULT_HEIGHT/2);

		return x >= x0 &&
			   x <= x0 + Pit.DEFAULT_WIDTH &&
			   y >= y0 &&
			   y <= y0 + Pit.DEFAULT_HEIGHT;
	}

	private int getRow()
	{
		return id / PitPanel.DEFAULT_COLS_NUMBER;
	}

	private int getCol()
	{
		if(id >= PitPanel.FIRST_UPPER_PIT && id <= PitPanel.LAST_UPPER_PIT)
		{
			return PitPanel.LAST_LOWER_PIT - (id % PitPanel.DEFAULT_COLS_NUMBER);
		}
		else if(id <= PitPanel.LAST_LOWER_PIT)
		{
			return id;
		}
		else
		{
			// throw error later
			return -1;
		}
	}
}
