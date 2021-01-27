package InterviewNinja_GAME;

public class Test_TypeWriterEffect {
    public static void main(String[] args) throws InterruptedException {

        String word = "Cybertek";

        for(int i=0; i<word.length();i++){
            String eachChar = word.charAt(i)+"";
            System.out.print(eachChar);
            Thread.sleep(500);

        }



    }
}
