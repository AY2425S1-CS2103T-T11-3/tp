package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressModel;
import seedu.address.model.book.addressbook.AddressModelManager;

public class HelpCommandTest {
    private AddressModel addressModel = new AddressModelManager();
    private AddressModel expectedAddressModel = new AddressModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), addressModel, expectedCommandResult, expectedAddressModel);
    }
}
