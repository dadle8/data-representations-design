package tk.dadle8.data.rep.design.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.api.BasicTableOperations;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.query.Operator;
import tk.dadle8.data.rep.design.datamodel.query.BooleanOperator;
import tk.dadle8.data.rep.design.datamodel.query.RelationCondition;
import tk.dadle8.data.rep.design.datamodel.query.BooleanCondition;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.util.validator.RelationTableValidator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class RelationTable implements BasicTableOperations {

    private String name;
    private Map<String, Column> columns;
    private List<Row> rows;

    private RelationTableValidator validator;
    private Operator operator;

    public RelationTable() {
        this.name = "";
        this.columns = new LinkedHashMap<>();
        this.rows = new ArrayList<>();

        this.validator = new RelationTableValidator();
    }

    public RelationTable(String name, Column[] columns, Row[] rows) {
        this();

        this.name = name;
        for (int i = 0; i < columns.length; i++) {
            this.columns.put(columns[i].getName(), columns[i]);
        }
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

    public Row insert(String[] columnNames, Object[] values) {

        Component[] newComponents = new Component[this.columns.size()];
        for (int i = 0; i < columnNames.length; i++) {
            Column column = this.columns.get(columnNames[i]);
            this.validator.valueTypeInRow(values[i], column.getType());
            newComponents[column.getOrder()] = new Component(values[i]);
        }

        Row newRow = new Row(newComponents);
        this.rows.add(newRow);

        return newRow;
    }

    public List<Row> select(Condition[] conditions) {
        return this.selectRows(conditions);
    }

    public int update(Condition[] conditions, String[] columnNames, Object[] values) {
        List<Row> selectedRows = this.selectRows(conditions);
        for (Row row : rows) {
            for (String columnName : columnNames) {
                Column column = this.columns.get(columnName);
                row.setComponentValue(column.getOrder(), values);
            }
        }
        return selectedRows.size();
    }

    public int delete(Condition[] conditions) {
        List<Row> selectedRows = this.selectRows(conditions);
        this.rows.removeAll(selectedRows);
        return selectedRows.size();
    }

    private List<Row> selectRows(Condition[] conditions) {
        List<Row> selectedRows = new ArrayList<>();

        if (conditions.length == 0) {
            return this.rows;
        }

        for (Row row : rows) {
            boolean isRowMatchesConditions = false;
            int i = 0;

            while (i < conditions.length) {
                if (conditions[i] instanceof RelationCondition) {
                    isRowMatchesConditions = this.doRelationCondition(row, (RelationCondition) conditions[i]);
                }
                if (conditions[i] instanceof BooleanCondition) {
                    BooleanCondition condition = (BooleanCondition) conditions[i];
                    boolean leftArg = isRowMatchesConditions;
                    boolean rightArg = this.doRelationCondition(row, (RelationCondition) conditions[i + 1]);

                    isRowMatchesConditions = this.doBooleanOperation(condition.getOperator(), leftArg, rightArg);
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

    private boolean doRelationCondition(Row row, RelationCondition condition) {
        Column column = this.columns.get(condition.getColumnName());
        return this.doOperation(condition.getOperator(), row.getComponentValue(column.getOrder()), condition.getValue());
    }

    private boolean doOperation(Operator operator, Object valueInTable, Object valueInQuery) {
        switch (operator) {
            case EQ:
                return valueInTable.equals(valueInQuery);
            case NE:
                return valueInTable.equals(valueInQuery);
            case GT:
                if (valueInTable instanceof Integer && valueInQuery instanceof Integer) {
                    return (Integer) valueInTable > (Integer) valueInQuery;
                }
                if (valueInTable instanceof Double && valueInQuery instanceof Double) {
                    return (Double) valueInTable > (Double) valueInQuery;
                }
                if (valueInTable instanceof Long && valueInQuery instanceof Long) {
                    return (Long) valueInTable > (Long) valueInQuery;
                }
                throw new RuntimeException("Invalid type for '>' operation");
            case LT:
                if (valueInTable instanceof Integer && valueInQuery instanceof Integer) {
                    return (Integer) valueInTable < (Integer) valueInQuery;
                }
                if (valueInTable instanceof Double && valueInQuery instanceof Double) {
                    return (Double) valueInTable < (Double) valueInQuery;
                }
                if (valueInTable instanceof Long && valueInQuery instanceof Long) {
                    return (Long) valueInTable < (Long) valueInQuery;
                }
                throw new RuntimeException("Invalid type for '<' operation");
            case GE:
                if (valueInTable instanceof Integer && valueInQuery instanceof Integer) {
                    return (Integer) valueInTable >= (Integer) valueInQuery;
                }
                if (valueInTable instanceof Double && valueInQuery instanceof Double) {
                    return (Double) valueInTable >= (Double) valueInQuery;
                }
                if (valueInTable instanceof Long && valueInQuery instanceof Long) {
                    return (Long) valueInTable >= (Long) valueInQuery;
                }
                throw new RuntimeException("Invalid type for '>=' operation");
            case LE:
                if (valueInTable instanceof Integer && valueInQuery instanceof Integer) {
                    return (Integer) valueInTable <= (Integer) valueInQuery;
                }
                if (valueInTable instanceof Double && valueInQuery instanceof Double) {
                    return (Double) valueInTable <= (Double) valueInQuery;
                }
                if (valueInTable instanceof Long && valueInQuery instanceof Long) {
                    return (Long) valueInTable <= (Long) valueInQuery;
                }
                throw new RuntimeException("Invalid type for '<=' operation");
            default:
                throw new RuntimeException("Unsupported operation");
        }
    }

    private boolean doBooleanOperation(BooleanOperator operator, boolean leftArg, boolean rightArg) {
        switch (operator) {
            case AND:
                return leftArg && rightArg;
            case OR:
                return leftArg || rightArg;
            default:
                throw new RuntimeException("Unsupported operation");
        }
    }
}
