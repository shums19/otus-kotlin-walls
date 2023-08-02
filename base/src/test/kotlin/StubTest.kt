import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class StubTest {

    @Test
    fun `my first test`() {
        assertEquals(42, 6*7)
    }

    @Test
    fun `my second test`() {
        assertNotEquals(42, 7*7)
    }
}
