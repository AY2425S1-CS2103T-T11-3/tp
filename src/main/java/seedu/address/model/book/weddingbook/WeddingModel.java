package seedu.address.model.book.weddingbook;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.book.BookObject;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.wedding.Wedding;

/**
 * The API of the WeddingModel component.
 */
public abstract class WeddingModel implements Model {
    public static Predicate<Wedding> PREDICATE_SHOW_ALL_WEDDINGS = unused -> true;
    public abstract void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    public abstract ReadOnlyUserPrefs getUserPrefs();

    public abstract GuiSettings getGuiSettings();

    public abstract void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' wedding book file path.
     */
    public abstract Path getWeddingBookFilePath();

    @Override
    public Path getBookFilePath() {
        return getWeddingBookFilePath();
    }

    /**
     * Sets the user prefs' wedding book file path.
     */
    public abstract void setWeddingBookFilePath(Path weddingBookFilePath);

    @Override
    public void setBookFilePath(Path bookFilePath) {
        setWeddingBookFilePath(bookFilePath);
    }

    /**
     * Replaces wedding book data with the data in {@code weddingBook}.
     */
    public abstract void setWeddingBook(ReadOnlyWeddingBook weddingBook);

    @Override
    public void setBook(ReadOnlyBook<? extends BookObject> book) {
        if (!(book instanceof ReadOnlyWeddingBook)) {
            // throw error
        }
        ReadOnlyWeddingBook weddingBook = (ReadOnlyWeddingBook) book;
        setWeddingBook(weddingBook);
    }

    /** Returns the WeddingBook */
    public abstract ReadOnlyWeddingBook getWeddingBook();

    @Override
    public ReadOnlyBook<? extends BookObject> book() {
        return getWeddingBook();
    }

    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the wedding book.
     */
    public abstract boolean hasWedding(Wedding wedding);

    /**
     * Returns true if an object with the same identity as {@code object} exists in the book.
     */
    public boolean hasObject(BookObject object) {
        if (!(object instanceof Wedding)) {
            throw new IllegalArgumentException("The object is not a Wedding.");
        }
        Wedding wedding = (Wedding) object;
        return hasWedding(wedding);
    }

    /**
     * Deletes the given wedding.
     * The wedding must exist in the wedding book.
     */
    public abstract void deleteWedding(Wedding wedding);

    @Override
    public void deleteObject(BookObject object) {
        if (!(object instanceof Wedding)) {
            throw new IllegalArgumentException("The object is not a Wedding.");
        }
        Wedding wedding = (Wedding) object;
        deleteWedding(wedding);
    }

    /**
     * Adds the given wedding.
     * {@code wedding} must not already exist in the wedding book.
     */
    public abstract void addWedding(Wedding wedding);

    @Override
    public void addObject(BookObject object) {
        if (!(object instanceof Wedding)) {
            throw new IllegalArgumentException("The object is not a Wedding.");
        }
        Wedding wedding = (Wedding) object;
        addWedding(wedding);
    }

    /**
     * Replaces the given wedding {@code target} with {@code editedWedding}.
     * {@code target} must exist in the wedding book.
     * The wedding identity of {@code editedWedding} must not be the same as another existing wedding
     * in the wedding book.
     */
    public abstract void setWedding(Wedding target, Wedding editedWedding);

    public void setObject(BookObject target, BookObject editedObject) {
        if (!(target instanceof Wedding) || !(editedObject instanceof Wedding)) {
            throw new IllegalArgumentException("The object is not a Wedding.");
        }
        Wedding target1 = (Wedding) target;
        Wedding editedWedding = (Wedding) editedObject;
        setWedding(target1, editedWedding);
    }

    /** Returns an unmodifiable view of the filtered wedding list */
    public abstract ObservableList<Wedding> getFilteredWeddingList();

    public ObservableList<? extends BookObject> getFilteredList() {
        return getFilteredWeddingList();
    }

    /**
     * Updates the filter of the filtered wedding list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public abstract void updateFilteredWeddingList(Predicate<Wedding> predicate);

    @Override
    public void updateFilteredList(Predicate<? extends BookObject> predicate) {
        if (!(predicate instanceof Predicate)) {
            throw new IllegalArgumentException("Predicate must be of type Predicate<Person>");
        }

        @SuppressWarnings("unchecked")
        Predicate<Wedding> weddingPredicate = (Predicate<Wedding>) predicate;
        updateFilteredWeddingList(weddingPredicate);
    }
}
