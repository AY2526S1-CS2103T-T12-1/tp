package seedu.edudex.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_END;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_START;
import static seedu.edudex.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.edudex.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.edudex.commons.core.index.Index;
import seedu.edudex.commons.util.CollectionUtil;
import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.logic.Messages;
import seedu.edudex.logic.commands.exceptions.CommandException;
import seedu.edudex.model.Model;
import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Email;
import seedu.edudex.model.person.Lesson;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.tag.Tag;

/**
 * Edits the details of an existing person in EduDex.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DAY + "DAY ]"
            + "[" + PREFIX_START + "START ]"
            + "[" + PREFIX_END + "END ]"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in EduDex.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;
        try {
            editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        } catch (IllegalArgumentException e) {
            // display start time after end time exception
            throw new CommandException(e.getMessage());
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        // Temporary before support for multiple subjects are added
        Lesson updatedSubject;

        if (editPersonDescriptor.getSubject().isPresent()) {
            // editSubjectDescriptor will handle this in the future
            updatedSubject = editPersonDescriptor.getSubject().get();
        } else if (editPersonDescriptor.getDay().isPresent()
                || editPersonDescriptor.getStartTime().isPresent()
                || editPersonDescriptor.getEndTime().isPresent()) {
            // Partial updates to subject fields (temporary before support for multiple subjects are added)
        //            Day updatedDay = editPersonDescriptor.getDay().orElse(personToEdit.getDay());
        //            Time updatedStartTime = editPersonDescriptor.getStartTime()
        //                    .orElse(personToEdit.getSubject().getStartTime());
        //            Time updatedEndTime = editPersonDescriptor.getEndTime()
        //                    .orElse(personToEdit.getSubject().getEndTime());
        //            updatedSubject = new Lesson(updatedDay, updatedStartTime, updatedEndTime);
        } else {
            // ".orElse()"
        //            updatedSubject = personToEdit.getSubject();
        }

                return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Lesson subject;

        // Temporary before support for multiple subjects are added
        private Day day;
        private Time startTime;
        private Time endTime;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setSubject(toCopy.subject);

            // Temporary before support for multiple subjects are added
            setDay(toCopy.day);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, subject, day, startTime, endTime);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setSubject(Lesson subject) {
            this.subject = subject;
        }

        public Optional<Lesson> getSubject() {
            return Optional.ofNullable(subject);
        }

        // Temporary before support for multiple subjects are added
        public Optional<Day> getDay() {
            return Optional.ofNullable(day);
        }

        // Temporary before support for multiple subjects are added
        public void setDay(Day day) {
            this.day = day;
        }

        // Temporary before support for multiple subjects are added
        public Optional<Time> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        // Temporary before support for multiple subjects are added
        public void setStartTime(Time time) {
            this.startTime = time;
        }

        // Temporary before support for multiple subjects are added
        public Optional<Time> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        // Temporary before support for multiple subjects are added
        public void setEndTime(Time time) {
            this.endTime = time;
        }



        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }



        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags)
                    && Objects.equals(subject, otherEditPersonDescriptor.subject);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("subject", subject)
                    .toString();
        }
    }
}
