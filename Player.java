/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

/**
 * The model of the player containing his/ her information.
 * @author Team Architects
 */
public class Player 
{
	/**
	 * Construct a new player object.
	 */
	public Player() 
	{
		this(false);
	}
	
	/**
	 * Construct a new player object and set if it is his turn.
	 * @param  myTurn true to set this player to move first or false otherwise
	 */
	public Player(boolean myTurn)
	{
		this.myTurn = myTurn;
	}
	
	/**
	 * Check if this is this player's turn.
	 * @return true if this is this player's turn or false otherwise.
	 */
	public boolean isMyTurn()
	{
		return myTurn;
	}
	
	/**
	 * Set this player turn.
	 * @param myTurn true to set this player to move next or false otherwise.
	 */
	public void setMyTurn(boolean myTurn)
	{
		this.myTurn = myTurn;
	}

	private boolean myTurn;
}
