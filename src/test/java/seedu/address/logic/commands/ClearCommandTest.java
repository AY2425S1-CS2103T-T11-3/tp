package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressBook;
import seedu.address.model.book.addressbook.AddressModel;
import seedu.address.model.book.addressbook.AddressModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        AddressModel addressModel = new AddressModelManager();
        AddressModel expectedAddressModel = new AddressModelManager();

        assertCommandSuccess(new ClearCommand(), addressModel, ClearCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        AddressModel addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressModel expectedAddressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), addressModel, ClearCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

}
