package cushionn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**Keeps track of player's score and whether score has changed in last round*/

public class Scores 
{
	private int score;
	private boolean updated;
	private VectorDouble position;
	private String playerName;
	private JLabel playerLabel;
	private JTextField playerText;
	private JPanel myPanel;
	private Color color;
	
	public Scores()
	{
		score = 0;
		updated = false;		
	}
	public Scores(double x, double y, String playerName, JPanel myPanel, Color color)
	{
		this.position = new VectorDouble(x,y);
		this.score = 0;
		this.updated = false;
		this.playerName = playerName;
		this.myPanel = myPanel;
		this.color = color;
		
		//Label
		playerLabel = new JLabel(playerName);
		playerLabel.setLocation((int)x,(int)y);
		playerLabel.setSize(100, 40);
		playerLabel.setForeground(color);
		playerLabel.setFont(new Font("serif",Font.BOLD,30));
		myPanel.add(playerLabel);
		
		//TextField
		playerText = new JTextField(score+"");
		playerText.setLocation((int)x + playerLabel.getWidth(),(int)y);
		playerText.setSize(40, 40);
		playerText.setEditable(false);
		playerText.setHorizontalAlignment(JTextField.CENTER);
		playerText.setFont(new Font("serif",Font.BOLD,24));
		myPanel.add(playerText);
		
	}
	public void increment()
	{		
			score++;	
			updated = true;
			playerText.setText(score+"");
	}	
	
	public void setScoresChanged(boolean isChanged)
	{
		updated = isChanged;
	}
	
	boolean isScoresChanged()
	{
		return updated;
	}	
	
	public void setScores(int s)
	{
		score = s;
	}
	
	int getScores()
	{
		return score;
	}
	public VectorDouble getPosition()
	{
		return position;
	}
	public String getName()
	{
		return playerName;
	}
	
}//Class Score
