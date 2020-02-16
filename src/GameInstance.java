import java.io.IOException;
import java.util.*;

public class GameInstance {
    private int numberOfQuestions;
    private List<String> categories;
    private List<Question> questionList;
    private List<String> result;
    private boolean gameIsCurrentlyRunning;
    private boolean hasAllCategories;
    private int questionType;

    private HashMap<String, List<Question>> allQuestions;
    private int numberOfAllMultipleChoiceQuestions;
    private int numberOfAllStandardQuestions;
    private int currentCorrectAnswer;
    private int currentQuestionNr;

    public GameInstance() {
        this.numberOfQuestions = 0;
        this.questionList = new LinkedList<Question>();
        this.categories = new LinkedList<String>();
        this.result = new LinkedList<String>();
        this.questionType = 0;
        this.hasAllCategories = false;
        this.currentCorrectAnswer = 0;
        this.currentQuestionNr = 1;
        this.gameIsCurrentlyRunning = false;
        try {
            this.allQuestions = DataHandler.getAllQuestions();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (this.allQuestions.isEmpty()) {
            System.out.println( "There is no data available to create a new game" );
            System.out.println( "Question Files need to be placed here:" );
            System.out.println( System.getProperty( "user.dir" ) );
            System.out.println( "You can also add new questions by typing 'add' while no game is running." );
            System.exit(1);
        }

        this.numberOfAllMultipleChoiceQuestions = 0;
        this.numberOfAllStandardQuestions = 0;

        System.out.println("There are currently the following categories available:");
        System.out.println();

        this.allQuestions.forEach((category, questionList) -> System.out.println(category + " (" + questionList.size() + " questions)"));
        System.out.println();
        System.out.println( "You can either start a standard game by typing 'standard', choose all categories by typing 'all' or add categories one by one.");
        System.out.println( "Only standard games can enter the highscore. Standard games consist of 10 random questions from all categories." );
        System.out.println( "To add a category type its name. If you don't want to add anymore categories type 'continue'" );

    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    private void appendQuestion(Question question) {
        this.questionList.add(question);
    }

    private void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public boolean getStatus() {
        if (this.gameIsCurrentlyRunning) {
            return true;
        } else {
            return false;
        }
    }

    public void createGame(){

        int currentNumberOfQuestions = 0;
        Random random = new Random();

        while( currentNumberOfQuestions < numberOfQuestions ){

            String tmpCategory = this.categories.get( random.nextInt( categories.size() ) );
            while( this.allQuestions.get( tmpCategory ).isEmpty() ){
                this.categories.remove( tmpCategory );
                tmpCategory = this.categories.get( random.nextInt( categories.size() ) );
            }

            int tmpInt = random.nextInt( this.allQuestions.get( tmpCategory ).size() );

            Question tmpQuestion = this.allQuestions.get( tmpCategory ).get( tmpInt );
            this.allQuestions.get( tmpCategory ).remove( tmpInt );


            if( this.questionType != 3 && this.questionType != tmpQuestion.getType() ){
                continue;
            }

            boolean found = false;

            questionList.add( tmpQuestion );
            currentNumberOfQuestions++;

        }
    }

    public boolean nextStep( String input ) {

        if ( !this.gameIsCurrentlyRunning ) {
            this.getGameSetup( input );
            return false;
        } else {
            if( this.questionList.isEmpty() ){
                return true;
            }
            boolean lastQuestion = this.nextQuestion( input );
            return lastQuestion;
        }

    }

    public boolean nextQuestion( String input ){
        if( this.questionList.get( 0 ).getType() == 1 ){
            int number = 0;
            try {
                number = Integer.parseInt( input );
            } catch ( NumberFormatException e ) {
                System.out.println("This doesn't seem to be a valid number. Try again");
                return false;
            }
            if( number == this.currentCorrectAnswer ){
                System.out.println( "Correct! Next Question :)");
            } else {
                System.out.println( "What a shame..." );
                System.out.println( "The correct answer was: " + this.questionList.get( 0 ).getCorrectAnswer() );
            }

        } else {

            if( input.toUpperCase().equals( this.questionList.get( 0 ).getCorrectAnswer().toUpperCase() ) ){
                System.out.println( "Correct! Next Question :)");
            } else {
                System.out.println( "What a shame..." );
                System.out.println( "The correct answer was: " + this.questionList.get( 0 ).getCorrectAnswer() );
            }

        }

        this.questionList.remove( 0 );
        this.currentQuestionNr++;
        if( !this.questionList.isEmpty() ){
            this.printQuestion( this.questionList.get( 0 ), this.currentQuestionNr );
            return false;
        }
        return true;


    }

    public boolean getGameSetup(String input) {

        //add question categories
        if ( !this.hasAllCategories ) {
            if ( StringHandler.checkIfContinue(input) && !this.categories.isEmpty() ) {
                this.hasAllCategories = true;
                System.out.println();
                System.out.println("Next, please choose how many questions you want to get during your game.");

                for( String cat : this.categories ) {
                    List<Question> questions = this.allQuestions.get( cat );
                    for( Question question : questions ){
                        if( question.getType() == 1 ){
                            this.numberOfAllMultipleChoiceQuestions++;
                        } else {
                            this.numberOfAllStandardQuestions++;
                        }
                    }
                }

                int totalCountOfQuestions = this.numberOfAllStandardQuestions + this.numberOfAllMultipleChoiceQuestions;
                System.out.println("Please enter a number between 1 and " + totalCountOfQuestions);
                System.out.println( "Note: there is only " + this.numberOfAllMultipleChoiceQuestions + " multiple choice questions and " + this.numberOfAllStandardQuestions + " standard questions available. If you choose a higher number you won't get to choose to only play these categories.");
                return true;
            }

            if (StringHandler.checkIfContinue(input) && this.categories.isEmpty()) {
                System.out.println("You need to choose at least 1 category before you can continue");
                return false;
            }

            if( input.equals( "all" ) || input.equals( "standard" ) ) {

                for( Map.Entry<String, List<Question>> entry : this.allQuestions.entrySet() ) {
                    List<Question> questions = entry.getValue();
                    for( Question question : questions ){
                        if( question.getType() == 1 ){
                            this.numberOfAllMultipleChoiceQuestions++;
                        } else {
                            this.numberOfAllStandardQuestions++;
                        }
                    }
                }
                if( this.numberOfAllStandardQuestions + this.numberOfAllMultipleChoiceQuestions < 10 && input.equals( "standard" ) ){
                    System.out.println( "There is not enough questions available (minimum 10) to do a standard game." );
                    System.out.println();
                    return false;
                }
                this.allQuestions.forEach((category, questionList) -> this.categories.add( category ) );
                this.hasAllCategories = true;

                if( input.equals( "standard" ) ){

                    this.numberOfQuestions = 10;
                    this.hasAllCategories = true;
                    this.questionType = 3;
                    this.createGame();
                    this.gameIsCurrentlyRunning = true;
                    System.out.println( "Questions are now coming..." );
                    printQuestion( this.questionList.get( 0 ), 1 );
                } else {
                    System.out.println();
                    System.out.println("Next, please choose how many questions you want to get during your game.");
                    int totalCountOfQuestions = this.numberOfAllStandardQuestions + this.numberOfAllMultipleChoiceQuestions;
                    System.out.println("Please enter a number between 1 and " + totalCountOfQuestions);
                    System.out.println( "Note: there is only " + this.numberOfAllMultipleChoiceQuestions + " multiple choice questions and " + this.numberOfAllStandardQuestions + " standard questions available. If you choose a higher number you won't get to choose to only play these categories.");
                }
                return true;
            }

            List<Question> questionList = this.allQuestions.get( input );

            if (questionList == null) {
                System.out.println(input + " is not a valid category option ");
                System.out.println();
                return false;
            }

            if (this.categories.contains(input)) {
                System.out.println("You have already chosen " + input);
                System.out.println();
                return false;
            }
            this.categories.add( input );
            System.out.println( input + " has been added to the categories.");
            return true;

        }

        //add number of questions
        if (this.numberOfQuestions == 0) {
            int totalCountOfQuestions = this.numberOfAllStandardQuestions + this.numberOfAllMultipleChoiceQuestions;

            int number = 0;
            try {
                number = Integer.parseInt( input );
            } catch ( NumberFormatException e ) {
                System.out.println("This doesn't seem to be a valid number. Try again");
                return false;
            }

            if ( number > totalCountOfQuestions ) {
                System.out.println("That number is too large. Not enough questions available");
                return false;
            }
            if ( number < 1 ) {
                System.out.println("That number is too small.");
                return false;
            }
            this.numberOfQuestions = number;


            if( this.numberOfAllStandardQuestions >= this.numberOfQuestions || this.numberOfAllMultipleChoiceQuestions >= this.numberOfQuestions ){

                System.out.println();
                System.out.println("Do you want to: ");

                if( this.numberOfAllMultipleChoiceQuestions >= this.numberOfQuestions ){
                    System.out.println("1: Do only multiple choice");
                }
                if( this.numberOfAllStandardQuestions >= this.numberOfQuestions ){
                    System.out.println("2. Do only standard questions");
                }
                System.out.println("3. Do whatever questions?");
                return true;

            } else {
                this.questionType = 3;
                this.createGame();
                this.gameIsCurrentlyRunning = true;
                System.out.println( "Questions are now coming..." );
                printQuestion( this.questionList.get( 0 ), 1 );

                return true;
            }

        }

        //get desired type of question
        if (this.questionType == 0) {
            if( input.equals( "1" ) && this.numberOfAllMultipleChoiceQuestions >= this.numberOfQuestions ){
                this.questionType = 1;
            } else if( input.equals( "2" ) && this.numberOfAllStandardQuestions >= this.numberOfQuestions ){
                this.questionType = 2;
            } else if( input.equals( "3") ){
                this.questionType = 3;
            } else {
                System.out.println( "please enter a valid option" );
                return false;
            }
        }

        this.createGame();
        this.gameIsCurrentlyRunning = true;
        System.out.println( "Questions are now coming..." );
        printQuestion( this.questionList.get( 0 ), 1 );

        return true;
    }

    public void printQuestion( Question question, int questionNr ){
        System.out.println( "Question " + questionNr );
        System.out.println( "Category: " + question.getCategory() );
        System.out.println( "Question: " + question.getQuestion() );
        if( question.getType() == 2 ){
            System.out.print( "Type answer: " );
            return;
        } else {
            List<String> answerList = question.getAllAnswers();
            Collections.shuffle( answerList );
            for( int i = 1; i <= answerList.size(); i++ ){
                System.out.println( i + ") " + answerList.get( i-1 ) );
                if( answerList.get( i-1 ).equals( question.getCorrectAnswer() ) ){
                    this.currentCorrectAnswer = i;
                }
            }
        }
    }
}