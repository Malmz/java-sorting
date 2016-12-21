
/* *************************************************************************\
|                                                                           |
|       Konsol - A class for simplified reading of numbers, characters      |
|                and strings from standard input. For use with Java 1.2.    |
|               Version 1.0 returns error values on error.                  |
|               Version 1.1 deprecated.                                     |
|               Version 1.2 throws exceptions on error.                     |
|               Version 1.2B the method formatDouble() added.               |
|                            exception-free version                         |
|                                                                           |
|       version 1.2B  5 Sep 2001                                            |
|       author  Leif �nneflod, Gothenburg, Sweden                           |
|                                                                           |
|       This software is granted for free use without fee,                  |
|       provided that this notice appears in all copies.                    |
|       THE AUTHOR MAKES NO WARRANTIES WHATSOEVER ABOUT HOW THIS            |
|       SOFTWARE WILL WORK UNDER ANY CIRCUMSTANCES.                         |
|                                                                           |
\***************************************************************************/

 /**
 | An interface for input of numbers and strings from standard input.
 | version 1.2B  5 Sep 2001
 | author  Leif Oenneflod, Gothenburg, Sweden 
 |
 | Arguments:
 |
 |    1. Without arguments:   No prompt is shown  
 |							  - behaves like arguments ("", true), see below.
 |
 |    2. One String argument: The string is used for prompting an input. 
 |							  Empty string allowed.
 |
 |    3. A second boolean arguments: The value 'true' is OPTIONAL.
 |
 |                  'true' makes correct input compulsory and no IOException 
 |                  is thrown. Input is repeatedly prompted for, until "correct".
 |
 |                  'false' makes the method return an "empty value" on error.
 |                  For numbers it is zero, for String an empty string and for 
 |                  char it is '\0'. No demand for repetition of input.
 |
 | Every input is stripped from any leading or trailing white space,
 | except for method readChar() where input of white space is made possible.
 |
 */

import java.io.*;

public final class Kbd {
    
    private static PrintWriter toScreen;    //Never throws exceptions
    private static BufferedReader fromKeyboard;
    
    static {   
    	//initializer on class level. (A substitute for constructors)
        // initializing of static variables above
        fromKeyboard  = new BufferedReader( new InputStreamReader( System.in ));
        toScreen = new PrintWriter( System.out );
    }
    
    /** 
    | Shared method used by the others. ( except readChar() )
    | Shows prompt and accepts a string as input.
    | Parameter p is the prompt to show. 
    | Returns the input string stripped from leading and trailing white space.
    |         On error, an empty string is returned.
    */
    private static String getValueForPrompt( String p ){
        toScreen.print( p );
        toScreen.flush();
        String s;
        try{
            s = fromKeyboard.readLine();
            s = s.trim();
        }
        catch( IOException e ){
            s = "";
        }
        finally{  // clears any remaining input 
            try{
                while( fromKeyboard.ready()) { fromKeyboard.read(); }
            }
            catch( IOException e ) {/*ignore*/}
        }
        return( s );
    }
    
    /** 
    | readInt() reads an integer value without prompting
    | Returns the integer value.
    |        
    */
    public static int readInt() { 
    	//calls method below
    	return( readInt( "", true )); 
    }  
    
    /** 
    | readInt(String) reads an integer value after displaying a prompt.
    | Returns the integer value.
    |         
    */
    public static int readInt( String prompt ) {
    	//calls method below
    	return( readInt( prompt, true ));
    }  
    
    /** 
    | read_Int(String) reads an integer value after displaying a prompt.
    | Returns the integer value.
    |         On error an IOException is thrown.
    |         
    */
    private static int read_Int( String prompt )
    throws IOException
    {
        String s = getValueForPrompt( prompt );
        int temp;        
        try{
            temp = Integer.parseInt( s );
        }
        catch( NumberFormatException e ){
            // conversion of exception above to a "standard" exception
            throw new IOException("Illegal integer value");
        }
        return( temp );
    }

    /** 
    | readInt(String, boolean) reads an integer value after a prompt.
    | Returns the integer value. No exception is thrown.
    |      On boolean value 'false', 0 (zero) is returned if an error occurs.
    |      On boolean value 'true' no erroneous values are returned.
    */
    public static int readInt( String prompt, boolean valueCompulsory ){
        boolean exceptionOccured = false; // posit this will be the case
        int i = 0;  // must have an initial value to avoid compiler warnings
        try{
            i = read_Int( prompt );
        }
        catch( IOException e ){
            i = 0; 
            exceptionOccured = true;
        }
        if( valueCompulsory ){
            while( exceptionOccured ){
                try{
                    i = read_Int( prompt );
                    exceptionOccured = false;  //this line executes IF no errors occur
                }
                catch( IOException e ) {/* ignore */}
            }
        }
        return( i );
    }
        
