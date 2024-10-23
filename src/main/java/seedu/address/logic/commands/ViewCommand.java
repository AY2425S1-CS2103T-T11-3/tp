package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.addressbook.AddressBook;
import seedu.address.model.book.addressbook.AddressBookModel;
import seedu.address.model.person.NameMatchesNamePredicate;

/**
 * View the person in address book whose name matches the keyword.
 * Keyword matching is case insensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the contact you want to see "
            + "with the name (case-insensitive).\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " alice";

    private final NameMatchesNamePredicate predicate;

    public ViewCommand(NameMatchesNamePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!(model instanceof AddressBookModel)) {
            return null;
        }
        AddressBookModel addressBookModel = (AddressBookModel) model;
        return this.executeCommand(addressBookModel);
    }

    public PersonCommandResult executeCommand(AddressBookModel addressBookModel) throws CommandException {
        requireNonNull(addressBookModel);
        addressBookModel.updateFilteredPersonList(predicate);
        return new PersonCommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, addressBookModel.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return predicate.equals(otherViewCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
