package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.book.weddingbook.WeddingBookModel;
import seedu.address.model.wedding.Wedding;


/**
 * Adds a wedding to the Address Book.
 */
public class AddwCommand extends Command {
    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the address book. "
            + "Parameters: "
            + PREFIX_HUSBAND_NAME + "HUSBAND'S NAME "
            + PREFIX_HUSBAND_FULLNAME + "HUSBAND'S FULLNAME"
            + PREFIX_HUSBAND_PHONE + "HUSBAND'S PHONE"
            + PREFIX_HUSBAND_EMAIL + "HUSBAND'S EMAIL"
            + PREFIX_HUSBAND_ADDRESS + "HUSBAND'S ADDRESS"
            + PREFIX_WIFE_NAME + "WIFE'S NAME"
            + PREFIX_WIFE_FULLNAME + "WIFE'S FULLNAME"
            + PREFIX_WIFE_PHONE + "WIFE'S PHONE"
            + PREFIX_WIFE_EMAIL + "WIFE'S EMAIL"
            + PREFIX_WIFE_ADDRESS + "WIFE'S ADDRESS"
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

    public CommandResult execute(Model model) throws CommandException {
        if (!(model instanceof WeddingBookModel)) {
            return null;
        }
        WeddingBookModel weddingBookModel = (WeddingBookModel) model;
        return this.executeCommand(weddingBookModel);
    }

    public WeddingCommandResult executeCommand(WeddingBookModel weddingBookModel) throws CommandException {
        requireNonNull(weddingBookModel);

        if (weddingBookModel.hasObject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        weddingBookModel.addObject(toAdd);
        return new WeddingCommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
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
