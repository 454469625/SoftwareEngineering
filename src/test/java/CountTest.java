import wordCount.CountUtils;
import wordCount.Main;
import org.junit.Test;

import java.io.IOException;

public class CountTest {

    @Test
    public void test() throws IOException {
        Main.main(new String[]{"-a","C:\\WordCountTest\\2.txt"});
    }
}
