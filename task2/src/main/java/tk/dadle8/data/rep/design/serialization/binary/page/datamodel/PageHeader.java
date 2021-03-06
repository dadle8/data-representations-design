package tk.dadle8.data.rep.design.serialization.binary.page.datamodel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageHeader {
    private int pageNumber;
    private int pageType;
    private int pageFreeSpace;
    private int pageId;
}
