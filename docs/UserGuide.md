---
layout: default.md
title: "User Guide"
pageNav: 3
---

# Bridal Boss User Guide

Bridal Boss is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Bridal Boss can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* The client parameter (c/) in wedding commands can accept either an index number or a name.<br>
  e.g. `c/1` or `c/John Doe` are both valid formats.

* Dates must be specified in YYYY-MM-DD format.<br>
  e.g. `d/2024-12-31` for December 31st, 2024.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/12345678 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Viewing a contact : `view`

View the contact of a specified person from the address book.

Format: `view KEYWORD`

* This command is case-insensitive. e.g `alex tan` will match `Alex Tan`
* The keyword can be either the full name or partial name (consisting of only one word).
* Only the name can be used for viewing

Examples:
* `view John` returns `john` and `John Doe`
* `view Alex Yeo` return `Alex Yeo` and `Alex Yeo Jun Jie`

### Deleting a person : `delete`

Deletes the specified person from the address book.

If you know the index of the specific contact you want to delete:

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.

If you do not know the index but know the name of the contact you want to delete:

Format: `delete KEYWORD`

* This command is case-insensitive. e.g. `alex tan` will match `Alex Tan`
* The keyword can be either the full name or partial name.
* When there are more than one contact, of which name contains the keyword, a filtered list containing those contacts
  will be given. From that list, you can obtain the index of the specific contact you want to delete. With that index,
  you can do a `delete INDEX` to delete that specific contact.

Example:

* `delete Betsy` will delete the contact of Betsy Tan directly if there are no duplicates.
* `delete Alex` will give a list of contacts named `Alex`, and user can choose which contact from filtered list to deleted from.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Filtering persons : `filter`

Filters and lists all persons in address book whose fields (name, role, email, phone, address) match any of the
specified keywords (case-insensitive).

Format: `filter [n/NAME] [r/ROLE] [e/EMAIL] [p/PHONE] [a/ADDRESS]`

* At least one field must be provided.
* Parameters can be in any order.
* Each field can only contain single word keywords (except address which can contain multiple words and
* email which allows partial matches).
* The search is case-insensitive. e.g. `n/john` will match `John`
* Different fields are matched with different precision:
    * Name: Exact match only (must be single word)
    * Role: Exact match only
    * Email: Partial match allowed
    * Phone: Exact match only
    * Address: Partial match allowed
* When multiple fields are provided, persons matching ANY of the fields will be returned (i.e. `OR` search).

Examples:
* `filter n/John` returns `john` and `John`
* `filter r/vendor` returns persons with role `vendor` (case-insensitive)
* `filter e/gmail` returns all persons whose email contains "gmail"
* `filter p/91234567` returns person with exact phone number
* `filter n/John r/vendor` returns persons who either have name `John` OR role `vendor`
* `filter e/gmail a/jurong` returns persons whose email contains "gmail" OR address contains "jurong"

### Managing Weddings

### Adding a wedding : `addw`

Adds a wedding to the address book.

Format: `addw n/WEDDING_NAME c/CLIENT [d/DATE] [v/VENUE]`

* The wedding name must be specified
* The client must be specified either by their index in the displayed list (e.g., `c/1`) or by their name (e.g., `c/John Doe`)
* A client can only have one wedding at a time
* Date must be in YYYY-MM-DD format when provided
* Venue cannot be blank when provided

Examples:
* `addw n/Beach Wedding c/1 d/2024-12-31 v/Sentosa Beach` adds a wedding for the first contact in the list
* `addw n/Garden Wedding c/John Doe v/Botanical Gardens` adds a wedding for John Doe (if there's only one contact with this name)
* `addw n/Church Wedding c/Alex` will show a list of all contacts named Alex if there are multiple matches

### Editing a wedding : `editw`

Edits an existing wedding in the address book.

Format: `editw w/INDEX [n/NAME] [d/DATE] [v/VENUE]`

* Edits the wedding at the specified `INDEX`. The index refers to the index number shown in the displayed wedding list.
* At least one of the optional fields must be provided.
* The index must be a positive integer (1, 2, 3, ...)
* Existing values will be updated to the input values
* The client of a wedding cannot be changed after creation
* Date must be in YYYY-MM-DD format when provided
* Venue cannot be blank when provided

Examples:
* `editw w/1 n/Sunset Wedding` changes the name of the first wedding to "Sunset Wedding"
* `editw w/2 d/2025-01-01 v/Grand Hotel` updates both the date and venue of the second wedding
* `editw w/1 v/` will show an error as venue cannot be blank

### Viewing wedding details : `vieww`

Views the details of a wedding from the address book.

Format: `vieww INDEX` or `vieww KEYWORD`

* Can view a wedding using either its index number or the client's name
* When using index: must be a positive integer (1, 2, 3, ...)
* When using name: matches are case-insensitive
* If multiple weddings match the name keyword, a list will be shown and you'll be prompted to use the index

Examples:
* `vieww 1` shows details of the first wedding in the list
* `vieww John` shows John's wedding if there's only one matching client named John
* `vieww Alex` will show a list of all weddings where the client's name contains "Alex" if there are multiple matches



### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: How do I add a wedding for an existing client?<br>
**A**: First use `list` to see all contacts. Then use either `addw n/WEDDING_NAME c/INDEX` using the client's index number, or `addw n/WEDDING_NAME c/CLIENT_NAME` using the client's name.

**Q**: What happens if I try to delete a client who has a wedding?<br>
**A**: The system will prevent you from deleting the client and show an error message. You must first handle the client's wedding before deleting the contact.

**Q**: Can I change a wedding's client after creation?<br>
**A**: No, a wedding's client cannot be changed after creation. You would need to create a new wedding for the different client.

**Q**: Can a client have multiple weddings?<br>
**A**: No, each client can only have one wedding at a time.
--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/ROLE]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Filter** | `filter [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [r/ROLE]`<br> e.g., `filter r/friends`
**View**   | `view KEYWORD`<br> e.g., `view Alex`, `view Alex Tan`
**List**   | `list`
**Addw**   | `addw n/WEDDING_NAME c/CLIENT [d/DATE] [v/VENUE]` <br> e.g., `addw n/Beach Wedding c/1 d/2024-12-31 v/Sentosa Beach`
**Editw**  | `editw w/INDEX [n/NAME] [d/DATE] [v/VENUE]`<br> e.g., `editw w/1 d/2024-12-31 v/Garden Venue`
**Vieww**  | `vieww INDEX` or `vieww KEYWORD`<br> e.g., `vieww 1`, `vieww John`
**Help**   | `help`
