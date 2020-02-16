import java.io.IOException;
import java.util.Scanner;

//if you want to add new functionality that is accessible while playing the game or creating new questions
//remember to add the keyword to the bannedNames array in the StringHandler Class and remove questions from existing files that have them as an answer or question

public class Main {

    static GameInstance game;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        String input = new String();


        System.out.println( "Hello dear player!" );
        System.out.println( "You can quit the program whenever you want by simply typing 'q' or 'Q'." );
        System.out.println( "To start a new game type 'Start' or 'start'. With this you can also start a new game while playing." );
        System.out.println( "To add new questions to the pool, type 'add'. This only works when no game is currently running." );
        System.out.println( "Please have fun :)" );

        while( true ){

            input = scanner.nextLine();
            if( input.isEmpty() ){
                continue;
            }
            if( StringHandler.checkIfQuit( input ) ) {
                System.exit(0);
            }

            if( StringHandler.checkIfStart( input ) ){
                if( game == null ){
                    game = new GameInstance();
                    continue;
                }
                if( game.getStatus() ){
                    System.out.println( "Do you really want to abort your current game and start a new one? ( type 'y' if yes )" );
                    input = scanner.nextLine();
                    if( !StringHandler.checkIfYesOrNo( input ) ) {
                        System.out.println( "Then please continue with answering your questions :)" );
                        continue;
                    } else {
                        game = new GameInstance();
                        continue;
                    }
                }
            }
            if( game == null ){

                if( StringHandler.checkIfAdd( input ) ){
                    DataHandler.createNewQuestion( scanner );
                }

                continue;
            }

            boolean gameFinished = game.nextStep( input );

            if( gameFinished ){
                game = null;
                System.out.println( "That was the last question.");
                System.out.println( "Type 'start' to start a new one, 'q' to quit or 'add' to add a new question to the game");
            }

        }
    }
}
