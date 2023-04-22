package ssvv.example;

import org.junit.Before;
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
    private StudentXMLRepository studentRepo;
    private TemaXMLRepository assignmentRepo;
    private NotaXMLRepository gradeRepo;
    private Service service;

    @Before
    public void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        studentRepo = new StudentXMLRepository(studentValidator, "studenti.xml");
        assignmentRepo = new TemaXMLRepository(temaValidator, "teme.xml");
        gradeRepo = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(studentRepo, assignmentRepo, gradeRepo);
    }

    @Test
    public void testAddStudentValid() {

        int result = service.saveStudent("12", "Ana", 223);
        assertEquals(result , 0);
        Student addedStudent = null;
        for(Student student : service.findAllStudents()) {
            if(student.getID().equals("12"))
            {
                addedStudent = student;
                break;
            }
        }
        assertEquals(addedStudent.getGrupa(), 223);
        assertEquals(addedStudent.getID(), "12");
        assertEquals(addedStudent.getNume(),"Ana");
    }

    @Test
    public void testAddStudentWrongGroup() {
        int result = service.saveStudent("11", "Ana", 1000);
        assertEquals(result, 1);
    }

    @Test
    public void testAddStudentWrongId() {
        int result = service.saveStudent("", "Ana", 221);
        assertEquals(result, 1);
    }


    @Test
    public void testAddStudentWrongName() {
        int result = service.saveStudent("15", "", 221);
        assertEquals(result, 1);
    }



}
