package nye.valyis.progtech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// ==========================================
// 3. JUNIT 5 TEST CLASS: BookOrderManagerTest
// ==========================================
class BookOrderManagerTest {

    private BookOrderManager manager;
    private Book sampleBook;

    // Fixture setup before each test case
    @BeforeEach
    void setUp() {
        manager = new BookOrderManager();
        sampleBook = new Book("Java Programming", 100.0, 10);
        manager.addBookToInventory(sampleBook);
    }

    // Fixture teardown after each test case
    @AfterEach
    void tearDown() {
        manager = null;
        sampleBook = null;
    }

    @Test
    void testCalculateOrderTotalStandard() {
        // State testing: verifying correct mathematical calculation
        double total = manager.calculateOrderTotal("Java Programming", 2);
        assertEquals(200.0, total, 0.001, "Total should be 200.0 without discount.");
    }

    @Test
    void testCalculateOrderTotalWithDiscount() {
        manager.setDiscountRate(0.1); // 10% discount
        double total = manager.calculateOrderTotal("Java Programming", 2);
        assertEquals(180.0, total, 0.001, "Total should be 180.0 with 10% discount.");
    }

    @Test
    void testCalculateOrderTotalNullTitleThrowsException() {
        // Exception testing using assertThrows
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.calculateOrderTotal(null, 1);
        });
        assertEquals("Book title cannot be null or empty.", exception.getMessage());
    }

    // ==========================================
    // HOMEWORK SECTION: BRANCH COVERAGE EXERCISE
    // ==========================================

    @Test
    void testAddNullBookThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addBookToInventory(null);
        });
        assertEquals("Book cannot be null.", exception.getMessage());
    }

    @Test
    void testSetInvalidDiscountRateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.setDiscountRate(-0.1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            manager.setDiscountRate(1.1);
        });
    }

    @Test
    void testCalculateOrderTotalInsufficientStock() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            manager.calculateOrderTotal("Java Programming", 11);
        });
        assertEquals("Insufficient stock available.", exception.getMessage());
    }

    @Test
    void testProcessCheckoutSuccessAndStockReduction() {
        boolean result = manager.processCheckout("Java Programming", 3, false);
        assertTrue(result);
        assertEquals(7, sampleBook.getStock());
    }
}
