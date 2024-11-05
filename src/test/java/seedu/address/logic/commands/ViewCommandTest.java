package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getAdditionalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookFilterWithWeddings;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.PersonHasWeddingPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ViewCommandTest {
    private Model model = new ModelManager(getAdditionalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        // predicate comparison
        NameMatchesKeywordPredicate firstPredicate =
                new NameMatchesKeywordPredicate(Collections.singletonList("first"));
        NameMatchesKeywordPredicate secondPredicate =
                new NameMatchesKeywordPredicate(Collections.singletonList("second"));

        ViewCommand viewFirstCommand = new ViewCommand(null, firstPredicate);
        ViewCommand viewSecondCommand = new ViewCommand(null, secondPredicate);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(null, firstPredicate);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));

        // index comparison
        viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON, null);
        viewSecondCommand = new ViewCommand(INDEX_SECOND_PERSON, null);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PERSON, null);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_validIndex_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredWeddingList(new PersonHasWeddingPredicate(personToView));
        expectedModel.updateFilteredPersonList(p -> p.equals(personToView));

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex, null);

        assertCommandFailure(viewCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        model.getFilteredPersonList().size()));
    }

    @Test
    public void execute_emptyWeddingList_throwsCommandException() {
        // Clear the wedding list
        Model model1 = new ModelManager(getTypicalAddressBookFilterWithWeddings(), new UserPrefs());
        model1.setAddressBook(new AddressBook());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON, null);

        assertCommandFailure(viewCommand, model1, ViewCommand.MESSAGE_VIEW_EMPTY_LIST_ERROR);
    }

    @Test
    public void execute_validKeyword_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        NameMatchesKeywordPredicate predicate = preparePredicate(personToView.getName().fullName);
        ViewCommand viewCommand = new ViewCommand(null, predicate);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredWeddingList(new PersonHasWeddingPredicate(personToView));

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleMatchesKeyword_returnsHandlingMessage() {
        // Set up scenario where multiple persons match the keyword
        NameMatchesKeywordPredicate predicate = preparePredicate("carl");
        ViewCommand viewCommand = new ViewCommand(null, predicate);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size()) + "\n" + ViewCommand.MESSAGE_DUPLICATE_HANDLING;

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        NameMatchesKeywordPredicate predicate = new NameMatchesKeywordPredicate(Arrays.asList("keyword"));
        ViewCommand viewCommand = new ViewCommand(null, predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewCommand.toString());
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameMatchesKeywordPredicate preparePredicate(String userInput) {
        return new NameMatchesKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
