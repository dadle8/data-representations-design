package tk.dadle8.data.rep.design.datamodel.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class Row {

    private Component[] components;

    @Override
    public String toString() {
        return Arrays.toString(components);
    }
}