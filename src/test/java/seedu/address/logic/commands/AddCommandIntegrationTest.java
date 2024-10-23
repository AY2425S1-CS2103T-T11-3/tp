package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.book.addressbook.AddressBookModelManager;
import seedu.address.model.book.addressbook.AddressBookModel;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private AddressBookModel addressAddressBookModel;

    @BeforeEach
    public void setUp() {
        addressAddressBookModel = new AddressBookModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        AddressBookModel expectedAddressAddressBookModel = new AddressBookModelManager(addressAddressBookModel.getAddressBook(), new UserPrefs());
        expectedAddressAddressBookModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), addressAddressBookModel,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedAddressAddressBookModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = addressAddressBookModel.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), addressAddressBookModel,
                AddCommand.MESSAGE_DUPLICATE_CONTACT);
    }

}
