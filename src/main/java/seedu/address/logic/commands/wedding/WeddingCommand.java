package seedu.address.logic.commands.wedding;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.weddingbook.WeddingModel;

/**
 * Represents a wedding command with hidden internal logic and the ability to be executed.
 */
public abstract class WeddingCommand extends Command {
    /**
     * Executes the wedding command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!(model instanceof WeddingModel)) {
            throw new CommandException("Invalid model type for WeddingCommand.");
        }
        return execute((WeddingModel) model);
    }

    protected abstract CommandResult execute(WeddingModel model) throws CommandException;
}
