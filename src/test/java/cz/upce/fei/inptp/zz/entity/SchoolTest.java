/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.inptp.zz.entity;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// TODO: drop all unnecessary comments and @author tags
/**
 *
 * @author Roman
 */
public class SchoolTest {

    private School school;
    private Student student;
    private Course course;
    private TimeSlot timeSlot;
    private CourseAction courseAction;

    @Before
    public void initialize() {
        school = new School();
        student = new Student();
        course = new Course();
        timeSlot = new TimeSlot(Day.Saturday, 10, 2);

        courseAction = new CourseAction();
        courseAction.setTimeSlot(timeSlot);
    }

    @Test
    public void testAddStudentToCourseAction() {

        school.addCourse(course);
        course.getActions().add(courseAction);
        school.getStudents().add(student);

        boolean result = school.addStudentToCourseAction(course, student, timeSlot);

        assertTrue(result);
        // TODO: do also invalid cases...
    }

    @Test
    public void testAddStudentToCourseActionWhenCourseIsNotPresent() {

        //school.courses.add(course);
        course.getActions().add(courseAction);
        school.getStudents().add(student);

        boolean result = school.addStudentToCourseAction(course, student, timeSlot);

        //assertFalse(result);
    }

    @Test
    public void testAddStudentToCourseActionWhenCourseIsMissingCourseAction() {

        school.addCourse(course);
        //course.actions.add(courseAction);
        school.getStudents().add(student);

        boolean result = school.addStudentToCourseAction(course, student, timeSlot);

        assertFalse(result);
    }

    @Test
    public void testAddStudentToCourseActionWhenStudentIsNotInSchool() {

        school.addCourse(course);
        course.getActions().add(courseAction);

        boolean result = school.addStudentToCourseAction(course, new Student(), timeSlot);

        assertEquals(false, result);
    }
    
    @Test
    public void testAddStudent() {
        School school = new School();
        Student student = new Student();
        
        school.addStudent(student);
        
        assertEquals(student, school.getStudents().get(0));
    }
    
    @Test
    public void testAddCourseAction() {
        Course course = new Course();
        CourseAction courseAction = new CourseAction();
        School school = new School();
        
        school.getCoursesList().add(course);
        school.addCourseAction(course, courseAction);
        
        assertEquals(courseAction, school.getCoursesList().get(0).getActions().get(0));
    }
    
    @Test
    public void testAddTeacher() {
        School school = new School();
        Teacher teacher = new Teacher();
        
        school.addTeacher(teacher);
        assertEquals(teacher, school.getTeachers().get(0));
    }
    
    @Test
    public void testAddCourse() {
        School school = new School();
        Course course = new Course();
        
        school.addCourse(course);
        assertEquals(course, school.getCoursesList().get(0));
    }

    @Test
    public void testComparePersonsByIDIsTrue(){
        student.setID("S0001");
        Student student2 = new Student();
        student2.setID("S0001");
        
        assertTrue(student.equals(student2));
    }
    
    @Test
    public void testComparePersonsByIDIsFalse(){
        student.setID("S0001");
        Student student2 = new Student();
        student2.setID("S0002");
        
        assertFalse(student.equals(student2));
    }
    
    @Test
    public void testNewStudentWasAdded(){
        school.addStudent(student);
        
        assertEquals(1, school.getStudents().size());
    }
    
    @Test
    public void testNewStudentWasNotAddedBecauseHeIsAlreadyPresent(){
        student.setID("S0001");
        school.addStudent(student);
       
        Student newStudent = new Student();
        newStudent.setID("S0001");
        school.addStudent(newStudent);
        
        assertEquals(1, school.getStudents().size());
    }
    
    @Test
    public void testStudentWasRemoved(){
        student.setID("S0001");
        school.addStudent(student);
        
        Student studentToBeRemoved = new Student();
        studentToBeRemoved.setID("S0001");
        school.removeStudent(studentToBeRemoved);
        
        assertEquals(0, school.getStudents().size());
    }
    
    @Test
    public void testStudentWasNotRemovedBecauseOfWrongID(){
        student.setID("S0001");
        school.addStudent(student);
        
        Student studentToBeRemoved = new Student();
        studentToBeRemoved.setID("S0002");
        school.removeStudent(studentToBeRemoved);
        
        assertEquals(1, school.getStudents().size());
    }
    
    @Test
    public void testStudentDoesNotHaveCourseActionsAfterHeWasRemoved(){
        student.getActions().add(courseAction);
        student.setID("S0001");
        school.addStudent(student);
        
        Student studentToBeRemoved = new Student();
        studentToBeRemoved.setID("S0001");
        school.removeStudent(studentToBeRemoved);
        
        assertEquals(0, student.getActions().size());
    }
    
    @Test
    public void testRemovedStudentWasRemovedFromHisCourseAction(){
        student.setID("S0001");
        student.getActions().add(courseAction);
        courseAction.getStudents().add(student);
        school.addStudent(student);
        
        school.removeStudent(student);
       
        assertEquals(0, courseAction.getStudents().size());
    }

    @Test
    public void testEmptySchoolCourses() {
        Iterator<Course> iterator = school.getCourses();

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddCourseToSchool() {
        school.addCourse(course);
        Iterator<Course> iterator = school.getCourses();

        assertEquals(course, iterator.next());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicityCourseToSchool() {
        school.addCourse(course);
        school.addCourse(course);
    }

    @Test
    public void testAddActionToSchoolCourse() {
        school.addCourse(new Course("C01"));
        boolean result = school.addCourseAction(new Course("C01"), courseAction);
        assertTrue(result);
    }

    @Test
    public void testAddActionToNonExistCourseInSchool() {
        school.addCourse(new Course("C01"));
        boolean result = school.addCourseAction(new Course("02"), courseAction);
        assertFalse(result);
    }
}
