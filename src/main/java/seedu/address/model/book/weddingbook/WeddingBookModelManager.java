package seedu.address.model.book.weddingbook;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.addressbook.AddressBookModelManager;
import seedu.address.model.wedding.Wedding;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class WeddingBookModelManager extends WeddingBookModel {
    private static final Logger logger = LogsCenter.getLogger(WeddingBookModelManager.class);

    private final WeddingBook weddingBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Wedding> filteredWeddings;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public WeddingBookModelManager(seedu.address.model.book.weddingbook.ReadOnlyWeddingBook weddingBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(weddingBook, userPrefs);

        logger.fine("Initializing with wedding book: " + weddingBook + " and user prefs " + userPrefs);

        this.weddingBook = new WeddingBook(weddingBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredWeddings = new FilteredList<>(this.weddingBook.getWeddingList());
    }

    public WeddingBookModelManager() {
        this(new WeddingBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getWeddingBookFilePath() {
        return userPrefs.getWeddingBookFilePath();
    }

    @Override
    public void setWeddingBookFilePath(Path weddingBookFilePath) {
        requireNonNull(weddingBookFilePath);
        userPrefs.setAddressBookFilePath(weddingBookFilePath);
    }

    //=========== WeddingBook ================================================================================

    public void setWeddingBook(ReadOnlyWeddingBook weddingBook) {
        this.weddingBook.resetData(weddingBook);
    }

    @Override
    public seedu.address.model.book.weddingbook.ReadOnlyWeddingBook getWeddingBook() {
        return weddingBook;
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddingBook.hasWedding(wedding);
    }

    @Override
    public void deleteWedding(Wedding target) {
        weddingBook.removeWedding(target);
    }

    @Override
    public void addWedding(Wedding wedding) {
        weddingBook.addWedding(wedding);
        updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
    }

    @Override
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireAllNonNull(target, editedWedding);

        weddingBook.setWedding(target, editedWedding);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return filteredWeddings;
    }

    @Override
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBookModelManager)) {
            return false;
        }

        WeddingBookModelManager otherModelManager = (WeddingBookModelManager) other;
        return weddingBook.equals(otherModelManager.weddingBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredWeddings.equals(otherModelManager.filteredWeddings);
    }
}
