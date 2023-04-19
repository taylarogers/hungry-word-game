package typingTutor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
		private AtomicBoolean done ; //REMOVE
		private AtomicBoolean started ; //REMOVE
		private AtomicBoolean won ; //REMOVE
		private AtomicBoolean calc ; 

		private FallingWord[] words;
		private FallingWord hungryWord;
		private int noWords;
		private final static int borderWidth=25; //appearance - border
		
		/** 
		This is the constructor
		*/
		GamePanel(FallingWord[] words, FallingWord word, int maxY, AtomicBoolean d, AtomicBoolean s, AtomicBoolean w) 
		{
			this.words=words; //shared word list
			hungryWord = word;
			noWords = words.length; //only need to do this once
			done=d; //REMOVE
			started=s; //REMOVE
			won=w; //REMOVE
		}

		/** 
		This sets the reference to the hungry word
		*/
		public void setHungryWord(FallingWord hungry)
		{
			hungryWord = hungry;
		}

		/**
		This repaints everything on the screen
		*/
		public void paintComponent(Graphics g) {
		    int width = getWidth()-borderWidth*2;
		    int height = getHeight()-borderWidth*2;
		    g.clearRect(borderWidth,borderWidth,width,height);//the active space
		    g.setColor(Color.pink); //change colour of pen
		    g.fillRect(borderWidth,height,width,borderWidth); //draw danger zone

		    g.setColor(Color.black);
		    g.setFont(new Font("Arial", Font.PLAIN, 26));

			// Set width and height for each word on screen to check for collisions
			for (int i=0;i<noWords;i++)
			{	    	
		    	words[i].setWidth(g.getFontMetrics().stringWidth(words[i].getWord()));
				words[i].setHeight(g.getFontMetrics().getHeight());	
		    }

		   // Draw the words
		    if (!started.get()) { // Game has not started
		    	g.setFont(new Font("Arial", Font.BOLD, 26));
				g.drawString("Type all the words before they hit the red zone,press enter after each one.",borderWidth*2,height/2);	
		    	
		    }
		    else if (!done.get()) {
				// Draw each falling word
		    	for (int i=0;i<noWords;i++)
				{	    	
		    		g.drawString(words[i].getWord(),words[i].getX()+borderWidth,words[i].getY());	
		    	}

				// Draw hungry word
				g.setColor(Color.green); //change colour of pen 
				g.drawString(hungryWord.getWord(), hungryWord.getX(), hungryWord.getY()); 

				// Draw border
		    	g.setColor(Color.lightGray); //change colour of pen
		    	g.fillRect(borderWidth,0,width,borderWidth);
		   }
		   else { if (won.get()) { // Player beat the game
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Well done!",width/3,height/2);	
		   } else { // Player lost the game
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Game over!",width/2,height/2);	
		   }
		   }
		}
		
		/** 
		This returns a valid random x-value for the falling words
		*/
		public int getValidXpos() {
			int width = getWidth()-borderWidth*4;
			int x= (int)(Math.random() * width);
			return x;
		}
		
		/** 
		This is what is run when thread is started
		*/
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(10); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		}

	}


