/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.02 2015/5/2
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.List;
import java.util.*;

/**
 * Model class that stores 
 */
public class Board
{
	public final static int PLAYER_A = 1;
	public final static int PLAYER_B = 2;
	public final static int MANCALA_A = 1;
	public final static int MANCALA_B = 2;
	public final static int MANCALA_A_HOLE = 13;
	public final static int MANCALA_B_HOLE = 12;

	private Player playerA;
	private Player playerB;
	private int[] stones; // 0-5 is A's pits, 6-11 is B's pits, 12 is A's mancala, 13 is B's mancala

	private int step; //step for the game, if A moved one step, B moved one step. The step is 2. 
	private List<int[]> snapShots;  
	private List<ChangeListener> observers;
	private boolean checkWinner;
	private boolean FirstTime;
	private final static int TOTAL_HOLES = 14; // pits + mancalas
	
	/**
	 * Board class construct 
	 */
	public Board()
	{
		observers = new ArrayList<>();
		stones = new int[TOTAL_HOLES];
		checkWinner = false;
		FirstTime = true;
		//used to take snapShots of board in each step
		step = 0;
		snapShots = new LinkedList<int[]>();
		

		for(int pit = 0; pit < stones.length; pit++)
		{
			if(pit == Board.MANCALA_A_HOLE || pit == Board.MANCALA_B_HOLE)
			{
				stones[pit] = Mancala.STARTING_STONES_NUMBER;
				//System.out.println("pit: " + pit);
			}
			else
			{	
				stones[pit] = Pit.DEFAULT_STONES_NUM;
			}
		}


		//take the original snapshot
		int[] temp = Arrays.copyOf(stones, stones.length);
		snapShots.add(step++, temp);
		
		playerA = new Player();
		playerB = new Player();
	}

	/**
	 * set who is the first player to play
	 * @param player input the player
	 */
	public void SetPlayer(int pit)
	{
		if(pit >= PitPanel.FIRST_LOWER_PIT && pit <= PitPanel.LAST_LOWER_PIT)
		{
			playerA.setMyTurn(true);
			playerB.setMyTurn(false);
		}

		if(pit >= PitPanel.FIRST_UPPER_PIT && pit <= PitPanel.LAST_UPPER_PIT)
		{
			playerB.setMyTurn(true);
			playerA.setMyTurn(false);
		}
	}
	
	public String getplayerturn()
	{
		if(playerA.isMyTurn())
		{
			return "playerA";
		}
		else
		{
			return "playerB";
		}
	}
	
	/**
	 * get the number of stones
	 * @param pit input the index of the array
	 * @return the number of stones with index
	 */
	public int getNumOfStones(int pit)
	{
		return stones[pit];
	}

	/**
	 * add change listener into the ArrayList
	 * @param observer input the observer for adding the change listener 
	 */
	public void attach(ChangeListener observer)
	{
		observers.add(observer);
	}

