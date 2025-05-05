import java.util.Scanner;

public class GetData {
    static Scanner sc = new Scanner(System.in);

    public static String getValidId() {
        String input;
        do {
            System.out.print("Please enter Id: ");
            input = sc.nextLine();
            // check if empty
            if (input.isEmpty()) {
                System.out.println("Id must not be empty. Please re-enter");
            }
        } //loop while input is empty
        while (input.isEmpty());

        return input;
    }

    public static String getValidName() {
        String input;
        do {
            System.out.print("Please enter student's name: ");
            input = sc.nextLine();
            // check if empty
            if (input.isEmpty()) {
                System.out.println("Name must not be empty. Please re-enter");
            }
        } //loop while input is empty
        while (input.isEmpty());

        return input;
    }

    public static int getValidInput(String msg, String outOfRangeMsg, int min, int max) {
        String input;
        int output = 0;
        boolean isValid = false;

        // loop until get valid input
        while (!isValid) {
            System.out.print(msg);
            input = sc.nextLine();
            // check if input is empty
            if (input.isEmpty()) {
                System.out.println("Input must not be empty. Please re-enter");
                continue;
            }

            // handle format of input
            try {
                // convert string(output) to number(input)
                output = Integer.parseInt(input);
                // check if output is in range (from min to max)
                if (output >= min && output <= max) {
                    isValid = true;
                } else {
                    System.out.println(outOfRangeMsg);
                }
            } //catch if output is not integer
            catch (NumberFormatException e) {
                System.out.println("Input must be integer. Please re-enter");
            }
        }
        return output;
    }

    public static String getValidCourseName(){
        String courseName = "";
        int choice = getValidInput("Please enter the course name (Enter 1.\"Java\", 2.\".Net\", 3.\"C/C++\"): ", "Choice must be from 1 to 3. Please re-enter!", 1, 3);

        //check if choice is 1
        if(choice==1){
            courseName = "Java";
        } //check if choice is 2
        else if (choice==2) {
            courseName = ".Net";
        } //check if choice is 3
        else if (choice==3) {
            courseName = "C/C++";
        }
        return courseName;
    }

    public static String getValidConfirm(String choice1, String choice2){
        String input = "";
        boolean isValid = false;

        //loop until get valid input
        while (!isValid){
            System.out.println("Please confirm: ");
            input = sc.nextLine();

            //check if empty
            if(input.isEmpty()){
                System.out.println("Input must not empty. Please re-enter!");
                continue;
            }
            //check if input is choice 1 or 2
            if(input.equalsIgnoreCase(choice1) || input.equalsIgnoreCase(choice2)){
                isValid = true;
            } else {
                System.out.println("Wrong confirm. Please re-enter " + choice1 + " or " + choice2);
                isValid = false;
            }
        }
        return input;
    }
}
