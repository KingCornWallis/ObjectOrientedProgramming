
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

public class PolynomialEvaluationTest {
    
    @Test
    public void polynomialEvaluationTest() {
        Polynomial p = new Polynomial("3xy+z^2");
        char[] c = {'x', 'y', 'z'};
        double[] d = {1, 1, 3};
        assertEquals(12.0, p.evalute(c, d));
    }

}