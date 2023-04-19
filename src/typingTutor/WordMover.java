package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordMover extends Thread {
	private FallingWord myWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch; //so all can start at once
	
	/** 
	This is a constructor to set the word
	*/
	WordMover( FallingWord word) {
		myWord = word;
	}
	
	/** 
	This is a constructor to set all of the values
	*/
	WordMover( FallingWord word,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}
	
	/** 
	This is what is run when the thread is started
	*/
	public void run() {

		// Wait for falling words
		try {
			System.out.println(myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // Wait for other threads to start
		System.out.println(myWord.getWord() + " started" );
		while (!done.get()) {				
			// Animate the word
			while (!myWord.dropped() && !done.get()) {
					// Move word
				    myWord.drop(10);

					// Word speed
					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get()&&!done.get()) {};
			}

			// Check if word has been missed
			if (!done.get() && myWord.dropped()) {
				//System.out.println("WordMover - " + myWord.getWord());
				score.missedWord();
				myWord.resetWord();
			}
			myWord.resetWord();
		}
	}
	
}
