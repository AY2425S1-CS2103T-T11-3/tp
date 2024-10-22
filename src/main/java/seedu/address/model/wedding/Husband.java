package seedu.address.model.wedding;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents the Husband in a Wedding.
 */
public class Husband extends Partner {

    /**
     * Constructs a {@code Husband}.
     */
    public Husband(Name name, Person person) {
        super(name, person);
    }
}