    /** 
    | readLong() reads a long value without prompting
    | Returns the long value.
    */
    public static long readLong() 
    { return( readLong( "", true )); }  //calls method below
        
    /** 
    | readLong(String) reads a long value after displaying a prompt.
    | Returns the long value.
    */
    public static long readLong( String prompt ) 
    { return( readLong( prompt, true )); }  //calls method below
    
    /** 
    | read_Long(String) reads a long value after displaying a prompt.
    | Returns the long value.
    |         On error an IOException is thrown.
    */
    private static long read_Long( String prompt )
    throws IOException
    {
        String s = getValueForPrompt( prompt );
        long temp;        
        try{
            temp = Long.parseLong( s );
        }
        catch( NumberFormatException e ){
            throw new IOException("Illegal long integer value");
        }
        return( temp );
    }

    /** 
    | readLong(String, boolean) reads a long value after a prompt.
    | Returns the long value. No exception is thrown.
    |      On boolean value 'false', 0 (zero) is returned if an error occurs.
    |      On boolean value 'true' no erroneous values are returned.
    */
    public static long readLong( String prompt, boolean valueCompulsory ){
        boolean exceptionOccured = false;
        long i = 0;  // must have an initial value to avoid compiler warnings
        try{
            i = read_Long( prompt );
        }
        catch( IOException e ){
            i = 0; 
            exceptionOccured = true;
        }
        if( valueCompulsory ){
            while( exceptionOccured ){
                try{
                    i = read_Long( prompt );
                    exceptionOccured = false;
                }
                catch( IOException e ) {/* ignore */}
            }
        }
        return( i );
    }

    /**
    | formatDouble( double, int ) accepts a double value and an integer value.
    |   The integer indicates the wanted precision, which the double value is rounded to.
    |     A positive value of the precision corresponds to the size of the fraction part.
    |     A zero value of the precision corresponds to rounding off to an integer.
    |     A negative value of the precision  corresponds to the number of trailing zeroes
    |       in a double rounded into tens, hundreds, thousands etc. 
    | Returns a string holding the formatted double value according to the rules above.
    | 	  No errors are thrown.
    */
    public static String formatDouble( double d, int p ){
        double prec = Math.pow( 10.0, p );
        if( d * prec > Long.MAX_VALUE ) 
            return( "##### too many digits in precision #####" );
        long   r    = Math.round( d * prec );
        String zeroes = "";
        String temp = Long.toString( r );
        int    lgd  = temp.length();
        for( ; lgd <= p; lgd++ )
            zeroes += "0";
        String out;
        if( p <= 0 ){  
            out = temp + zeroes;
        }
        else{         
            temp = zeroes + temp;
            out = temp.substring( 0, lgd - p ) + "." + temp.substring( lgd - p, lgd );
        }
        return( out );
    }
        
    /** 
    | readDouble() reads a double value without prompting
    | Returns the double value.
    */
    public static double readDouble() 
    { return( readDouble( "", true )); }  //calls method below
        
    /** 
    | readDouble(String) reads a double value after displaying a prompt.
    | Returns the double value.
    */
    public static double readDouble( String prompt ) 
    { return( readDouble( prompt, true )); }  //calls method below
    
    /** 
    | read_Double(String) reads a double value after displaying a prompt.
    | Returns the double value.
    |         On error an IOException is thrown.
    */
    private static double read_Double( String prompt )
    throws IOException
    {
        String s = getValueForPrompt( prompt );
        double temp;        
        try{
            temp = (Double.valueOf( s )).doubleValue();
        }
        catch( NumberFormatException e ){
            throw new IOException("Illegal float number value");
        }
        return( temp );
    }

    /** 
    | readDouble(String, boolean) reads a double value after a prompt.
    | Returns the double value. No exception is thrown.
    |      On boolean value 'false', 0.0 (zero) is returned if an error occurs.
    |      On boolean value 'true', no erroneous values are returned.
    */
    public static double readDouble( String prompt, boolean valueCompulsory ){
        boolean exceptionOccured = false;
        double d = 0.0;  // must have an initial value to avoid compiler warnings
        try{
            d = read_Double( prompt );
        }
        catch( IOException e ){
            d = 0.0;
            exceptionOccured = true;
        }
        if( valueCompulsory ){
            while( exceptionOccured ){
                try{
                    d = read_Double( prompt );
                    exceptionOccured = false;
                }
                catch( IOException e ) {/* ignore */}
            }
        }
        return( d );
    }
        
    /** 
    | readString() reads a string value without prompting
    | Returns the string value. 
    */
    public static String readString() 
    { return( readString( "", true )); }  //calls method below
        
    /** 
    | readString(String) reads a string value after displaying a prompt.
    | Returns the string value. 
    */
    public static String readString( String prompt ) 
    { return( readString( prompt, true )); }  //calls method below
    
