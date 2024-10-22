package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.wedding.WeddingCommand;
import seedu.address.logic.parser.WeddingBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookObject;
import seedu.address.model.book.ReadOnlyBook;
import seedu.address.model.book.weddingbook.WeddingModel;
import seedu.address.storage.Storage;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * The main WeddingLogicManager of the app.
 */
public class WeddingLogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(WeddingLogicManager.class);

    private final WeddingModel weddingModel;
    private final Storage storage;
    private final WeddingBookParser weddingBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public WeddingLogicManager(WeddingModel weddingModel, Storage storage) {
        this.weddingModel = weddingModel;
        this.storage = storage;
        weddingBookParser = new WeddingBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        WeddingCommand command = weddingBookParser.parseCommand(commandText);
        commandResult = command.execute(weddingModel);

//        try {
//            //storage.saveAddressBook(weddingModel.getWeddingBook());
//        } catch (AccessDeniedException e) {
//            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
//        } catch (IOException ioe) {
//            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
//        }

        return commandResult;
    }

    @Override
    public ReadOnlyBook<? extends BookObject> getBook() {
        return weddingModel.getWeddingBook();
    }

    @Override
    public ObservableList<? extends BookObject> getFilteredList() {
        return weddingModel.getFilteredWeddingList();
    }

    @Override
    public Path getBookFilePath() {
        return weddingModel.getWeddingBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return weddingModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        weddingModel.setGuiSettings(guiSettings);
    }

}
