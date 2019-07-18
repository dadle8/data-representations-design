package tk.dadle8.data.rep.design.serialization.binary.page.impl;

import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PageWriter {

    private Page page;
    private ObjectOutputStream oos;

    public PageWriter(Page page, File file) throws IOException {
        this.page = page;
        this.oos = new ObjectOutputStream(new FileOutputStream(file));
    }

    public void writePage() throws IOException {
        writePageHeader();
        writePageDate();
    }

    public void writePageDate() throws IOException {
        oos.write(page.getData().getData().array());
    }

    public void writePageHeader() throws IOException {
        PageHeader header = page.getHeader();
        oos.writeInt(header.getPageNumber());
        oos.writeInt(header.getPageType());
        oos.writeInt(header.getPageFreeSpace());
        oos.writeInt(header.getPageId());
    }
}
