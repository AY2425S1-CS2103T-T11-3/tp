package seedu.address.model.book.weddingbook;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class WeddingBook extends ReadOnlyWeddingBook {
    private final UniqueWeddingList weddings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        weddings = new UniqueWeddingList();
    }

    public WeddingBook() {}

    /**
     * Creates a WeddingBook using the Weddings in the {@code toBeCopied}
     */
    public WeddingBook(ReadOnlyWeddingBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the wedding list with {@code weddings}.
     * {@code weddings} must not contain duplicate weddings.
     */
    public void setWeddings(List<Wedding> weddings) {
        this.weddings.setWeddings(weddings);
    }

    /**
     * Resets the existing data of this {@code WeddingBook} with {@code newData}.
     */
    public void resetData(ReadOnlyWeddingBook newData) {
        requireNonNull(newData);

        setWeddings(newData.getWeddingList());
    }

    //// wedding-level operations

    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the wedding book.
     */
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddings.contains(wedding);
    }

    /**
     * Adds a wedding to the wedding book.
     * The wedding must not already exist in the wedding book.
     */
    public void addWedding(Wedding p) {
        weddings.add(p);
    }

    /**
     * Replaces the given wedding {@code target} in the list with {@code editedWedding}.
     * {@code target} must exist in the wedding book.
     * The wedding identity of {@code editedWedding} must not be the same as another existing wedding in the wedding book.
     */
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireNonNull(editedWedding);

        weddings.setWedding(target, editedWedding);
    }

    /**
     * Removes {@code key} from this {@code WeddingBook}.
     * {@code key} must exist in the wedding book.
     */
    public void removeWedding(Wedding key) {
        weddings.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("weddings", weddings)
                .toString();
    }

    @Override
    public ObservableList<Wedding> getWeddingList() {
        return weddings.asUnmodifiableObservableList();
    }

}
