/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

public class Player 
{
	private boolean myTurn;
	
	public Player() 
	{
		this(false);
	}
	
	public Player(boolean myTurn)
	{
		this.myTurn = myTurn;
	}
	
	public boolean isMyTurn()
	{
		return myTurn;
	}
	
	public void setMyTurn(boolean myTurn)
	{
		this.myTurn = myTurn;
	}
}
