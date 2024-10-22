package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_HUSBAND_NAME = new Prefix("hn/");
    public static final Prefix PREFIX_HUSBAND_FULLNAME = new Prefix("hfn/");
    public static final Prefix PREFIX_HUSBAND_PHONE = new Prefix("hp/");
    public static final Prefix PREFIX_HUSBAND_EMAIL = new Prefix("he/");
    public static final Prefix PREFIX_HUSBAND_ADDRESS = new Prefix("ha/");
    public static final Prefix PREFIX_WIFE_NAME = new Prefix("wn/");
    public static final Prefix PREFIX_WIFE_FULLNAME = new Prefix("wfn/");
    public static final Prefix PREFIX_WIFE_PHONE = new Prefix("wp/");
    public static final Prefix PREFIX_WIFE_EMAIL = new Prefix("we/");
    public static final Prefix PREFIX_WIFE_ADDRESS = new Prefix("wa/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_VENUE = new Prefix("v/");
}
