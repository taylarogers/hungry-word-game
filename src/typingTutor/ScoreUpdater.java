package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;

public class ScoreUpdater  implements Runnable {
	private Score score;
	JLabel caught;
	JLabel missed;
	JLabel scoreView;
	private AtomicBoolean done;
	private AtomicBoolean won;
	private int maxWords;

	/** 
	This is a constructor to set all of the values
	*/
	ScoreUpdater(JLabel c, JLabel m, JLabel s, Score score, 
			AtomicBoolean d, AtomicBoolean w, int max) {
        this.caught=c;
        missed = m;
        scoreView = s;
        this.score=score;
        done=d;
         won=w;
        maxWords=max;
    }
	
	/** 
	This is what is run when the thread is started
	*/
	public void run() {
        while (true) {    
				// Set the values of the score	
                caught.setText("Caught: " + score.getCaught() + "    ");
                missed.setText("Missed:" +  score.getMissed()+ "    " );
                scoreView.setText("Score:" + score.getScore()+ "    " );  //setText is thread safe (I think)
				if ((score.getMissed())>=3) {
					   // Game is completed when hit 3
		               caught.setText("Caught: " + score.getCaught() + "    ");
		               missed.setText("Missed:" +  score.getMissed()+ "    " );
		               scoreView.setText("Score:" + score.getScore()+ "    " );  //setText is thread safe (I think)
					   done.set(true); //game ends when missed 3
					   won.set(false);
				} else if (score.getCaught()>=maxWords) {
					   // Game still in progress
					   done.set(true); //game ends when missed 3
					   won.set(true);
		               caught.setText("Caught: " + score.getCaught() + "    ");
		               missed.setText("Missed:" +  score.getMissed()+ "    " );
		               scoreView.setText("Score:" + score.getScore()+ "    " );  
				}

        }
    }
}
