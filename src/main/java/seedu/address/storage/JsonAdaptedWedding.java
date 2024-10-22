package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Husband;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.Wife;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
public class JsonAdaptedWedding {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String husbandName;
    private final String wifeName;
    private final String date;
    private final String venue;

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given wedding details.
     */
    @JsonCreator
    public JsonAdaptedWedding(@JsonProperty("husbandName") String husbandName,
                              @JsonProperty("wifeName") String wifeName,
                              @JsonProperty("date") String date,
                              @JsonProperty("venue") String venue) {
        this.husbandName = husbandName;
        this.wifeName = wifeName;
        this.date = date;
        this.venue = venue;
    }

    /**
     * Converts a given {@code wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        husbandName = source.getHusband().getNameToUse().toString();
        wifeName = source.getHusband().getNameToUse().toString();
        date = source.getDate().toString();
        venue = source.getVenue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {

        if (husbandName == null || wifeName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(husbandName) || Name.isValidName(wifeName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelHusbandName = new Name(husbandName);
        final Husband modelHusband = new Husband(modelHusbandName);
        final Name modelWifeName = new Name(wifeName);
        final Wife modelWife = new Wife(modelWifeName);

        Date modelDate = null;
        if (date != null) {
            if (!Date.isValidDate(date)) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
            modelDate = new Date(date);
        }

        Venue modelVenue = null;
        if (venue != null) {
            if (!Venue.isValidVenue(venue)) {
                throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
            }
            modelVenue = new Venue(venue);
        }

        return new Wedding(modelHusband, modelWife, modelDate, modelVenue);
    }
}
