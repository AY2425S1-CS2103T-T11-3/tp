package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.addressbook.AddressBook;
import seedu.address.model.book.addressbook.AddressBookModel;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

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
        addressBookModel.setAddressBook(new AddressBook());
        return new PersonCommandResult(MESSAGE_SUCCESS);
    }
}
