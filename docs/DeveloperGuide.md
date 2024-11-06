---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# Bridal Boss Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `WeddingListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` and `Wedding` objects (which are contained in a `UniquePersonList` and `UniqueWeddingList` object).
* stores the currently 'selected' `Person` and/or `Wedding` objects (e.g., results of a search query) as separate _filtered_ lists which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

## Product Scope

### Target User Profile

A small to medium scale **wedding organizer** responsible for planning and managing weddings. They coordinate with vendors, clients (brides, grooms, and their families), and participants (guests, photographers, caterers, etc.). Their work involves juggling multiple tasks and deadlines to ensure that each wedding runs smoothly. They may need to manage multiple weddings simultaneously and prefer efficient tools that help streamline their workflow. They are comfortable using desktop applications and can type quickly, preferring typing over mouse interactions.

### Value Proposition

**Bridal Boss** enables wedding organizers to manage multiple weddings simultaneously while maintaining detailed vendor and client records. It offers fast and efficient access to information, helping organizers categorize and update contacts related to each wedding easily. By providing streamlined management of vendor contacts, client preferences, and event timelines, Bridal Boss helps wedding organizers accommodate last-minute changes effectively and keep everything up to date.

### User Stories

Priorities:

- High (must have) - `* * *`
- Medium (nice to have) - `* *`
- Low (unlikely to have) - `*`

| Priority | As a...                    | I want to...                                       | So that I can...                                                              |
|----------|----------------------------|----------------------------------------------------|-------------------------------------------------------------------------------|
| `* * *`  | Wedding organizer          | create separate profiles for each wedding          | manage multiple weddings without confusion                                    |
| `* * *`  | Wedding organizer          | view an overview of all ongoing weddings           | manage multiple events at once without losing track                           |
| `* * *`  | Wedding organizer          | add and categorize vendors                         | keep track of service providers for each wedding                              |
| `* * *`  | Wedding organizer          | view stakeholders related to each specific wedding | easily check their schedules                                                  |
| `* * *`  | Wedding organizer          | update client preferences easily                   | accommodate last-minute changes and keep everything up to date                |
| `* * *`  | Wedding organizer          | delete profiles and roles                          | remove outdated or incorrect information                                      |
| `* * *`  | Wedding organizer          | quickly search for specific contacts or vendors    | access critical information without delays                                    |
| `* * *`  | Wedding organizer          | filter contacts based on roles                     | retrieve contacts of stakeholders involved in a wedding or of a specific role |
| `* *`    | Wedding organizer          | archive completed weddings                         | focus on current and upcoming events without clutter                          |
| `* *`    | Wedding organizer          | track expenses for each wedding                    | manage the overall wedding budget efficiently                                 |
| `* *`    | Wedding organizer          | set reminders for important tasks or deadlines     | ensure critical milestones are not missed                                     |
| `* *`    | Wedding organizer          | track RSVPs from clients                           | keep an accurate guest count for each wedding                                 |
| `* *`    | Wedding organizer          | generate reports on completed tasks                | review progress and share updates with clients                                |
| `* *`    | Wedding organizer          | assign tasks to team members                       | delegate responsibilities and track progress efficiently                      |
| `*`      | Wedding organizer          | upload important documents (e.g., contracts)       | access them quickly during planning                                           |
| `*`      | Wedding organizer          | send automated reminders to vendors and clients    | ensure they stay informed of upcoming deadlines                               |
| `*`      | Wedding organizer          | set up recurring tasks for common preparations     | avoid manually creating the same tasks for each event                         |

### Use Cases

(For all use cases below, the **System** is *Bridal Boss* and the **Actor** is the *Wedding Organizer*.)

#### **Use Case: Add a Contact**

**MSS:**

1. Wedding Organizer adds a contact using the add command.
   Use case ends.

**Extensions:**

- **1a.** Name, phone, email, address prefixes not inputed
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Name is blank, contains invalid symbol or beyond 70 characters
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Phone is not 8 digits, and does not begin with '8' or '9'
    - **1c1.** Error message displayed in result display.
        - Use case ends.
- **1d.** Email is blank, or does not contain '@'
    - **1d1.** Error message displayed in result display.
        - Use case ends.
- **1e.** Address is blank
    - **1e1.** Error message displayed in result display.
        - Use case ends.
- **1f.** Role is blank when prefix is inputted, or is not one word
    - **1f1.** Error message is displayed in result display.  
- **1g.** Wedding is blank when prefix is inputted, or is not one word
    - **1g1.** Error message is displayed in result display.
- **1h.** Wedding field is not an index.
    - **1h1.** Error message is displayed in result display.
- **1i.** Wedding index inputted is not in the list.
    - **1i1.** Error message is displayed in result display.

#### **Use Case: View a Contact**
1. Wedding Organizer views a contact using the view command.

**Extensions:**

