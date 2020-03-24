import wordCount.CountUtils;
import wordCount.Main;
import org.junit.Test;

import java.io.IOException;

public class CountTest {
    CountUtils countUtils = new CountUtils();
    Main m = new Main();

    @Test
    public void testMainReCursion() throws IOException {
        Main.main(new String[]{"-s","-c","-w","-l","-a","C:\\WordCountTest\\*.txt"});
    }
}
