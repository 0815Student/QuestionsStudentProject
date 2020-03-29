//this class handles String Checks - it is pretty simple

public class StringHandler {

    private static String[] bannedNames = { "continue", "all", "standard", "&!&", "start", "Start", "Continue", "All", "Standard", "save", "Save", "load", "Load", "highscore", "Highscore'" };

    public static boolean checkIfQuit( String toCheck ){
        if( toCheck.equals( "q" ) || toCheck.equals( "Q" ) ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfStart( String toCheck ){
        if( toCheck.equals( "start" ) || toCheck.equals( "Start" ) ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfYesOrNo( String toCheck ){
        if( toCheck.equals( "y" ) || toCheck.equals( "Y" ) ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfContinue( String toCheck ) {
        if( toCheck.equals( "continue" ) || toCheck.equals( "Continue" ) ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfAdd( String toCheck ) {
        if( toCheck.equals( "add") || toCheck.equals( "Add" ) ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfSave( String toCheck ) {
        if( toCheck.equals( "save" ) || toCheck.equals( "Save" ) ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfLoad( String toCheck ) {
        if( toCheck.equals( "load" ) || toCheck.equals( "Load" ) ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfHighscore( String toCheck ){
        if( toCheck.equals( "highscore" ) || toCheck.equals( "Highscore" ) ){
            return true;
        } else {
            return false;
        }

    }

    public static boolean checkIfStringValid( String toCheck ){
        if( StringHandler.checkIfQuit( toCheck ) ){
            System.exit( 0 );
        }
        if( toCheck.isEmpty() ){
            System.out.println( "An empty String is not allowed" );
            return false;
        }
        for( String s : bannedNames ){
            if( s.equals( toCheck ) ) {
                System.out.println( "'" + toCheck + "' is not a valid String. Please enter something else or 'q' to quit." );
                return false;
            }
        }

        return true;
    }

}
