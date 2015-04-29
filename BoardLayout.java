/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;

public interface BoardLayout 
{
	public Shape mancalaLayout(int x, int y, int width, int height);
	public Shape pitsLayout(int x, int y, int width, int height);
}
