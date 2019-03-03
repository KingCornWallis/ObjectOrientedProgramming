
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

public class PolynomialMulTest {
    
    @Test
    public void polynomialMulTest() {
        Polynomial p1 = new Polynomial("x+xy");
        Polynomial p2 = new Polynomial("x-y");
        assertEquals("x^2-1xy+x^2y-1xy^2", p1.mul(p2).toString());
    }

}