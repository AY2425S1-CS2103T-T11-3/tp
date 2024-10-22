package seedu.address.logic.parser;

import seedu.address.logic.commands.PersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code PersonCommand} of type {@code T}.
 */
public interface Parser<T extends PersonCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;
}
