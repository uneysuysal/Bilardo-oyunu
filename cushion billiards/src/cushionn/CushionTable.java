package cushionn;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.EventListener;

// Class defining the billiards table and its aggregation of billiards balls.


public class CushionTable extends JPanel implements EventListener
{
    private double[] dimTable, dimCushion, dimBorder, dimFloor;
    private double borderCorner, borderWidth, cushionWidth, floorWidth, radius, mass,gap,cueStickLength,pullDistance;
    private int ppi;//pixels per inch
    private Ball whiteball, redball, yellowball;
    /** current ball being played, can be white or yellow*/
    private Ball currentBall;
    /** keeps track of the other ball which isn't played, can be yellow or white (is the opposite color of currentBall */
    private Ball otherBall;  
    private BallPath path;
    private Cue cueStick;    
    
    private ArrayList<Ball> balls = new ArrayList<>();
    private Color felt, border, floor, edge, mark;
    int mouseX,mouseY;
    boolean mouseDown,showCue;     
    boolean isSwitchPlayers = false;        
    /**Keeps track of white player's Scores*/
    Scores whiteScores;
    /**Keeps track of yellow player's Scores*/
    Scores yellowScores;
    /**Keeps track of current player's Scores (white or yellow)*/
    Scores currentScores;
    int Scores = 0;//players Scores
   
    
    /**
     * Default constructor draws all field values from BilliardConstants
     */

