package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.role.Role;
import seedu.address.testutil.PersonWithRoleDescriptorBuilder;

public class AssignCommandParserTest {

    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validInputAssignRoleOnly_success() throws ParseException {
        String input = "1 " + PREFIX_ROLE + "Friend";
        AssignCommand expectedCommand = new AssignCommand(Index.fromOneBased(1), null,
                new PersonWithRoleDescriptorBuilder().withRole("Friend").build(), null);
        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_validInputAssignWeddingOnly_success() throws ParseException {
        String input = "1 " + PREFIX_WEDDING + "2";
        AssignCommand expectedCommand = new AssignCommand(Index.fromOneBased(1), null,
                new PersonWithRoleDescriptorBuilder().build(),
                Set.of(Index.fromOneBased(2)));
        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_validInputAssignRoleAndWedding_success() throws ParseException {
        String input = "1 " + PREFIX_ROLE + "Friend " + PREFIX_WEDDING + "2";
        AssignCommand expectedCommand = new AssignCommand(Index.fromOneBased(1), null,
                new PersonWithRoleDescriptorBuilder().withRole("Friend").build(),
                Set.of(Index.fromOneBased(2)));
        assertEquals(expectedCommand, parser.parse(input));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String input = "0 " + PREFIX_ROLE + "Friend";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE), thrown.getMessage());
    }

    @Test
    public void parse_missingPreamble_throwsParseException() {
        String input = " " + PREFIX_ROLE + "Friend";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE), thrown.getMessage());
    }


    @Test
    public void parse_invalidRole_throwsParseException() {
        String input = "1 " + PREFIX_ROLE + " "; // Invalid role (empty string)
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(Role.MESSAGE_CONSTRAINTS, thrown.getMessage());
    }

    @Test
    public void parse_noDuplicatePrefixes_throwsParseException() {
        String input = "1 " + PREFIX_ROLE + "Alice " + PREFIX_ROLE + "Bob";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(MESSAGE_DUPLICATE_FIELDS + PREFIX_ROLE, thrown.getMessage());
    }

    @Test
    public void parse_validInputWithName_success() throws ParseException {
        String input = "Alice " + PREFIX_ROLE + "Friend";
        AssignCommand expectedCommand = new AssignCommand(null,
                new NameMatchesKeywordPredicate(Collections.singletonList("Alice")),
                new PersonWithRoleDescriptorBuilder().withRole("Friend").build(),
                null);
        assertEquals(expectedCommand, parser.parse(input));
    }
}
