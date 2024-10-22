package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HUSBAND_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WIFE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.wedding.AddwCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Husband;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.Wife;

/**
 * Parses input arguments and creates a new AddwCommand object
 */
public class AddwCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddwCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_HUSBAND_NAME, PREFIX_WIFE_NAME,
                        PREFIX_DATE, PREFIX_VENUE);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_HUSBAND_NAME, PREFIX_WIFE_NAME,
                PREFIX_DATE, PREFIX_VENUE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddwCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_HUSBAND_NAME, PREFIX_WIFE_NAME,
                PREFIX_DATE, PREFIX_VENUE);

        Name husbandName = ParserUtil.parseName(argMultimap.getValue(PREFIX_HUSBAND_NAME).get());
        Name wifeName = ParserUtil.parseName(argMultimap.getValue(PREFIX_WIFE_NAME).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());

        Husband groom = new Husband(husbandName);
        Wife bride = new Wife(wifeName);

        Wedding wedding = new Wedding(groom, bride, date, venue);

        return new AddwCommand(wedding);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
