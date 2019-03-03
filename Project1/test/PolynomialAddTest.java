
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

public class PolynomialAddTest {
    
    @Test
    public void polynomialAddTest() {
        Polynomial p1 = new Polynomial("x+xy");
        Polynomial p2 = new Polynomial("x-y");
        assertEquals("2x+xy-1y", p1.add(p2).toString());
    }
}