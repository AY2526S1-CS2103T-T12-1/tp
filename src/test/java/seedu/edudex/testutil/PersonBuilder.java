package seedu.edudex.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.edudex.model.person.Address;
import seedu.edudex.model.person.Day;
import seedu.edudex.model.person.Name;
import seedu.edudex.model.person.Person;
import seedu.edudex.model.person.Phone;
import seedu.edudex.model.person.School;
import seedu.edudex.model.person.Subject;
import seedu.edudex.model.person.Time;
import seedu.edudex.model.tag.Tag;
import seedu.edudex.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_SCHOOL = "Queenstown Primary School";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DAY = "Monday";
    public static final String DEFAULT_STARTTIME = "10:00";
    public static final String DEFAULT_ENDTIME = "12:00";

    private Name name;
    private Phone phone;
    private School school;
    private Address address;
    private Set<Tag> tags;
    private Day day;
    private Time startTime;
    private Time endTime;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        school = new School(DEFAULT_SCHOOL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        day = new Day(DEFAULT_DAY);
        startTime = new Time(DEFAULT_STARTTIME);
        endTime = new Time(DEFAULT_ENDTIME);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        school = personToCopy.getSchool();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        day = personToCopy.getSubject().getDay();
        startTime = personToCopy.getSubject().getStartTime();
        endTime = personToCopy.getSubject().getEndTime();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code School} of the {@code Person} that we are building.
     */
    public PersonBuilder withSchool(String school) {
        this.school = new School(school);
        return this;
    }

    /**
     * Sets the {@code day} of the {@code Person} that we are building.
     */
    public PersonBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Person} that we are building.
     */
    public PersonBuilder withStartTime(String startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Person} that we are building.
     */
    public PersonBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }

    public Person build() {
        return new Person(name, phone, school, address, tags, new Subject(day, startTime, endTime));
    }

}
