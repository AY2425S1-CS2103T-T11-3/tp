package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the Date of a Wedding.
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Date is not in the correct format or is not in the future.";
    public static final String MESSAGE_CONSTRAINTS_WRONG_FORMAT =
            "Date should be in the following format, "
                    + "YYYY-MM-DD.";
    public static final String MESSAGE_CONSTRAINTS_NOT_IN_FUTURE =
            "The date inputted should be in the future.";

    public static final String MESSAGE_CONSTRAINTS_NOT_WITHIN_3_YEARS =
            "The date inputted should be within 3 years from today.";

    // Use built-in formatter to parse and validate the date
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private LocalDate fullDate;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date in the format of "yyyy-MM-dd".
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date) && isInTheFuture(date), MESSAGE_CONSTRAINTS);
        this.fullDate = LocalDate.parse(date, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date according to the format "yyyy-MM-dd".
     *
     * @param test string to be tested
     * @return whether the string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a date in the future.
     *
     * @param test string to be tested
     * @return whether the string is a future date.
     */
    public static boolean isInTheFuture(String test) {
        LocalDate date = LocalDate.parse(test, FORMATTER);
        LocalDate today = LocalDate.now();
        return date.isAfter(today);
    }

    /**
     * Returns true if a given string is a date within the next 3 years.
     *
     * @param test string to be tested
     * @return whether the string is a date within the next 3 years.
     */
    public static boolean isWithinNextThreeYears(String test) {
        LocalDate date = LocalDate.parse(test, FORMATTER);
        LocalDate today = LocalDate.now();

        LocalDate max = LocalDate.of(today.getYear() + 3, today.getMonth(), today.getDayOfMonth());
        return date.isBefore(max);
    }

    @Override
    public String toString() {
        return fullDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return fullDate.equals(otherDate.fullDate);
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }
}
