package typingTutor;

import java.awt.Font;
import java.awt.FontMetrics;

public class FallingWord {
	private String word; // the word
	private int x; //position - width
	private int y; // postion - height
	private int width;
	private int height;
	private int maxY; //maximum height
	private int maxX; //maximum width
	private boolean dropped; //flag for if user does not manage to catch word in time
	private boolean hungry; // to see if it is a hungry word or not
	private boolean reset;

	private int randomSleep;
	
	private int fallingSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	public static WordDictionary dict;
	
	/** 
	This is the constructor with default values
	*/
	FallingWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=0;	
		maxY=300;
		maxX=300;
		width = 0;
		height = 0;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		this.reset = false;
		randomSleep = 0;
	}
	
	/** 
	This is the constructor for the text value
	*/
	FallingWord(String text) { 
		this();
		this.word=text;
	}
	
	/** 
	This is the constructor used for falling words
	*/
	FallingWord(String text,int x, int maxY) { //most commonly used constructor - sets it all.
		this(text);
		this.x=x; //only need to set x, word is at top of screen at start
		this.maxY=maxY;
		this.hungry = false;
	}

	/** 
	This is the constructor used for hungry word
	*/
	FallingWord(String text, int y, int maxX, boolean hungry)
	{
		this(text);
		this.y = y;
		this.hungry = hungry;
		this.maxX = maxX;
	}
	
	/** 
	This method increases the speed of the word
	*/
	public static void increaseSpeed( ) {
		minWait+=50;
		maxWait+=50;
	}
	
	/**
	This resets the speed of the word 
	*/
	public static void resetSpeed( ) {
		maxWait=1000;
		minWait=100;
	}
	
	/** 
	This sets the y-value of the word
	*/
	public synchronized void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true; //user did not manage to catch this word
		}
		this.y=y;
	}
	
	/** 
	This sets the c-value of the word
	*/
	public synchronized void setX(int x) {
		if (x > maxX) // Check if Hungry Word reaches the end
		{
			x = maxX;
			dropped=true;
		}

		this.x=x;
	}
	
	/** 
	This sets the word
	*/
	public synchronized  void setWord(String text) {
		this.word=text;
	}

	/** 
	This gets the word
	*/
	public synchronized  String getWord() {
		return word;
	}
	
	/** 
	This gets the x-value of the word
	*/
	public synchronized  int getX() {
		return x;
	}	
	
	/** 
	This gets the y-value of the word
	*/
	public synchronized  int getY() {
		return y;
	}
	
	/** 
	This returns the speed of the word
	*/
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	/** 
	This returns whether it is a hungry word
	*/
	public synchronized boolean getHungry()
	{
		return hungry;
	}

	/** 
	This sets the width of the word
	*/
	public synchronized void setWidth(int width)
	{
		this.width = width;
	}

	/** 
	This sets the height of the word
	*/
	public synchronized void setHeight(int height)
	{
		this.height = height;
	}

	/** 
	This returns the width of the word
	*/
	public synchronized int getWidth()
	{
		return width;
	}

	/** 
	This returns the height of the word
	*/
	public synchronized int getHeight()
	{
		return height;
	}
	
	/** 
	This returns the random sleep value
	*/
	public synchronized int getRandomSleep()
	{
		return randomSleep;
	}

	/**
	This returns whether or not the word must sleep
	*/
	public synchronized boolean getReset()
	{
		return reset;
	}

	/** 
	This sets the x and y values of the word
	*/
	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	/** 
	This resets the position of the word to a starting position
	*/
	public synchronized void resetPos() {
		// A hungry would reset on the y-axis and a falling word would reset on the x-axis
		if (hungry == false) 
			setY(0);
		else
			setX(0);

	}

	/** 
	This resets the word value, position and speed, and checks to see if a sleep must be invoked on the word
	*/
	public synchronized void resetWord() {
		resetPos();
		word=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 

		// A hungry word must randomly sleep before its next run
		if (hungry == true)
		{
			reset = true;
			randomSleep = (int)(Math.random() * 10000);
		}
		
		
		//System.out.println(getWord() + " falling speed = " + getSpeed());
	}
	
	/** 
	This checks if a word matches and resets it
	*/
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.word)) {
			resetWord();
			return true;
		}
		else
			return false;
	}

	/**
	This resets a word
	*/
	public synchronized void matchWord() {
		//System.out.println("Matching against: "+text);
		resetWord();
	}

	/** 
	This changes a falling words y-value
	*/
	public synchronized  void drop(int inc) {
		setY(y+inc);
	}

	/** 
	This changes a hungry word's x-value
	*/
	public synchronized void move(int inc)
	{
		// Reset reset value so tat it does not sleep again
		if (reset == true)
			reset = false;

		setX(x+inc);
	}
	
	/** 
	This returns if the word has been missed or not
	*/
	public synchronized  boolean dropped() {
		return dropped;
	}

}
