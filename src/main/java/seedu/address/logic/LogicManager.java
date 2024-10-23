package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.PersonCommandResult;
import seedu.address.logic.commands.WeddingCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.GeneralParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookObject;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.book.addressbook.AddressBookModel;
import seedu.address.model.book.addressbook.ReadOnlyAddressBook;
import seedu.address.model.book.weddingbook.ReadOnlyWeddingBook;
import seedu.address.model.book.weddingbook.WeddingBookModel;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.storage.Storage;
import seedu.address.storage.WeddingBookStorage;

/**
 * The main AddressLogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final AddressBookModel addressBookModel;
    private final WeddingBookModel weddingBookModel;
    private final Storage storage;
    private final GeneralParser generalParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(AddressBookModel addressBookModel, WeddingBookModel weddingBookModel, Storage storage) {
        this.addressBookModel = addressBookModel;
        this.weddingBookModel = weddingBookModel;
        this.storage = storage;
        generalParser = new GeneralParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = generalParser.parseCommand(commandText);
        CommandResult cr1 = command.execute(addressBookModel);
        CommandResult cr2 = command.execute(weddingBookModel);
        commandResult = CommandResult.selectNonNull(cr1, cr2);

        try {
            storage.saveAddressBook(addressBookModel.getAddressBook());
            storage.saveWeddingBook(weddingBookModel.getWeddingBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBookModel.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressBookModel.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return addressBookModel.getAddressBookFilePath();
    }

    @Override
    public ReadOnlyWeddingBook getWeddingBook() {
        return weddingBookModel.getWeddingBook();
    }

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return weddingBookModel.getFilteredWeddingList();
    }

    @Override
    public Path getWeddingBookFilePath() {
        return weddingBookModel.getWeddingBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return addressBookModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        addressBookModel.setGuiSettings(guiSettings);
    }
}
