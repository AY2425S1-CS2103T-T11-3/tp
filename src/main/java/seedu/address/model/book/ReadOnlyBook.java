package seedu.address.model.book;

import javafx.collections.ObservableList;

/**
 * A generic class for ReadOnlyAddressBook and ReadOnlyWeddingBook.
 * @param <T> Wedding or Person.
 */
public interface ReadOnlyBook<T> {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<T> getList();
}
