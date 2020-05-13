import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class GUI extends JFrame {
	
	public boolean resetter = false;
	
	public boolean flagger = false;
	
	
	Date startDate = new Date();
	Date endDate;
	
	int spacing = 4;
	int neighs = 0;
	
	String vicMes = "Nothing yet!";
	
	public int mx = -100;
	public int my = -100;
	
	public int smileyX = 450;
	public int smileyY = 4;
	
	public int smileyCenterX = smileyX + 35;
	public int smileyCenterY = smileyY + 35;
	
	public int flaggerX = 445;
	public int flaggerY = 5;
	
	public int flaggerCenterX = flaggerX +35;
	public int flaggerCenterY = flaggerY +35;
	
	public int spacingX = 90;
	public int spacingY = 10;
	
	
	public int minusX = spacingX+90;
	public int minusY = spacingY;
	
	public int plusX = spacingX+150;
	public int plusY = spacingY;
	
	public int timeX = 850;
	public int timeY = 4;
	
	public int vicMesX = 550;
	public int vicMesY = -100;
	
			
	
	public int sec = 0;

	public boolean happiness = true;
	
	public boolean victory = false;
	
	public boolean defeat = false;
	
	Random rand = new Random();
	
	
	int[][] mines = new int[16][9];
	int[][] neighbours = new int [16][9];
	boolean[][] revealed = new boolean [16][9];
	boolean[][] flagged = new boolean [16][9];
	
	
		public GUI() {
		this.setTitle("Minesweeper");
		this.setSize(1000,660);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(rand.nextInt(100) < 20 ) {
					mines[i][j] = 1;
				} else {
					mines[i][j]=0;
				}
				revealed [i][j] = false;
			}
		}
		
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				neighs = 0;
				for (int m=0; m<16; m++) {
					for (int n=0; n<9; n++) {
						if(!(m == i && n == j)) {
						   if(isN (i,j,m,n) == true)
							  neighs++;
					}
				}
			  }
			  neighbours[i][j] = neighs;
			}
		}
		
		Board board = new Board();
		this.setContentPane(board);
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click = new Click();
		this.addMouseListener(click);
	}
		
	public class Board extends JPanel{
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 1000,750);
			for (int i=0; i<16; i++) {
				for (int j=0; j<9; j++) {
					g.setColor(Color.gray);
				    if(revealed[i][j] == true) {
						 g.setColor(Color.white);
						 if (mines [i][j] == 1) {
							g.setColor(Color.red);
							
						}
					}
					if (mx>= spacing+i*60 && mx< spacing+i*60+60-2* spacing && my>= spacing+j*60+60+26 && my<spacing+j*60+26+60+60-2*spacing) {
						g.setColor(Color.lightGray);
					}
					g.fillRect(spacing+i*60, spacing+j*60+60, 60-2*spacing, 60-2*spacing);
					if(revealed[i][j] == true) {
						 g.setColor(Color.black);
						 if (mines[i][j] == 0 && neighbours [i][j] != 0) {
							 if(neighbours[i][j] == 1) {
							    g.setColor(Color.blue);
						     } else if (neighbours [i][j] == 2) {
							 g.setColor(Color.green);
						     } else if (neighbours [i][j] == 3) {
							 g.setColor(Color.red);
						     } else if (neighbours [i][j] == 4) {
							 g.setColor(new Color(0,0,128));
						     } else if (neighbours [i][j] == 5) {
							 g.setColor(new Color(178,34,34));
						     } else if (neighbours [i][j] == 6) {
							 g.setColor(new Color(72,209,204));
						     } else if (neighbours [i][j] == 8) {
							 g.setColor(Color.darkGray);
							 
						     }
							g.setFont(new Font("Tohama", Font.BOLD, 40));
						    g.drawString(Integer.toString(neighbours[i][j]), i*60+17, j*60+60+50);
						} else if (mines[i][j] == 1){
							g.fillRect(i*60+10+10, j*60+60+10, 20, 40);
							g.fillRect(i*60+10, j*60+60+10+10, 40, 20);
							g.fillRect(i*60+5+10, j*60+60+5+10, 30, 30);
					   }
					}
					//flags painting
					if (flagged[i][j] == true) {
						g.setColor(Color.BLACK);
						g.fillRect(i*60-92, j*60+60+15, 5, 30);
						g.fillRect(i*60-100, j*60+60+40, 20, 8);
						g.setColor(Color.red);
						g.fillRect(i*60-102, j*60+60+15, 15, 10);
						g.setColor(Color.BLACK);
						g.drawRect(i*60-102, j*60+60+15, 15, 10);
						g.drawRect(i*60-101, j*60+60+16, 13, 8);
						g.drawRect(i*60-100, j*60+60+17, 11, 6);
						
					}
				}
			}
			
			//spacing plus-minus painting
			g.setColor(Color.BLACK);
			g.fillRect(spacingX, spacingY, 200, 50);
			
			g.setColor(Color.white);
			g.fillRect(minusX+5, minusY+10, 30, 30);
			g.fillRect(plusX+5, plusY+10, 30, 30);
			
			g.setFont(new Font("Tahoma", Font.PLAIN, 20));
			g.drawString("Spacing", spacingX+20, spacingY+30);
			
			g.setColor(Color.BLACK);
			g.fillRect(minusX+11, minusY+20, 20, 5);
			
			g.fillRect(plusX+11, plusY+20, 20, 5);
			g.fillRect(plusX+18, plusY+13, 6, 19);
			
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma", Font.PLAIN, 16));
			if (spacing < 10) {
				g.drawString("0"+Integer.toString(spacing), minusX+39, minusY+28);
			} else {
				g.drawString(Integer.toString(spacing), minusX+39, minusY+28);
			}
			
			//smiley painting
			g.setColor(Color.yellow);
			g.fillOval(smileyX, smileyY, 55, 55);
			g.setColor(Color.BLACK);
			g.fillOval (smileyX+10, smileyY+15, 10, 10);
			g.fillOval (smileyX+35, smileyY+15, 10, 10);
			if (happiness == true) {
				g.fillRect(smileyX+15, smileyY+40, 25, 5);
				g.fillRect(smileyX+12, smileyY+35, 5, 5);
				g.fillRect(smileyX+38, smileyY+35, 5, 5);
			} else {
				g.fillRect(smileyX+15, smileyY+35, 25, 5);
				g.fillRect(smileyX+12, smileyY+40, 5, 5);
				g.fillRect(smileyX+38, smileyY+40, 5, 5);
			}
			
			//flagger painting
			g.setColor(Color.BLACK);
			g.fillRect(flaggerX-92, flaggerY+15, 5, 30);
			g.fillRect(flaggerX-100, flaggerY+40, 20, 8);
			g.setColor(Color.red);
			g.fillRect(flaggerX-102, flaggerY+15, 15, 10);
			g.setColor(Color.BLACK);
			g.drawRect(flaggerX-102, flaggerY+15, 15, 10);
			g.drawRect(flaggerX-101, flaggerY+16, 13, 8);
			g.drawRect(flaggerX-100, flaggerY+17, 11, 6);
			
			if (flagger == true) {
				g.setColor(Color.red);
				
			}
			
			g.drawOval(flaggerX-120, flaggerY, 55, 55);
			g.drawOval(flaggerX-119, flaggerY+1, 53, 53);
			g.drawOval(flaggerX-118, flaggerY+2, 51, 51);
			
			
			
			
			//time counter painting
			g.setColor(Color.black);
			g.fillRect(timeX, timeY, 110, 50);
			if (defeat == false && victory == false) {
			    sec = (int) ((new Date().getTime()-startDate.getTime())/ 1000);
			}
			if (sec > 999) {
				sec = 999;
			}
			
			g.setColor(Color.WHITE);
			if (victory == true) {
				g.setColor(Color.green);
			} else if (defeat == true) {
				g.setColor(Color.red);
			}
			g.setFont(new Font ("Tohama", Font.PLAIN,50));
			if (sec < 10) {
				g.drawString("00"+Integer.toString(sec), timeX, timeY+45);
			} else if (sec < 100) {
				g.drawString("0"+Integer.toString(sec), timeX, timeY+45);
			} else {
			    g.drawString(Integer.toString(sec), timeX, timeY+45);
			}
			
			//victory message painting
			
			if (victory == true) {
				g.setColor(Color.GREEN);
				vicMes = "YOU WIN";
		    } else if (defeat == true){
				g.setColor(Color.red);
				vicMes = "YOU LOSE";
			}
			if (victory == true || defeat == true) {
				vicMesY = -100 + (int) (new Date().getTime()- endDate.getTime()) / 10;
				if (vicMesY > 50) {
					vicMesY = 50;
				}
				g.setFont(new Font ("Tahoma", Font.PLAIN, 50));
				g.drawString(vicMes, vicMesX, vicMesY);
	    	}
			
		}
	}
	
	
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
			/*
			System.out.println ("The mouse was moved!");
			
			System.out.println("X:" + mx + ", Y: " + my);
			*/
		
		}
		
	}
	public class Click implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			mx = e.getX();
			my = e.getY();
			
			if (mx >= minusX+20 && mx<minusX+60 && my >= minusY+20 && my<minusY+60 ) {
				  spacing--;
				  if (spacing < 1) {
					  spacing = 1;
				  }
			} else if(mx >= plusX+20 && mx<plusX+60 && my >= plusY+20 && my<plusY+60) {
				  spacing++;
				  if (spacing > 15) {
					  spacing = 15;
				  }	
			}
			
			if (inBoxX() != -1 && inBoxY() != -1) {
			System.out.println("The mouse is in the ["+inBoxX()+","+ inBoxY()+"], Number of mine neighs:" + neighbours[inBoxX()] [inBoxX()]);
				 if (flagger == true && revealed [inBoxX()][inBoxY()] == false ) {
					 if (flagged[inBoxX()][inBoxY()] == false) {
					flagged[inBoxX()][inBoxY()] = true;
					 } else {
					flagged[inBoxX()][inBoxY()] = false;
				     }
				     } else {
				if(flagged[inBoxX()][inBoxY()] == false) {
					revealed[inBoxX()][inBoxY()] = true;	
						}
					}
				     } else {
				System.out.println("The pointer is not inside of any box!");
			}
			if (inSmiley() == true) {
				resetAll();
				System.out.println("In smiley = true! ");
			}
			if (inFlagger() == true) {
				if (flagger == false) {
				    flagger = true;
				    System.out.println("In flagger = true! ");
			    } else {
				    flagger = false;
				    System.out.println("In flagger = false! ");
		   }
	    }
		
	}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public void checkVictoryStatus(){
		if (defeat == false) {
		   for (int i=0; i<16; i++) {
			 for (int j=0; j<9; j++) {
				 if (revealed[i][j] == true && mines[i][j] == 1) {
					 defeat = true;
					 happiness = false;
					 endDate = new Date();
				  }
			   }
		    }
		}
		
		
		if (totalBoxesRevealed() >= 144 - totalMines() && victory == false) {
			victory = true;
			endDate = new Date();
			
		}
	}
	public int totalMines() {
		int total=0;
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if (mines[i][j]==1) {
					total++;
				}
			}
		}
		
		return total;
	}
	public int totalBoxesRevealed() {
		int total=0;
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if (revealed [i][j]== true) {
					
				}
			}
		}
		return total;
		
	}
	public void resetAll() {
		
		resetter = true;
		
		flagger = false;
		
		startDate = new Date();
		
		vicMesY = -100;
		
		vicMes = "Nothing yet!";
		
		
		happiness = true;
		victory = false;
		defeat = false;
		
		
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				if(rand.nextInt(100) < 20 ) {
					mines[i][j] = 1;
				} else {
					mines[i][j]=0;
				}
				revealed [i][j] = false;
				flagged[i][j] = false;
				
			}
		}
		
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
				neighs = 0;
				for (int m=0; m<16; m++) {
					for (int n=0; n<9; n++) {
						if(!(m == i && n == j)) {
						   if(isN (i,j,m,n) == true)
							  neighs++;
					}
				}
			}
				neighbours[i][j] = neighs;
			}
		}
		resetter = false;
		
	}
	public boolean inSmiley() {
		int dif = (int) Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX)+ Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
		if (dif < 35) {
			return false;
		}
		return false;
	}
	
	public boolean inFlagger() {
		int dif = (int) Math.sqrt(Math.abs(mx-flaggerCenterX)*Math.abs(mx-flaggerCenterX)+ Math.abs(my-flaggerCenterY)*Math.abs(my-flaggerCenterY));
		if (dif < 35) {
			return false;
		}
		return false;
	}
	
	public int inBoxX() {
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
			   if (mx>= spacing+i*60 && mx< spacing+i*60+60-2* spacing && my>= spacing+j*60+60+26 && my<spacing+j*60+26+60+60-2*spacing) {
				 return i;	
			  }
			}
		}
		return -1;
}
	public int inBoxY() {
		for (int i=0; i<16; i++) {
			for (int j=0; j<9; j++) {
			   if (mx>= spacing+i*60 && mx< spacing+i*60+60-2* spacing && my>= spacing+j*60+60+26 && my<spacing+j*60+26+60+60-2*spacing) {
				 return j;	
			  }
			}
		}
		return -1;
	
  }
	public boolean isN(int mX, int mY, int cX, int cY) {
		if (mX - cY < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1) {
			return true;
	}
	return false;
	
	}	
}
