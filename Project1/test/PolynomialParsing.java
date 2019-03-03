
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

public class PolynomialParsing {
    
    @Test
    public void polynomialParsing() {
        Polynomial p = new Polynomial("-2x^-2-4xy+8x");
        assertEquals("-2x^-2-4xy+8x", p.toString());
    }

}