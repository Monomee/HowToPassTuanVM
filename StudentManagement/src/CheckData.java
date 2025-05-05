import java.util.ArrayList;

public class CheckData {
    public static boolean checkDuplicatedStudent(ArrayList<Student> studentList, Student newStudentInfo) {
        boolean isDuplicate = false;
        int countCourse = 1;
        //loop each student in studentList
        for (Student student : studentList) {
            // 1 người -> 1 kì -> 1 môn/lần (thầy sẽ hỏi về logic này)
            //check if id is equal (same student)
            if(newStudentInfo.getId().equals(student.getId())){
                //check if semester is equal (same semester)
                if(newStudentInfo.getSemester() == student.getSemester()){
                    //check if same course
                    if(newStudentInfo.getCourseName().equals(student.getCourseName())){
                        countCourse++;
                    }
                }
            }
        }
        //Check if course greater than 1
        if(countCourse > 1){
            System.out.println("Student duplicated. Please re-enter");
            isDuplicate = true;
        }
        return isDuplicate;
    }

    public static boolean checkIdUniqueStudent(ArrayList<Student> studentList, Student newStudentInfo) {
        boolean isNotUnique = false;
        //loop each student in studentList
        for (Student student : studentList){
            //check if id is equal
            if (newStudentInfo.getId().equals(student.getId())) {
                //check if name is not equal
                if(!newStudentInfo.getStudentName().equals(student.getStudentName())) {
                    System.out.println("Id is unique. Please re-enter");
                    isNotUnique = true;
                    break;
                }
            }
        }
        return isNotUnique;
    }
}
