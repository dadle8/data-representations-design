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

    public Page() {
        header = new PageHeader();
        header.setPageFreeSpace(PageUtils.defaultDataLength - PageUtils.pageHeaderSize);
        data = new PageData(PageUtils.defaultDataLength - PageUtils.pageHeaderSize);
        dataLength = PageUtils.defaultDataLength;
    }

    public Page(int dataLength) {
        this.header = new PageHeader();
        this.header.setPageFreeSpace(dataLength - PageUtils.pageHeaderSize);
        this.data = new PageData(dataLength - PageUtils.pageHeaderSize);
        this.dataLength = dataLength;
    }

    public void reduceSpace(int length) {
        header.setPageFreeSpace(header.getPageFreeSpace() - length);
    }
}
