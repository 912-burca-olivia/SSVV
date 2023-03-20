package ssvv.example;

import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.validation.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddStudentTest {

    @Test
    public void testAddStudentGroup() {
        Student student = new Student("11", "Ana", 223);
        assertEquals(student.getGrupa(), 223);
    }

    @Test
    public void testAddStudentWrongGroup() {
        Student student = new Student("11", "Ana", 1000);
        boolean thrown = false;
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository studentFileRepo = new StudentXMLRepository(studentValidator, "studenti.xml");


        try {
            Student result = studentFileRepo.save(student);
            assertEquals(result, null);
            studentValidator.validate(student);

        } catch (ValidationException e) {
            thrown = true;
        }

        assertTrue(thrown);


    }






}
