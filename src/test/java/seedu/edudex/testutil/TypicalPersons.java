package seedu.edudex.testutil;

import static seedu.edudex.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.edudex.logic.commands.CommandTestUtil.VALID_DAY_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.edudex.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
//import static seedu.edudex.logic.commands.CommandTestUtil.VALID_STARTTIME_BOB;
//import static seedu.edudex.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.edudex.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edudex.model.EduDex;
import seedu.edudex.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withSchool("Raffles Primary School")
            .withPhone("94351253")
            .withTags("friends")
            .withDay("Tuesday")
            .withStartTime("10:00")
            .withEndTime("11:00")
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withSchool("Nanyang Primary School").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withDay("Monday")
            .withStartTime("13:00")
            .withEndTime("15:00").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withSchool("Jurong Primary School").withAddress("wall street").withDay("Monday")
            .withStartTime("13:00")
            .withEndTime("15:00").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withSchool("Queenstown Primary School").withAddress("10th street").withTags("friends")
            .withStartTime("13:00")
            .withEndTime("15:00").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withSchool("Newtown Primary School").withAddress("michegan ave").withDay("Monday")
            .withStartTime("13:00")
            .withEndTime("15:00").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withSchool("NUS").withAddress("little tokyo").withDay("Monday")
            .withStartTime("13:00")
            .withEndTime("15:00").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withSchool("NUS").withAddress("4th street").withDay("Monday")
            .withStartTime("13:00")
            .withEndTime("15:00").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withSchool("Raffles Primary School").withAddress("little india").withDay("Monday")
            .withStartTime("13:00")
            .withEndTime("15:00").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withSchool("Nanyang Primary School").withAddress("chicago ave").withDay("Monday")
            .withStartTime("13:00")
            .withEndTime("15:00").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withSchool(VALID_SCHOOL_AMY).withAddress(VALID_ADDRESS_AMY).withDay("Tuesday")
            .withStartTime("10:00").withEndTime("12:00").build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withSchool(VALID_SCHOOL_BOB).withAddress(VALID_ADDRESS_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code EduDex} with all the typical persons.
     */
    public static EduDex getTypicalEduDex() {
        EduDex ab = new EduDex();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
