package seedu.edudex.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.UniquePersonList;

/**
 * Wraps all data at the EduDex level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class EduDex implements ReadOnlyEduDex {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
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
     * Resets the existing data of this {@code EduDex} with {@code newData}.
     */
    public void resetData(ReadOnlyEduDex newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
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
}
