package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.addressbook.AddressModel;
import seedu.address.model.book.addressbook.AddressModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameMatchesNamePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ViewCommandTest {
    private AddressModel addressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());
    private AddressModel expectedAddressModel = new AddressModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameMatchesNamePredicate firstPredicate =
                new NameMatchesNamePredicate(Collections.singletonList("first"));
        NameMatchesNamePredicate secondPredicate =
                new NameMatchesNamePredicate(Collections.singletonList("second"));

        ViewCommand viewFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand viewSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(firstPredicate);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameMatchesNamePredicate predicate = preparePredicate(" ");
        ViewCommand command = new ViewCommand(predicate);
        expectedAddressModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, addressModel, expectedMessage, expectedAddressModel);
        assertEquals(Collections.emptyList(), addressModel.getFilteredPersonList());
    }

    @Test
    public void execute_multipleWordsInKeyword_exactNameMatched() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameMatchesNamePredicate predicate = preparePredicate("Kurz Elle Kunz");
        ViewCommand command = new ViewCommand(predicate);
        expectedAddressModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, addressModel, expectedMessage, expectedAddressModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), addressModel.getFilteredPersonList());
    }

    @Test
    public void execute_singleWordInKeyword_partialNameMatched() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        NameMatchesNamePredicate predicate = preparePredicate(KEYWORD_MATCHING_MEIER);
        ViewCommand command = new ViewCommand(predicate);
        expectedAddressModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, addressModel, expectedMessage, expectedAddressModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), addressModel.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameMatchesNamePredicate predicate = new NameMatchesNamePredicate(Arrays.asList("keyword"));
        ViewCommand viewCommand = new ViewCommand(predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameMatchesNamePredicate preparePredicate(String userInput) {
        return new NameMatchesNamePredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
