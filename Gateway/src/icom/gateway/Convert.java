package icom.gateway;


import java.io.PrintWriter;
import java.sql.Timestamp;



public final class Convert {
    /** maximum int value that can be converted to char */
    public static final int MAX_CHAR_INT = 35;

    /**
    *   encodes a string of 4-digit hex numbers to unicode
    *   <br />
    *   Note: this method is slower than hexToString(String,StringBuffer)
    *   @param hex string of 4-digit hex numbers
    *   @return normal java string
    */
    public static final String hexToString ( String hex ) {
        if ( hex == null ) return "";
        int length = hex.length() & -2;
        StringBuffer result = new StringBuffer( length/2);
        hexToString( hex, result);
        return result.toString();
    }

    /**
    *   encodes a string of 4-digit hex numbers to unicode
    *   @param hex string of 4-digit hex numbers
    *   @param out normal java string
    *   @todo to improve performance, implement a table-lookup instead of parseInt(substring())
    */
    public static final void hexToString ( String hex, StringBuffer out ) {
        if ( hex == null ) return;
        int length = hex.length() & -2;
        for ( int pos=0; pos < length; pos += 2) {
            int this_char = 0;
            try { this_char = Integer.parseInt( hex.substring( pos,pos+2), 16);}
            catch ( NumberFormatException NF_Ex) { /* dont care*/ }
            out.append( (char) this_char);
        }
    }

    /**
    *   encodes a unicode string to a string of 4-digit hex numbers
    *   <br />
    *   Note: this method is slower than stringToHex(String,StringBuffer)
    *   @param java normal java string
    *   @return string of 4-digit hex numbers
    */
    public static final String stringToHex ( String java ) {
        if ( java == null ) return "";
        int length = java.length();
        StringBuffer result = new StringBuffer( length*4);
        stringToHex( java, result);
        return result.toString();
    }

    /**
    *   encodes a unicode string to a string of 4-digit hex numbers
    *   @param java normal java string
    *   @param out string of 4-digit hex numbers
    *   @todo to improve performance, implement a table-lookup instead of if/then cases
    */
    public static final void stringToHex ( String java, StringBuffer out ) {
        if ( java == null ) return;
        int length = java.length();
        for ( int pos=0; pos < length; pos ++) {
            int this_char = (int) java.charAt( pos);
            for ( int digit = 0; digit < 4; digit ++ ) {
                int this_digit = this_char & 0xf000;
                this_char = this_char << 4;
                this_digit = (this_digit >> 12);
                if ( this_digit >= 10 )
                    out.append ( (char)( this_digit+87));
                else
                    out.append ( (char)( this_digit+48));
            }
        }
    }

    /**
    *   encodes a unicode string to a string of 4-digit hex numbers
    *   @param java normal java string
    *   @param out string of 4-digit hex numbers
    *   @todo to improve performance, implement a table-lookup instead of if/then cases
    */
    public static final void stringToHex ( String java, PrintWriter out ) {
        if ( java == null ) return;
        int length = java.length();
        for ( int pos=0; pos < length; pos ++) {
            int this_char = (int) java.charAt( pos);
            for ( int digit = 0; digit < 4; digit ++ ) {
                int this_digit = this_char & 0xf000;
                this_char = this_char << 4;
                this_digit = (this_digit >> 12);
                if ( this_digit >= 10 )
                    out.print ( (char)( this_digit+87));
                else
                    out.print ( (char)( this_digit+48));
            }
        }
    }

    /**
    *   checks, if the parameter is a decimal number
    */
    public static final boolean isDecimalNumber ( String fix_value) {
        int fix_size = fix_value.length();
        if ( fix_size == 0 ) return false;
        int dotcount = 0;
        for ( int fix_pos = 0; fix_pos < fix_size; fix_pos ++ ) {
            char probe = fix_value.charAt(fix_pos);
            if (!((( probe >= '0' ) && ( probe <= '9' ))
                || (( fix_pos == 0) && ( probe == '-' ))
                || (( fix_pos > 0) && (probe == '.' ))))
            {
                return false;
            }
            if ( probe == '.' ) {
                dotcount ++;
                if ( dotcount > 1 ) return  false;
            }
        }
        return true;
    }

