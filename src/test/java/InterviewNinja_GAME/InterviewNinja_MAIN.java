package InterviewNinja_GAME;

/*
@Authors: Daniel Vanshtein and Steph Galvez
 */

//EXPECTED RELEASE DATE: 02/01/21**********************

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class InterviewNinja_MAIN {


    //PART I - Question format setup
    //Custom class object array created
    //Located in the "MAIN" class for accessibility

    //Custom class object array created ---> Located in the "MAIN" class for accessibility ---> Specifier: static. One copy needed for all the classes.
    public static ArrayList<QuestionAndSolution> wholeQuestionsWithSolutionList = new ArrayList<>();

    //Custom method with "FileInputStream" parameter
    public static void populateQuestionList(FileInputStream file1) {
        //Scanner object to read from "file1" instead of "System.in"
        Scanner scanner = new Scanner(file1);
        //ArrayList created to add all the interview questions from "file1"
        ArrayList<String> interviewQuestions = new ArrayList<>();
        //While loop added ---> loop will run each line of "file1". Each line is treated like an index.
        while (scanner.hasNext()) {
            interviewQuestions.add(scanner.nextLine());
        }

        //For loop will iterate throughout the interviewQuestions ArrayList and it'll add each question to the wholeQuestionsWithSolutionList ArrayList
        for (int i = 0; i < interviewQuestions.size(); i++) {
            wholeQuestionsWithSolutionList.add(new QuestionAndSolution((i + 1)));
            wholeQuestionsWithSolutionList.get(i).questionPart = interviewQuestions.get(i).substring(interviewQuestions.get(i).indexOf("]") + 2, interviewQuestions.get(i).indexOf("-"));
            wholeQuestionsWithSolutionList.get(i).solutionPart = interviewQuestions.get(i).substring(interviewQuestions.get(i).indexOf("-") + 2);
            wholeQuestionsWithSolutionList.get(i).time = interviewQuestions.get(i).substring(interviewQuestions.get(i).indexOf("min.") - 2, interviewQuestions.get(i).indexOf("min."));
            wholeQuestionsWithSolutionList.get(i).topic = interviewQuestions.get(i).substring(interviewQuestions.get(i).indexOf("[") + 1, interviewQuestions.get(i).indexOf("]"));

        }
    }


    //PART II - The timer setup

    public static void questionAndTimeWindowSetup(String str, String time) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        WebDriver driver1 = new ChromeDriver();


        Dimension d1 = new Dimension(1800, 500);
        driver1.manage().window().setSize(d1);
        driver1.get("https://watsgucci.github.io/");// from a different project/repo. This is the github domain i created thats given to me from my github account!. HTML file is in watsgucci.github.io repo.

        //=================================================================================
        //TYPEWRITER STARTS
        //=================================================================================
        for (int i = 0; i < str.length(); i++) {
            String eachChar = str.charAt(i) + "";
            driver1.findElement(By.name("questionText")).sendKeys((eachChar));
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted doodoo head" + e.getMessage());
            }
        }

        //=================================================================================
        //driver2 (timer) is initiated
        //=================================================================================
        WebDriver driver2 = new ChromeDriver();
        Dimension d2 = new Dimension(1800, 500);
        Point p2 = new Point(0, 550);
        driver2.manage().window().setPosition(p2);
        driver2.manage().window().setSize(d2);
        driver2.get("https://timer.onlineclock.net/");//goes to URL


        Select dropDown = new Select(driver2.findElement(By.id("minutesSelect")));//FORM SOURCE CODE
        //WORK ON THE TIMER
        if (time.contains("1")) { // if the timer is "1 Minute" then we need to differentiate it from "Minutes"
            dropDown.selectByVisibleText(time.concat("Minute"));
        } else {
            dropDown.selectByVisibleText(time.concat("Minutes"));
        }


        //ADD TO LOOP A BUTTON IN HTML THAT WILL HELP US CLOSE THE BROWSERS IF THE PERSON ANSWERS THE QUESTION EARLIER THAN TIMER.
        //OR MAYBE TEXT IN THE INPUT FIELD FOR IT TO DETECT A STOP

        //Still Working... to Good Job! Closing Browsers Now... // ELEMENT = ""


        while (driver2.getCurrentUrl().equals("https://timer.onlineclock.net/")) {
            driver2.getCurrentUrl();

            if (driver2.getCurrentUrl().equals("https://timer.onlineclock.net/alarm.html") || driver1.findElement(By.xpath("//div[@id='myDIV']")).getText().contains("Good")) {
                driver2.quit();
                break;
            }


        }

        driver2.quit();
        driver1.quit();
    }

    //Todo add an "about me" part in this game somewhere - Daniel
    //PART III - Player selection setup
    /*
TODO ADD PEOPLE SO THAT WE CAN KNOW THE ORDER OF THE PEOPLE WHO WILL GO. - Steph start the new custom class for people. Daniel work on it after.
So in RoundRobin, if there are 5 players, we want to enter everyone names and then prompt the name later in the game when its that
person's turn.
 */
    //Custom method created to develop the player's selection
    public static void playersSelection() {

        //ArrayList created to add the name of the players
        ArrayList<String> playersNames = new ArrayList<>();
        ArrayList<String> tempPlayersNames = new ArrayList<>();

        //Scanner object added to take the player's input for both number of players and name of the players
        Scanner scan = new Scanner(System.in);
        //Random object created to make random selection from the ArrayList
        Random rand3 = new Random();

        System.out.println("\tHow many players?");
        int players = scan.nextInt();
        int count = 0;

        //For loop used to iterate throughout the number of players entered by user.
        // With the conditions inside the loop, it will be determined if player's name need to be entered
        for (int i = 0; i < players; i++) {
            if (players == 1) {
                System.out.println("You are playing SOLO");
                continue;
            } else {
                count++;
                System.out.println("Enter player " + count + " name");
                String playerName = scan.next();
                playersNames.add(playerName);
            }
        }

        System.out.println("\tPlayers order will be as follow: ");

        //While loop used to iterate throughout the playersNames ArrayList. The loop will run as long the list is not empty
        //In this loop the random variable will be initialized and it will pick name randomly from the playersName ArrayList.
        //The name picked will be added to the tempPlayersName ArrayList
        while (!playersNames.isEmpty()) {
            int randName = rand3.nextInt(playersNames.size());
            tempPlayersNames.add(playersNames.get(randName));
            playersNames.remove(randName);
        }
        System.out.println(tempPlayersNames);
    }


    //PART IV - Game setup


    public static void main(String[] args) throws FileNotFoundException, InterruptedException {


        //Try & Catch exception added in the case "file1" path is lost
        try {
            FileInputStream file1 = new FileInputStream("src/test/java/InterviewNinja_GAME/Questions.txt");
            populateQuestionList(file1); // method to populate an array with all the questions from Questions.txt
        } catch (FileNotFoundException e) {
            System.err.println("File path is wrong dude, Go To File1 and Change its path! I wont Load the questions/program till you change it");
            System.exit(0);
        }

        //Scanner object added to take the player's input regarding game styles and topics
        Scanner scan = new Scanner(System.in);

        /* Commented out the players
        System.out.println("How many players?");
        int players = scan.nextInt();
        */

        //Todo add info about the actual amount of questions and amount of questions of each topic..an INFO option - Daniel


        System.out.println("\n" +
                "██╗███╗░░██╗████████╗███████╗██████╗░██╗░░░██╗██╗███████╗░██╗░░░░░░░██╗  ███╗░░██╗██╗███╗░░██╗░░░░░██╗░█████╗░\n" +
                "██║████╗░██║╚══██╔══╝██╔════╝██╔══██╗██║░░░██║██║██╔════╝░██║░░██╗░░██║  ████╗░██║██║████╗░██║░░░░░██║██╔══██╗\n" +
                "██║██╔██╗██║░░░██║░░░█████╗░░██████╔╝╚██╗░██╔╝██║█████╗░░░╚██╗████╗██╔╝  ██╔██╗██║██║██╔██╗██║░░░░░██║███████║\n" +
                "██║██║╚████║░░░██║░░░██╔══╝░░██╔══██╗░╚████╔╝░██║██╔══╝░░░░████╔═████║░  ██║╚████║██║██║╚████║██╗░░██║██╔══██║\n" +
                "██║██║░╚███║░░░██║░░░███████╗██║░░██║░░╚██╔╝░░██║███████╗░░╚██╔╝░╚██╔╝░  ██║░╚███║██║██║░╚███║╚█████╔╝██║░░██║\n" +
                "╚═╝╚═╝░░╚══╝░░░╚═╝░░░╚══════╝╚═╝░░╚═╝░░░╚═╝░░░╚═╝╚══════╝░░░╚═╝░░░╚═╝░░  ╚═╝░░╚══╝╚═╝╚═╝░░╚══╝░╚════╝░╚═╝░░╚═╝");
        System.out.println("_______________________________________________________________________________________________________________________");


        System.out.println("What style of INTERVIEW NINJA do you want to play?");

        //*********************************************************
        //=========================================================
        System.out.println("\t[1] Round Robin Style - Multiplayer");
        /*
        RoundRobin Explanation:
        1. Gets a random question from the Questions.txt and sets a timer for it.
        2. Runs until list is finished.
        3. Each question can only be asked twice.
         */
        //*********************************************************
        //=========================================================

        System.out.println("\t[2] Topic Based Style - Multiplayer");
        /*
        TopicBased Explanation:
        1. Presents player(s) with a menu of topics to pick from.
        2. Takes every question with that topic and adds it to an ArrayList.
        3. Runs the randomQuestion generator.
        4. Each question can only be asked once.
         */
        //*********************************************************
        //=========================================================

        System.out.println();

        //Customizes the output message for style of game the user chose to play
        System.out.print(">̶ Style Number: ");
        int typeOfGame = scan.nextInt();
        if (typeOfGame != 69) {//too not print this in case we are accessing admin settings
            String styleChosen = "";
            styleChosen = (typeOfGame == 1) ? "ROUND ROBIN STYLE" : "TOPIC BASED STYLE";
            System.out.println("\n>̶ You have chosen " + styleChosen);
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (typeOfGame == 69) { //secret admin access. Runs the adminStatistics Method
            try {
                FileInputStream file2 = new FileInputStream("src/test/java/InterviewNinja_GAME/Questions.txt");
                adminStatistics(file2); // method to populate an array with all the questions from Questions.txt
            } catch (FileNotFoundException e) {
                System.err.println("File path is wrong dude, Go To File1 and Change its path! I wont Load the questions/program till you change it");
                System.exit(0);
            }
            System.exit(0);
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        //*********************************************************
        //=========================================================

        //Player selection custom method call
        playersSelection();


        //*********************************************************
        //=========================================================
        //If conditions(1 of 2) implemented to move throughout the different game styles according to player's selection.
        if (typeOfGame == 1) {

            roundRobin:
            //While loop implemented. It will run the game as long player selects to do so.
            while (true) {

                //Random object created to randomly select a question from the wholeQuestionSolutionList ArrayList
                Random rand = new Random();

                //For loop implemented to control the number of questions printed out
                for (int i = 0; i < 1; i++) {

                    //Initializing a random number using our RandomClass object "rand"
                    int randInt = rand.nextInt(wholeQuestionsWithSolutionList.size());
                    wholeQuestionsWithSolutionList.get(randInt).questionAnswered();
                    questionAndTimeWindowSetup(wholeQuestionsWithSolutionList.get(randInt).questionPart, wholeQuestionsWithSolutionList.get(randInt).time); // RUNS
                    // TODO Make it come out with a typeWriter effect, use a diff method with an, arrayList, for loop and sleep..make the sum of the milliseconds open the second window

//TODO task = NEED TO CHANGE THIS STATEMENT BELOW TO PREDICATE FORM! - Steph (DONE DONE DONE)

                    //Lambda expression: Predicate (removedIf();) used to removed questions that have been selected twice already
                    wholeQuestionsWithSolutionList.removeIf(p -> p.isSolvedCount >= 2);

                    /*
                    if (wholeQuestionsWithSolutionList.get(randInt).isSolvedCount >= 2) {
                        wholeQuestionsWithSolutionList.remove(wholeQuestionsWithSolutionList.get(randInt));
                    }

                    */

                    //Question and answer to be displayed after browsers quit
                    System.out.println("===================================================================================================================================================");

                    //Prints the question.
                    System.out.println(wholeQuestionsWithSolutionList.get(randInt).questionPart);
                    //Prints out the solution.
                    System.out.println(wholeQuestionsWithSolutionList.get(randInt).solutionPart);

                    System.out.println("===================================================================================================================================================");
                }

                //If condition implemented to notify player if there are no more questions in the ArrayList
                if (wholeQuestionsWithSolutionList.isEmpty()) {
                    System.out.println(">̶ No more Questions. Thanks for playing");
                    break;
                }

                System.out.println(">̶ Do you want to continue?");
                System.out.print("Yes or No: ");
                String continueYesOrNo = scan.next();
                continueYesOrNo = continueYesOrNo.toLowerCase();
                //TODO*** NEED TO DO MORE FORMATTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                //If condition implemented to continue game if player's input is "yes", otherwise game will be terminated
                if (continueYesOrNo.contains("yes") || continueYesOrNo.equalsIgnoreCase("y") || continueYesOrNo.equalsIgnoreCase("1")) {
                    continue roundRobin;
                } else {
                    System.out.println("Thanks for playing!");
                    break;
                }
               /* } else if (continueYesOrNo.contains("no") || continueYesOrNo.contains("n")) {
                    System.out.println("Thanks for playing!");
                    break;
                }
                */
            }
        }
//todo add a countdown of how many questions are left of each topic and then the topic they choose count the number of questions as well - Steph

        //If conditions(2 of 2) implemented to move throughout the different game styles according to player's selection.
        if (typeOfGame == 2) {
            System.out.println("===================================================================================================================================================");
            System.out.println("WELCOME TO INTERVIEW NINJA - TOPIC!\n");

            gameMenu:
            //While loop implemented. It will run the game as long player selects to do so.
            while (true) {

                System.out.println(">̶ Select topic to be quizzed on:");
                System.out.println("[1] Soft Skills");
                System.out.println("[2] Java");
                System.out.println("[3] Selenium");
                System.out.println("[4] Git & GitHub");
                System.out.println("[5] Jira");
                System.out.println("[6] Technical ");

                //Variable that will saved player's input regarding game style
                String topicSelection = scan.next();

                //Random object created to randomly select a question from the wholeQuestionSolutionList ArrayList
                Random rand2 = new Random();

                //ArrayList created from custom class object that it will contain all the questions based on topic selection
                ArrayList<QuestionAndSolution> topicQuestionsWithSolutionList = new ArrayList<>();
                String topic = "";


                //Switch statement implemented to compare the value the "topicSelection" variable
                // topic variable will be assigned based on the number the player inputted and assigned to topicSelection
                switch (topicSelection) {

                    case "1":
                        topic = "SS";
                        break;

                    case "2":
                        topic = "Java";
                        break;

                    case "3":
                        topic = "Selenium";
                        break;

                    case "4":
                        topic = "GH";
                        break;

                    case "5":
                        topic = "Jira";
                        break;

                    case "6":
                        topic = "TQ";
                        break;

                    default://added default to make sure an inappropriate input is handled
                        System.out.println("\n******************************************************************************************************************\nYour input is not valid...Please choose from the following menu...\n");
                        continue gameMenu;

                }


                //For loop implemented to iterate throughout the wholeQuestionsWithSolutionList ArrayList and add the questions to new create ArrayList based on Topic selection
                for (QuestionAndSolution each : wholeQuestionsWithSolutionList) {
                    if (each.topic.equals(topic)) {
                        topicQuestionsWithSolutionList.add(each);
                    }
                }


                //For loop that it will iterate throughout the topicQuestionsWithSolutionList ArrayList size
                for (int j = 0; j < topicQuestionsWithSolutionList.size(); j++) {

                    //Initializing a random number using our RandomClass object "randTopic"
                    int randTopic = rand2.nextInt(topicQuestionsWithSolutionList.size());
                    topicQuestionsWithSolutionList.get(randTopic).questionAnswered();
                    questionAndTimeWindowSetup(topicQuestionsWithSolutionList.get(randTopic).questionPart, topicQuestionsWithSolutionList.get(randTopic).time);


                    System.out.println("===================================================================================================================================================");

                    //Prints the question
                    System.out.println(topicQuestionsWithSolutionList.get(randTopic).questionPart);
                    //Prints out the solution
                    System.out.println(topicQuestionsWithSolutionList.get(randTopic).solutionPart);

                    System.out.println("===================================================================================================================================================");

                    //If condition implemented to to discard questions already picked and removed from ArrayList
                    if (topicQuestionsWithSolutionList.get(randTopic).isSolvedCount >= 1) {
                        topicQuestionsWithSolutionList.remove(topicQuestionsWithSolutionList.get(randTopic));
                        j--;
                    }
                }
                //TODO Talk about what to do about prompting user or add time between questions or maybe a countdown or gives a setAmount of questions before prompting - DECIDE ON DEFAULT SETTINGS(topic based style)

                //If condition implemented to to let player know that topic ArrayList is empty
                if (topicQuestionsWithSolutionList.isEmpty()) {

                    System.out.println(">̶ No more Questions for this topic left in the ArrayList");
                    System.out.println(">̶ Do you want to try a different topic?");
                    System.out.print("Yes or No: ");
                    String continueYesOrNo = scan.next();

                    //If condition implemented to continue game if player's input is "yes", otherwise game will be terminated
                    if (continueYesOrNo.contains("yes") || continueYesOrNo.contains("y") || continueYesOrNo.equalsIgnoreCase("1")) {
                        continue;
                    } else {
                        System.out.println("Thanks for playing!");
                        break; //ONLY WAY TO BREAK FROM WHILE LOOP
                    }

                }

            }

        }

    }
//TODO Add settings to each style.

    public static void adminStatistics(FileInputStream file1) {
        List<QuestionAndSolution> javaTopicList = new ArrayList<>();
        List<QuestionAndSolution> SSTopicList = new ArrayList<>();
        List<QuestionAndSolution> TQTopicList = new ArrayList<>();
        List<QuestionAndSolution> JiraTopicList = new ArrayList<>();
        List<QuestionAndSolution> SeleniumTopicList = new ArrayList<>();
        List<QuestionAndSolution> GHTopicList = new ArrayList<>();

        List<QuestionAndSolution> timerList = new ArrayList<>();


        populateQuestionList(file1);
        for (QuestionAndSolution eachWholeQuestion : wholeQuestionsWithSolutionList) {
            if (eachWholeQuestion.topic.equals("Java")) {
                javaTopicList.add(eachWholeQuestion);
            }
        }

        wholeQuestionsWithSolutionList.forEach(p -> {
            if (p.topic.equals("SS")) SSTopicList.add(p);
        });
        wholeQuestionsWithSolutionList.forEach(p -> {
            if (p.topic.equals("TQ")) TQTopicList.add(p);
        });
        wholeQuestionsWithSolutionList.forEach(p -> {
            if (p.topic.equals("Jira")) JiraTopicList.add(p);
        });
        wholeQuestionsWithSolutionList.forEach(p -> {
            if (p.topic.equals("Selenium")) SeleniumTopicList.add(p);
        });
        wholeQuestionsWithSolutionList.forEach(p -> {
            if (p.topic.equals("GH")) GHTopicList.add(p);
        });

        System.out.println();
        System.out.println("**************************************");
        System.out.println("Java Questions: " + javaTopicList.size() + " questions");
        System.out.println("SoftSkill Questions: " + SSTopicList.size() + " questions");
        System.out.println("TQ Questions: " + TQTopicList.size() + " questions");
        System.out.println("Jira Questions: " + JiraTopicList.size() + " questions");
        System.out.println("Selenium Questions: " + SeleniumTopicList.size() + " questions");
        System.out.println("GitHub Questions: " + GHTopicList.size() + " questions");
        System.out.println("**************************************");

        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("[1] > Print out the questions for a certain topic");
        System.out.println("[2] > Remove a topic from the game");
        System.out.println("[3] > EXIT ADMIN");
        System.out.print("INPUT: ");
        int input = sc.nextInt();

        if (input == 1) {
            System.out.println();
            System.out.println("======================================");
            System.out.println("Which topic questions would you like to see?");
            System.out.println("[1] Java");
            System.out.println("[2] Soft-Skill");
            System.out.println("[3] Technical - Java");
            System.out.println("[4] Jira");
            System.out.println("[5] Selenium");
            System.out.println("[6] Git Hub");
            System.out.print("INPUT: ");
            input = sc.nextInt();

            System.out.println();
            if ((input == 1)) {

                javaTopicList.forEach(p ->
                        System.out.println(p.questionPart));
            } else {
                if ((input == 2)) {
                    SSTopicList.forEach(p ->
                            System.out.println(p.questionPart));
                } else {
                    if ((input == 3)) {
                        TQTopicList.forEach(p ->
                                System.out.println(p.questionPart));
                    } else {
                        if ((input == 4)) {
                            JiraTopicList.forEach(p ->
                                    System.out.println(p.questionPart));
                        } else {
                            if ((input == 5)) {
                                SeleniumTopicList.forEach(p ->
                                        System.out.println(p.questionPart));
                            } else {
                                GHTopicList.forEach(p ->
                                        System.out.println(p.questionPart));
                            }
                        }
                    }
                }
            }

        }
        //todo code input 2 (Remove a Topic From the Game then continue the game) - Daniel
        if (input == 3 || input == 2) { // for now we included 2 in here because we havent coded this option yet
            return;
        }


    }


}
