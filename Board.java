import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.List;
import java.util.*;

//serves as model
public class Board
{
	public final static int PLAYER_A = 1;
	public final static int PLAYER_B = 2;
	public final static int MANCALA_A = 1;
	public final static int MANCALA_B = 2;
	public final static int MANCALA_A_HOLE = 12;
	public final static int MANCALA_B_HOLE = 13;

	private Player playerA;
	private Player playerB;
	private int[] stones; // 0-5 is A's pits, 6-11 is B's pits, 12 is A's mancala, 13 is B's mancala
	private List<ChangeListener> observers;

	private final static int TOTAL_HOLES = 14; // pits + mancalas
	
	public Board()
	{
		observers = new ArrayList<>();
		stones = new int[TOTAL_HOLES];

		for(int pit = 0; pit < stones.length; pit++)
		{
			if(pit == Board.MANCALA_A_HOLE || pit == Board.MANCALA_B_HOLE)
			{
				stones[pit] = Mancala.STARTING_STONES_NUMBER;
				System.out.println("pit: " + pit);
			}
			else
			{
				stones[pit] = Pit.DEFAULT_STONES_NUM;
			}
		}

		playerA = new Player(true);
		playerB = new Player();
	}

	public int getNumOfStones(int pit)
	{
		return stones[pit];
	}

	public void attach(ChangeListener observer)
	{
		observers.add(observer);
	}

	public void update()
	{
		for(ChangeListener observer : observers)
		{
			observer.stateChanged(new ChangeEvent(this));
		}
	}

	public void select(int pit)
	{	
		//look for the pit which is clicked
		// int pit = whichPit;
		// System.out.println("whosePit: " + whosePit);
		
		if(pit >= PitPanel.FIRST_LOWER_PIT && pit <= PitPanel.LAST_LOWER_PIT && playerA.isMyTurn())
		{
			oneMove(pit);
			playerA.setMyTurn(false);
			playerB.setMyTurn(true);
		}

		if(pit >= PitPanel.FIRST_UPPER_PIT && pit <= PitPanel.LAST_UPPER_PIT && playerB.isMyTurn())
		{
			oneMove(pit);
			playerB.setMyTurn(false);
			playerA.setMyTurn(true);
		}

		update();
	}

	
	// //pick up all stones in one pit, and move counter-clock wise
	public void oneMove(int pit)
	{
		int whosePit = -1;

		if(pit >= PitPanel.FIRST_LOWER_PIT && pit <= PitPanel.LAST_LOWER_PIT)
		{
			whosePit = Board.PLAYER_A;
		}
	 	else if(pit >= PitPanel.FIRST_UPPER_PIT && pit <= PitPanel.LAST_UPPER_PIT)
		{
			whosePit = Board.PLAYER_B;
		}
			 
		int remainingStones = stones[pit]; //Stones left in the players hand
		stones[pit] = 0;

		//If it is A's turn, and he click on pit[5]
		if(pit == PitPanel.LAST_LOWER_PIT && remainingStones > 0 && whosePit == Board.PLAYER_A)
		{
			stones[Board.MANCALA_B_HOLE]++;
			remainingStones--;
			pit = PitPanel.FIRST_UPPER_PIT;
		}
		//If it is B's turn, and he click on pit[11]
		else if(pit == PitPanel.LAST_UPPER_PIT && remainingStones > 0 && whosePit == Board.PLAYER_B)
		{
			stones[Board.MANCALA_A_HOLE]++;
			remainingStones--;
			pit = PitPanel.FIRST_LOWER_PIT;
		}
		else 
		{
			pit++;
		}
		
		while(remainingStones > 0)
		{
			stones[pit]++;
			remainingStones--;

			if(pit == PitPanel.LAST_LOWER_PIT && remainingStones > 0 && whosePit == Board.PLAYER_B)
			{
				stones[Board.MANCALA_B_HOLE]++;
				remainingStones--;
				pit = PitPanel.FIRST_UPPER_PIT;
			}
			else if(pit == PitPanel.LAST_UPPER_PIT && remainingStones > 0 && whosePit == Board.PLAYER_A)
			{
				stones[Board.MANCALA_A_HOLE]++;
				remainingStones--;
				pit = PitPanel.FIRST_LOWER_PIT;
			}
			else
			{
				pit++;
			}
		}

		return;
	}
}
