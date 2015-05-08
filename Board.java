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
 * Model class that store the state of the game.
 */
public class Board
{
	/**
	 * Construct a new board model. 
	 */
	public Board()
	{
		observers = new ArrayList<>();
		stones = new int[TOTAL_HOLES];
		checkWinner = false;
		FirstTime = true;

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

		//A and B can only undo at most 3 times
		aUndoTime = 3;
		bUndoTime = 3;
		
		playerA = new Player();
		playerB = new Player();
	}

	/**
	 * Set the next move according to the most recent selected pit.
	 * @param pit the most recent selected pit.
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
	
	/**
	 * Return the name of the player whose turn is now.
	 * @return string "playerA" if it is player A's turn or "playerB" otherwise.
	 */
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
	 * Get the number of stones.
	 * @param pit the index of the pit.
	 * @return the number of stones.
	 */
	public int getNumOfStones(int pit)
	{
		return stones[pit];
	}

	/**
	 * Add an observer.
	 * @param observer the observer of this model.
	 */
	public void attach(ChangeListener observer)
	{
		observers.add(observer);
	}

	/**
	 * Update observers with the latest changes in model.
	 */
	public void update()
	{
		for(ChangeListener observer : observers)
		{
			observer.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Check if a player has won the game and return a message.
	 * @param player the player in query.
	 * @return message containing information about the winner of the game.
	 */
	public String Endgame(Player player)
	{
		checkWinner = true;
		String output = "";
		if(player == playerA)
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
		else if(player == playerB)
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

	/**
	 * Select a pit.
	 * @param pit the selected pit.
	 */
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
			lastStep = Arrays.copyOf(stones, stones.length);
			nextStep = null;
			whoMoveLastStep = "A";
			oneMove(pit);	

			//check end game or not
			System.out.println(Endgame(playerA));
		}

		if(pit >= PitPanel.FIRST_UPPER_PIT && pit <= PitPanel.LAST_UPPER_PIT && playerB.isMyTurn())
		{
			lastStep = Arrays.copyOf(stones, stones.length);
			nextStep = null;
			whoMoveLastStep = "B";
			oneMove(pit);
			//check end game or not
			System.out.println(Endgame(playerB));
		}
		
		update();
	}
	
	/**
	 * Return the remaining undo left for playerA.
	 * @return the number of remaining undo.
	 */
	public int getAundoTime()
	{
		return bUndoTime;
	}
	
	/**
	 * Return the remaining undo left for playerB.
	 * @return the number of remaining undo.
	 */
	public int getBundoTime()
	{
		return aUndoTime;
	}

	/**
	 * Undo the latest change and go back to the previous state of the game.
	 */
	public void undo()
	{
		if(lastStep == null)
		{
			return;	
		}
			
		//remember the current turn before undo
		if(playerA.isMyTurn())
		{
			turnCache = "A";
		}
		
		if(playerB.isMyTurn())
		{
			turnCache = "B";
		}
		
		if(whoMoveLastStep.equals("B")) 
		{
			if(aUndoTime == 0)
			{
				return;	
			}
				
			playerB.setMyTurn(true); 
			playerA.setMyTurn(false); 
			aUndoTime--; 
		}
		else if(whoMoveLastStep.equals("A")) 
		{
			if(bUndoTime == 0)
			{
				return;
			}
				
			playerA.setMyTurn(true); 
			playerB.setMyTurn(false); 
			bUndoTime--;
		}

		nextStep = Arrays.copyOf(stones, stones.length);
		stones = Arrays.copyOf(lastStep, lastStep.length);
		lastStep = null;
		update();
		System.out.println(playerA.isMyTurn() + " " + playerB.isMyTurn());
	}

	/**
	 * Cancel the latest undo and proceed with the latest state of the game.
	 */
	public void redo()
	{
		if(nextStep == null)
		{
			return;
		}
			
		if(turnCache.equals("B")) 
		{
			playerB.setMyTurn(true); 
			playerA.setMyTurn(false);
		}
		else if(turnCache.equals("A")) 
		{
			playerA.setMyTurn(true); 
			playerB.setMyTurn(false);
		}
		
		stones = Arrays.copyOf(nextStep, nextStep.length);
		nextStep = null;
		update();
	}

	/**
	 * Move the stones in the selected pit in counter clock-wise direction.
	 * @param pit the selected pit.
	 */
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
				//System.out.println("1 pit: " + pit + " <- execpted 5");
				stones[Board.MANCALA_A_HOLE]++;
				MancalaAdded = true;
				//set playerA for next turn when last pit in mancala 
				if(pit == PitPanel.LAST_LOWER_PIT && remainingStones == 1 && whosePit == Board.PLAYER_A)
				{
					//System.out.println("pit A: " + pit + " <- execpted 5");
					//System.out.println("2 pit: " + pit + " <- execpted 5");
					playerA.setMyTurn(true);
					playerB.setMyTurn(false);
				}
				
				remainingStones--;
				pit = PitPanel.FIRST_UPPER_PIT;
				//System.out.println("3 pit: " + pit + " <- execpted 6");
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
				//System.out.println("4 pit: " + pit + " <- execpted 0 or 6");
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

	public final static int PLAYER_A = 1;
	public final static int PLAYER_B = 2;
	public final static int MANCALA_A = 1;
	public final static int MANCALA_B = 2;
	public final static int MANCALA_A_HOLE = 13;
	public final static int MANCALA_B_HOLE = 12;
	private Player playerA;
	private Player playerB;
	private int[] stones; // 0-5 is A's pits, 6-11 is B's pits, 12 is A's mancala, 13 is B's mancala
	private int[] lastStep; //used to record the last step
	private int[] nextStep; //uesd to record the next step after redo
	private int aUndoTime; //number of times A and B can undo
	private int bUndoTime;
	private String whoMoveLastStep;
	private String turnCache; //store whose turn is it before click on undo.
	private List<ChangeListener> observers;
	private boolean checkWinner;
	private boolean FirstTime;
	private final static int TOTAL_HOLES = 14; // pits + mancalas
}
