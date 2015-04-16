import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class MancalaPanel extends JPanel implements ChangeListener
{
	private int mancalaAStones;
	private int mancalaBStones;

	private final static int DEFAULT_MANCALA_WIDTH = 60;
	private final static int DEFAULT_MANCALA_HEIGHT = 100;

	private final static int DEFAULT_PANEL_WIDTH = 120;
	private final static int DEFAULT_PANEL_HEIGHT = 180;

	private final static int STARTING_STONES_NUMBER = 0;
	private final static int DEFAULT_STONE_X = 0;
	private final static int DEFAULT_STONE_Y = 0;
	private final static int DEFAULT_STONE_SIZE = 10;

	public MancalaPanel()
	{
		this(MancalaPanel.DEFAULT_PANEL_WIDTH, MancalaPanel.DEFAULT_PANEL_HEIGHT);
	}

	public MancalaPanel(int width, int height) 
	{
		mancalaAStones = MancalaPanel.STARTING_STONES_NUMBER;
		mancalaBStones = MancalaPanel.STARTING_STONES_NUMBER;
		
		setPreferredSize(new Dimension(width, height));
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		draw(g2, Board.MANCALA_A);
		draw(g2, Board.MANCALA_B);
	}

	public void stateChanged(ChangeEvent e)
	{

	}

	public void addStone(int whichMancala)
	{
		if(whichMancala == Board.MANCALA_A)
		{
			mancalaAStones++;
		}
		else if(whichMancala == Board.MANCALA_B)
		{
			mancalaBStones++;
		}
		else
		{
			// will throw error later
			return;
		}
	}

	private void draw(Graphics2D g2, int whichMancala)
	{
		int numStones = 0;

		if(whichMancala == MancalaPanel.MANCALA_A)
		{
			numStones = mancalaAStones;
		}
		else if(whichMancala == MancalaPanel.MANCALA_B)
		{
			numStones = mancalaBStones;
		}
		else 
		{
			// will throw error later
			return;
		}

		int x_centered = getWidth()/2 - MancalaPanel.DEFAULT_MANCALA_WIDTH/2;
		int y_centered = getHeight()/2 - MancalaPanel.DEFAULT_MANCALA_HEIGHT/2;

		Rectangle body = new Rectangle(
			x_centered, 
			y_centered, 
			MancalaPanel.DEFAULT_MANCALA_WIDTH, 
			MancalaPanel.DEFAULT_MANCALA_HEIGHT
		);

		g2.draw(body);

		int Xcircle = MancalaPanel.DEFAULT_STONE_X;
		int Ycircle = MancalaPanel.DEFAULT_STONE_Y;
		for(int i = 0; i < numStones; i++)
		{
			//create Pit inside the Rectangle, MAX Pit will be 36.
			Ellipse2D.Double circle = new Ellipse2D.Double (
				x_centered + Xcircle, 
				y_centered+ Ycircle, 
				MancalaPanel.DEFAULT_STONE_SIZE, 
				MancalaPanel.DEFAULT_STONE_SIZE
			); 

			g2.fill(circle);

			Xcircle += MancalaPanel.DEFAULT_STONE_SIZE;

			if(Xcircle == MancalaPanel.DEFAULT_MANCALA_WIDTH)
			{
				Xcircle = x_centered;
				Ycircle += MancalaPanel.DEFAULT_STONE_SIZE;
			}
		}
	}
}