    /**
    *   converts a one-digit number to an int (base 36)
    *   <br />
    *   Note: the result is undefined, if parameter is out of range
    *   @todo to improve performance, implement a table-lookup instead of if/then cases
    */
    public static final int charToInt( char base36) {
        if ( base36 <= '9')
            if ( base36 >= '0')
                return ((int)( base36 - '0'));
            else
                return 0;
        else if ( base36 < 'a')
            if ( base36 <= 'Z')
                return ((int)( base36 - 'A'))+10;
            else
                return 0;
        else
            if ( base36 <= 'z')
                return ((int)( base36 - 'a'))+10;
            else
                return 0;
    }

    /**
    *   converts an int number between 0 and 36 to a char (base 36)
    *   <br />
    *   Note: the result is undefined, if parameter is out of range
    *   @todo to improve performance, implement a table-lookup instead of if/then cases
    */
    public static final char intToChar( int zeroTo36) {
        if ( zeroTo36 <= 9)
            if ( zeroTo36 >= 0)
                return (char)( zeroTo36 + ((int)'0'));
            else
                return 0;
        else
            if ( zeroTo36 < 36)
                return (char)( zeroTo36-10 + ((int)'a'));
            else
                return 0;
    }

    /**
    *   converts an int number to hexadecimal representation
    */
    public static final void intToHex( int x, StringBuffer out) {
        for ( int pos = 0; pos < 8; pos ++ )
            out.append( intToChar((x>>>(28-(pos*4)))&0xf));
    }

    /**
    *   converts an UTC time to local time
    *   or local time to UTC.
    *   @param Time string representing the time
    *   @param Minutes minutes to be added to Time
    *   @return String where minutes are added to Time
    *           or null in case of errors
    */
    public static String addToTime ( String Time, long Minutes ) {
        String result = null;
        try {
            //  complete waste of memory - but how else do i convert this?
            result = ( new java.sql.Timestamp (
                java.sql.Timestamp.valueOf( Time).getTime() + ( Minutes * 60000)))
                .toString();
        }
        catch ( RuntimeException RunEx) {
            //System.out.print(RunEx.toString());
        }
        return result;
    }

    /**
    *   replaces several characters in a string and writes the result to a printwriter
    *   @param original string that shall be converted and copied to out
    *   @param badGuys characters that shall be replaced
    *   @param goodGuys replacements for badGuys
    *   @param out PrintWriter to which the result is written
    */
    public static void replace ( String original, char[] badGuys, String[] goodGuys,
        PrintWriter out)
    {
        //  check + init params:
        if ( original == null ) return;
        int startOutputAt = 0;      //  this variable indicates
                                    //  which chars haven't yet been printed
        int count_rules = badGuys.length;

        //  do the transformation:
        int orig_length = original.length();
        for ( int pos = 0; pos < orig_length; pos ++ ) {
            //  get + init params:
            char probe = original.charAt(pos); //  character at current string position
            String appendAfterFlush = null; //  string, the current charater shall be replaced by

            //  is this a bad character:
            for ( int rule = 0; rule < count_rules; rule ++ )
                if ( probe == badGuys[rule]) { appendAfterFlush = goodGuys[rule]; }

            //  flush if bad character found:
            if ( appendAfterFlush != null) {
                out.print( original.substring( startOutputAt, pos));
                out.print( appendAfterFlush);
                startOutputAt = pos+1;
            }

            //  if this is the last character, print the tail of the string
            if ( (pos+1)==orig_length )
                out.print( original.substring( startOutputAt));
        }
    }

}
