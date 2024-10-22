package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.AddressLogicManager;
import seedu.address.logic.WeddingLogicManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.addressbook.AddressBook;
import seedu.address.model.book.addressbook.AddressModel;
import seedu.address.model.book.addressbook.AddressModelManager;
import seedu.address.model.book.addressbook.ReadOnlyAddressBook;
import seedu.address.model.book.weddingbook.WeddingBook;
import seedu.address.model.book.weddingbook.WeddingModel;
import seedu.address.model.book.weddingbook.WeddingModelManager;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.*;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;


/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected AddressLogicManager addressLogic;
    protected WeddingLogicManager weddingLogic;
    protected Storage storage;
    protected Storage weddingStorage;
    protected AddressModel addressModel;
    protected WeddingModel weddingModel;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        WeddingBookStorage weddingBookStorage = new JsonWeddingBookStorage(userPrefs.getWeddingBookFilePath());

        storage = new StorageManager(addressBookStorage, weddingBookStorage, userPrefsStorage);

        addressModel = initAddressModelManager(storage, userPrefs);
        weddingModel = initWeddingModelManager(storage, userPrefs);

        addressLogic = new AddressLogicManager(addressModel, storage);
        weddingLogic = new WeddingLogicManager(weddingModel, storage);

        ui = new UiManager(addressLogic, weddingLogic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private AddressModel initAddressModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialData = new AddressBook();
        }

        return new AddressModelManager(initialData, userPrefs);
    }

    private WeddingModel initWeddingModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getWeddingBookFilePath());

        Optional<seedu.address.model.book.weddingbook.ReadOnlyWeddingBook> weddingBookOptional;
        seedu.address.model.book.weddingbook.ReadOnlyWeddingBook initialData;
        try {
            weddingBookOptional = storage.readWeddingBook();
            if (!weddingBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialData = weddingBookOptional.orElseGet(SampleDataUtil::getSampleWeddingBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getWeddingBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty Wedding.");
            initialData = new WeddingBook();
        }

        return new WeddingModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping AddressBook ] =============================");
        try {
            storage.saveUserPrefs(addressModel.getUserPrefs());
//            weddingStorage.saveUserPrefs(weddingModel.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
