import java.util.*;

public class StudentManagement {
    static Scanner sc = new Scanner(System.in);

    void createStudent(ArrayList<Student> studentList){
        String id;
        String studentName;
        int semester;
        String courseName;

        String confirmInput;
        boolean isContinue = true;
        int count = 1;
        boolean isDuplicate = false;
        boolean isNotUnique = false;

        do {
            System.out.printf("%d. \n", count);

            //id
            id = GetData.getValidId().trim();

            //name
            studentName = GetData.getValidName().trim();

            //semester
            semester = GetData.getValidInput("Please enter the semester: ", "Out of range. Please re-enter", Integer.MIN_VALUE, Integer.MAX_VALUE);

            //course
            courseName = GetData.getValidCourseName().trim();

            Student newStudentInfo = new Student(id, studentName, semester, courseName);

            //function check duplicate
            isNotUnique = CheckData.checkIdUniqueStudent(studentList, newStudentInfo);

            //check if id is not unique
            if (isNotUnique) {
                continue;
            } else {
                isDuplicate = CheckData.checkDuplicatedStudent(studentList, newStudentInfo);
                //check if duplicated
                if (isDuplicate){
                    continue;
                }
            }

            studentList.add(newStudentInfo);
            count++;

            //check if count > greater than 10
            if (count > 10) {
                System.out.print("Do you want to continue (Y/N)? Choose Y to continue, N to return main screen:");
                confirmInput = GetData.getValidConfirm("Y", "N").trim();

                //check if confirm is yes
                if (confirmInput.equalsIgnoreCase("Y")) {
                    isContinue = true;
                } else {
                    isContinue = false;
                }
            }
        } //loop if continue
        while (isContinue);
    }

    void findByIdAndSortByName(ArrayList<Student> studentList){
        ArrayList<Student> studentFound = new ArrayList<>();
        String partOfName;
        System.out.println("Please enter name of student to find: ");
        partOfName = sc.nextLine();

        //loop each student in student list
        for (Student student : studentList){
            //check if each student's name contains part of name input
            if (student.getStudentName().toLowerCase().contains(partOfName.toLowerCase())) {
                studentFound.add(student);
            }
        }

        //check if not found any student (studentFound is empty)
        if(studentFound.isEmpty()){
            System.out.println("Not found any students!");
            return;
        }

        int length = studentFound.size();

        //loop each index student in studentFound list start from first index
        for (int i = 0; i < length; i++) {
            //loop each index student in studentFound list start from next index
            for (int j = i + 1; j < length; j++) {
                //check to compare between 2 student's name
                if (studentFound.get(j).getStudentName().compareTo(studentFound.get(i).getStudentName()) < 0) {
                    //function swap 2 student
                    Collections.swap(studentFound, i, j);
                }
            }
        }

        System.out.printf("%-30s|%-8s|%-30s\n", "Student Name", "Semester", "Course Name");

        //loop each student in studentFound list
        for (Student student : studentFound){
            System.out.printf("%-30s|%-8d|%-30s\n", student.getStudentName(), student.getSemester(), student.getCourseName());
        }

        studentFound.clear();
    }

