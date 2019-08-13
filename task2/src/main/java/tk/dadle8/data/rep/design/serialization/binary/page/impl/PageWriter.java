package tk.dadle8.data.rep.design.serialization.binary.page.impl;

import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageData;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Closeable;

public class PageWriter implements Closeable {
    private ObjectOutputStream oos;

    public PageWriter(File file) throws IOException {
        this.oos = new ObjectOutputStream(new FileOutputStream(file));
    }

    public void writePage(Page page) throws IOException {
        writePageHeader(page.getHeader());
        writePageDate(page.getData());
        oos.flush();
    }

    public void close() throws IOException {
        oos.close();
    }

    public void writePageDate(PageData data) throws IOException {
        oos.write(data.getData().array());
    }

    public void writePageHeader(PageHeader header) throws IOException {
        oos.writeInt(header.getPageNumber());
        oos.writeInt(header.getPageType());
        oos.writeInt(header.getPageFreeSpace());
        oos.writeInt(header.getPageId());
    }
}
