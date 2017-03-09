import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test
    public void testRegisterForClass(){
        this.student.registerForClass("Student", "Class", 2017);
        assertTrue(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testRegisterForClassNone() {
        this.student.registerForClass("Student", "Class2", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testRegisterForClassFull(){
        this.admin.createClass("Class2", 2017, "Instructor2", 1);
        this.student.registerForClass("Student", "Class2", 2017);
        this.student.registerForClass("Student2", "Class2", 2017);
        assertFalse(this.student.isRegisteredFor("Student2", "Class2", 2017));
    }

    @Test
    public void testDropClass(){
        this.student.registerForClass("Student", "Class", 2017);
        this.student.dropClass("Student", "Class", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testDropClassNotEnrolled(){
        this.student.dropClass("Student", "Class", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testDropClassEnded(){
        //assuming Class2 was created in 2016 and exists, and student was registered
        this.student.dropClass("Student", "Class2", 2016);
        assertTrue(this.student.isRegisteredFor("Student", "Class2", 2016));
    }

    @Test
    public void testSubmitHomework(){
        this.student.registerForClass("Student", "Class", 2017);
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.student.submitHomework("Student", "HW", "Answer", "Class", 2017);
        assertTrue(this.student.hasSubmitted("Student", "HW","Class", 2017));
    }

    @Test
    public void testSubmitHomeworkNotAssigned(){
        this.student.registerForClass("Student", "Class", 2017);
        this.student.submitHomework("Student", "HW", "Answer", "Class", 2017);
        assertFalse(this.student.hasSubmitted("Student", "HW","Class", 2017));
    }

    @Test
    public void testSubmitHomeworkNotRegistered(){
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Description");
        this.student.submitHomework("Student", "HW", "Answer", "Class", 2017);
        assertFalse(this.student.hasSubmitted("Student", "HW","Class", 2017));
    }

    @Test
    public void testSubmitHomeworkFuture(){
        this.admin.createClass("Class2", 2018,"Instructor2",  15);
        this.instructor.addHomework("Instructor2", "Class2", 2018, "HW", "Description");
        this.student.submitHomework("Student", "HW", "Answer", "Class2", 2018);
        assertFalse(this.student.hasSubmitted("Student", "HW", "Class2", 2018));
    }

    @Test
    public void testSubmitHomeworkPast(){
        //assuming Class2 was created in 2016 and exists, and homework HW was assigned
        this.student.submitHomework("Student", "HW", "Answer", "Class2", 2016);
        assertFalse(this.student.hasSubmitted("Student", "HW", "Class2", 2016));
    }




}
