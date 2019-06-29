package tk.dadle8.data.rep.design.util.condition;

import tk.dadle8.data.rep.design.datamodel.query.BooleanOperator;
import tk.dadle8.data.rep.design.datamodel.query.Operator;
import tk.dadle8.data.rep.design.datamodel.query.RelationCondition;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

public class ConditionUtils {

    public boolean doRelationCondition(Column column, Row row, RelationCondition condition) {
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

    public boolean doBooleanOperation(BooleanOperator operator, boolean leftArg, boolean rightArg) {
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
