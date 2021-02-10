package InterviewNinja_GAME;


public class QuestionAndSolution {

    //QuestionAndSolution q1 = new QuestionAndSolution();
    //q1.topic or q1.isSolvedCount
    //if we have instance variables or instance methods we NEED an object to invoke them
    //All these variables are different for each object/question but they also BELONG to that specific object/question


    public int isSolvedCount = 0;
    public int numberOfQuestion;

    public String questionPart, solutionPart;
    public String time;
    public String topic = ""; // Had to initialize her otherwise i get a NullExceptionError later



    //constructor that attaches the questions number to that question...this is for reference purposes later
    public QuestionAndSolution(int numberOfQuestion) {//constructor format to
        this.numberOfQuestion = numberOfQuestion;
    //in order to fully assign all the variables to the object, we needed to have it read a file of questions line by line first

    }
    /*
    This is a way we were planning on referencing the object later on, kind of like having an index for each questions:
   if (q1.numberOfquestion == 60){

    }
    */
    //instance methods

    public void questionAnswered() {
        //q1.questionAnswered(); -> way to invoke. This is the way to do it. Attached to the specific object/question
        //QuestionAndSolution.questionAnswered(); --> CANNOT DO THIS
        isSolvedCount++;

    }


    //just a formality
    @Override
    public String toString() {
        return questionPart +"\n"+
                " isSolvedCount=" + isSolvedCount +
                ", numberOfQuestion='" + numberOfQuestion + '\'' +
                '}';
    }


}
