import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegexTest {
    @Test
    public void regexTest() {
        String regex = "[\\w\\d\\s\\v \\-,.!'\"]*";
        String st = "sadasda31231sdasd! 12312 Dsdasd, dasdas da. \" - ' ";
        Assert.assertTrue(st.matches(regex));
    }

    @Test
    public void test() {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        System.out.println(pe.encode("Tima123"));
    }
}
