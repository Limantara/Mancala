import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;


public class BoardFrame extends JFrame
{
	private Board board;

	private final static int DEFAULT_WIDTH = 1000;
	private final static int DEFAULT_HEIGHT = 200;

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

		PitPanel pitController = new PitPanel(board);
		Mancala mancalaA = new Mancala(board, Board.MANCALA_A);
		Mancala mancalaB = new Mancala(board, Board.MANCALA_B);
		
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
		
		
		add(pitController, BorderLayout.CENTER);
		add(mancalaA, BorderLayout.EAST);
		add(mancalaB, BorderLayout.WEST); 
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}