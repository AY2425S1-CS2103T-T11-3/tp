package seedu.address.logic.commands;

public class WeddingCommandResult extends CommandResult {
    public WeddingCommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        super(feedbackToUser, showHelp, exit);
    }

    public WeddingCommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }
}
