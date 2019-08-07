package tk.dadle8.data.rep.design.serialization.binary.page.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils;

@Getter
@Setter
public class Page {
    private PageHeader header;
    private PageData data;

    public Page(PageHeader header) {
        this.header = header;
        this.data = new PageData(PageUtils.pageDataLength);
    }

    public Page(PageHeader header, PageData data) {
        this.header = header;
        this.data = data;
    }

    public int getFreeSpace() {
        return header.getPageFreeSpace();
    }

    public void reduceSpace(int length) {
        header.setPageFreeSpace(header.getPageFreeSpace() - length - PageUtils.sizeOffFullPointer);
    }

    public void writeData(byte[] data) {
        this.data.writeData(data);
    }

    public byte[] readData() {
        return this.data.readData();
    }
}
