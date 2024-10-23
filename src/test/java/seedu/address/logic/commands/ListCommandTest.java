package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressBookModelManager;
import seedu.address.model.book.addressbook.AddressBookModel;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private AddressBookModel addressAddressBookModel;
    private AddressBookModel expectedAddressAddressBookModel;

    @BeforeEach
    public void setUp() {
        addressAddressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressAddressBookModel = new AddressBookModelManager(addressAddressBookModel.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), addressAddressBookModel, ListCommand.MESSAGE_SUCCESS, expectedAddressAddressBookModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(addressAddressBookModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), addressAddressBookModel, ListCommand.MESSAGE_SUCCESS, expectedAddressAddressBookModel);
    }
}