    public CushionTable() 
    {
        borderCorner   = BilliardsConstant.BORDER_CORNER;
        borderWidth    = BilliardsConstant.BORDER_WIDTH;
        cushionWidth   = BilliardsConstant.CUSHION_WIDTH;
        floorWidth     = BilliardsConstant.FLOOR_WIDTH;
        radius         = BilliardsConstant.BALL_DIAMETER / 2;
        mass		   = BilliardsConstant.BALL_MASS;
        gap			   = BilliardsConstant.GAP;
        cueStickLength = BilliardsConstant.CUE_LENGTH;
        pullDistance   = 0;
        mouseDown      = false;
        showCue 	   = false;

        dimTable = BilliardsConstant.TABLE_DIMENSION;
        dimCushion = new double[]{dimTable[0]   + 2*cushionWidth, dimTable[1]   + 2*cushionWidth};
        dimBorder  = new double[]{dimCushion[0] + 2*borderWidth , dimCushion[1] + 2*borderWidth};
        dimFloor   = new double[]{dimBorder[0]  + 2*floorWidth  , dimBorder[1]  + 2*floorWidth};
        
        cueStick = new Cue();
        whiteball  = new Ball(dimTable[0] / 3 , dimTable[1] /2  ,radius,mass, BilliardsConstant.WHITE);
        redball    = new Ball(dimTable[0] *2/3, dimTable[1] *2/5,radius,mass, BilliardsConstant.RED);
        yellowball = new Ball(dimTable[0] *2/3, dimTable[1] *3/5,radius,mass, BilliardsConstant.YELLOW);
        
        currentBall = whiteball; //white starts first        
        currentBall.setCurrentBall(true);
        otherBall = yellowball;
       
        path = new BallPath(currentBall.getPosition(),0,30);
        
        balls.add(whiteball);
        balls.add(redball);
        balls.add(yellowball);              
        
        whiteball.setVelocity(0,0);
        redball.setVelocity(0,0);
        yellowball.setVelocity(0,0);            
        
        whiteScores   = new Scores((dimTable[0]* 2/7)*ppi,(dimTable[1]* 1/15)*ppi,"Player1",this, Color.WHITE);
        yellowScores  = new Scores((dimTable[0]* 5/7)*ppi,(dimTable[1]* 1/15)*ppi,"Player2",this, Color.YELLOW);
        currentScores = whiteScores; //white starts first  
       
        felt   = BilliardsConstant.FELT;
        border = BilliardsConstant.BORDER;
        floor  = BilliardsConstant.FLOOR;
        edge   = BilliardsConstant.EDGE;
        mark   = BilliardsConstant.MARK;
        
        
        this.addMouseListener(new MouseListener()
        {	@Override
			public void mouseClicked(MouseEvent e) 	{}
        
			@Override
			public void mousePressed(MouseEvent e) 
			{
				mouseDown = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) 
			{
				mouseDown = false;				
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
        	
        });
        
        this.addMouseMotionListener(new MouseMotionListener()
        {
        	public void mouseMoved(MouseEvent e)
        	{
        		mouseX = e.getX();
        		mouseY = e.getY();
        	}
        	
        	public void mouseDragged(MouseEvent e) {}
        });//MouseMotionListener
        
    }


    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);        
        drawTable(g);
        drawBalls(g);   
        //Drawing cue stick         
        if(showCue)
        {        	
        	System.out.printf("Scores = %d\n", Scores);        	
        	    	
        	drawCue(g);
        }//if        
    }//paintComponent


    //--------------------------------------------------------------------------------
    /** Drawing Table */
    //--------------------------------------------------------------------------------
    public void drawTable(Graphics g)
    {    	
        int cushionEdge = (int) ((floorWidth + borderWidth) * ppi);
        int tableEdge   = (int) ((floorWidth + borderWidth + cushionWidth) * ppi);
        
        /* draw a filled rectangle for the floor surrounding the table border */
        g.setColor(floor);
        g.fillRect(0, 0, (int)(dimFloor[0] * ppi), (int)(dimFloor[1] * ppi));
        
        /* draw a filled round rect for the rail border surrounding the cushions */
        g.setColor(border);
        g.fillRoundRect((int)(floorWidth * ppi)  , (int)(floorWidth * ppi),
                		(int)(dimBorder[0] * ppi), (int)(dimBorder[1] * ppi),
                		(int)(borderCorner * ppi), (int)(borderCorner * ppi));

        /* draw a filled rectangle for the play area plus the cushions */
        g.setColor(felt);
        g.fillRect(cushionEdge, cushionEdge,
                   (int)(dimCushion[0] * ppi),
                   (int)(dimCushion[1] * ppi));

        /* draw a rectangle outline for the play area */
        g.setColor(edge);
        g.drawRect(tableEdge, tableEdge,
                   (int)(dimTable[0] * ppi),
                   (int)(dimTable[1] * ppi));
               
        /* draw a line segment connecting the play field corners to each border corner */
        g.drawLine(cushionEdge, cushionEdge, tableEdge, tableEdge);
        g.drawLine(cushionEdge + (int)dimCushion[0] * ppi, cushionEdge,
                   tableEdge + (int)dimTable[0] * ppi, tableEdge);
        g.drawLine(cushionEdge, cushionEdge + (int)dimCushion[1] * ppi,
                   tableEdge, tableEdge + (int) dimTable[1] * ppi);
        g.drawLine(cushionEdge + (int)dimCushion[0] * ppi, cushionEdge + (int)dimCushion[1] * ppi,
                   tableEdge + (int)dimTable[0] * ppi, tableEdge + (int)dimTable[1] * ppi);

        /* draw filled circles to mark the edges */
        g.setColor(mark);
        int markRadius = (int)(cushionWidth / 2 * ppi); 
        int marks = 8;
        int sideMarks = 3;
        
        for (int i = 0; i <= marks; i++) 
        {   
        	//Top border markers:
        	g.fillOval((int)(cushionEdge + (cushionWidth/2 + i*dimTable[0]/marks )*ppi),        			   
        			   (int)(cushionEdge - markRadius - borderWidth/2),
        			   markRadius, markRadius);
            //Bottom border markers:                                 
            g.fillOval((int)(cushionEdge + (cushionWidth/2 + i*dimTable[0]/marks )*ppi),
            		   (int)(cushionEdge + (dimTable[1] + 2*cushionWidth)* ppi + borderWidth/2),                      
            		   markRadius, markRadius);
        }
        
        for (int i = 1; i <= sideMarks; i++) 
        {
        	//Left border markers:
        	g.fillOval((int)(cushionEdge - markRadius - borderWidth/2),        			   
     			       (int)(cushionEdge + (i*dimTable[1]/(sideMarks+1))*ppi),
     			       markRadius, markRadius);
        	//Right border markers:
        	g.fillOval((int)(cushionEdge + (2*cushionWidth + dimTable[0])*ppi + borderWidth/2),        			   
  			       	   (int)(cushionEdge + (i*dimTable[1]/(sideMarks+1))*ppi),
  			           markRadius, markRadius);        	
        }
    }//drawTable
    
    
    //--------------------------------------------------------------------------------
    /* Drawing Balls */
    //--------------------------------------------------------------------------------
    public void drawBalls(Graphics g)
    {        
    	int x, y, width, height;    	
    	width  = (int)(2*radius*ppi);
    	height = (int)(2*radius*ppi);
    	
        for (Ball b: balls) 
        {   
        	x = (int)((floorWidth + borderWidth + cushionWidth + b.getPosition().x-radius) * ppi);
        	y = (int)((floorWidth + borderWidth + cushionWidth + b.getPosition().y-radius) * ppi);        	
        	
        	// draw a filled circle for each ball 
            g.setColor(b.getColor());
            g.fillOval(x, y, width, height);            
            // draw a circle outline for each ball 
            g.setColor(edge);
            g.drawOval(x, y, width, height);
        }    	
    }//drawBalls
    
    
    //--------------------------------------------------------------------------------
    /* Drawing Cue Stick */
    //--------------------------------------------------------------------------------
    public void drawCue(Graphics g)
    {
    	double dx = mouseX- ((floorWidth + borderWidth + cushionWidth+currentBall.getPosition().x) * ppi);
    	double dy = mouseY - ((floorWidth + borderWidth + cushionWidth+currentBall.getPosition().y) * ppi);
    
    	double angle = Math.atan2(dy, dx) - Math.toRadians(90);
    	cueStick.setAngle(angle);
            	
		cueStick.setPosition(( floorWidth + borderWidth + cushionWidth + currentBall.getPosition().x - BilliardsConstant.CUE_DIAMETER*.5 ) ,
			(pullDistance + floorWidth + borderWidth +cushionWidth + currentBall.getPosition().y - radius + gap));	
            
    	int[] cuesX = new int[]{(int)(cueStick.getX()*ppi),
    					(int)((cueStick.getX() + BilliardsConstant.CUE_DIAMETER )*ppi),
    					(int)((cueStick.getX() +  1.5*BilliardsConstant.CUE_DIAMETER )*ppi),
    					(int)((cueStick.getX() -  BilliardsConstant.CUE_DIAMETER*.5 )*ppi)};
    	int[]cuesY = new int[]{(int)((cueStick.getY() + BilliardsConstant.CUE_DIAMETER*.5)*ppi),
    					(int)((cueStick.getY() + BilliardsConstant.CUE_DIAMETER*.5)*ppi),
    					(int)((cueStick.getY() + BilliardsConstant.CUE_DIAMETER*.5 + cueStickLength)*ppi),
    					(int)((cueStick.getY() + BilliardsConstant.CUE_DIAMETER*.5 + cueStickLength)*ppi)};
    
    	path.updateProp(currentBall.getPosition(), angle);
    	 path.draw(g,cushionWidth,floorWidth,borderWidth);
                 
    	AffineTransform transform = new AffineTransform();
    
    	transform.rotate(angle,(floorWidth + borderWidth + cushionWidth + currentBall.getPosition().x )*ppi,(floorWidth + borderWidth + cushionWidth + currentBall.getPosition().y  )*ppi);
    	Graphics2D g2d = (Graphics2D)g;
    	g2d.transform(transform);
		g2d.setColor(Color.BLUE);
		g2d.fillArc((int)(cueStick.getX()*ppi) , 
			   (int)(cueStick.getY()*ppi),
			   (int)(BilliardsConstant.CUE_DIAMETER*ppi) ,(int)(BilliardsConstant.CUE_DIAMETER*ppi) ,0 ,180);
	
		g2d.setColor(new Color(0xDBB84D));
		g2d.fillPolygon(cuesX, cuesY, 4);    	
    }//drawCue
    
        

    public void update()
    {
    	
    	
    	//who reach 50 points game over !!!!
    	if(currentScores.getScores()==50&&whiteball.isCurrentBall()) 
    	{
    		JOptionPane.showMessageDialog(null, "Game Over\nPlayer1 wins");
    		 yellowScores.setScores(0);
             whiteScores.setScores(0);
            // Go back to homescreen
            AppContainer.changePanel(new Menu());	
           
    	}
    	
    	else if(currentScores.getScores()==50&&yellowball.isCurrentBall())
    	{
    		JOptionPane.showMessageDialog(null, "Game Over\nPlayer2 wins");
    		 yellowScores.setScores(0);
             whiteScores.setScores(0);
            // Go back to homescreen
            AppContainer.changePanel(new Menu());		
    		
    	}
    	//Mouse down even to hit ball    
		if(mouseDown && pullDistance < BilliardsConstant.MAX_PULL && showCue)
		{
			pullDistance += BilliardsConstant.PULL_RATE;
			cueStick.setPower(pullDistance);
			
		}else if(!mouseDown && pullDistance > 0)
		{
			pullDistance -= 3; 
		}
		if(pullDistance < 0)
		{//cue will hit ball
			Physics.hitBall(cueStick,currentBall);
			pullDistance = 0;
			
			isSwitchPlayers = true; //reset 			
			currentScores.setScoresChanged(false);//reset			
			
			redball.setIsHit(false);//reset			
			whiteball.setIsHit(false);//reset
			yellowball.setIsHit(false);//reset
		}
		
		//Check if balls are at rest
    	if (isBallsStopped())		
		{
    		if(currentBall == whiteball)
    		{
    			otherBall = yellowball;
    		}else
    		{
    			otherBall = whiteball;
    		}
    		
    		if(redball.isHit() && otherBall.isHit())
    		{    		
    			currentScores.increment();//Scores updated (flag set internally)
    			Scores++;//will be replaced by Scores class
    			isSwitchPlayers = false; //reset
    			redball.setIsHit(false);//reset
    			otherBall.setIsHit(false);//reset
    		}else
    		{     			
    			currentScores.setScoresChanged(false);
    		}
			showCue = true;			

//-------------------------------------------------------------------
			if (isSwitchPlayers && !currentScores.isScoresChanged())
		    {
		    	switchPlayers();	
		    	isSwitchPlayers = false; //reset		    	
    		}//if
//-------------------------------------------------------------------
		}
		else
		{
			showCue = false;
		}
		
    	//Update each ball
    	for(int i = 0; i < balls.size(); i++)
    	{
    		Physics.checkCusionCollision(balls.get(i),this);
    		
    		Ball ball1, ball2;
    		for(int j = i + 1; j < balls.size(); j++)
    		{    			
    			ball1 = balls.get(i);
    			ball2 = balls.get(j);
    			
    			if (Physics.checkBallCollision(ball1, ball2))
    			{//had a collision
    				if (ball1.isCurrentBall() == true)
    				{     					 
    					ball2.setIsHit(true);
    				}
    				else if (ball2.isCurrentBall() == true)
    				{        					
    					ball1.setIsHit(true);
    				}    				
    			}
    		}//for j
    		balls.get(i).update();
    	}//for i
    	
    	repaint();
    }//update
    
    
    public boolean isBallsStopped()
    {
    	return ( whiteball.getVelocity().x  == 0 && whiteball.getVelocity().y   == 0 &&
				 yellowball.getVelocity().x == 0 && yellowball.getVelocity().y  == 0 &&
				 redball.getVelocity().x    == 0 && redball.getVelocity().y     == 0);    	
    }
    
    
    public void switchPlayers()
    {	//switch players (balls)
		if (whiteball.isCurrentBall())
		{
			whiteball.setCurrentBall(false);
			yellowball.setCurrentBall(true);			
			currentBall = yellowball;	
			
			currentScores = yellowScores;
		}
		else//ball is yellow
		{
			whiteball.setCurrentBall(true);
			yellowball.setCurrentBall(false);				
			currentBall = whiteball;
			
			currentScores = whiteScores;
		}	
    }//switchPlayers
    
    
    public int getPixelsPerInch() 
    {
        return ppi;
    }

    public void setPixelsPerInch(int pixelsPerInch) 
    {
        if (pixelsPerInch < 1)
            throw new IllegalArgumentException("Pixels per inch must be positive.");
        this.ppi = pixelsPerInch;
    }
    
    //Get Table Dimensions
    public double getTableWidth()
    {
    	return dimTable[0];
    }
    
    public double getTableHeight()
    {
    	return dimTable[1];
    }

    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension((int)(dimFloor[0] * ppi), (int)(dimFloor[1] * ppi));
    }    
}