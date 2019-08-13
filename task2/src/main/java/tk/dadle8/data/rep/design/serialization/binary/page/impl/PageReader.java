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

    private byte[] rawData = new byte[PageUtils.pageLength];
    private ObjectInputStream ois;

    public PageReader(File file) throws IOException {
        this.ois = new ObjectInputStream(new FileInputStream(file));
    }

    public Page readPage() throws IOException {
        if (ois.read(rawData) == -1) {
            return null;
        }

        return new Page(readPageHeader(), readPageDate());
    }

    public void close() throws IOException {
        ois.close();
    }

    public PageData readPageDate() {
        return new PageData(Arrays.copyOfRange(rawData, PageUtils.pageHeaderSize, PageUtils.pageLength));
    }

    public PageHeader readPageHeader() {
        ByteBuffer buffer = ByteBuffer.wrap(rawData, 0, PageUtils.pageHeaderSize);

        return PageHeader.builder()
                .pageNumber(buffer.getInt())
                .pageType(buffer.getInt())
                .pageFreeSpace(buffer.getInt())
                .pageId(buffer.getInt())
                .build();
    }

}
