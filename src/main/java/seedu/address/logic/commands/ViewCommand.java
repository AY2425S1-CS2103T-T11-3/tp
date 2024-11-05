package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.PersonHasWeddingPredicate;


/**
 * View the person in address book whose name matches the keyword or at the given index.
 * Keyword matching is case insensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the contact you want to see "
            + "with the name (case-insensitive).\n"
            + "Parameters: NAME (the name of contact)\n"
            + "Example: " + COMMAND_WORD + " alice";

    public static final String MESSAGE_VIEW_EMPTY_LIST_ERROR = "There is no contact to view.";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Viewing contact: %s";

    public static final String MESSAGE_DUPLICATE_HANDLING =
            "To view a specific contact, please specify the index of the contact you want to view.\n"
                    + "Find the index from the list below and type view INDEX\n"
                    + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;
    private final NameMatchesKeywordPredicate predicate;

    /**
     * Creates a ViewCommand to view the contact details of the specified person
     */
    public ViewCommand(Index targetIndex, NameMatchesKeywordPredicate predicate) {
        this.targetIndex = targetIndex;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.targetIndex != null) {
            Person personToView = getPersonByIndex(model);
            model.updateFilteredPersonList(p -> p.equals(personToView));
            model.updateFilteredWeddingList(new PersonHasWeddingPredicate(personToView));
            return new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName()));
        } else {
            Person personToView = getPersonByKeyword(model);
            if (personToView != null) {
                // unique person found
                model.updateFilteredWeddingList(new PersonHasWeddingPredicate(personToView));
                return new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName()));
            } else {
                return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                                model.getFilteredPersonList().size()) + "\n" + MESSAGE_DUPLICATE_HANDLING);
            }
        }
    }

    /**
     * Gets the person by index.
     */
    private Person getPersonByIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_VIEW_EMPTY_LIST_ERROR);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Gets the person by keyword.
     */
    private Person getPersonByKeyword(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_VIEW_EMPTY_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            return filteredList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;

        // Both commands have null fields
        boolean bothHaveNullIndex = targetIndex == null && otherViewCommand.targetIndex == null;
        boolean bothHaveNullPredicates = predicate == null && otherViewCommand.predicate == null;

        // Both commands have non-null fields
        boolean bothHaveIndex = targetIndex != null && otherViewCommand.targetIndex != null;
        boolean bothHavePredicates = predicate != null && otherViewCommand.predicate != null;

        // Case 1: Both have null targetIndex and null predicate
        if (bothHaveNullIndex && bothHaveNullPredicates) {
            return true;
        }

        // Case 2: Both have targetIndex but null predicate
        if (bothHaveIndex && bothHaveNullPredicates) {
            return targetIndex.equals(otherViewCommand.targetIndex);
        }

        // Case 3: Both have null targetIndex but have predicate
        if (bothHaveNullIndex && bothHavePredicates) {
            return predicate.equals(otherViewCommand.predicate);
        }

        // All other cases are false
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
