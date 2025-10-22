package seedu.edudex.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.edudex.commons.core.GuiSettings;
import seedu.edudex.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' EduDex file path.
     */
    Path getEduDexFilePath();

    /**
     * Sets the user prefs' EduDex file path.
     */
    void setEduDexFilePath(Path eduDexFilePath);

    /**
     * Replaces EduDex data with the data in {@code eduDex}.
     */
    void setEduDex(ReadOnlyEduDex eduDex);

    /** Returns the EduDex */
    ReadOnlyEduDex getEduDex();

    /**
     * Returns true if a person with the same identity as {@code person} exists in EduDex.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in EduDex.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in EduDex.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in EduDex.
     * The person identity of {@code editedPerson} must not be the same as another existing person in EduDex.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void sortFilteredPersonList(Comparator<Person> comparator);

    ObservableList<Person> getSortedPersonList();
}
