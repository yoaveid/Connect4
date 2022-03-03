import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class FourInARowGrafic implements ActionListener {
	
	JFrame frame = new JFrame();
	static JButton buttons [][] = new JButton[FourInARow.row][FourInARow.col];
	JButton sumbitType = new JButton("Submit");
	JButton sumbitLevel = new JButton("Submit");
	JButton sumbitStart = new JButton("Submit");
	JPanel select_panel = new JPanel();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel text = new JLabel();
	JRadioButton Player1Game,Player2Game,hard,medium,easy;	
	ButtonGroup typeGroup,chooseLevel;
	boolean turn = true; // true - blue turn , false - red turn
	boolean type = true; // true - the game is against CPU , false - 2 player game
	boolean whoStart = true; // true - the man/blue start , false - the CPU/the red start
	FourInARow.States level;
	
	
	public FourInARowGrafic() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
		frame.setSize(1000,800); // sets the size
		frame.getContentPane().setBackground(new Color(75,75,75));
		frame.setLayout(new BorderLayout());
		
		title_panel.setBackground(new Color(20,20,20));
		text.setBackground(new Color(20,20,20));
		text.setForeground(new Color(25,250,0));
		text.setFont(new Font("INk",Font.BOLD,75));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setText("Four In A Row");
		text.setOpaque(true);
		
		Player1Game = new JRadioButton("player againts CPU");
		Player1Game.setBackground(new Color(230,20,10));
		Player1Game.setForeground(new Color(0,220,0));
		Player2Game = new JRadioButton("player againts player");
		Player2Game.setBackground(new Color(230,20,10));
		Player2Game.setForeground(new Color(0,220,0));

		hard = new JRadioButton("hard");
		medium = new JRadioButton("medium");
		easy = new JRadioButton("easy");
		
		hard.setBackground(new Color(230,20,10));
		hard.setForeground(new Color(0,220,0));
		
		medium.setBackground(new Color(230,20,10));
		medium.setForeground(new Color(0,220,0));
		
		easy.setBackground(new Color(230,20,10));
		easy.setForeground(new Color(0,220,0));
		
		typeGroup = new ButtonGroup();
		chooseLevel = new ButtonGroup();
		
		typeGroup.add(Player1Game);
		typeGroup.add(Player2Game);
		
		chooseLevel.add(easy);
		chooseLevel.add(medium);
		chooseLevel.add(hard);
		
		Player1Game.setFont(new Font("fd",Font.BOLD,80));
		Player1Game.setFocusable(false);
		Player1Game.addActionListener(this);
		
		
		Player2Game.setFont(new Font("fd",Font.BOLD,80));
		Player2Game.setFocusable(false);
		Player2Game.addActionListener(this);
		
		sumbitType.setFont(new Font("fd",Font.BOLD,80));
		sumbitType.setFocusable(false);
		sumbitType.addActionListener(this);
		
		hard.setFont(new Font("fd",Font.BOLD,35));
		hard.setFocusable(false);
		hard.addActionListener(this);
		
		medium.setFont(new Font("fd",Font.BOLD,35));
		medium.setFocusable(false);
		medium.addActionListener(this);
		
		easy.setFont(new Font("fd",Font.BOLD,35));
		easy.setFocusable(false);
		easy.addActionListener(this);
		
		sumbitLevel.setFont(new Font("fd",Font.BOLD,35));
		sumbitLevel.setFocusable(false);
		sumbitLevel.addActionListener(this);
		
		sumbitStart.setFont(new Font("fd",Font.BOLD,35));
		sumbitStart.setFocusable(false);
		sumbitStart.addActionListener(this);
		
		button_panel.setLayout(new GridLayout(buttons.length,buttons[0].length));
		button_panel.setForeground(new Color(25,250,0));
		for(int i = 0; i< buttons.length; i++)
			for(int j =0; j<  buttons[0].length; j++)
			{
			
				buttons[i][j] = new JButton();
				buttons[i][j].setFont(new Font("INK pink",Font.BOLD,100));
				buttons[i][j].setFocusable(false);
				buttons[i][j].setForeground(Color.GREEN);
				if(i == buttons.length-1)
					buttons[i][j].addActionListener(this);
				button_panel.add(buttons[i][j]);
			}
		
		select_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		select_panel.setBackground(new Color(230,20,10));
		select_panel.add(Player1Game);
		select_panel.add(Player2Game);
		select_panel.add(sumbitType);
		frame.add(select_panel);
	//	frame.add(button_panel);
		title_panel.add(text);
		frame.add(title_panel,BorderLayout.NORTH);
		frame.setVisible(true); // make frame visible constructor stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sumbitType)
		{
			if(!Player1Game.isSelected() && !Player2Game.isSelected())
				return;
			if(Player1Game.isSelected())
				type = true;
			else
				type = false;
			typeGroup.clearSelection();
			levelSelection();
		}
		else if(e.getSource() == sumbitLevel)
		{
			if(hard.isSelected())
				level = FourInARow.States.HARD;
			else if(easy.isSelected())
				level = FourInARow.States.EASY;
			else
				level = FourInARow.States.MEDIUM;
			chooseLevel.clearSelection();
			whoStart();
		}
		else if(e.getSource() == sumbitStart)
		{
			if(!Player1Game.isSelected() && !Player2Game.isSelected())
				return;
			if(Player1Game.isSelected())
				whoStart = true;
			else
				whoStart = false;
			Player1Game.setText("player againts CPU");
			Player2Game.setText("player againts player");
			typeGroup.clearSelection();		
			startGame();
		}
		String color;
		for(int i = 0; i< buttons.length; i++)
			for(int j =0; j<  buttons[0].length; j++)
			{
				if(buttons[i][j] == e.getSource() && FourInARow.board[i][j] == null)
				{
					if(turn)
					{
						color = "b";
						buttons[i][j].setBackground(Color.BLUE);

					}
					else
					{
						color = "r";
						buttons[i][j].setBackground(Color.red);

					}
					FourInARow.board[i][j] = new Point(color);
					if(i != 0)
						FourInARow.avilabelPlaces[j]--;
					if(i != 0)
						buttons[i-1][j].addActionListener(this);
					FourInARow.States whoWon = FourInARow.isWin(turn,FourInARow.States.REAL);
					if(whoWon != FourInARow.States.NOONEWON)
					{
						if(whoWon == FourInARow.States.BLUEWON)
							text.setText("The Blue wins");
						else if(whoWon == FourInARow.States.REDWON)
							text.setText("The Red wins");
						else
							text.setText("This ia a tie, good game");
						String[] options = { "Yes", "No,start over", "exit"};
						int answer = JOptionPane.showOptionDialog(null, "Do you want to play again? same option?", "restart",
								JOptionPane.NO_OPTION,JOptionPane.DEFAULT_OPTION, null, options, 0);
						if(answer == 2 || answer == -1)
							System.exit(0);
						else if(answer == 0)
						{
							intalizedGame();
						}
						else if(answer == 1)
							intalized();
						return;
					}
					turn = !turn;
					if((turn == false) && type)
					{
						text.setText("Ammmm let me think....");
						text.repaint();
						text.validate();
						title_panel.repaint();
						title_panel.validate();
			
						int temp = FourInARow.getCPUPlace(level,turn);
						e.setSource(buttons[FourInARow.avilabelPlaces[temp]][temp]);
						i = 0;
						j = -1;
					}
					if(turn)
						text.setText("This Blue turn");
					else
						text.setText("This Red turn");
				}
			}
	}
	private void intalized() {
		for(int i = 0; i< buttons.length; i++)
			for(int j =0; j<  buttons[0].length; j++)
			{
				
				if(i != FourInARow.row-1)
					buttons[i][j].removeActionListener(this);
				buttons[i][j].setBackground(Color.getColor("submit"));
				buttons[i][j].setText("");
				FourInARow.board[i][j] = null;
			}	
		for(int i =0; i< FourInARow.col; i ++)
			FourInARow.avilabelPlaces[i] = FourInARow.row-1;
		frame.remove(button_panel);
		text.setText("Connect 4");
		select_panel.remove(sumbitStart);
		select_panel.add(Player1Game);
		select_panel.add(Player2Game);
		select_panel.add(sumbitType);
		frame.add(select_panel);
		frame.repaint();
		frame.validate();
			
	}

	private void intalizedGame() {
		for(int i = 0; i< buttons.length; i++)
			for(int j =0; j<  buttons[0].length; j++)
			{
				
				if(i != FourInARow.row-1)
					buttons[i][j].removeActionListener(this);
				buttons[i][j].setBackground(Color.getColor("submit"));
				buttons[i][j].setText("");
				FourInARow.board[i][j] = null;
			}
		for(int i =0; i< FourInARow.col; i ++)
			FourInARow.avilabelPlaces[i] = FourInARow.row-1;
		turn = whoStart;
		if(whoStart == false)
		{
			buttons[FourInARow.row-1][FourInARow.col/2].setBackground(Color.red);
			FourInARow.avilabelPlaces[FourInARow.col/2]--;
			FourInARow.board[FourInARow.row -1][FourInARow.col/2] = new Point("r");
			buttons[FourInARow.row-2][FourInARow.col/2].addActionListener(this);
			turn = true;
		}
	}
	

	private void whoStart() {
		if(type == true)
		{
			select_panel.remove(easy);
			select_panel.remove(medium);
			select_panel.remove(hard);
			select_panel.remove(sumbitLevel);
			
		}
	Player2Game.setText("CPU start");
	Player1Game.setText("I start");
	select_panel.add(Player1Game);
	select_panel.add(Player2Game);
	select_panel.add(sumbitStart);
	frame.add(select_panel);
	frame.repaint();
	frame.validate();		
	}

	private void levelSelection() {
		select_panel.remove(Player1Game);
		select_panel.remove(Player2Game);
		select_panel.remove(sumbitType);
		select_panel.repaint();
		select_panel.validate();
		if(type == true)
		{
			select_panel.add(easy);
			select_panel.add(medium);
			select_panel.add(hard);
			select_panel.add(sumbitLevel);
			frame.add(select_panel);
			frame.repaint();
			frame.validate();
		}
		else
			startGame();
	}

	private void startGame() 
		{
			frame.remove(select_panel);
			select_panel.repaint();
			frame.add(button_panel);
			frame.repaint();
			frame.validate();
			turn = whoStart;
			if(whoStart == false)
			{
				buttons[FourInARow.row-1][FourInARow.col/2].setBackground(Color.red);
				FourInARow.avilabelPlaces[FourInARow.col/2]--;
				FourInARow.board[FourInARow.row -1][FourInARow.col/2] = new Point("r");
				buttons[FourInARow.row-2][FourInARow.col/2].addActionListener(this);
				turn = true;
			}
		}		
			
	}

	
