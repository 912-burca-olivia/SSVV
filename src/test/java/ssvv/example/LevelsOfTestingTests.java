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
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

import static org.junit.Assert.assertEquals;

public class LevelsOfTestingTests {
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
    public void testAddStudent() {
        assertEquals(service.saveStudent("14", "Ana", 223), 0);
    }

    @Test
    public void testAddAssignment() {
        assertEquals(service.saveTema("99", "Tema", 13, 2), 0);
    }

    @Test
    public void testAddGrade() {
        assertEquals(service.saveNota("9999", "9999", 9, 13, "Ok"), -1);
    }

    @Test
    public void testBigBang() {
        assertEquals(service.saveStudent("4001", "Mihai", 222), 1);
        assertEquals(service.saveTema("4001", "Tema", 8, 7), 1);
        assertEquals(service.saveNota("4001", "4001", 9, 8, "Ok"), 1);
    }



}
