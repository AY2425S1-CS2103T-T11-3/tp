package seedu.address.logic.commands;

public class PersonCommandResult extends CommandResult {
    public PersonCommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        super(feedbackToUser, showHelp, exit);
    }

    public PersonCommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }
}