    void updateOrDeleteById(ArrayList<Student> studentList) {
        HashMap<Integer, Integer> studentFound = new LinkedHashMap<>();
        String id;
        int stt = 0;
        System.out.print("Please enter id to found: ");
        id = sc.nextLine().trim();

        //loop each index of student in student list
        for (int i = 0; i < studentList.size(); i++) {
            //check if id is found
            if (studentList.get(i).getId().equals(id)) {
                stt++;
                studentFound.put(stt, i);
            }
        }

        //check if not found any student (studentFound is empty)
        if(studentFound.isEmpty()) {
            System.out.println("Not found any students");
            return;
        }

        //loop each student in studentFound
        for (Map.Entry<Integer, Integer> student : studentFound.entrySet()) {
            int key = student.getKey();
            int valueIndex = student.getValue();
            System.out.println(key + ". " + studentList.get(valueIndex).toString());
        }

        System.out.println("Which one do you want to choose?");
        int choice = GetData.getValidInput("Please choose the student: ", "Out of range. Please re-enter!", 1, studentFound.size());

        System.out.println("Do you want to update (U) or delete (D) student. If user chooses U, the program allows user updating. Choose D for deleting student.");
        String confirmInput = GetData.getValidConfirm("U", "D");

        //check if confirm is update
        if(confirmInput.equalsIgnoreCase("U")) {
            boolean isDuplicate = false;
            boolean isNotUnique = false;
            boolean isContinue = false;
            Student updatedStudent = studentList.get(studentFound.get(choice));
            String updatedId;
            String updatedName;
            int updatedSemester;
            String updatedCourse;

            do {
                updatedId = GetData.getValidId();
                updatedName = GetData.getValidName();
                updatedSemester = GetData.getValidInput("Please enter the semester: ", "Out of range. Please re-enter", Integer.MIN_VALUE, Integer.MAX_VALUE);
                updatedCourse = GetData.getValidCourseName();

                isNotUnique = CheckData.checkIdUniqueStudent(studentList, new Student(updatedId, updatedName, updatedSemester, updatedCourse));

                //check if id is not unique
                if (!isNotUnique) {
                    isContinue = true;
                }
                //check if id is unique
                if (isNotUnique) {
                    isDuplicate = CheckData.checkDuplicatedStudent(studentList, new Student(updatedId, updatedName, updatedSemester, updatedCourse));
                    //check if duplicated
                    if(isDuplicate){
                        isContinue = true;
                    } else isContinue = false;
                }
            } //loop if continue (when not dupplicate)
            while (isContinue);

            updatedStudent.setId(updatedId);
            updatedStudent.setStudentName(updatedName);
            updatedStudent.setSemester(updatedSemester);
            updatedStudent.setCourseName(updatedCourse);

            System.out.println("Update completed!");
        }

        //check if confirm is delete
        else if(confirmInput.equalsIgnoreCase("D")){
            studentList.remove((int)studentFound.get(choice));
            System.out.println("Remove completed!");
        }
    }

    void reportCourse(ArrayList<Student> studentList){
        System.out.printf("%-30s|%-30s%-10s\n", "Student Name", "Course Name", "Total");

        //Java
        courseReport("Java", studentList);
        //.Net
        courseReport(" Net", studentList);
        //C/C++
        courseReport("C/C", studentList);
    }
    void courseReport(String courseName, ArrayList<Student> studentList){
        int count;
        ArrayList<Student> studentCourseList = new ArrayList<>();

        // loop each student in studentList
        for (Student student : studentList) {
            // check if student learn courseName
            if (student.getCourseName().equals(courseName)) {
                studentCourseList.add(student);
            }
        }

        boolean isAccept;

        // loop each student in studentCourseList by index
        for (int i = 0; i < studentCourseList.size(); i++) {
            count = 1;
            isAccept = true;

            // check if i is greater than 0
            if (i > 0) {
                // loop each student before current in studentCourseList by index
                for (int k = i - 1; k >= 0; k--) {
                    // check if same id with current
                    if (studentCourseList.get(k).getId().equals(studentCourseList.get(i).getId())) {
                        isAccept = false;
                        break;
                    }
                }
            }

            // Check if count accepted
            if (isAccept) {
                // loop each next student in studentCourseList
                for (int j = i + 1; j < studentCourseList.size(); j++) {
                    // check if same id
                    if (studentCourseList.get(j).getId().equals(studentCourseList.get(i).getId())) {
                        count++;
                    }
                }

                System.out.printf("%-30s|%-30s|%-10d\n",
                        studentCourseList.get(i).getStudentName(),
                        studentCourseList.get(i).getCourseName(),
                        count);
            }
        }

        studentCourseList.clear();
    }
}
