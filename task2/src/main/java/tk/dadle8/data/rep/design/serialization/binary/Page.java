package tk.dadle8.data.rep.design.serialization.binary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {
    private PageHeader header;
    private PageData data;
}
