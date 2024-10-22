package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.wedding.AddwCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Husband;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.Wife;

/**
 * Parses input arguments and creates a new AddwCommand object
 */
public class AddwCommandParser implements Parser<AddwCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddwCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_HUSBAND_NAME, PREFIX_HUSBAND_FULLNAME,
                        PREFIX_HUSBAND_PHONE, PREFIX_HUSBAND_EMAIL, PREFIX_HUSBAND_ADDRESS,
                        PREFIX_WIFE_NAME, PREFIX_WIFE_FULLNAME,
                        PREFIX_WIFE_PHONE, PREFIX_WIFE_EMAIL, PREFIX_WIFE_ADDRESS,
                        PREFIX_DATE, PREFIX_VENUE);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_HUSBAND_NAME, PREFIX_HUSBAND_FULLNAME,
                PREFIX_HUSBAND_PHONE, PREFIX_HUSBAND_EMAIL, PREFIX_HUSBAND_ADDRESS,
                PREFIX_WIFE_NAME, PREFIX_WIFE_FULLNAME,
                PREFIX_WIFE_PHONE, PREFIX_WIFE_EMAIL, PREFIX_WIFE_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddwCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_HUSBAND_NAME, PREFIX_HUSBAND_FULLNAME,
                PREFIX_HUSBAND_PHONE, PREFIX_HUSBAND_EMAIL, PREFIX_HUSBAND_ADDRESS,
                PREFIX_WIFE_NAME, PREFIX_WIFE_FULLNAME,
                PREFIX_WIFE_PHONE, PREFIX_WIFE_EMAIL, PREFIX_WIFE_ADDRESS,
                PREFIX_DATE, PREFIX_VENUE);

        Name husbandName = ParserUtil.parseName(argMultimap.getValue(PREFIX_HUSBAND_NAME).get());
        Name husbandFullName = ParserUtil.parseName(argMultimap.getValue(PREFIX_HUSBAND_FULLNAME).get());
        Phone husbandPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_HUSBAND_PHONE).get());
        Email husbandEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_HUSBAND_EMAIL).get());
        Address husbandAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_HUSBAND_ADDRESS).get());

        Name wifeName = ParserUtil.parseName(argMultimap.getValue(PREFIX_WIFE_NAME).get());
        Name wifeFullName = ParserUtil.parseName(argMultimap.getValue(PREFIX_WIFE_FULLNAME).get());
        Phone wifePhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_WIFE_PHONE).get());
        Email wifeEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_WIFE_EMAIL).get());
        Address wifeAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_WIFE_ADDRESS).get());

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("Client"));

        Husband husband = new Husband(husbandName, new Person(
                husbandFullName, husbandPhone, husbandEmail, husbandAddress, tagSet));
        Wife wife = new Wife(wifeName, new Person(
                wifeFullName, wifePhone, wifeEmail, wifeAddress, tagSet));

        Wedding wedding = new Wedding(husband, wife, date, venue);

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
