package seedu.edudex.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.UniquePersonList;
import seedu.edudex.model.subject.UniqueSubjectList;

/**
 * Wraps all data at the EduDex level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class EduDex implements ReadOnlyEduDex {

    private final UniquePersonList persons;
    private final UniqueSubjectList subjects;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        subjects = new UniqueSubjectList();
    }

    public EduDex() {}

    /**
     * Creates an EduDex using the Persons in the {@code toBeCopied}
     */
    public EduDex(ReadOnlyEduDex toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the subject list with {@code subjects}.
     * {@code subjects} must not contain duplicate subjects.
     */
    public void setSubjects(List<seedu.edudex.model.subject.Subject> subjects) {
        this.subjects.setSubjects(subjects);
    }

    /**
     * Resets the existing data of this {@code EduDex} with {@code newData}.
     */
    public void resetData(ReadOnlyEduDex newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setSubjects(newData.getSubjectList());
    }

    //// subject-level operations

    /**
     * Returns true if a subject with the same name as {@code subject} exists in EduDex.
     */
    public boolean hasSubject(seedu.edudex.model.subject.Subject subject) {
        requireNonNull(subject);
        return subjects.contains(subject);
    }

    /**
     * Adds a subject to EduDex.
     * The subject must not already exist in EduDex.
     */
    public void addSubject(seedu.edudex.model.subject.Subject s) {
        subjects.add(s);
    }

    /**
     * Replaces the given subject {@code target} in the list with {@code editedSubject}.
     * {@code target} must exist in EduDex.
     * The subject name of {@code editedSubject} must not be the same as another existing subject in EduDex.
     */
    public void setSubject(seedu.edudex.model.subject.Subject target,
                           seedu.edudex.model.subject.Subject editedSubject) {
        requireNonNull(editedSubject);

        subjects.setSubject(target, editedSubject);
    }

    /**
     * Removes {@code key} from this {@code EduDex}.
     * {@code key} must exist in EduDex.
     */
    public void removeSubject(seedu.edudex.model.subject.Subject key) {
        subjects.remove(key);
    }


    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in EduDex.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to EduDex.
     * The person must not already exist in EduDex.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in EduDex.
     * The person identity of {@code editedPerson} must not be the same as another existing person in EduDex.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code EduDex}.
     * {@code key} must exist in EduDex.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<seedu.edudex.model.subject.Subject> getSubjectList() {
        return subjects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EduDex)) {
            return false;
        }

        EduDex otherEduDex = (EduDex) other;
        return persons.equals(otherEduDex.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    /**
     * Finds and returns a person who has a lesson that conflicts with the given lesson.
     * Excludes the specified person from the search.
     * @param lesson
     * @param personToExclude
     * @return Person with conflicting lesson, or null if none found.
     */
    public Person findPersonWithLessonConflict(Lesson lesson, Person personToExclude) {
        for (Person person : persons) {
            if (person.equals(personToExclude)) {
                continue; // skip checking against the specified person
            }
            Lesson conflictingLesson = person.hasLessonConflict(lesson, -1);
            if (conflictingLesson != null) {
                return person; // found a person with a conflicting lesson
            }
        }
        return null; // no conflicts found
    }
}
