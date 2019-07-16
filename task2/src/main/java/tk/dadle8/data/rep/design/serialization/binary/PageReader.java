package tk.dadle8.data.rep.design.serialization.binary;

import java.io.*;
import java.nio.ByteBuffer;

public class PageReader {

    private int off = 0;
    private int headerOff = 16;
    private int len = 8192;
    private byte[] rawData = new byte[len];
    private ObjectInputStream ois;

    public PageReader(File file) throws IOException {
        this.ois = new ObjectInputStream(new FileInputStream(file));
    }

    public Page readPage() throws IOException {
        ois.read(rawData, off, len);
        off += len;

        Page page = new Page();
        page.setHeader(readPageHeader());
        page.setData(readPageDate());

        return page;
    }

    public PageData readPageDate() {
        ByteBuffer buffer = ByteBuffer.wrap(rawData, headerOff, len - headerOff);

        PageData data = new PageData();
        data.setData(buffer.array());

        return data;
    }

    public PageHeader readPageHeader() throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(rawData, 0, headerOff);


        PageHeader header = new PageHeader();
        header.setPageNumber(buffer.getInt());
        header.setPageType(buffer.getInt());
        header.setPageFreeSpace(buffer.getInt());
        header.setPageId(buffer.getInt());

        return header;
    }

}
