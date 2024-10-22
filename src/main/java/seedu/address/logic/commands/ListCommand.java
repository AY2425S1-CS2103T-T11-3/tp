package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.book.addressbook.AddressModel.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.book.addressbook.AddressModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends PersonCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(AddressModel addressModel) {
        requireNonNull(addressModel);
        addressModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
