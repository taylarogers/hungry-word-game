package typingTutor;

public class WordDictionary {
	int size;
	// Default array value
	static String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary
	
	/** 
	Constructor that assigns an array of words
	*/
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
		
	}
	
	/** 
	Constructor to use the default list
	*/
	WordDictionary() {
		size=theDict.length;
	}
	
	/** 
	This gets a new random word from the dictionary
	*/
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return theDict[wdPos];
	}
	
}
