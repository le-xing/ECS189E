import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {
    private IAdmin admin;
    private IStudent student1;
    private IStudent student2;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student1 = new Student();
        this.student2 = new Student();
    }

    @Test
    public void testCreateClassValid() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateClassPastYear() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test",2016));
    }

    @Test
    public void testCreateClassCapacityZero() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateClassCapacityNeg() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateClassInstructor2() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    @Test
    public void testChangeCapacityIncrease(){
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.student1.registerForClass("Student1", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 3);
        assertEquals(3, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacityDecreaseValid(){
        this.admin.createClass("Test", 2017, "Instructor", 3);
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student2.registerForClass("Student2", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 2);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacityDecreaseInvalid(){
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student2.registerForClass("Student2", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 1);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    public void testChangeCapacityNeg() {
        this.admin.createClass("Test", 2017, "Instructor", -15);
        this.admin.changeCapacity("Test", 2017, -17);
        assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
}
