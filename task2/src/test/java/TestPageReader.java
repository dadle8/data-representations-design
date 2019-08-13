import org.junit.Test;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageHeader;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageReader;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageWriter;
import tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils;

import java.io.File;
import java.io.IOException;

public class TestPageReader {

    private String pathName = "table.td";

    @Test
    public void test_write_read_page() throws IOException {
        File file = new File(pathName);

        PageWriter writer = new PageWriter(file);
        writer.writePage(new Page(PageHeader.builder()
                .pageNumber(0)
                .pageType(PageUtils.pageTypeNameColumns)
                .pageId(0)
                .pageFreeSpace(PageUtils.pageDataLength)
                .build())
        );
        writer.writePage(new Page(PageHeader.builder()
                .pageNumber(1)
                .pageType(PageUtils.pageTypeNameColumns)
                .pageId(0)
                .pageFreeSpace(PageUtils.pageDataLength)
                .build())
        );
        writer.close();

        PageReader reader = new PageReader(file);
        Page page1 = reader.readPage();
        Page page2 = reader.readPage();
        reader.close();

        System.out.println(page1.getHeader().getPageNumber());
        System.out.println(page2.getHeader().getPageNumber());
    }
}
