package ssvv.example;

import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddStudentTest {

    @Test
    public void testAddStudentGroup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        service.saveStudent("12", "Ana", 223);
        Student addedStudent = null;
        for(Student student : service.findAllStudents()) {
            if(student.getID().equals("12"))
            {
                addedStudent = student;
                break;
            }
        }
        assertEquals(addedStudent.getGrupa(), 223);
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
