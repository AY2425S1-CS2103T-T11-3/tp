package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressBookModel;
import seedu.address.model.book.addressbook.AddressBookModelManager;

public class HelpCommandTest {
    private AddressBookModel addressAddressBookModel = new AddressBookModelManager();
    private AddressBookModel expectedAddressAddressBookModel = new AddressBookModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), addressAddressBookModel, expectedCommandResult, expectedAddressAddressBookModel);
    }
}
