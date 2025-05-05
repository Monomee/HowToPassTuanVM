import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> studentList = new ArrayList<>();

        StudentManagement manager = new StudentManagement();
        int choice;
        boolean isExit = false;

        do {
            // Step1: Display menu
            Menu.displayMenu();

            // Step2: Get choice from user
            choice = GetData.getValidInput(
                    "Please enter input (from 1 to 5): ",
                    "Input must be in range from 1 to 5. Please re-enter!",
                    1, 5
            );

            // Step3: Do user choice
            switch (choice) {
                case 1:
                    // Function 1: Create student
                    manager.createStudent(studentList);
                    break;
                case 2:
                    // Function 2: Find by ID and Sort by name
                    manager.findByIdAndSortByName(studentList);
                    break;
                case 3:
                    // Function 3: Update/Delete by ID
                    manager.updateOrDeleteById(studentList);
                    break;
                case 4:
                    // Function 4: Report course
                    manager.reportCourse(studentList);
                    break;
                case 5:
                    // Function 5: Exit
                    System.out.println("Program closed. Goodbye!");
                    isExit = true;
                    break;
            }
        } //loop if not exit
        while (!isExit);
    }
}
