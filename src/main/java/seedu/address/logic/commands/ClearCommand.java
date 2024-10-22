package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.book.addressbook.AddressBook;
import seedu.address.model.book.addressbook.AddressModel;

/**
 * Clears the address book.
 */
public class ClearCommand extends PersonCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(AddressModel addressModel) {
        requireNonNull(addressModel);
        addressModel.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
