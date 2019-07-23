package tk.dadle8.data.rep.design.serialization.binary.page.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils;

@Getter
@Setter
public class Page {
    private PageHeader header;
    private PageData data;
    private int dataLength;

    public Page(int type) {
        header = new PageHeader();
        header.setPageType(type);
        header.setPageFreeSpace(PageUtils.pageDataLength);
        data = new PageData(PageUtils.pageDataLength);
        dataLength = PageUtils.pageLength;
    }

    public Page(int type, int dataLength) {
        this.header = new PageHeader();
        this.header.setPageFreeSpace(dataLength - PageUtils.pageHeaderSize);
        this.header.setPageType(type);
        this.data = new PageData(dataLength - PageUtils.pageHeaderSize);
        this.dataLength = dataLength;
    }

    public Page(PageHeader header, PageData data) {
        this.header = header;
        this.data = data;
        this.dataLength = PageUtils.pageHeaderSize + data.getData().capacity();
    }

    public int getFreeSpace() {
        return header.getPageFreeSpace();
    }

    public void reduceSpace(int length) {
        header.setPageFreeSpace(header.getPageFreeSpace() - length);
    }

    public void writeData(byte[] data) {
        this.data.writeData(data);
    }

    public byte[] readData() {
        return this.data.readData();
    }
}
