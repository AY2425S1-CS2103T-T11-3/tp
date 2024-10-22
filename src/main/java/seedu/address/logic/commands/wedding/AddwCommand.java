package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HUSBAND_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WIFE_NAME;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.book.weddingbook.WeddingModel;
import seedu.address.model.wedding.Wedding;


/**
 * Adds a wedding to the Address Book.
 */
public class AddwCommand extends WeddingCommand {
    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the address book. "
            + "Parameters: "
            + PREFIX_HUSBAND_NAME + "HUSBAND'S NAME "
            + PREFIX_WIFE_NAME + "WIFE'S NAME"
            + PREFIX_DATE + "DATE "
            + PREFIX_VENUE + "VENUE ";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";
    private final Wedding toAdd;

    /**
     * Creates an AddWedding to add the specified {@code Wedding}
     */
    public AddwCommand(Wedding wedding) {
        requireNonNull(wedding);
        toAdd = wedding;
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        if (model.hasWedding(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        model.addWedding(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddwCommand)) {
            return false;
        }

        AddwCommand otherAddwCommand = (AddwCommand) other;
        return toAdd.equals(otherAddwCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddw", toAdd)
                .toString();
    }
}
