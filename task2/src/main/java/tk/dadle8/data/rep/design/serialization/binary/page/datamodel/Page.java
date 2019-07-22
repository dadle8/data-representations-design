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
        header.setPageFreeSpace(PageUtils.defaultDataLength - PageUtils.pageHeaderSize);
        data = new PageData(PageUtils.defaultDataLength - PageUtils.pageHeaderSize);
        dataLength = PageUtils.defaultDataLength;
    }

    public Page(int type, int dataLength) {
        this.header = new PageHeader();
        this.header.setPageFreeSpace(dataLength - PageUtils.pageHeaderSize);
        this.header.setPageType(type);
        this.data = new PageData(dataLength - PageUtils.pageHeaderSize);
        this.dataLength = dataLength;
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
}
