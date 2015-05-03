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
import javax.swing.border.*;


public class MainFrame extends JFrame
{
	public MainFrame()
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
		
		addMenu();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void addMenu()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1));

		JPanel upperPanel = getUpperPanel();
		JPanel lowerPanel = getLowerPanel();

		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		add(getStartButton(), BorderLayout.SOUTH);
	}

	private JPanel getUpperPanel()
	{
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BorderLayout());

		JLabel teamInformation = new JLabel(
			"<html>" +
			    "<body>" +
			       "<br><br><br><br><h1>Mancala Multiplayer</h1><br><br><br><br>" +
			    "</body>" +
			"</html>",
		SwingConstants.CENTER);

		upperPanel.add(teamInformation, BorderLayout.CENTER);
		return upperPanel;
	}

	private JPanel getLowerPanel()
	{
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(2, 1));

		JPanel stonesOptions = getStonesOptions();
		JPanel layoutOptions = getLayoutOptions();

		lowerPanel.add(stonesOptions);
		lowerPanel.add(layoutOptions);
		return lowerPanel;
	}

	private JPanel getStonesOptions()
	{
		JPanel stonesOptions = new JPanel();
		stonesOptions.setLayout(new GridLayout(1, 2));
		stonesOptions.setBorder(new EmptyBorder(10, 150, 0, 150));

		JButton option1 = new JButton("<html><body><h4>3 stones</h4></body></html>");
		option1.setBorder(SELECTED);

		JButton option2 = new JButton("<html><body><h4>4 stones</h4></body></html>");
		option2.setBorder(DEFAULT);

		option1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				option1.setBorder(SELECTED);
				option2.setBorder(DEFAULT);
				Pit.setDefaultStone(3);
			}
		});

		option2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				option1.setBorder(DEFAULT);
				option2.setBorder(SELECTED);
				Pit.setDefaultStone(4);
			}
		});

		stonesOptions.add(option1);
		stonesOptions.add(option2);
		return stonesOptions;
	}

	private JPanel getLayoutOptions()
	{
		JPanel layoutOptions = new JPanel();
		layoutOptions.setLayout(new GridLayout(1, 2));
		layoutOptions.setBorder(new EmptyBorder(0, 150, 10, 150));

		JButton option1 = new JButton("<html><body><h4>Circular</h4></body></html>");
		option1.setBorder(DEFAULT);

		JButton option2 = new JButton("<html><body><h4>Rectangular</h4></body></html>");
		option2.setBorder(SELECTED);

		option1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				option1.setBorder(SELECTED);
				option2.setBorder(DEFAULT);
				b = new BoardFrame(new Board());
				b.setBoardLayout(new CircularLayout());

			}
		});

		option2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				option1.setBorder(DEFAULT);
				option2.setBorder(SELECTED);
				b = new BoardFrame(new Board());
				b.setBoardLayout(new RectangularLayout());
			}
		});

		layoutOptions.add(option1);
		layoutOptions.add(option2);
		return layoutOptions;
	}

	private JPanel getStartButton()
	{
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JPanel startButtonPanel = new JPanel();
		JButton startButton = new JButton("<html><body><h1>Start Playing! </h1></body></html>");
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				repaint();

				if(b == null)
				{
					b = new BoardFrame(new Board());
					b.setBoardLayout(new RectangularLayout());
				}
				
				b.repaint();
				b.setVisible(true);
			}
		});

		startButtonPanel.add(startButton);

		panel.add(startButtonPanel);
		panel.add(new JLabel(
			"<html>" +
			    "<body>" +
			       "<br><p><center>© 2015 Team Architects</center></p>" +
			       "<p>Boya Zhou - Edwin Limantara - Kun Su</p><br>" +
			    "</body>" +
			"</html>",
		SwingConstants.CENTER));
		return panel;
	}

	private BoardFrame b;
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 500;
	private static Border DEFAULT;
	private static Border SELECTED; 
	private static Border OUTSIDE_MARGIN;

	static 
	{
		OUTSIDE_MARGIN = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		DEFAULT = new CompoundBorder(OUTSIDE_MARGIN, new LineBorder(Color.BLACK, 1));
		SELECTED = new CompoundBorder(OUTSIDE_MARGIN, new LineBorder(Color.BLACK, 3));
	}
}