- **1a.** No prefixes inputted.
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Person name does not exist in the address book
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Person index is not in the displayed list
    - **1c1.** Error message displayed in result display.
        - Use case ends.
- **1d.** Duplicate contacts with name containing inputted name
    - **1d1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1d2.** Person list filtered to contain contacts with names containing inputted name.
    - **1d3.** Error message is displayed in result display.
        - Use case ends.
- **1e.** Person is a client of a wedding
    - **1e1.** Error message is displayed in result display.
        - Use case ends.

#### **Use Case: Filter Contacts**
1. Wedding Organizer filters contacts using the filter command.

**Extensions:**

- **1a.** No prefixes inputted.
    - **1a1.** Error message displayed in result display.
        - Use case ends

#### **Use Case: Delete a Contact**
1. Wedding Organizer deletes a contact using the delete command.

**Extensions:**

- **1a.** Person name does not exist in the address book
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Person index is not in the displayed list
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Duplicate contacts with name containing inputted name
    - **1c1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1c2.** Use case resumes from step 1.
- **1d.** Person is a client of a wedding
    - **1d1.** Error message is displayed in result display.
        - Use case ends.

#### **Use Case: Add a Wedding**

**MSS:**

1. Wedding Organizer adds a wedding using the addw command.
   Use case ends.

**Extensions:**

- **1a.** Contact name does not exist in the address book
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Contact/Wedding index is not in the displayed list
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Duplicate contacts with name containing inputted name
    - **1c1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1c2.** Use case resumes from step 1.
  - **1d.** Contact is already a client of another wedding
    - **1d1.** Error message displayed in result display.
        - Use case ends.

#### **Use Case: Assign a contact to a Wedding**

**MSS:**

1. Wedding Organizer assigns the person (by name or index) to the wedding (by index) using the assign command.
   Use case ends.

**Extensions:**
- **1a.** Contact name does not exist in the address book
    - **1a1.** Error message displayed in result display.
        - Use case ends.
- **1b.** Contact/Wedding index is not in the displayed list
    - **1b1.** Error message displayed in result display.
        - Use case ends.
- **1c.** Duplicate contacts with name containing inputted name
    - **1c1.** Bridal Boss filters the person list to show contacts containing inputted name.
    - **1c2.** Use case resumes from step 1.
- **1d.** Contact is already assigned to the wedding
    - **1d1.** Error message displayed in result display.
        - Use case ends.
- **1e.** Contact is the client of the wedding
    - **1e1.** Error message displayed in result display.
        - Use case ends.
- **1f.** Contact does not have a role
    - **1f1.** Error message displayed in result display.
        - Use case ends.

### Non-Functional Requirements

1. **Performance Requirements:**
    - **Response Time:** The system should respond to user commands within 2 seconds under normal operating conditions (e.g., managing up to 100 weddings and 1,000 contacts). This should ensure that the system performs well for fast typists, complying with the typing-preferred constraint.
    - **Startup Time:** The system should start up and be fully usable within 5 seconds on modern machines (with at least 8GB RAM and SSD storage), ensuring it is portable and doesn't rely on any installer or external dependencies.

2. **Scalability:**
    - The system should be designed for future scalability to support new features like advanced budgeting tools, contact management enhancements, and more, while maintaining object-oriented principles.
    - The application should scale to handle up to 500 weddings and 10,000 contacts without significant performance degradation.

3. **Portability:**
    - The system must work cross-platform on Windows, macOS, and Linux running Java 17 and above, avoiding the use of platform-specific libraries or features. This ensures that it can be used by a wide range of users across different environments.
    - The product must be packaged in a single JAR file to comply with the single-file packaging constraint, avoiding external dependencies that could complicate installation or distribution.

4. **Usability:**
    - The system should offer a CLI-first experience optimized for fast typists, with a user interface that caters to wedding organizers who prefer text-based commands. The GUI should primarily provide feedback but allow command-based input to be the primary interaction method.
    - The system must comply with the CLI-first recommendation, ensuring that typing tasks (e.g., adding vendors, managing profiles) is faster than using a GUI-only interface.

5. **Data Security and Privacy:**
    - All sensitive information should be stored in a human-editable text file and should not require a DBMS, per the human-editable and no-DBMS constraints. Encryption should only be used if the system provides a simple way to decrypt and edit manually, to preserve the human-editability of the data file.
    - The system must comply with data protection regulations such as PDPA, ensuring that data is stored and processed securely. However, storing data locally in a text file should assume that the system is used in a secure, password-protected environment.

6. **Data Backup and Recovery:**
    - Users should be able to back up and restore their data easily. Since data is stored locally in human-editable text files, users should be able to create backups manually by copying the text file, ensuring minimal disruption in the event of failure.

