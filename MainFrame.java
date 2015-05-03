/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;


public class MainFrame extends JFrame
{
	private BoardFrame b;
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 500;

	public MainFrame(BoardFrame b)
	{
		setLayout(new BorderLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x_centered = (int) screenSize.getWidth()/2 - MainFrame.DEFAULT_WIDTH/2;
		int y_centered = (int) screenSize.getHeight()/2 - MainFrame.DEFAULT_HEIGHT/2;

		setBounds(
			x_centered, 
		  	y_centered, 
		  	MainFrame.DEFAULT_WIDTH, 
		  	MainFrame.DEFAULT_HEIGHT
	    );
		
		JButton circleDisplay = new JButton("Circle");
		circleDisplay.addActionListener(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
					setVisible(false);
					repaint();
					b.setVisible(true);
					b.setBoardLayout(new CircularLayout());
					b.repaint();
				}
			}
		);

		JButton rectangleDisplay = new JButton("Rectangle");

		rectangleDisplay.addActionListener(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
					setVisible(false);
					repaint();
					b.setVisible(true);
					b.setBoardLayout(new RectangularLayout());
					b.repaint();
				}
			}
		);

		JLabel textLabel = new JLabel("Choose Layout: ");
		JPanel mainPanel = new JPanel();

		mainPanel.add(textLabel);
		mainPanel.add(circleDisplay);
		mainPanel.add(rectangleDisplay);

		add(mainPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}