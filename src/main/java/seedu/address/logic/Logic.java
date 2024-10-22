package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookObject;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.book.addressbook.AddressModel;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Book.
     *
     * @see AddressModel#getAddressBook()
     */
    ReadOnlyBook<? extends BookObject> getBook();

    /** Returns an unmodifiable view of the filtered list */
    ObservableList<? extends BookObject> getFilteredList();

    /**
     * Returns the user prefs'  book file path.
     */
    Path getBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
