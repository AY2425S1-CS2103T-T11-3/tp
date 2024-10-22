package seedu.address.model.book.addressbook;

import javafx.collections.ObservableList;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public abstract class ReadOnlyAddressBook implements ReadOnlyBook<Person> {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    public abstract ObservableList<Person> getPersonList();

    @Override
    public ObservableList<Person> getList() {
        return this.getPersonList();
    }
}
