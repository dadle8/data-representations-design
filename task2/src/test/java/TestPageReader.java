import org.junit.Test;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageReader;

import java.io.File;
import java.io.IOException;

public class TestPageReader {

    @Test
    public void test_read() throws IOException {
        PageReader reader = new PageReader(new File("table.td"));
        Page page1 = reader.readPage();
        Page page2 = reader.readPage();
        reader.close();

        System.out.println(page1);
        System.out.println(page2);
    }
}
