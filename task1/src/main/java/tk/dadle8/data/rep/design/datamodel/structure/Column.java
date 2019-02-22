package tk.dadle8.data.rep.design.datamodel.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Column {

    private String name;
    private Class<?> type;
    private int order;
//    private Attribute attribute;
//    private Restriction[] restrictions


    @Override
    public String toString() {
        return getName();
    }
}
