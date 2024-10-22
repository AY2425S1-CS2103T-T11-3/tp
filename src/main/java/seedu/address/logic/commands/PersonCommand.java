package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.addressbook.AddressModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class PersonCommand extends Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!(model instanceof AddressModel)) {
            throw new CommandException("Invalid model type for WeddingCommand.");
        }
        return execute((AddressModel) model);
    }

    protected abstract CommandResult execute(AddressModel model) throws CommandException;

}
