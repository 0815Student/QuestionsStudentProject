import java.io.*;
import java.util.*;

//this class handles all input from files and output to files - it is pretty selfexplanatory by the method names

public class DataHandler {

    public static HashMap<String, List<Question>> getAllQuestions() throws IOException {

        String rootPath = new String( System.getProperty( "user.dir" ) );
        File folder = new File( rootPath );
        File[] listOfFiles = folder.listFiles();
        HashMap<String, List<Question> > allQuestions = new HashMap<String, List<Question> >();
        for (File file : listOfFiles) {
            if (file.isFile()) {

                String fileName = new String( file.getName() );
                BufferedReader br = new BufferedReader(new FileReader( file.getPath() ));
                String line;

                if( fileName.endsWith( "quest" ) ){

                    String category = new String( fileName.substring( 0, fileName.length()-6 ) );

                    List<Question> questionList = new LinkedList<Question>();
                    int currLine = 0;
                    while( ( line = br.readLine() ) != null ){

                        currLine++;

                        String[] lineArr = line.split( "&!&" );

                        if( lineArr.length < 2 ) {
                            System.out.println( "Attention: There is an invalid question in file " + fileName + " on line " + currLine );
                            continue;
                        }

                        String question = lineArr[0];
                        String correctAnswer = lineArr[1];
                        List<String> allAnswers = new LinkedList( Arrays.asList( lineArr ) );

                        allAnswers.remove( 0 );

                        Question questionObj = new Question( category, question, correctAnswer, allAnswers );
                        questionList.add( questionObj );
                    }
                    allQuestions.put( category, questionList );
                }

            }
        }

        return allQuestions;

    }

    public static boolean createNewQuestion( Scanner scanner ) throws IOException {

        //if you want to add new functionality here remember to add the keyword to the bannedNames array in the StringHandler Class and remove questions from existing files that have them as an answer or question
        System.out.println( "Please note that while creating a new question 'q' and 'Q' always quits the program. So this cannot be a question or an answer. 'continue', 'add', 'all', 'start', 'save', 'load', 'highscore' and '&!&' are also forbidden as these are keywords during game setup/delimiters." );
        System.out.println( "If you enter multiple answers you will create a multiple choice question. If you enter just 1 answer you will create a normal question.");
        System.out.println( "Please enter your question category:");

        String category = new String();
        String question = new String();
        String correctAnswer = new String();
        List<String> allAnswers = new LinkedList<String>();

        String input = new String();

        boolean validCategory = false;
        while( !validCategory ){
            input = scanner.nextLine().trim();
            category = input;
            validCategory = StringHandler.checkIfStringValid( input );
            if( !validCategory ){
                System.out.println( "Please enter a new category." );
            }
        }

        System.out.println( "Please enter your question next:" );

        boolean validQuestion = false;
        while( !validQuestion ){
            input = scanner.nextLine().trim();
            question = input;
            validQuestion = StringHandler.checkIfStringValid( input );
            if( !validQuestion ){
                System.out.println( "Please enter a new question." );
            }
        }

        System.out.println( "Please enter your correct answer" );

        boolean validCorrAnswer = false;
        while( !validCorrAnswer ){
            input = scanner.nextLine().trim();
            correctAnswer = input;
            validCorrAnswer = StringHandler.checkIfStringValid( input );
            if( !validCorrAnswer ){
                System.out.println( "Please enter again a new correct answer." );
            } else {
                allAnswers.add( input );
            }
        }


        System.out.println( "If you want to add another (wrong) answer, type 'Y' or 'y' - else type anything else" );

        boolean decideNextStep = true;
        while( decideNextStep ){
            input = scanner.nextLine().trim();
            decideNextStep = StringHandler.checkIfYesOrNo( input );

            if( decideNextStep ) {

                System.out.println( "Please enter your wrong answer:" );

                boolean validWrongAnwer = false;
                while (!validWrongAnwer) {
                    input = scanner.nextLine().trim();
                    validWrongAnwer = StringHandler.checkIfStringValid(input);
                    if (!validWrongAnwer) {
                        continue;
                    } else {
                        allAnswers.add( input );
                        System.out.println( "If you want to add another (wrong) answer, type 'Y' or 'y' - else type anything else" );
                    }
                }
            }
        }
        Question questionObj = new Question( category, question, correctAnswer, allAnswers );

        String rootPath = new String( System.getProperty( "user.dir" ) );
        File file = new File( rootPath + "\\" + category + ".quest" );
        boolean newFile = false;

        if( !file.exists() || !file.isFile() ){
            file.createNewFile();
            newFile = true;
            System.out.println( "New category... New File created." );
        }
        if( file.exists() && file.isFile() ){
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true), "UTF-8"));

            if( !newFile ){
                writer.newLine();
            }
            writer.write( questionObj.toString() );
            writer.close();
            System.out.println( "Question added." );
            System.out.println();
        } else {
            System.out.println( "Couldn't write question" );
            System.out.println();
        }
        System.out.println( "To start a new game type 'Start' or 'start'. With this you can also start a new game while playing." );
        System.out.println( "To add new questions to the pool, type 'add'. This only works when no game is currently running." );
        return true;

    }

    public static GameInstance loadGame(){
        String rootPath = new String( System.getProperty( "user.dir" ) );
        File file = new File( rootPath + "\\" + "save.sav" );

        if( !file.exists() || !file.isFile() ){
            System.out.println( "No savegame found." );
        }
        try {
            FileInputStream fis = new FileInputStream( rootPath + "\\" + "save.sav" );
            ObjectInputStream ois = new ObjectInputStream( fis );
            GameInstance game = (GameInstance) ois.readObject();
            return game;
        }
        catch( Exception e ){
            System.out.println( "Couldn't load savegame." );
            return null;
        }
    }

    public static void saveHighscore( String username, int score ) throws IOException {
        String rootPath = new String( System.getProperty( "user.dir" ) );
        File file = new File( rootPath + "\\" + "highscore.score" );
        boolean newFile = false;

        if( !file.exists() || !file.isFile() ){
            file.createNewFile();
            newFile = true;
            System.out.println( "First Highscore... New File created." );
        }
        if( file.exists() && file.isFile() ){
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true), "UTF-8"));

            if( !newFile ){
                writer.newLine();
            }
            writer.write( username + "&!&" + score );
            writer.close();
            System.out.println( "Highscore added." );
            System.out.println();
        } else {
            System.out.println( "Couldn't write highscore" );
            System.out.println();
        }
    }

    public static void printHighscore() throws IOException {
        String rootPath = new String( System.getProperty( "user.dir" ) );
        File file = new File( rootPath + "\\" + "highscore.score" );
        List<LinkedList<String> > allScores = new ArrayList<LinkedList<String> >();
        if( !file.exists() || !file.isFile() ){
            System.out.println( "No highscore file found." );
            return;
        }
        for( int i = 0; i<=10; i++ ){
            allScores.add( new LinkedList<String>() );
        }
        BufferedReader br = new BufferedReader(new FileReader( file.getPath() ));
        String line;

        while( ( line = br.readLine() ) != null ) {

            String[] lineArr = line.split( "&!&" );

            if( lineArr.length != 2 ) {
                System.out.println( "Attention: There is an invalid highscore!" );
                continue;
            }

            String username = lineArr[0];
            String score = lineArr[1];

            int scoreInt = Integer.parseInt( score );
            if( !allScores.get( scoreInt ).contains( username ) ){
                allScores.get( scoreInt ).add( username );
            }
        }

        for( int i = 0; i<=10; i++ ){
            System.out.println( i + " points: " + String.join(", ", allScores.get( i )));

        }
    }

}
