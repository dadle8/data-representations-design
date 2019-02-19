package tk.dadle8.data.rep.design.datamodel.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Column {

    private Attribute attribute;

    @Override
    public String toString() {
        return attribute.getName();
    }
}
