package tk.dadle8.data.rep.design.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class RelationTable {

    private String name;
    private Map<String, Column> columns;
    private List<Row> rows;

    public RelationTable() {
        this.name = "";
        this.columns = new LinkedHashMap<>();
        this.rows = new ArrayList<>();
    }

    public RelationTable(String name, Column[] columns, Row[] rows) {
        this();

        this.name = name;
        Stream.of(columns).forEach(column -> this.columns.put(column.getName(), column));
        this.rows.addAll(Arrays.asList(rows));
    }

    @Override
    public String toString() {
        return name
                + '\n'
                + '(' + columns.keySet().toString() + ')'
                + '\n'
                + Arrays.stream(rows.toArray()).map(Objects::toString).collect(Collectors.joining("\n"));
    }
}
