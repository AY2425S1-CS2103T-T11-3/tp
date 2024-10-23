package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.book.addressbook.AddressBookModel.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.addressbook.AddressBook;
import seedu.address.model.book.addressbook.AddressBookModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

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
        addressBookModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new PersonCommandResult(MESSAGE_SUCCESS);
    }
}
