package seedu.address.model.wedding;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents the Wife in a Wedding.
 */
public class Wife extends Partner {

    /**
     * Constructs a {@code Wife}.
     */
    public Wife(Name name, Person person) {
        super(name, person);
    }
}
