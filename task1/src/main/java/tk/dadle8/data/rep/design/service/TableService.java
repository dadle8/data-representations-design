package tk.dadle8.data.rep.design.service;

import tk.dadle8.data.rep.design.api.BasicTableOperations;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.query.BooleanCondition;
import tk.dadle8.data.rep.design.datamodel.query.BooleanOperator;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.query.Operator;
import tk.dadle8.data.rep.design.datamodel.query.RelationCondition;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.util.validator.RelationTableValidator;

import java.util.ArrayList;
import java.util.List;

public class TableService implements BasicTableOperations {
    private RelationTableValidator validator;
    private Operator operator;

    public TableService() {
        this.validator = new RelationTableValidator();
    }

    public Row insert(RelationTable table, String[] columnNames, Object[] values) {

        Component[] newComponents = new Component[table.getColumns().size()];
        for (int i = 0; i < columnNames.length; i++) {
            Column column = table.getColumns().get(columnNames[i]);
            this.validator.valueTypeInRow(values[i], column.getType());
            newComponents[column.getOrder()] = new Component(values[i]);
        }

        Row newRow = new Row(newComponents);
        table.getRows().add(newRow);

        return newRow;
    }

    public List<Row> select(RelationTable table, Condition[] conditions) {
        return this.selectRows(table, conditions);
    }

    public int update(RelationTable table, Condition[] conditions, String[] columnNames, Object[] values) {
        List<Row> selectedRows = this.selectRows(table, conditions);
        for (Row row : table.getRows()) {
            for (String columnName : columnNames) {
                Column column = table.getColumns().get(columnName);
                row.setComponentValue(column.getOrder(), values);
            }
        }
        return selectedRows.size();
    }

    public int delete(RelationTable table, Condition[] conditions) {
        List<Row> selectedRows = this.selectRows(table, conditions);
        table.getRows().removeAll(selectedRows);
        return selectedRows.size();
    }

    private List<Row> selectRows(RelationTable table, Condition[] conditions) {
        List<Row> selectedRows = new ArrayList<>();

        if (conditions.length == 0) {
            return table.getRows();
        }

        for (Row row : table.getRows()) {
            boolean isRowMatchesConditions = false;
            int i = 0;

            while (i < conditions.length) {
                if (conditions[i] instanceof RelationCondition) {
                    isRowMatchesConditions = this.doRelationCondition(table, row, (RelationCondition) conditions[i]);
                }
                if (conditions[i] instanceof BooleanCondition) {
                    BooleanCondition condition = (BooleanCondition) conditions[i];
                    boolean leftArg = isRowMatchesConditions;
                    boolean rightArg = this.doRelationCondition(table, row, (RelationCondition) conditions[i + 1]);

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

    private boolean doRelationCondition(RelationTable table, Row row, RelationCondition condition) {
        Column column = table.getColumns().get(condition.getColumnName());
        return this.doOperation(condition.getOperator(), row.getComponentValue(column.getOrder()), condition.getValue());
    }

    private boolean doOperation(Operator operator, Object valueInTable, Object valueInQuery) {
        switch (operator) {
            case EQ:
                return valueInTable.equals(valueInQuery);
            case NE:
                return !valueInTable.equals(valueInQuery);
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
