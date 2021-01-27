package InterviewNinja_GAME;

public class QuestionAndSolution {

    public int isSolvedCount = 0;
    public int numberOfQuestion;

    public String questionPart, solutionPart;
    public String time;
    public String topic;




    public QuestionAndSolution(int numberOfQuestion) {//constructor format
        this.numberOfQuestion = numberOfQuestion;


    }

    public void questionAnswered() {
        isSolvedCount++;

    }



    @Override
    public String toString() {
        return questionPart +"\n"+
                " isSolvedCount=" + isSolvedCount +
                ", numberOfQuestion='" + numberOfQuestion + '\'' +
                '}';
    }


}
