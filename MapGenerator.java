package BlockGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

public class MapGenerator {
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	public int rows;
	public int cols;
	//random logic for red block generation
	Random rand = new Random();
	public int n = rand.nextInt(10) + 1;
	//generate two arrays which hold x and y coordinates, respectively, for red block locations
	public int[] arrX = new int[n];
	public int[] arrY = new int[n];

	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		rows = row;
		cols = col;
		brickWidth = 1900/col;
		brickHeight = 450/row;
		//this for loop assigns values to the x and y red block coordinate arrays
			for (int s = 0; s < n; s++) {
			int x = rand.nextInt(rows);
			arrX[s] = x;
			int y = rand.nextInt(cols);
			arrY[s] = y;
		}
	}

	public void draw(Graphics2D g) {
		//for a random number of randomly placed brick: random number 1-10, random number 0-row, random number 0-col. loop randomNum times to produce [x,y] arrays. 
		//paint these bricks red initially.  Later make them either score double or last for two collisions.
	
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j] > 0) {
					
					g.setColor(Color.white);
					g.fillRect(j*brickWidth + 80, i* brickHeight + 150, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*brickWidth + 80, i* brickHeight + 150, brickWidth, brickHeight);
					
					for(int t=0; t<n; t++) {
						if(i==arrX[t] && j==arrY[t]) {
							g.setColor(Color.red);
							g.fillRect(j*brickWidth + 80, i* brickHeight + 150, brickWidth, brickHeight);
							
							g.setStroke(new BasicStroke(3));
							g.setColor(Color.black);
							g.drawRect(j*brickWidth + 80, i* brickHeight + 150, brickWidth, brickHeight);
						}
					}
				}
			}
		}
	}

	public void setBrickValue(int value, int row, int col) {
		for(int t=0; t<n; t++) {
			if(row==arrX[t] && col==arrY[t]) {
				GamePlay.score += 15;
			}
		}
		map[row][col] = value;
	}
//	public void superBrick(Graphics2D d, int row, int col) {
//			d.setColor(Color.red);
//			d.fillRect(col*brickWidth + 80, row*brickHeight + 150, brickWidth, brickHeight);
//		}	
//	}
}

