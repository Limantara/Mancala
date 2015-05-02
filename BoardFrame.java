/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;
<<<<<<< HEAD
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

=======
>>>>>>> bbcb66138bf613580f161e7c1ccaea10deee0065
import javax.swing.*;
import javax.swing.event.*;


public class BoardFrame extends JFrame
{
	private Board board;
	private Mancala mancalaA;
	private Mancala mancalaB;
	private PitPanel pitController;
<<<<<<< HEAD
	private JPanel redoPanel;
=======
>>>>>>> bbcb66138bf613580f161e7c1ccaea10deee0065

	public final static int DEFAULT_WIDTH = 1000;
	public final static int DEFAULT_HEIGHT = 200;

	public BoardFrame(Board board)
	{
		this.board = board;
		setLayout(new BorderLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x_centered = (int) screenSize.getWidth()/2 - BoardFrame.DEFAULT_WIDTH/2;
		int y_centered = (int) screenSize.getHeight()/2 - BoardFrame.DEFAULT_HEIGHT/2;

		setBounds(
			x_centered, 
		  	y_centered, 
		  	BoardFrame.DEFAULT_WIDTH, 
		  	BoardFrame.DEFAULT_HEIGHT
	    );

		pitController = new PitPanel(board);
		mancalaA = new Mancala(board, Board.MANCALA_A);
		mancalaB = new Mancala(board, Board.MANCALA_B);
		
		//set ChangeListener for MancalaA
		ChangeListener listenerA = new
				ChangeListener()
				{
						public void stateChanged(ChangeEvent e)
						{
							//System.out.println(mancalaA.getmancala());
							mancalaA.setmancala(board.getNumOfStones(board.MANCALA_A_HOLE) );
							
							repaint();
						}
				};
		
		//set ChangeListener for MancalaB
		ChangeListener listenerB = new
				ChangeListener()
				{
						public void stateChanged(ChangeEvent e)
						{
							//System.out.println(mancalaA.getmancala());
							mancalaB.setmancala(board.getNumOfStones(board.MANCALA_B_HOLE) );
							
							repaint();
						}
				};
		//add ChangeListener to the board class
		board.attach(listenerA);
		board.attach(listenerB);
		
<<<<<<< HEAD
		//add the Undo panel
		redoPanel = new JPanel();
		JButton redoBtn = new JButton("REDO");
		redoBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				board.redo();
			}
		});
		
		JButton undoBtn = new JButton("UNDO");
		undoBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				board.undo();
			}
		});
		redoPanel.add(redoBtn);
		redoPanel.add(undoBtn);
		
		add(redoPanel, BorderLayout.NORTH);
=======
		
>>>>>>> bbcb66138bf613580f161e7c1ccaea10deee0065
		add(pitController, BorderLayout.CENTER);
		add(mancalaA, BorderLayout.EAST);
		add(mancalaB, BorderLayout.WEST); 
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
	}
	
	public void setBoardLayout(BoardLayout layout)
	{
		mancalaA.setMancalaLayout(layout);
		mancalaB.setMancalaLayout(layout);
		pitController.setPanelLayout(layout);
		repaint();
	}
}