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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IncrementalTest {

    @Mock
    private StudentXMLRepository studentRepo;
    @Mock
    private TemaXMLRepository assignmentRepo;
    @Mock
    private NotaXMLRepository gradeRepo;
    private Service service;

    @Before
    public void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        studentRepo = mock(StudentXMLRepository.class);
        assignmentRepo = mock(TemaXMLRepository.class);
        gradeRepo = mock(NotaXMLRepository.class);

        service = new Service(studentRepo, assignmentRepo, gradeRepo);
    }
    @Test
    public void testAddStudent() {
        assertEquals(service.saveStudent("1", "Ana", 223), 1);
    }

    @Test
    public void testAddAssignment() {
        when(studentRepo.findOne("2")).thenReturn(new Student("2","Mihai",222));
        assertEquals(service.saveTema("1", "Tema", 8, 7), 1);
    }

    @Test
    public void testAddGrade() {
        when(studentRepo.findOne("3")).thenReturn(new Student("3","a",223));
        when(assignmentRepo.findOne("2")).thenReturn(new Tema("2","a",8, 6));
        assertEquals(service.saveNota("3", "2", 9, 8, "Ok"), 1);
    }

   /* @After
    public void tearDown(){
        service.deleteStudent("4002");
        service.deleteStudent("4003");
        service.deleteTema("40003");
        service.deleteTema("40002");

    } */
}
