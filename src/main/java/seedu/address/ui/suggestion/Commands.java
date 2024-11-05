package seedu.address.ui.suggestion;

/**
 * Enum representing the various commands available in the application.
 * Each command has a name, an example format, and associated prefixes for parameters.
 */
public enum Commands {
    ADD("add", "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [r/ROLE] [w/WEDDING...]",
            new String[]{"n/", "p/", "e/", "a/", "r/", "w/"}),
    DELETE("delete", "delete INDEX/NAME", new String[]{}),
    CLEAR("clear", "clear", new String[]{}),
    VIEW("view", "view NAME", new String[]{}),
    FILTER("filter", "filter KEYWORD [MORE_KEYWORDS...]", new String[]{}),
    LIST("list", "list", new String[]{}),
    EXIT("exit", "exit", new String[]{}),
    HELP("help", "help", new String[]{}),
    ADDWEDDING("addw", "addw n/WEDDINGNAME c/CLIENT [d/DATE] [v/VENUE]",
            new String[]{"d/", "v/", "c/", "n/"}),
    TAGWEDDING("tagw", "tagw INDEX/NAME w/WEDDING r/ROLE ", new String[]{"w/", "r/"}),
    EDIT("edit", "edit INDEX/NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]",
            new String[]{"n/", "p/", "e/", "a/"}),
    VIEWWEDDING("vieww", "vieww WEDDINGNAME/INDEX", new String[]{}),


    DELETEWEDDING("deletew", "deletew INDEX", new String[]{}),
    TAGGING("tag", "tag INDEX/NAME [t/TAG...]", new String[]{"t/"});

    private final String commandName;
    private final String formatExample;
    private final String[] formatPrefixes;

    /**
     * Constructor for Commands enum.
     *
     * @param commandName   The name of the command.
     * @param formatExample  The example format of the command.
     * @param formatPrefixes The prefixes for parameters associated with the command.
     */
    Commands(String commandName, String formatExample, String[] formatPrefixes) {
        this.commandName = commandName;
        this.formatExample = formatExample;
        this.formatPrefixes = formatPrefixes;
    }

    /**
     * Gets the command name.
     *
     * @return The name of the command.
     */
    public String getCommand() {
        return commandName;
    }

    /**
     * Gets the example format of the command.
     *
     * @return The format example of the command.
     */
    public String getExample() {
        return formatExample;
    }

    /**
     * Gets the prefixes associated with the command.
     *
     * @return An array of prefixes for the command's parameters.
     */
    public String[] getPrefix() {
        return formatPrefixes;
    }
}
