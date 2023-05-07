package ssvv.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

import static org.junit.Assert.assertEquals;

public class IncrementalTesting {

    @Mock
    private StudentXMLRepository studentRepo;
    @Mock
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
    public void testAddStudent() {
        assertEquals(service.saveStudent("14", "Ana", 223), 0);
    }

    @Test
    public void testAddAssignment() {
        assertEquals(service.saveStudent("4002", "Mihai", 222), 1);
        assertEquals(service.saveTema("4002", "Tema", 8, 7), 1);
    }

    @Test
    public void testAddGrade() {
        assertEquals(service.saveStudent("4003", "Mihai", 222), 1);
        assertEquals(service.saveTema("4003", "Tema", 8, 7), 1);
        assertEquals(service.saveNota("4003", "4002", 9, 8, "Ok"), 1);
    }

    @After
    public void tearDown(){
        service.deleteStudent("4002");
        service.deleteStudent("4003");
        service.deleteTema("4003");
    }
}
