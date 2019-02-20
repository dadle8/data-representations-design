package tk.dadle8.data.rep.design.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.api.BasicTableOperations;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.structure.Attribute;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class RelationTable implements BasicTableOperations {

    private String name;
    private Map<Attribute, Integer> columnOrderMap;
    private List<Row> rows;

    private int columnCount;
    private int rowCount;

    public RelationTable() {
        this.name = "";
        this.columnOrderMap = new HashMap<>();
        this.rows = new ArrayList<>();
    }

    public RelationTable(String name, Attribute[] attributes, Row[] rows) {
        this();

        this.name = name;
        for(int i = 0; i < attributes.length; i++) {
            this.columnOrderMap.put(attributes[i], i);
        }
        this.rows.addAll(Arrays.asList(rows));

        this.setCounts(attributes.length, rows.length);
    }

    @Override
    public String toString() {
        return name
                + '\n'
                + '(' + columnOrderMap.keySet().toString() + ')'
                + '\n'
                + Arrays.stream(rows.toArray()).map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public Row insert(Attribute[] attributes, Component[] components) {
        // проверка что нет одинаковых атрибутов

        Component[] newComponents = new Component[this.columnCount];
        for(int i = 0; i < attributes.length; i++) {
            newComponents[this.columnOrderMap.get(attributes[i])] = components[i];
        }

        Row newRow = new Row(newComponents);
        this.rows.add(newRow);

        return newRow;
    }

    public RelationTable select(Condition[] relationConditions, Column[] columns, Row[] rows) {
        return null;
    }

    public int update(Condition[] relationConditions, Column[] columns, Row[] rows) {
        return 0;
    }

    public int delete(Condition[] relationConditions) {
        return 0;
    }

    private void setCounts(int columnCount, int rowCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }
}
