import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInstructor {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.instructor = new Instructor();
        this.student = new Student();
        this.student.registerForClass("Student", "Class", 2017);
    }

    @Test
    public void testAddHomework() {
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        assertTrue(this.instructor.homeworkExists("Class", 2017, "HW"));
    }

    @Test
    public void testAddHomeworkWrongInstructor(){
        this.instructor.addHomework("Instructor2", "Class", 2017, "HW", "Description");
        assertFalse(this.instructor.homeworkExists("Class", 2017, "HW"));
    }

    @Test
    public void testAssignGrade(){
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.student.submitHomework("Student", "HW","Answer", "Class", 2017 );
        this.instructor.assignGrade("Instructor", "Class", 2017, "HW", "Student", 90);
        assertTrue(90 == this.instructor.getGrade("Class", 2017, "HW", "Student"));
    }

    @Test
    public void testAssignGradeZero() {
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.student.submitHomework("Student", "HW","Answer", "Class", 2017 );
        this.instructor.assignGrade("Instructor", "Class", 2017, "HW", "Student", 0);
        assertTrue(0 == this.instructor.getGrade("Class", 2017, "HW", "Student"));
    }

    @Test
    public void testAssignGradeEC() {
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.student.submitHomework("Student", "HW","Answer", "Class", 2017 );
        this.instructor.assignGrade("Instructor", "Class", 2017, "HW", "Student", 101);
        assertTrue(101 == this.instructor.getGrade("Class", 2017, "HW", "Student"));
    }

    @Test
    public void testAssignGradeNeg() {
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.student.submitHomework("Student", "HW","Answer", "Class", 2017 );
        this.instructor.assignGrade("Instructor", "Class", 2017, "HW", "Student", -1);
        assertNull(this.instructor.getGrade("Class", 2017, "HW", "Student"));
    }

    @Test
    public void testAssignGradeWrongInstructor() {
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.student.submitHomework("Student", "HW","Answer", "Class", 2017 );
        this.instructor.assignGrade("Instructor2", "Class", 2017, "HW", "Student", 0);
        assertNull(this.instructor.getGrade("Class", 2017, "HW", "Student"));
    }

    @Test
    public void testAssignGradeNoHW() {
        this.instructor.assignGrade("Instructor", "Class", 2017, "HW2", "Student", 90);
        assertNull(this.instructor.getGrade("Class", 2017, "HW2", "Student"));
    }

    @Test
    public void testAssignGradeNoSubmit() {
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.instructor.assignGrade("Instructor", "Class", 2017, "HW", "Student", 90);
        assertNull(this.instructor.getGrade("Class", 2017, "HW", "Student"));
    }
}
