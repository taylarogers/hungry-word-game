package typingTutor;

public class Score {
	private int missedWords;
	private int caughtWords;
	private int gameScore;
	
	/** 
	This is a constructor to begin all values at 0
	*/
	Score() {
		missedWords=0;
		caughtWords=0;
		gameScore=0;
	}
		
	/**
	This returns the number of missed words
	*/
	synchronized public int getMissed() {
		return missedWords;
	}

	/** 
	This returns the number of caught words
	*/
	synchronized public int getCaught() {
		return caughtWords;
	}
	
	/** 
	This returns the total of the game
	*/
	synchronized public int getTotal() {
		return (missedWords+caughtWords);
	}

	/** 
	This returns the player's score
	*/
	synchronized public int getScore() {
		return gameScore;
	}
	
	/** 
	This increments the number of missed words
	*/
	synchronized public void missedWord() {
		//System.out.println("Literally how have I missed a word - you aren't even running.");
		missedWords++;
	}

	/** 
	This increments the number of caught words and the score of the game
	*/
	synchronized public void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}

	/** 
	This resets all score values
	*/
	synchronized public void reset() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
	}
}