    /** 
    | read_String(String) reads a string value after displaying a prompt.
    | Returns the string value. 
    |        On error an IOException is thrown. Empty string causes an error.
    */
    private static String read_String( String prompt )
    throws IOException
    {
        String s = getValueForPrompt( prompt );
        if( s.equals("")) throw new IOException("Empty string not legal here.");
        return( s );
    }

    /** 
    | readString(String, boolean) reads a string value after a prompt.
    | Returns the string value. No exception is thrown.
    |         On boolean value 'true' no empty strings is accepted or returned.
    |         On boolean value 'false' empty strings are legal and returned.
    |                                  Empty strings returned on illegal input.
    */
    public static String readString( String prompt, boolean valueCompulsory ){
        boolean exceptionOccured = false;
        String s = "";  // must have an initial value to avoid compiler warnings
        try{
            s = read_String( prompt );
        }
        catch( IOException e ){
            s = "";
            exceptionOccured = true;
        }
        if( valueCompulsory ){
            while( exceptionOccured ){
                try{
                    s = read_String( prompt );
                    exceptionOccured = false;
                }
                catch( IOException e ) {/* ignore */}
            }
        }
        return( s );
    }
        
    /** 
    | readChar() reads a char value without prompting
    | Returns the char value
    */
    public static char readChar() 
    { return( readChar( "", true )); }  //calls method below
        
    /** 
    | readChar(String) reads a char value after displaying a prompt.
    | Returns the char value
    */
    public static char readChar( String prompt ) 
    { return( readChar( prompt, true )); }  //calls method below
    
    /** 
    | read_Char(String) reads a char value after displaying a prompt.
    | Returns the char value
    |         On error an IOException is thrown.
    */
    private static char read_Char( String prompt )
    throws IOException
    {
        toScreen.print( prompt );
        toScreen.flush();
        String s;
        try{
            s = fromKeyboard.readLine();
        }
        catch( IOException e ){
            s = null;
        }
        finally{  // clears any remaining input 
            try{
                while( fromKeyboard.ready()) { fromKeyboard.read(); }
            }
            catch( IOException e ) {/*ignore*/}
        }
        char temp;        
        try{
            temp = s.charAt( 0 );
        }
        catch( StringIndexOutOfBoundsException e ){
            throw new IOException("Illegal character value");
        }
        return( temp );
    }

    /** 
    | readChar(String, boolean) reads a char value after a prompt.
    | Returns the char value. All visible char values can be input.
    |                         Some white space characters can be input.
    |         On boolean value 'false', '\0' is returned if an error occurs.
    |         On boolean value 'true' no erroneous values are returned.
    */
    public static char readChar( String prompt, boolean valueCompulsory ){
        boolean exceptionOccured = false;
        char c = '\0';  // must have an initial value to avoid compiler warnings
        try{
            c = read_Char( prompt );
        }
        catch( IOException e ){
            c = '\0';
            exceptionOccured = true;
        }
        if( valueCompulsory ){
            while( exceptionOccured ){
                try{
                    c = read_Char( prompt );
                    exceptionOccured = false;
                }
                catch( IOException e ) {/* ignore */}
            }
        }
        return( c );
    }
        
}
/*
    /** 
    | readHex() reads an hexadecimal value without prompting
    | Returns a long value.
    |         On error an IOException is thrown.
    * /
    public static long readHex() 
    throws IOException
    { return( readHex("")); }  //calls method below
    
    /** 
    | readHex(String) reads an hexadecimal value after displaying a prompt.
    | Returns a long value.
    |         On error an IOException is thrown.
    * /
    public static long readHex( String prompt )
    throws IOException
    {
        String s = getValueForPrompt( prompt );
        long temp;        
        try{
            temp = Integer.parseLong( s, 16 );
        }
        catch( NumberFormatException e ){
            // conversion of exception above to a "standard" exception
            throw new IOException("Illegal hexadecimal value");
        }
        return( temp );
    }

    /** 
    | readHex(String, boolean) reads an hexadecimal value after a prompt.
    | Returns a long value. No exception is thrown.
    |      On boolean value 'false', 0 (zero) is returned if an error occurs.
    |      On boolean value 'true' no erroneous values are returned.
    * /
    public static long readHex( String prompt, boolean valueCompulsory ){
        boolean exceptionOccured = false; // posit this will be the case
        long h = 0;  // must have an initial value to avoid compiler warnings
        try{
            h = readHex( prompt );
        }
        catch( IOException e ){
            h = 0; 
            exceptionOccured = true;
        }
        if( valueCompulsory ){
            while( exceptionOccured ){
                try{
                    h = readHex( prompt );
                    exceptionOccured = false;  //this line executes IF no errors occur
                }
                catch( IOException e ) {/* ignore /}
            }
        }
        return( h );
    }
*/
