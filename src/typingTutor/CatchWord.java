package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static FallingWord hungryWord; // hungry word
	private static int noWords; //how many
	private static Score score; //user score
	
	/** 
	This is the constructor
	*/
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	/** 
	This sets the falling words
	*/ 
	public static void setWords(FallingWord[] wordList, FallingWord word) {
		words=wordList;	
		noWords = words.length;
		hungryWord = word;
	}

	/**
	This sets the hungry word
	*/
	public static void setHungryWord(FallingWord hungry)
	{
		hungryWord = hungry;
	}
	
	/**
	This sets the score reference
	*/
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	/**
	This sets the done and pause atomic variables
	*/
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}

	/** 
	This is what is run when the thread is started
	*/
	public void run() {
		// Set initial values
		int i=0;
		int lowestValue = -1;
		int lowestIndex = -1;

		while (i<noWords) {		
			while(pause.get()) {};

			synchronized(this)
			{
				// Check for matching word with lowest y-value
				if (target.equals(words[i].getWord())) {
					if (words[i].getY() > lowestValue)	
					{
						lowestValue = words[i].getY();
						lowestIndex = i;
					}
					//FallingWord.increaseSpeed();
				}
			}
		   i++;
		}

		// Check if it matches hungry word
		if (target.equals(hungryWord.getWord()))
		{
			hungryWord.matchWord();
			System.out.println( " score! '" + target); //for checking
			score.caughtWord(target.length());
		}
		else if (!(lowestIndex == -1)) // Resets word that was the lowest
		{
			words[lowestIndex].matchWord();
			System.out.println( " score! '" + target); //for checking
			score.caughtWord(target.length());
		}
		
	}
}
