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
public class AddAssignmentTest {
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
    public void testAddAssignmentValid() {
        int result = service.saveTema("10", "Tema", 13, 2);
        assertEquals(result , 0);
        Tema addedAssignment = null;
        for (Tema assignment : service.findAllTeme()) {
            if (assignment.getID().equals("10")) {
                addedAssignment = assignment;
                break;
            }
        }
        assertEquals(addedAssignment.getDeadline(), 13);
        assertEquals(addedAssignment.getID(), "10");
        assertEquals(addedAssignment.getDescriere(), "Tema");
        assertEquals(addedAssignment.getStartline(), 2);
    }

    @Test
    public void testAddAssignmentInvalidDeadline() {
        int result = service.saveTema("11", "Tema", 15, 2);
        assertEquals(result, 1);

    }

    @Test
    public void testAddAssignmentInvalidStartline() {
        int result = service.saveTema("11", "Tema", 10, -2);
        assertEquals(result, 1);

    }

    @Test
    public void testAddAssignmentInvalidID() {
        int result = service.saveTema(null, "Tema", 10, 8);
        assertEquals(result, 1);
    }

    @Test
    public void testAddAssignmentInvalidDescription() {
        int result = service.saveTema("10", "", 10, 8);
        assertEquals(result, 1);
    }
}
