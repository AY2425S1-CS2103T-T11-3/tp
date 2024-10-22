package seedu.address.model.book.weddingbook;

import javafx.collections.ObservableList;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.wedding.Wedding;

/**
 * Unmodifiable view of an wedding book
 */
public abstract class ReadOnlyWeddingBook implements ReadOnlyBook<Wedding> {

    /**
     * Returns an unmodifiable view of the weddings list.
     * This list will not contain any duplicate weddings.
     */
    public abstract ObservableList<Wedding> getWeddingList();

    @Override
    public ObservableList<Wedding> getList() {
        return this.getWeddingList();
    }
}
