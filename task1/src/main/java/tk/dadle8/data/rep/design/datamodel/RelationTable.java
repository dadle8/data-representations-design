package tk.dadle8.data.rep.design.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class RelationTable {

    private String name;
    private Column[] columns;
    private Row[] rows;

    @Override
    public String toString() {
        return name
                + '(' + Arrays.toString(columns) + ')' + '\n'
                + Arrays.stream(rows).map(Objects::toString).collect(Collectors.joining("\n"));
    }
}
