package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.addressbook.ReadOnlyAddressBook;
import seedu.address.model.book.weddingbook.ReadOnlyWeddingBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private WeddingBookStorage weddingBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage,
                          WeddingBookStorage weddingBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.weddingBookStorage = weddingBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ WeddingBook methods ==============================

    @Override
    public Path getWeddingBookFilePath() {
        return weddingBookStorage.getWeddingBookFilePath();
    }

    @Override
    public Optional<ReadOnlyWeddingBook> readWeddingBook() throws DataLoadingException {
        return readWeddingBook(weddingBookStorage.getWeddingBookFilePath());
    }

    @Override
    public Optional<ReadOnlyWeddingBook> readWeddingBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return weddingBookStorage.readWeddingBook(filePath);
    }

    @Override
    public void saveWeddingBook(ReadOnlyWeddingBook weddingBook) throws IOException {
        saveWeddingBook(weddingBook, weddingBookStorage.getWeddingBookFilePath());
    }

    @Override
    public void saveWeddingBook(ReadOnlyWeddingBook weddingBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        weddingBookStorage.saveWeddingBook(weddingBook, filePath);
    }
}
