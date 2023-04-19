package typingTutor;

/**
 * This class control the movement of the Hungry Word across the screen
 *
 * @author Tayla Rogers
 * @since 28-08-2022
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends Thread
{
    private FallingWord myWord;
    FallingWord[] words;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch;

    /** 
    This is a constructor to set the word
    */
    HungryWordMover(FallingWord word) 
    {
		myWord = word;
	}
	
    /** 
    This is aconstructor to set all of the values
    */
	HungryWordMover(FallingWord word, FallingWord[] words, WordDictionary dict, Score score, CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) 
    {
		this(word);
        this.words = words;
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}

    /** 
    This is what is run when the thread is started
    */
    public void run()
    {
        // Get all waiting threads
		try 
        {
			System.out.println(myWord.getWord() + " waiting to start (Hungry)" );
			startLatch.await();
		} 
        catch (Exception e1) 
        {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start

        System.out.println(myWord.getWord() + " started (Hungry)" );

        while (!done.get()) 
        {				
			// Animate the word
			while (!myWord.dropped() && !done.get()) 
            {
                    // Hungry word must sleep after reset
                    if (myWord.getReset() == true)
                    {
                        try
                        {
                            sleep(myWord.getRandomSleep());
                        }
                        catch (Exception e)
                        {
                            System.out.println("Could not sleep.");
                        }
                    }

                    // Move word
				    myWord.move(10);

                    //System.out.println("moved// X: " + Integer.toString(myWord.getX()) + " Y: " + Integer.toString(myWord.getY()));

                    // Check for collisions to "eat" falling words
                    for (int i = 0; i < words.length; i++)
                    {
                        if(myWord.getX() < words[i].getX() + words[i].getWidth() && myWord.getX() + myWord.getWidth() > words[i].getX() && myWord.getY() < words[i].getY() + words[i].getHeight() && myWord.getY() + myWord.getHeight() > words[i].getY())
                        {
                            words[i].resetWord();
                            score.missedWord();
                        }
                    }

                    // Word speed
					try 
                    {
						sleep(myWord.getSpeed());
					} 
                    catch (InterruptedException e) 
                    {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};	

					while(pause.get()&&!done.get()) {};
			}

            // Check if word has been missed
			if (!done.get() && myWord.dropped()) 
            {
                //System.out.println("HungryWordMover");
				score.missedWord();
				myWord.resetWord();
			}

			myWord.resetWord();
		}
    }
}