package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.book.addressbook.AddressModel;
import seedu.address.model.book.addressbook.AddressModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private AddressModel addressModel;

    @BeforeEach
    public void setUp() {
        addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        AddressModel expectedAddressModel = new AddressModelManager(addressModel.getAddressBook(), new UserPrefs());
        expectedAddressModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), addressModel,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedAddressModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = addressModel.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), addressModel,
                AddCommand.MESSAGE_DUPLICATE_CONTACT);
    }

}
