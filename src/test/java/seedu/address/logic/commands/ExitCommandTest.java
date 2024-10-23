package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressBookModelManager;
import seedu.address.model.book.addressbook.AddressBookModel;

public class ExitCommandTest {
    private AddressBookModel addressAddressBookModel = new AddressBookModelManager();
    private AddressBookModel expectedAddressAddressBookModel = new AddressBookModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), addressAddressBookModel, expectedCommandResult, expectedAddressAddressBookModel);
    }
}