7. **Interoperability:**
    - The system should be able to import/export data in common formats such as CSV or PDF, making it compatible with other wedding management software or services without relying on a remote server.
    - The system should not depend on external APIs, avoiding dependency on unreliable networks (following the recommendation to minimize network reliance).

8. **Reliability and Fault Tolerance:**
    - The system should have robust error handling that gives users clear feedback when errors occur.
    - In case of errors, the system should allow users to retry operations or resolve issues without losing data.

9. **Maintainability:**
    - The codebase should follow **OOP principles** and be modular to facilitate future maintenance and extension. Each component should be documented and follow best practices to ensure maintainability.
    - Incremental updates are recommended to align with the **Incremental Delivery** constraint, ensuring that the product evolves gradually rather than in big, risky jumps.

10. **Testability:**
    - The system should include unit and integration tests for at least **80%** of the codebase to ensure high test coverage. Testability must be a priority, avoiding features that make testing difficult (e.g., account-based logins, reliance on external APIs).
    - Manual testing should also be easy to perform, with clear feedback provided to testers.

11. **Documentation:**
    - Both **user documentation** and **developer documentation** must be provided. User documentation should explain how to interact with the CLI and manage wedding data, while developer documentation should detail system architecture and extensions.

12. **Compliance:**
    - The system must follow the **no-remote-server** constraint, ensuring it can operate entirely offline without dependence on a remote server.
    - Legal compliance should include industry standards for data encryption and storage, even though it stores data locally.

13. **Environmental Requirements:**
    - The system should operate efficiently across different environments, such as machines with varying processing power, ensuring that the product runs smoothly on modern systems (with at least 8GB RAM).
    - It should also work offline by default, only relying on internet access for optional features like calendar syncing, but without any dependency on continuous network access.

14. **Graphical Interface:**
    - The GUI must be designed to work on **common screen resolutions** (1920x1080 and higher) and should remain usable at lower resolutions like 1280x720 and higher, following the **screen resolution** constraints. The interface should be scalable to accommodate different screen sizes without compromising usability.

---

These non-functional requirements ensure that **Bridal Boss** remains a reliable, secure, and scalable application tailored to the needs of wedding organizers. They address critical aspects like performance, security, usability, and maintainability, ensuring the system meets both current and future needs.
### Glossary

- **Wedding Organizer:** A professional responsible for planning and managing wedding events, coordinating with clients and vendors.
- **Vendor:** A service provider involved in the wedding (e.g., florist, caterer, photographer).
- **Client:** The individuals who have hired the wedding organizer, typically the bride and groom.
- **Stakeholders:** All parties involved in the wedding event, including clients, vendors, and participants.
- **Role:** A role assigned to contacts to categorize and filter them (e.g., "Florist", "Photographer").
- **Contact:** An entry in the system containing information about a person or vendor.
- **Event Timeline:** A schedule outlining all tasks and deadlines related to a wedding event.
- **RSVP:** A confirmation from an invited guest about their attendance at the wedding.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>
</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Editing a person

1. Editing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `edit 1 n/NAME`<br>
       Expected: First contact has name field edited to NAME. Details of edited contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `edit Alice n/Alice Teo`<br>
       Expected (No duplicated Alice): Contact with name field containing Alice has name field edited to Alice Teo. Details of edited contact shown in the status message. Timestamp in the status bar is updated.
       Expected (Duplicated Alice): No contact edited. Person list is filtered to show only contacts with name field containing Alice. Status message shows message to input person by indexing.

### Viewing a person
1. Viewing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `view Alice` <br>
       Expected: Contacts with name field containing Alice shown. Status message shows number of contacts shown.

    1. Test case: `view Alice Pauline` <br>
       Expected: Contacts with name field containing Alice and Pauline shown. Status message shows number of contacts shown.

### Deleting a person

#### Deleting using INDEX

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size, or negative integer)<br>
      Expected: Similar to previous.

1. Deleting a person while a filtered list of contacts is shown

   1. Prerequisites and test cases are similar to the scenario above but the size of list will depend on the size of filtered list.

#### Deleting using NAME

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   2. Test case: `delete betsy`, assuming there is only one contact with this name `betsy` <br>
      Expected: The contact of Betsy Crow will be deleted.

   3. Test case: `delete alex`, assuming there is more than one contact with the name `alex` <br>
      For example: `Alex Tan`, `Alex Crow`, `Alex Rodrigo` <br>
      Expected: The contacts of all persons matching `alex` will be filtered and listed. No person is deleted.
      Multiple person found message will be shown, prompting user to specify the contact to delete using index of filtered list.

   4. Test case: `delete alice`, assuming there is no contact with the name `alice` <br>
      Expected: No person is deleted. Error details is shown in the status message.

1. Deleting a person while a filtered list of contacts is shown

   1. Prerequisite: A partial list of contacts is shown.

   2. Test cases used can be the same since `delete NAME` searches from the entire list of contacts, rather than only the partial list.

