/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * this is one of the Component for Board Frame 
 * @author Team Architects
 */
public class Mancala extends JComponent implements ChangeListener
{
	public final static int STARTING_STONES_NUMBER = 0;

	private Board board;
	private BoardLayout layout;
	private int stone;
	private int whichMancala;
	private final static int DEFAULT_WIDTH = 60;
	private final static int DEFAULT_HEIGHT = 100;
	private final static int DEFAULT_OUTER_WIDTH = 120;
	private final static int DEFAULT_OUTER_HEIGHT = 100;

	private final static int DEFAULT_STONE_X = 0;
	private final static int DEFAULT_STONE_Y = 0;
	private final static int DEFAULT_STONE_SIZE = 10;

	public Mancala(Board board, int whichMancala) 
	{
		this.board = board;
		
		if(whichMancala == Board.MANCALA_A)
		{
			this.whichMancala = Board.MANCALA_A;
		}
		else if(whichMancala == Board.MANCALA_B)
		{
			this.whichMancala = Board.MANCALA_B;
		}
		else
		{
			// throw error
		}

		setPreferredSize(new Dimension(Mancala.DEFAULT_OUTER_WIDTH, Mancala.DEFAULT_OUTER_HEIGHT));
	}

	public void setmancala(int stones)
	{
		this.stone = stones;
	}
	
	public String getmancala()
	{
		return " " + stone;
	}
	
	public void setMancalaLayout(BoardLayout layout)
	{
		
		this.layout = layout;
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D) g;
		int x_centered = getWidth()/2 - Mancala.DEFAULT_WIDTH/2;
		int y_centered = getHeight()/2 - Mancala.DEFAULT_HEIGHT/2;

		g2.draw(layout.mancalaLayout(x_centered, y_centered, Mancala.DEFAULT_WIDTH, Mancala.DEFAULT_HEIGHT));

		int Xcircle = Mancala.DEFAULT_STONE_X;
		int Ycircle = Mancala.DEFAULT_STONE_Y;
		int numStones;

		if(whichMancala == Board.MANCALA_A)
		{
			numStones = board.getNumOfStones(Board.MANCALA_A_HOLE); 
			g2.drawString("Mancala" + 'A', 33, 10);
		}
		else
		{
			numStones = board.getNumOfStones(Board.MANCALA_B_HOLE);
			g2.drawString("Mancala" + 'B', 33, 10);
		}

		
		for(int i = 0; i < numStones; i++)
		{
			//create Pit inside the Rectangle, MAX Pit will be 36.
			Ellipse2D.Double circle = new Ellipse2D.Double (
				x_centered + Xcircle, 
				y_centered+ Ycircle, 
				Mancala.DEFAULT_STONE_SIZE, 
				Mancala.DEFAULT_STONE_SIZE
			); 

			g2.fill(circle);

			Xcircle += Mancala.DEFAULT_STONE_SIZE;

			if(Xcircle == Mancala.DEFAULT_WIDTH)
			{
				Xcircle = DEFAULT_STONE_X;
				Ycircle += Mancala.DEFAULT_STONE_SIZE;
			}
			
			
		}
	}

	public void stateChanged(ChangeEvent e)
	{
		board = (Board) e.getSource();
		repaint();
	}
}
