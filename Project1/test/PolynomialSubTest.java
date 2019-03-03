
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

public class PolynomialSubTest {
    
    @Test
    public void polynomialSubTest() {
        Polynomial p1 = new Polynomial("x+xy");
        Polynomial p2 = new Polynomial("x-y");
        assertEquals("xy-1y", p1.sub(p2).toString());
    }

}