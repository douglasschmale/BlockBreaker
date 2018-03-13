package BlockGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	public static int score = 0;
	private int totalBricks = 21;
	private int games = 0;
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 930;
	
	private int ballposX = 360;
	private int ballposY = 1050;
	private int ballXdir = -3;
	private int ballYdir = -6;
	
	private boolean left = false;
	private boolean right = false;
	
	private MapGenerator map;
	
//	public void setScore(int score) {
//		this.score = score;
//	}
//	public int getScore() {
//		return this.score;
//	}
	
	public GamePlay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1,2092,1792);
		
		//map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 9, 1792);
		g.fillRect(0, 0, 2092, 9);
		g.fillRect(2091, 0, 9, 1792);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 40));
		g.drawString("score: "+ score, 1770, 50);
		
		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX,1700,300,20);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX,ballposY,60,60);
		
		// the second ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX,ballposY,60,60);
		
		
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.drawString("You've won! score: "+ score, 570, 900);
			
			g.drawString("Press enter to restart.", 570, 1000);
			g.setFont(new Font("serif", Font.BOLD, 40));
			games ++;
		}
		
		if(ballposY > 1750) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.drawString("Game Over. score: "+ score, 570, 900);
			
			g.drawString("Press enter to restart.", 570, 1000);
			g.setFont(new Font("serif", Font.BOLD, 40));
			games = 0;
		}
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		//paddle collision detection logic
		if(play) {
			
			if (left && ballXdir > 0){
				if(new Rectangle(ballposX, ballposY, 60,60).intersects(new Rectangle(playerX, 1700, 100, 20))) {
					ballYdir = -ballYdir;
					ballXdir = -ballXdir;
				}
				else if(new Rectangle(ballposX, ballposY, 60,60).intersects(new Rectangle(playerX, 1700, 300, 20))) {
					ballYdir = -ballYdir;
					}
				}
					
			else if (right && ballXdir < 0) {
				if(new Rectangle(ballposX, ballposY, 60,60).intersects(new Rectangle(playerX+200, 1700, 100, 20))) {
					ballYdir = -ballYdir;
					ballXdir = -ballXdir;
					}
				else if(new Rectangle(ballposX, ballposY, 60,60).intersects(new Rectangle(playerX, 1700, 300, 20))) {
					ballYdir = -ballYdir;
					}
				}
			else if(new Rectangle(ballposX, ballposY, 60,60).intersects(new Rectangle(playerX, 1700, 300, 20))) {
				ballYdir = -ballYdir;
			}

			//logic to detect intersections of the ball with individual bricks:
			A: for(int i =0; i < map.map.length; i++) {
				for(int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 150;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {

							map.setBrickValue(0, i, j);
							totalBricks--;
							score+= 5;

							// logic if ball hits the side of the bricks
							if(ballposX + 59 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;	
							}
							//logic if ball hits the bottom or top of the bricks
							else {
								ballYdir = -ballYdir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			//logic for wall and ceiling collisions
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 2050) {
				ballXdir = -ballXdir;
			}
			
//			if(score >= 25) {
//				ballXdir += 1;
//				ballYdir += 2;
//			}
//			if(score >= 50) {
//				delay -= 1;
//			}
//			if(score >= 75) {
//				delay -= 1;
//			}
//			if(score >= 90) {
//				delay -= 1;
//			}
//		}
		
		repaint();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 1800) {
				playerX = 1800;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 360;
				ballposY = 1050;
				ballXdir = -3;
				ballYdir = -6;
				playerX = 930;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX+=60;
		left = false;
		right = true;
	}
	public void moveLeft() {
		play = true;
		playerX-=60;
		left = true;
		right = false;
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
