package tk.dadle8.data.rep.design.datamodel.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Component {

    private Object value;

    @Override
    public String toString() {
        return value == null ? "null" : value.toString();
    }
}
