package seedu.edudex.model.person;

import static seedu.edudex.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.edudex.commons.util.ToStringBuilder;
import seedu.edudex.model.tag.Tag;

/**
 * Represents a Person in EduDex.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final School school;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // Subject field
    private final Subject subject;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, School school, Address address, Set<Tag> tags, Subject subject) {
        requireAllNonNull(name, phone, school, address, tags);
        this.name = name;
        this.phone = phone;
        this.school = school;
        this.address = address;
        this.tags.addAll(tags);
        this.subject = subject;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public School getSchool() {
        return school;
    }

    public Address getAddress() {
        return address;
    }

    public Subject getSubject() {
        return subject;
    }

    public Day getDay() {
        return subject != null ? subject.getDay() : null;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && school.equals(otherPerson.school)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, school, address, tags, subject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("school", school)
                .add("address", address)
                .add("tags", tags)
                .add("day", subject.getDay())
                .add("startTime", subject.getStartTime())
                .add("endTime", subject.getEndTime())
                .toString();
    }
}
