package seedu.edudex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.edudex.commons.core.Config;
import seedu.edudex.commons.core.LogsCenter;
import seedu.edudex.commons.core.Version;
import seedu.edudex.commons.exceptions.DataLoadingException;
import seedu.edudex.commons.util.ConfigUtil;
import seedu.edudex.commons.util.StringUtil;
import seedu.edudex.logic.Logic;
import seedu.edudex.logic.LogicManager;
import seedu.edudex.model.EduDex;
import seedu.edudex.model.Model;
import seedu.edudex.model.ModelManager;
import seedu.edudex.model.ReadOnlyEduDex;
import seedu.edudex.model.ReadOnlyUserPrefs;
import seedu.edudex.model.UserPrefs;
import seedu.edudex.model.util.SampleDataUtil;
import seedu.edudex.storage.EduDexStorage;
import seedu.edudex.storage.JsonEduDexStorage;
import seedu.edudex.storage.JsonUserPrefsStorage;
import seedu.edudex.storage.Storage;
import seedu.edudex.storage.StorageManager;
import seedu.edudex.storage.UserPrefsStorage;
import seedu.edudex.ui.Ui;
import seedu.edudex.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    private boolean isFirstLaunch = false;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing EduDex ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        EduDexStorage eduDexStorage = new JsonEduDexStorage(userPrefs.getEduDexFilePath());
        storage = new StorageManager(eduDexStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s EduDex and {@code userPrefs}. <br>
     * The data from the sample EduDex will be used instead if {@code storage}'s EduDex is not found,
     * or an empty EduDex will be used instead if errors occur when reading {@code storage}'s EduDex.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getEduDexFilePath());

        Optional<ReadOnlyEduDex> eduDexOptional;
        ReadOnlyEduDex initialData;
        isFirstLaunch = false;

        try {
            eduDexOptional = storage.readEduDex();
            if (!eduDexOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getEduDexFilePath()
                        + " populated with a sample EduDex.");
                isFirstLaunch = true;
            }
            initialData = eduDexOptional.orElseGet(SampleDataUtil::getSampleEduDex);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getEduDexFilePath() + " could not be loaded."
                    + " Will be starting with an empty EduDex.");
            initialData = new EduDex();
        }
        return new ModelManager(initialData, userPrefs);
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
        logger.info("Starting EduDex " + MainApp.VERSION);
        ui.start(primaryStage);

        // Show welcome message if first launch
        if (isFirstLaunch) {
            ui.showWelcomeMessage();
        }
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping EduDex ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
