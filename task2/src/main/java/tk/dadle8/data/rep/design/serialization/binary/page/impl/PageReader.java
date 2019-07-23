package tk.dadle8.data.rep.design.serialization.binary.page.impl;

import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageData;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageHeader;
import tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PageReader {

    private int off = 0;
    private byte[] rawData = new byte[PageUtils.pageLength];
    private ObjectInputStream ois;

    public PageReader(File file) throws IOException {
        this.ois = new ObjectInputStream(new FileInputStream(file));
    }

    public Page readPage() throws IOException {
        ois.read(rawData, off, PageUtils.pageLength);
        off += PageUtils.pageLength;

        return new Page(readPageHeader(), readPageDate());
    }

    public PageData readPageDate() {
        return new PageData(Arrays.copyOfRange(rawData, PageUtils.pageHeaderSize, PageUtils.pageLength));
    }

    public PageHeader readPageHeader() {
        ByteBuffer buffer = ByteBuffer.wrap(rawData, 0, PageUtils.pageHeaderSize);

        PageHeader header = new PageHeader();
        header.setPageNumber(buffer.getInt());
        header.setPageType(buffer.getInt());
        header.setPageFreeSpace(buffer.getInt());
        header.setPageId(buffer.getInt());

        return header;
    }

}