	/**
	 * send out the change event to update all Views
	 */
	public void update()
	{
		for(ChangeListener observer : observers)
		{
			observer.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Check does the player finish the game or not
	 * then print out who is the winner
	 * @param players the current player to check
	 * @return a string for who is the winner
	 */
	public String Endgame(Player players)
	{
		checkWinner = true;
		String output = "";
		if(players == playerA)
		{
			for(int i = PitPanel.FIRST_LOWER_PIT; i <= PitPanel.LAST_LOWER_PIT; i++)
			{
				if(stones[i] != 0)
				{
					checkWinner = false;
				}
			}
			if(checkWinner)
			{
				for(int j = PitPanel.FIRST_UPPER_PIT; j <= PitPanel.LAST_UPPER_PIT; j++)
				{
					if(stones[j] != 0)
					{
						stones[MANCALA_B_HOLE] += stones[j];
						stones[j] = 0;
						update();
					}
				}
			}
		}
		else if(players == playerB)
		{
			for(int i = PitPanel.FIRST_UPPER_PIT; i <= PitPanel.LAST_UPPER_PIT; i++)
			{
				if(stones[i] != 0)
				{
					checkWinner = false;
				}
			}
			if(checkWinner)
			{
				for(int j = PitPanel.FIRST_LOWER_PIT; j <= PitPanel.LAST_LOWER_PIT; j++)
				{
					if(stones[j] != 0)
					{
						stones[MANCALA_A_HOLE] += stones[j];
						stones[j] = 0;
						update();
					}
				}
			}
		}
		
		if(checkWinner)
		{
			if(stones[MANCALA_A_HOLE] > stones[MANCALA_B_HOLE]) //check who has more stones in mancala
			{
				output = "Game Over and Player A is the WInner!";
				
			}
			else
			{
				output = "Game Over and Player B is the WInner!";
			}
		}
		return output;
	}
	
	public void select(int pit)
	{	
		//look for the pit which is clicked
		// int pit = whichPit;
		// System.out.println("whosePit: " + whosePit);
		
		//set who is the first player
		if(FirstTime)
		{
			SetPlayer(pit);
			FirstTime = false;
		}
		
		
		if(pit >= PitPanel.FIRST_LOWER_PIT && pit <= PitPanel.LAST_LOWER_PIT && playerA.isMyTurn())
		{
			oneMove(pit);	

			takeSnapShot();

			//check end game or not
			System.out.println(Endgame(playerA));
		}

		if(pit >= PitPanel.FIRST_UPPER_PIT && pit <= PitPanel.LAST_UPPER_PIT && playerB.isMyTurn())
		{
			oneMove(pit);

			takeSnapShot();

			
			//check end game or not
			System.out.println(Endgame(playerB));
		}
		
		update();
		
		//System.out.println("playerA" + playerA.isMyTurn());
		//System.out.println("playerB" + playerB.isMyTurn()); 
	}
	
	// //pick up all stones in one pit, and move counter-clock wise
	public void oneMove(int pit)
	{
		int whosePit = -1;

		if(pit >= PitPanel.FIRST_LOWER_PIT && pit <= PitPanel.LAST_LOWER_PIT)
		{
			whosePit = Board.PLAYER_A;
			//default player turn setting
			playerB.setMyTurn(true);
			playerA.setMyTurn(false);
		}
	 	else if(pit >= PitPanel.FIRST_UPPER_PIT && pit <= PitPanel.LAST_UPPER_PIT)
		{
			whosePit = Board.PLAYER_B;
			//default player turn setting
			playerA.setMyTurn(true);
			playerB.setMyTurn(false);
		}
			 
		int remainingStones = stones[pit]; //Stones left in the players hand		
		stones[pit] = 0;
		
		boolean MancalaAdded = false;
		//System.out.println("remainingStones: " + remainingStones);
		//stem.out.println("pit: " + pit);
		while(remainingStones > 0)
		{
			
			//If it is A's turn, and he click on pit[5]
			if(pit == PitPanel.LAST_LOWER_PIT && remainingStones > 0 && whosePit == Board.PLAYER_A)
			{
				System.out.println("1 pit: " + pit + " <- execpted 5");
				stones[Board.MANCALA_A_HOLE]++;
				MancalaAdded = true;
				//set playerA for next turn when last pit in mancala 
				if(pit == PitPanel.LAST_LOWER_PIT && remainingStones == 1 && whosePit == Board.PLAYER_A)
				{
					//System.out.println("pit A: " + pit + " <- execpted 5");
					System.out.println("2 pit: " + pit + " <- execpted 5");
					playerA.setMyTurn(true);
					playerB.setMyTurn(false);
				}
				
				remainingStones--;
				pit = PitPanel.FIRST_UPPER_PIT;
				System.out.println("3 pit: " + pit + " <- execpted 6");
			}
			//If it is B's turn, and he click on pit[11]
			else if(pit == PitPanel.LAST_UPPER_PIT && remainingStones > 0 && whosePit == Board.PLAYER_B)
			{
				
				stones[Board.MANCALA_B_HOLE]++;
				MancalaAdded = true;
				//set playerB for next turn when last pit in mancala 
				if(pit == PitPanel.LAST_UPPER_PIT && remainingStones == 1 && whosePit == Board.PLAYER_B)
				{
					//System.out.println("pit B: " + pit);
					playerB.setMyTurn(true);
					playerA.setMyTurn(false);
				}
				
				remainingStones--;
				pit = PitPanel.FIRST_LOWER_PIT;
			}		
			
			//boolean continues = true;
			else if(MancalaAdded) //if Mancala added, the pit is already added so do nothing
			{
				//do nothing
				System.out.println("4 pit: " + pit + " <- execpted 0 or 6");
				stones[pit]++;
				remainingStones--;
				MancalaAdded = false; //set MancalaAdded back to default
				//continues = false;
				//System.out.println("do nothing");
			}
			else
			{
				//System.out.println("4 pit: " + pit + " <- execpted 5");
				pit++; 
				stones[pit]++;
				remainingStones--;
				//System.out.println("5 pit: " + pit + " <- execpted 6");
				//continues = false;
			}
			
			//If the last stone you drop is in an empty pit on your side, 
			//you get to take that stone and all of your opponents stones that are in the opposite pit.
			if(stones[pit] == 1 && remainingStones == 0 && pit >= PitPanel.FIRST_LOWER_PIT && pit <= PitPanel.LAST_LOWER_PIT &&  whosePit == Board.PLAYER_A)
			{
				//System.out.println("pit: " + pit + " whosepit: " + whosePit);
				stones[pit] += stones[11-pit];
				stones[11-pit] = 0;
			}
			else if(stones[pit] == 1 && remainingStones == 0 && pit >= PitPanel.FIRST_UPPER_PIT && pit <= PitPanel.LAST_UPPER_PIT &&  whosePit == Board.PLAYER_B)
			{
				//System.out.println("pit: " + pit + " whosepit: " + whosePit);
				stones[pit] += stones[11-pit];
				stones[11-pit] = 0;
			}
			
		
		
		}
		return;
	}
	
	/**
	 * take snap shot after each step
	 */
	public void takeSnapShot(){
		int[] temp = Arrays.copyOf(stones, stones.length);
		if(step < snapShots.size())
			snapShots.set(step++, temp);
		else
			snapShots.add(step++, temp);
	}
	
	public void undo(){
		if(step < 2)
			return;
		step -= 2;
		stones = Arrays.copyOf(snapShots.get(step), snapShots.get(step++).length);
		if(playerA.isMyTurn()) {playerB.setMyTurn(true); playerA.setMyTurn(false);}
		else if(playerB.isMyTurn()) {playerA.setMyTurn(true); playerB.setMyTurn(false);}
		update();
	}
	
	public void redo(){
		if(step == snapShots.size())
			return;
		stones = Arrays.copyOf(snapShots.get(step), snapShots.get(step++).length);
		if(playerA.isMyTurn()) {playerB.setMyTurn(true); playerA.setMyTurn(false);}
		else if(playerB.isMyTurn()) {playerA.setMyTurn(true); playerB.setMyTurn(false);}
		update();
	}
}
