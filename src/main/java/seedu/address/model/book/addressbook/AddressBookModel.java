package seedu.address.model.book.addressbook;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.BookObject;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;

/**
 * The API of the AddressModel component.
 */
public abstract class AddressBookModel implements seedu.address.model.Model {
    /** {@code Predicate} that always evaluate to true */
    public static Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    public abstract void setUserPrefs(ReadOnlyUserPrefs userPrefs);;

    public abstract ReadOnlyUserPrefs getUserPrefs();

    public abstract GuiSettings getGuiSettings();

    public abstract void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    public abstract Path getAddressBookFilePath();

    @Override
    public Path getBookFilePath() {
        return getAddressBookFilePath();
    }

    /**
     * Sets the user prefs' address book file path.
     */
    public abstract void setAddressBookFilePath(Path addressBookFilePath);

    @Override
    public void setBookFilePath(Path bookFilePath) {
        setAddressBookFilePath(bookFilePath);
    }

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    public abstract void setAddressBook(ReadOnlyAddressBook addressBook);

    @Override
    public void setBook(ReadOnlyBook<? extends BookObject> book) {
        if (!(book instanceof ReadOnlyAddressBook)) {
            // throw error
        }
        ReadOnlyAddressBook addressBook = (ReadOnlyAddressBook) book;
        setAddressBook(addressBook);
    }

    /** Returns the AddressBook */
    public abstract ReadOnlyAddressBook getAddressBook();

    @Override
    public ReadOnlyBook<? extends BookObject> book() {
        return getAddressBook();
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public abstract boolean hasPerson(Person person);

    /**
     * Returns true if an object with the same identity as {@code object} exists in the book.
     */
    public boolean hasObject(BookObject object) {
        if (!(object instanceof Person)) {
            // throw error
        }
        Person person = (Person) object;
        return hasPerson(person);
    }

    /**
     * Returns true if a person has the same phone number as {@code person} in the address book.
     */
    public abstract boolean hasPhone(Person person);

    /**
     * Returns true if a person has the same email address as {@code person} in the address book.
     */
    public abstract boolean hasEmail(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    public abstract void deletePerson(Person target);

    @Override
    public void deleteObject(BookObject object) {
        if (!(object instanceof Person)) {
            // throw error
        }
        Person person = (Person) object;
        deletePerson(person);
    }

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    public abstract void addPerson(Person person);

    @Override
    public void addObject(BookObject object) {
        if (!(object instanceof Person)) {
            // throw error
        }
        Person person = (Person) object;
        addPerson(person);
    }

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public abstract void setPerson(Person target, Person editedPerson);

    public void setObject(BookObject target, BookObject editedObject) {
        if (!(target instanceof Person) || !(editedObject instanceof Person)) {
            // throw error
        }
        Person target1 = (Person) target;
        Person editedPerson = (Person) editedObject;
        setPerson(target1, editedPerson);
    }

    /** Returns an unmodifiable view of the filtered person list */
    public abstract ObservableList<Person> getFilteredPersonList();

    public ObservableList<? extends BookObject> getFilteredList() {
        return getFilteredPersonList();
    }

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public abstract void updateFilteredPersonList(Predicate<Person> predicate);

    @Override
    public void updateFilteredList(Predicate<? extends BookObject> predicate) {
        if (!(predicate instanceof Predicate)) {
            throw new IllegalArgumentException("Predicate must be of type Predicate<Person>");
        }

        @SuppressWarnings("unchecked")
        Predicate<Person> personPredicate = (Predicate<Person>) predicate;
        updateFilteredPersonList(personPredicate);
    }
}
