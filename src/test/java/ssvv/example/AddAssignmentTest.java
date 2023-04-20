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
public class AddAssignmentTest {
    @Test
    public void testAddAssignmentValid() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        service.saveTema("10", "Tema", 13, 2);
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
        Tema assignment = new Tema("11", "Tema", 15, 2);
        boolean thrown = false;
        Validator<Tema> assignmentValidator = new TemaValidator();
        TemaXMLRepository assignmentFileRepo = new TemaXMLRepository(assignmentValidator, "teme.xml");

        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

        assignment.setDeadline(0);
        thrown = false;
        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

        assignment.setDeadline(1);
        thrown = false;
        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }

    @Test
    public void testAddAssignmentInvalidStartline() {
        Tema assignment = new Tema("11", "Tema", 10, -1);
        boolean thrown = false;
        Validator<Tema> assignmentValidator = new TemaValidator();
        TemaXMLRepository assignmentFileRepo = new TemaXMLRepository(assignmentValidator, "teme.xml");

        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

        assignment.setStartline(11);
        thrown = false;
        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

        assignment.setStartline(16);
        thrown = false;
        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }

    @Test
    public void testAddAssignmentInvalidID() {
        Tema assignment = new Tema(null, "Tema", 10, 8);
        boolean thrown = false;
        Validator<Tema> assignmentValidator = new TemaValidator();
        TemaXMLRepository assignmentFileRepo = new TemaXMLRepository(assignmentValidator, "teme.xml");

        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

        assignment.setID("");
        thrown = false;
        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }

    @Test
    public void testAddAssignmentInvalidDescription() {
        Tema assignment = new Tema("10", null, 10, 8);
        boolean thrown = false;
        Validator<Tema> assignmentValidator = new TemaValidator();
        TemaXMLRepository assignmentFileRepo = new TemaXMLRepository(assignmentValidator, "teme.xml");

        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

        assignment.setDescriere("");
        thrown = false;
        try {
            Tema result = assignmentFileRepo.save(assignment);
            assertEquals(result, null);
            assignmentValidator.validate(assignment);
        } catch (ValidationException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }
}
