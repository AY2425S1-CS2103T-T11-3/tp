package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.PersonCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.GeneralParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.book.addressbook.AddressModel;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main AddressLogicManager of the app.
 */
public class AddressLogicManager implements Logic {

    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(AddressLogicManager.class);

    private final AddressModel addressModel;
    private final Storage storage;
    private final GeneralParser generalParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public AddressLogicManager(AddressModel addressModel, Storage storage) {
        this.addressModel = addressModel;
        this.storage = storage;
        generalParser = new GeneralParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        PersonCommand command = generalParser.parseCommand(commandText);
        commandResult = command.execute(addressModel);

        try {
            storage.saveAddressBook(addressModel.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyBook<Person> getBook() {
        return addressModel.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredList() {
        return addressModel.getFilteredPersonList();
    }

    @Override
    public Path getBookFilePath() {
        return addressModel.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return addressModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        addressModel.setGuiSettings(guiSettings);
    }
}
