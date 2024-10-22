package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.BookObject;
import seedu.address.model.book.ReadOnlyBook;

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' book file path.
     */
    Path getBookFilePath();

    /**
     * Sets the user prefs' book file path.
     */
    void setBookFilePath(Path bookFilePath);

    /**
     * Replaces book data with the data in {@code book}.
     */
    void setBook(ReadOnlyBook<? extends BookObject> book);

    /** Returns the book */
    ReadOnlyBook<? extends BookObject> book();

    /**
     * Returns true if an object with the same identity as {@code object} exists in the book.
     */
    boolean hasObject(BookObject object);

    /**
     * Deletes the given object.
     * The object must exist in the book.
     */
    void deleteObject(BookObject object);

    /**
     * Adds the given object.
     * {@code object} must not already exist in the book.
     */
    void addObject(BookObject object);

    /**
     * Replaces the given object {@code target} with {@code editedObject}.
     * {@code target} must exist in the book.
     * The object identity of {@code editedObject} must not be the same as another existing object in the book.
     */
    void setObject(BookObject target, BookObject editedObject);

    /** Returns an unmodifiable view of the filtered list */
    ObservableList<? extends BookObject> getFilteredList();

    /**
     * Updates the filter of the filtered list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredList(Predicate<? extends BookObject> predicate);
}