### Filtering persons

1. Filtering while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    2. Test case: `filter n/John`<br>
       Expected: All persons with exact name match "John" (case-insensitive) are shown.

    3. Test case: `filter r/vendor e/gmail`<br>
       Expected: All persons who have role "vendor" OR have "gmail" in their email are shown.

    4. Test case: `filter n/Alex Tan`<br>
       Expected: Error shown. Error details shown in the status message as name must be a single word.

2. Invalid filter commands to try:

    1. Test case: `filter`<br>
       Expected: Error shown. At least one filter criteria must be provided.

    2. Test case: `filter n/`<br>
       Expected: Error shown. Parameter cannot be empty.

    3. Test case: `filter x/value`<br>
       Expected: Error shown. Unknown prefix.

3. Edge cases to test:

    1. Test case: `filter n/john`<br>
       Expected: Shows matches regardless of case (e.g., "John", "JOHN").

    2. Test case: `filter e/gmail a/street`<br>
       Expected: Shows all contacts with either "gmail" in email OR "street" in address.

    3. Test case: `filter p/91234567`<br>
       Expected: Shows only exact phone number matches.

### Adding a Wedding
Success action: When wedding is successfully added, the details of the added wedding is shown in the status message and reflected in the wedding list.

1. Adding a wedding using INDEX for client (c/)

    1. Prerequisites: List all contacts using the `list` command. Multiple persons in the list.

    1. Test case: `addw n/Church Wedding c/1 d/2024-12-12 v/Church of the Holy Spirit`<br>
       Expected: Wedding added with first person in the persons list set as client, with given date and venue. Details of the added wedding is displayed on the status message.

   1. Test case: `addw n/Church Wedding c/1`<br>
      Expected: Wedding added with first person in the persons list set as client, with no date and venue. Details of the added wedding is displayed on the status message.

   1. Test case: `addw`
      Test case: `addw n/Church Wedding`<br>
      Test case: `addw c/1`<br>
      Expected: No wedding added. Addw Command format is shown in the status messgae.

    1. Test case: `addw n/Wedding 1 c/1`<br>
       Expected: No wedding is added. Restrictions on WEDDING NAME is shown in the status message.

   1. Test case: `addw n/Wedding c/1.5`<br>
      Expected: No wedding is added. CLIENT input options are shown in the status message.

   1. Test case: `addw n/Wedding c/1 d/2024-13-50`<br>
      Expected: No wedding is added. Restrictions on DATE is shown in the status message.

   1. Test case: `addw n/Wedding c/1 v/`<br>
      Expected: No wedding is added. Restrictions on VENUE is shown in the status message.

    1. Test case: `addw n/Church Wedding c/0`<br>
       Test case: `addw n/Church Wedding c/x` (where x is a negative number) <br>
        Expected: No wedding added. Error message on x is not a non-zero unsigned integer.

    1. Test case: `addw n/Church Wedding c/x` (where x is larger than the size of the wedding list)
        Expected: No wedding added. Error message states that the index is invalid, and prompts user to key indexes from within a specified range.

    1. Test case: `addw n/Church Wedding c/Alice`
       Expected (No duplicated Alice): Wedding added with contact having name field containing Alice set to be client. Details of the added wedding is displayed on the status message.
       Expected (Duplicated Alice): No wedding added. Person list is filtered to show contacts with names containing Alice. Status message prompts user to re-input CLIENT using index shown in the newly filtered list.
       Expected (No Alice): No wedding added. Error message states that the person inputted does not exist in the address book.

### Editing a Wedding
1. Success action: When wedding is successfully edited, the details of the updated wedding is shown in the status message and reflected in the wedding list.

    1. Prerequisites: List all contacts using the `list` command. Multiple persons in the list.

    1. Test case: `editw w/1 [n/NAME] [d/DATE] [v/VENUE]`<br>
       Expected: First wedding in list is edited with the given inputs. Details of the edited wedding is displayed on the status message.

    1. Test case: `editw`<br>
       Test case: `editw w/1`<br>
       Test case: `editw w/1 c/1` <br>
       Expected: No wedding added. Editw Command format is shown in the status messgae.

    1. Test case: `editw w/0 [n/NEW WEDDING NAME] [d/NEW DATE] [v/NEW VENUE]`<br>
       Test case: `editw w/x [n/NEW WEDDING NAME] [d/NEW DATE] [v/NEW VENUE]` (where x is a negative number) <br>
       Expected: No wedding added. Error message on x is not a non-zero unsigned integer.

    1. Test case: `addw w/x [n/NEW WEDDING NAME] [d/NEW DATE] [v/NEW VENUE]` (where x is larger than the size of the wedding list)
       Expected: No wedding added. Error message states that the index is invalid, and prompts user to key indexes from within a specified range.

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
