/**
 * @author Michael Avnyin
 * This .java file is an extension of a previously provided file.
 * I have added more to the conditionals to meet the specifics of the DFA
 * We are checking the current character from our input file and determining
 * which appropriate state to go to next. Our cases include all the situations
 * 
 * 
<letter>-> a | b | ... | z | A | B | ... | Z 
<digit> -> 0 | 1 | ... | 9 
<id> -> <letter> {<letter> | <digit>} 
<int> -> [+|-] {<digit>}+ 
<float> -> [+|-] ( {<digit>}+ "." {<digit>}  |  "." {<digit>}+ ) 
<floatE> -> (<int> | <float>) (e|E) [+|-] {<digit>}+ 
<add> -> + 
<sub> -> - 
<mul> -> * 
<div> -> / 
<or> -> | 
<and> -> & 
<not> -> ! 
<lt> -> < 
<le> -> "<=" 
<gt> -> > 
<ge> -> ">=" 
<eq> -> = 
<LParen> -> ( 
<RParen> -> ) 
<LBrace> -> { 
<RBrace> -> } 

This lexical analyzer  accepts the token categories plus the following keywords, all in lowercase letters only:
class, if, null

 */

import java.lang.*;

public class LexArith extends IO
	{
		public enum State
			{
				// all non-final states

				Start,
				Period,
				E,
				EPlusMinus,

				// all final states

				Id,
				Int,
				Float,
				FloatE,
				Add,
				Sub,
				Mul,
				Div,
				LParen,
				RParen,
				LBrace,
				RBrace,
				Lt,
				Le,
				Gt,
				Ge,
				Eq,
				Not,
				Or,
				And,
				Keyword_if,
				Keyword_class,
				Keyword_null,
				


				UNDEFINED;

				private boolean isFinal() { return ( this.compareTo(State.Id) >= 0 ); }
			}

		public static String Token; // holds extracted token
		public static State state; // current state of the FA

		private static int driver()

			// This is the driver of the FA.
			// If a valid token is found, assigns it to "processedString" and returns 1.
			// If an invalid token is found, assigns it to "processedString" and returns 0.
			// If end-of-stream is reached without finding any non-whitespace character, returns -1.

		{
			State nextSt; // the next state of the FA

			Token = "";
			state = State.Start;

			if ( Character.isWhitespace((char) Input) )
				Input = getChar(); // get the next non-whitespace character
			if ( Input == -1 ) // end-of-stream is reached
				return -1;

			while ( Input != -1 ) // do the body if "a" is not end-of-stream
				{
					currentCharacter = (char) Input;
					nextSt = nextState( state, currentCharacter);
					if ( nextSt == State.UNDEFINED) // The FA will halt.
						{
							if ( state.isFinal() )
								return 1; // valid token extracted
							else // "currentCharacter" is an unexpected character
								{
									Token = Token + currentCharacter;
									Input = getNextChar();
									return 0; // invalid token found
								}
						}
					else // The FA will go on.
						{
							state = nextSt;
							Token = Token + currentCharacter;
							Input = getNextChar();
						}
				}

			// end-of-stream is reached while a token is being extracted

			if ( state.isFinal() )
				return 1; // valid token extracted
			else
				return 0; // invalid token found
		} // end driver

		public static void getToken()

			// Extract the next token using the driver of the FA.
			// If an invalid token is found, issue an error message.

		{
			int i = driver();
			if ( i == 0 )
				displayln(Token + " : Lexical Error, invalid token");
		}

		private static State nextState(State currentState, char currentCharacter)

			// Returns the next state of the FA given the current state and input char;
			// if the next state is undefined, UNDEFINED is returned.	

		{

			switch( state )
				{
					// all of these are conditions to check from the start state.
					// depending on the character will determine which state it will go to
					case Start:
						if ( Character.isLetter(currentCharacter) )
							return State.Id; // go to state id
						else if ( Character.isDigit(currentCharacter) )
							return State.Int; // go to state Int
						else if ( currentCharacter == '+' )
							return State.Add; // go to state Add
						else if ( currentCharacter == '-' )
							return State.Sub; // etc.........
						else if ( currentCharacter == '*' )
							return State.Mul;
						else if ( currentCharacter == '/' )
							return State.Div;
						else if ( currentCharacter == '(' )
							return State.LParen;
						else if ( currentCharacter == ')' )
							return State.RParen;
						else if ( currentCharacter == '|')
							return State.Or;
						else if ( currentCharacter == '&')
							return State.And;
						else if ( currentCharacter == '!')
							return State.Not;
						else if ( currentCharacter == '<')
							return State.Lt;
						else if ( currentCharacter == '>')
							return State.Gt;
						else if ( currentCharacter == '=')
							return State.Eq;
						else if ( currentCharacter == '{')
							return State.LBrace;
						else if ( currentCharacter == '}')
							return State.RBrace;
						else if ( currentCharacter == '.')
							return State.Period;
						else
							return State.UNDEFINED; // else nothing is found 
						
					case Id:

						// The following sections of code check for any keywords while the DFA is in the ID state
						if ((Token + currentCharacter).equals("if"))
							return State.Keyword_if;
						else if ((Token + currentCharacter).equals("class"))
							return State.Keyword_class;
						else if ((Token + currentCharacter).equals("null"))
							return State.Keyword_null;

						// If no keywords are found, then it checks for character or digit and returns ID
						else if ( Character.isLetterOrDigit(currentCharacter) )
							return State.Id;
						else
							return State.UNDEFINED;

						// Ensures the return of an ID state if there is anything appended to the end of the keyword that does not belong there
					case Keyword_if:
						if ( Character.isLetterOrDigit(currentCharacter))
							return State.Id;

					case Keyword_class:
						if ( Character.isLetterOrDigit(currentCharacter))
							return State.Id;

					case Keyword_null:
						if ( Character.isLetterOrDigit(currentCharacter))
							return State.Id;

					case Int: // in Int state
						if ( Character.isDigit(currentCharacter) )
							return State.Int;
						else if ( currentCharacter == '.' )
							return State.Float;
						else if ( currentCharacter == 'e' || currentCharacter == 'E')
							return State.E;
						else
							return State.UNDEFINED;
						
					case Add: // in Add state
						if ( Character.isDigit(currentCharacter))
							return State.Int;
						else if ( currentCharacter == '.')
							return State.Period;
						
					case Sub: // in sub state
						if ( Character.isDigit(currentCharacter))
							return State.Int; // if character is digit go to INT state
						else if ( currentCharacter == '.')
							return State.Period; // else if we have a "." go to period state
						
					case Period: // in period state non final
						if ( Character.isDigit(currentCharacter) ) // current character is digit
							return State.Float;  // go to float state
						else
							return State.UNDEFINED; // nothing found
						
					case Float: // in period state 
						if ( Character.isDigit(currentCharacter) )
							return State.Float; // return to state float
						else if ( currentCharacter == 'e' || currentCharacter == 'E' )
							return State.E; // return to state E
						else
							return State.UNDEFINED;
						
					case E: // in E state 
						if ( Character.isDigit(currentCharacter) ) // if digit 
							return State.FloatE; // go to Float E
						else if ( currentCharacter == '+' || currentCharacter == '-' ) // if + or -
							return State.EPlusMinus;  // go to EPlusMinus
						else
							return State.UNDEFINED;
						
					case EPlusMinus: // in EPlusMinus state
						if ( Character.isDigit(currentCharacter) )
							return State.FloatE; // if a digit go to FloatE
						else
							return State.UNDEFINED; // nothing undefined
						
					case FloatE: // go to FloatE
						if ( Character.isDigit(currentCharacter) )
							return State.FloatE;
						else
							return State.UNDEFINED;
					case Lt: // go to Lt
						if ( currentCharacter == '=')
							return State.Le;
					case Gt: // go to Gt
						if ( currentCharacter == '=')
							return State.Ge;
					default: // if all cases fail go to default
						return State.UNDEFINED; // go to state undefined
				}
		} // end nextState

		public static void main(String argv[])

			{

				setIO( argv[0], argv[1] ); // our input and output file for arguments

				int i;

				while ( Input != -1 ) // while "a" is not end-of-stream
					{
						i = driver(); // extract the next token
						if ( i == 1 )
							displayln( Token + "   : " + state.toString() ); // print to outputfile
						else if ( i == 0 ) 
							displayln( Token + " : Lexical Error, invalid token"); // error to file 
					}

				closeIO();
			}


	}