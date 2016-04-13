/**
 * @author Michael Avnyin 
 * This program is used 
 */

import java.io.*;

public abstract class IO
{
	public static BufferedReader inStream; // for input file
	public static PrintWriter outStream; // to print output file

	public static int Input; // the current input character on "inStream"
	public static char currentCharacter; // used to convert the variable "a" to the char type whenever necessary

	
	/**
	 * getNextChar() gets the next character of our input file
	 * @return -1 if error
	 */
	public static int getNextChar()

	// Returns the next character on the input stream.

	{
		try
		{
			return inStream.read();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	public static int getChar()

	// Returns the next non-whitespace character on the input stream.
	// Returns -1, end-of-stream, if the end of the input stream is reached.

	{
		int i = getNextChar(); // set i to next chart
		while ( Character.isWhitespace((char) i) )
			i = getNextChar();
		return i;
	}
	
	/**
	 * print to output textfile
	 * @param s
	 */
	public static void display(String s)
	{
		outStream.print(s);
	}

	/**
	 * print to output textfile
	 * @param s
	 */
	public static void displayln(String s)
	{
		outStream.println(s);
	}

	public static void setIO(String inFile, String outFile)

	// Sets the input and output streams to "inFile" and "outFile", respectively.
	// Sets the current input character "a" to the first character on the input stream.

	{
		try
		{
			inStream = new BufferedReader( new FileReader(inFile) );
			outStream = new PrintWriter( new FileOutputStream(outFile) );
			Input = inStream.read();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace(); // print stack trace if we have a File Not Found exception
		}
		catch(IOException e)
		{
			e.printStackTrace(); // print stack trace if we have an IO exception
		}
	}

	public static void closeIO()
	{
		try
		{
			inStream.close(); // closes our input stream
			outStream.close(); // closes our output stream
		}
		catch(IOException e)  
		{
			e.printStackTrace(); // print stack trace if we have an IO exception
		}
	}
}