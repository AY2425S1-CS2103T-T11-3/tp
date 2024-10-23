package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressBook;
import seedu.address.model.book.addressbook.AddressBookModel;
import seedu.address.model.book.addressbook.AddressBookModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        AddressBookModel addressAddressBookModel = new AddressBookModelManager();
        AddressBookModel expectedAddressAddressBookModel = new AddressBookModelManager();

        assertCommandSuccess(new ClearCommand(), addressAddressBookModel, ClearCommand.MESSAGE_SUCCESS, expectedAddressAddressBookModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        AddressBookModel addressAddressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());
        AddressBookModel expectedAddressAddressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressAddressBookModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), addressAddressBookModel, ClearCommand.MESSAGE_SUCCESS, expectedAddressAddressBookModel);
    }

}
