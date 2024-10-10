package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(
                Arrays.asList(firstPredicateKeyword));
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(
                Arrays.asList(secondPredicateKeyword));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(
                Arrays.asList(firstPredicateKeyword));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "family").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("family", "friends").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("fRiEnDs"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Keywords match name
        // TODO keywords to match whole contact (phone, email and address), but not tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice", "12345"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("bruh").build()));
    }
}
