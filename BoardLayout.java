/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;

/**
 * Independent layout manager of Mancala multiplayer board game.
 */
public interface BoardLayout 
{
	/**
	 * Description of how the Mancalas look like on the board.
	 * @param  x      the upper left x-coordinate of the mancala's space.
	 * @param  y      the upper left y-coordinate of the mancala's space.
	 * @param  width  the width of the mancala's space.
	 * @param  height the height of the mancala's space.
	 * @return the Shape of the mancalas on the board.
	 */
	public Shape mancalaLayout(int x, int y, int width, int height);

	/**
	 * Description of how the pits look like on the board.
	 * @param  x      the upper left x-coordinate of the pit's space.
	 * @param  y      the upper left y-coordinate of the pit's space.
	 * @param  width  the width of the pit's space.
	 * @param  height the height of the pit's space.
	 * @return the Shape of the pits on the board.
	 */
	public Shape pitsLayout(int x, int y, int width, int height);
}
