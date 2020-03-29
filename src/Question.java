import java.io.Serializable;
import java.util.List;

//this class is a wrapper for easy handling of single questions,
//this is useful for example if you wanted to add another question type it would be pretty easy to do here

public class Question implements Serializable {

    private String category;
    private String question;
    private String correctAnswer;
    private List<String> allAnswers;
    //type 1 == multiple choice, type 2 == standard question
    private int type;

    public Question( String category, String question, String correctAnswer, List<String> allAnswers ){
        this.category = category;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.allAnswers = allAnswers;
        if( this.allAnswers.size() == 1 ){
            this.type = 2;
        } else {
            this.type = 1;
        }
    }

    public String getCategory(){
        return this.category;
    }
    public int getType(){
        return this.type;
    }

    public String getQuestion(){
        return this.question;
    }

    public List<String> getAllAnswers(){
        return this.allAnswers;
    }

    public String getCorrectAnswer(){
        return this.correctAnswer;
    }

    public boolean equals( Question question ){

        for ( String answer : this.allAnswers ) {
            boolean found = false;
            for( String answer2 : question.getAllAnswers() ){
                if( answer.equals( answer2 ) ){
                    found = true;
                }
            }
            if( !found ) return false;
        }

        for ( String answer : question.getAllAnswers() ) {
            boolean found = false;
            for( String answer2 : this.allAnswers ){
                if( answer.equals( answer2 ) ){
                    found = true;
                }
            }
            if( !found ) return false;
        }

        if( this.allAnswers.size() != question.getAllAnswers().size() ) return false;

        if( this.category == question.category
            && this.correctAnswer ==question.correctAnswer
            && this.type == question.type
            && this.question == question.question ){
            return true;
        } else {
            return false;
        }
    }

    public String toString(){
        return this.question + "&!&" + String.join( "&!&", this.allAnswers );
    }

}
