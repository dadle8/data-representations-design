package tk.dadle8.data.rep.design.serialization.binary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageHeader {
    private int pageNumber;
    private int pageType;
    private int pageFreeSpace;
    private int pageId;
}
