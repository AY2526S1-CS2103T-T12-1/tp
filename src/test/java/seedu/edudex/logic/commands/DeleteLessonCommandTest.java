package seedu.edudex.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.edudex.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edudex.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edudex.testutil.TypicalLessons.SCIENCE;
import static seedu.edudex.testutil.TypicalPersons.getTypicalEduDex;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.parser.DeleteLessonCommandParser;
import seedu.edudex.logic.parser.exceptions.ParseException;
import seedu.edudex.model.EduDex;
import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.UserPrefs;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.subject.Subject;
import seedu.edudex.testutil.PersonBuilder;
import seedu.edudex.testutil.TypicalLessons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {

    private Model model = new ModelManager(getTypicalEduDex(), new UserPrefs());

    @Test
    public void execute_validIndexLesson_success() {
        List<Lesson> lessons = TypicalLessons.getTypicalLessons();
        Person student = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()))
                .withLessons(lessons)
                .build();
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), student);

        // Delete the first lesson
        DeleteLessonCommand deleteLessonCommand =
                new DeleteLessonCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                lessons.get(0), student.getName());

        Model expectedModel = new ModelManager(new EduDex(model.getEduDex()), new UserPrefs());

        // Get the person instance that belongs to expectedModel (not the one from 'model')
        Person personInExpectedModel = expectedModel.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());

        // Build expectedStudent based on 'student' but replace lessons with the expected remaining list
        Person expectedStudent = new PersonBuilder(student).build();
        expectedStudent.setLessons(new ArrayList<>(List.of(SCIENCE))); // use a mutable list to match production usage

        // Replace the correct target in expectedModel
        expectedModel.setPerson(personInExpectedModel, expectedStudent);

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        Person student = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        Lesson mathLesson = new Lesson(new Subject("Math"), new Day("Monday"),
                new Time("10:00"), new Time("11:00"));
        student.setLessons(List.of(mathLesson));

        // commit changes to model
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), student);

        // There’s only 1 lesson, so index 2 is invalid
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_INDEX);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex, Index.fromOneBased(1));

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLessonCommand deleteFirstLesson = new DeleteLessonCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(1));
        DeleteLessonCommand deleteSecondLesson = new DeleteLessonCommand(INDEX_FIRST_PERSON,
                Index.fromOneBased(2));
        DeleteLessonCommand deleteDifferentStudent = new DeleteLessonCommand(INDEX_SECOND_PERSON,
                Index.fromOneBased(1));

        // same object -> true
        assertTrue(deleteFirstLesson.equals(deleteFirstLesson));

        // same values -> true
        DeleteLessonCommand deleteFirstLessonCopy = new DeleteLessonCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        assertTrue(deleteFirstLesson.equals(deleteFirstLessonCopy));

        // different lesson index -> false
        assertFalse(deleteFirstLesson.equals(deleteSecondLesson));

        // different student index -> false
        assertFalse(deleteFirstLesson.equals(deleteDifferentStudent));

        // null -> false
        assertFalse(deleteFirstLesson.equals(null));

        // different type -> false
        assertFalse(deleteFirstLesson.equals(new ClearCommand()));
    }

    @Test
    public void toStringMethod() {
        Index studentIndex = Index.fromOneBased(1);
        Index lessonIndex = Index.fromOneBased(2);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(studentIndex, lessonIndex);

        String expected = DeleteLessonCommand.class.getCanonicalName()
                + "{studentIndex=" + studentIndex
                + ", lessonIndex=" + lessonIndex + "}";
        assertTrue(deleteLessonCommand.toString().contains(expected));
    }

    @Test
    public void parse_nonIntegerLesson_throwsFormat() {
        DeleteLessonCommandParser parser = new DeleteLessonCommandParser();
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("1 a"));
        assertEquals("Invalid format — indices must be integers", thrown.getMessage());
    }

    @Test
    public void parse_nonIntegerStudent_throwsFormat() {
        DeleteLessonCommandParser parser = new DeleteLessonCommandParser();
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("a 1"));
        assertEquals("Invalid format — indices must be integers", thrown.getMessage());
    }

    @Test
    public void parse_zeroStudent_throwsInvalidStudent() {
        DeleteLessonCommandParser parser = new DeleteLessonCommandParser();
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("0 1"));
        assertEquals("Invalid student index", thrown.getMessage());
    }

    @Test
    public void parse_zeroLesson_throwsInvalidLesson() {
        DeleteLessonCommandParser parser = new DeleteLessonCommandParser();
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("1 0"));
        assertEquals("Invalid lesson index", thrown.getMessage());
    }

    @Test
    public void parse_validNumericIndexes_returnsCommand() throws Exception {
        DeleteLessonCommandParser parser = new DeleteLessonCommandParser();
        DeleteLessonCommand cmd = parser.parse("1 2");
        assertNotNull(cmd);
    }

}
