/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * this is one of the Panel for the Board Frame
 * @author Team Architects
 */
// Controller: Front Side
public class PitPanel extends JPanel 
{
	public final static int DEFAULT_ROWS_NUMBER = 2;
	public final static int DEFAULT_COLS_NUMBER = 6;
	public final static int DEFAULT_PITS_NUMBER = 12;

	public final static int FIRST_LOWER_PIT = 0;
	public final static int LAST_LOWER_PIT = 5;
	public final static int FIRST_UPPER_PIT = 6;
	public final static int LAST_UPPER_PIT = 11;

	private Board board;
	private Pit[] myPits;
	private BoardLayout layout;

	public PitPanel(Board board) 
	{	
		this.board = board;
		setLayout(new GridLayout(
			PitPanel.DEFAULT_ROWS_NUMBER, 
			PitPanel.DEFAULT_COLS_NUMBER
		));

		initializePits();
		
		addMouseListener(new MouseAdapter()  //controller
		{	
			public void mouseClicked(MouseEvent e)
			{
				int selectedPit = -1;

				for(int i = 0; i < myPits.length; i++)
				{
					if(myPits[i].contains(e.getX(), e.getY()))
					{
						selectedPit = i;
					}
				}

				System.out.println(selectedPit + " is selected");
				board.select(selectedPit);
			}
		});

		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void setPanelLayout(BoardLayout layout)
	{
		this.layout = layout;
		for(Pit p : myPits)
		{
			p.setPitLayout(layout);
		}
	}

	private void initializePits()
	{
		myPits = new Pit[PitPanel.DEFAULT_PITS_NUMBER];

		for(int id = PitPanel.LAST_UPPER_PIT; id >= PitPanel.FIRST_UPPER_PIT; id--)
		{
			myPits[id] = new Pit(id, board);
			//board.attach(myPits[id]);
			myPits[id].setPitLayout(layout);
			add(myPits[id]);
		}

		for(int id = PitPanel.FIRST_LOWER_PIT; id <= PitPanel.LAST_LOWER_PIT; id++)
		{
			myPits[id] = new Pit(id, board);
			//board.attach(myPits[id]);
			myPits[id].setPitLayout(layout);
			add(myPits[id]);
		}
	}
}
