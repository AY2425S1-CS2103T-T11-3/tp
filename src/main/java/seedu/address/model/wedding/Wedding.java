package seedu.address.model.wedding;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents a wedding plan.
 */
public class Wedding {
    private Name name;
    private Client client;
    private Date date;
    private Venue venue;

    /**
     * Constructs a temporary {@code Wedding}.
     */
    public Wedding(Name name, Date date, Venue venue) {
        requireAllNonNull(name);
        this.name = name;
        this.date = date;
        this.venue = venue;
    }

    /**
     * Constructs a {@code Wedding}.
     */
    public Wedding(Name name, Client client, Date date, Venue venue) {
        requireAllNonNull(name);
        this.name = name;
        if (client.getPerson().getOwnWedding() == null || client.getPerson().getOwnWedding()!= this) {
            client.getPerson().setOwnWedding(this);
        }
        this.client = client;
        this.date = date;
        this.venue = venue;
    }

    public void setClient(Person person) {
        this.client = new Client(person);
        if (person.getOwnWedding() == null || person.getOwnWedding() != this) {
            throw new IllegalArgumentException("client in Wedding does not match ownWedding in Client");
        }
    }

    public Name getName() {
        return name;
    }

    public Client getClient() {
        return client;
    }

    public Date getDate() {
        return date;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     * Returns true if both weddings have the same identity.
     */
    public boolean isSameWedding(Wedding otherWedding) {
        return this.equals(otherWedding);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("client", client)
                .add("date", date)
                .add("venue", venue)
                .toString();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Wedding)) {
            return false;
        }

        Wedding otherWedding = (Wedding) other;
        return name.equals(otherWedding.name)
               && ((client == null && otherWedding.client == null) || client.equals(otherWedding.client))
               && ((date == null && otherWedding.date == null) || date.equals(otherWedding.date))
               && ((venue == null && otherWedding.venue == null) || venue.equals(otherWedding.venue));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, venue);
    }
}
