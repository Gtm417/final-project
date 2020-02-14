import org.junit.Assert;
import org.junit.Test;

public class RegexTest {
    @Test
    public void regexTest() {
        String regex = "[\\w\\d\\s\\v \\-,.!'\"]*";
        String st = "sadasda31231sdasd! 12312 Dsdasd, dasdas da. \" - ' ";
        Assert.assertTrue(st.matches(regex));
    }
}
