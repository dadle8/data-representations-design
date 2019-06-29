package tk.dadle8.data.rep.design.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.api.BasicTableOperations;
import tk.dadle8.data.rep.design.datamodel.query.*;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.util.condition.ConditionUtils;
import tk.dadle8.data.rep.design.util.validator.ValidatorUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class RelationTable implements BasicTableOperations {

    private String name;
    private Map<String, Column> columns = new LinkedHashMap<>();
    private List<Row> rows = new ArrayList<>();
    private int columnsLength;
    private ValidatorUtils validatorUtils = new ValidatorUtils();
    private ConditionUtils conditionUtils = new ConditionUtils();

    public RelationTable(String name) {
        this.name = name;
    }

    public RelationTable(String name, Column[] columns, Row[] rows) {
        this(name);

        Stream.of(columns).forEach(column -> this.columns.put(column.getName(), column));
        this.rows.addAll(Arrays.asList(rows));
        this.columnsLength = columns.length;
    }

    private Column getColumnByName(String name) {
        return columns.get(name);
    }

    public int getOrderByColumnName(String name) {
        return columns.get(name).getOrder();
    }

    public void insert(String[] columnNames, Object[] values) {
        Component[] newComponents = new Component[columnsLength];
        for (int i = 0; i < columnNames.length; i++) {
            Column column = getColumnByName(columnNames[i]);
            validatorUtils.valueTypeInRow(values[i], column.getType());
            newComponents[column.getOrder()] = new Component(values[i]);
        }

        rows.add(new Row(newComponents));
    }

    public List<Row> select(Condition[] conditions) {
        return selectRows(conditions);
    }

    public int update(Condition[] conditions, String[] columnNames, Object[] values) {
        List<Row> selectedRows = selectRows(conditions);
        for (Row row : selectedRows) {
            for (var i = 0; i < columnNames.length; i++) {
                row.setComponentValue(getOrderByColumnName(columnNames[i]), values[i]);
            }
        }
        return selectedRows.size();
    }

    public int delete(Condition[] conditions) {
        List<Row> selectedRows = selectRows(conditions);
        rows.removeAll(selectedRows);
        return selectedRows.size();
    }

    private List<Row> selectRows(Condition[] conditions) {
        List<Row> selectedRows = new ArrayList<>();

        if (conditions.length == 0) {
            return rows;
        }

        for (Row row : rows) {
            boolean isRowMatchesConditions = false;
            int i = 0;

            while (i < conditions.length) {
                if (conditions[i] instanceof RelationCondition) {
                    RelationCondition condition = (RelationCondition) conditions[i];
                    isRowMatchesConditions = conditionUtils.doRelationCondition(getColumnByName(condition.getColumnName()), row, condition);
                }
                if (conditions[i] instanceof BooleanCondition) {
                    BooleanCondition condition = (BooleanCondition) conditions[i];
                    RelationCondition relationCondition = (RelationCondition) conditions[i + 1];
                    boolean leftArg = isRowMatchesConditions;
                    boolean rightArg = conditionUtils.doRelationCondition(getColumnByName(relationCondition.getColumnName()), row, relationCondition);

                    isRowMatchesConditions = conditionUtils.doBooleanOperation(condition.getOperator(), leftArg, rightArg);
                    i++;
                }
                i++;
            }

            if (isRowMatchesConditions) {
                selectedRows.add(row);
            }
        }
        return selectedRows;
    }

    @Override
    public String toString() {
        return name
                + '\n'
                + '(' + columns.keySet().toString() + ')'
                + '\n'
                + rows.stream().map(Row::toString).collect(Collectors.joining("\n"));
    }
}
