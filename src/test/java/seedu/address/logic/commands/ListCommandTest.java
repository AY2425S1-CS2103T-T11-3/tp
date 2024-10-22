package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressModel;
import seedu.address.model.book.addressbook.AddressModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private AddressModel addressModel;
    private AddressModel expectedAddressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), addressModel, ListCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(addressModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), addressModel, ListCommand.MESSAGE_SUCCESS, expectedAddressModel);
    }
}
