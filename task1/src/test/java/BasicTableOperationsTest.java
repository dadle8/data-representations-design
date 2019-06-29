import org.junit.Assert;
import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.query.BooleanCondition;
import tk.dadle8.data.rep.design.datamodel.query.RelationCondition;
import tk.dadle8.data.rep.design.datamodel.query.Operator;
import tk.dadle8.data.rep.design.datamodel.query.BooleanOperator;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

import java.util.List;

public class BasicTableOperationsTest extends DataModelTest {

    @Test
    public void test_insert_rows() {
        Integer[] values = new Integer[]{1, 2, 3};

        for (int i = 0; i < rowCount; i++) {
            relationTable.insert(columnNames, values);
        }
    }

    @Test
    public void test_insert_rows_with_null() {
        Integer[] valuesWithNull = new Integer[]{1, 2, null};

        for (int i = 0; i < rowCount; i++) {
            relationTable.insert(columnNames, valuesWithNull);
        }
    }

    @Test(expected = RuntimeException.class)
    public void test_insert_rows_with_different_types() {
        Object[] valuesWithAnotherTypes = new Object[]{1, String.valueOf(2), 3};
        relationTable.insert(new String[]{"Column1", "Column3", "Column2"}, valuesWithAnotherTypes);
    }

    @Test
    public void test_select_row() {
        Integer[] values = new Integer[]{1, 2, 3};

        for (int i = 0; i < 1000; i++) {
            relationTable.insert(new String[]{"Column1", "Column3", "Column2"}, values);
            relationTable.insert(new String[]{"Column1", "Column3", "Column2"}, values);
            relationTable.insert(new String[]{"Column2", "Column1", "Column3"}, values);
        }

        List<Row> selectedRows = relationTable.select(
                new Condition[]{
                        new RelationCondition(
                                "Column1",
                                2,
                                Operator.EQ),
                        new BooleanCondition((
                                BooleanOperator.OR)),
                        new RelationCondition(
                                "Column2",
                                3,
                                Operator.EQ)
                });
        selectedRows.forEach(row -> {
            Assert.assertTrue(row.getComponentValue(relationTable.getOrderByColumnName("Column1")).equals(2)
                    || row.getComponentValue(relationTable.getOrderByColumnName("Column2")).equals(3));
        });
        System.out.println(selectedRows.size());
    }

    @Test
    public void test_delete_row() {
        Integer[] values = new Integer[]{1, 2, 3};

        relationTable.insert(new String[]{"Column1", "Column3", "Column2"}, values);
        relationTable.insert(new String[]{"Column1", "Column3", "Column2"}, values);
        relationTable.insert(new String[]{"Column2", "Column1", "Column3"}, values);

        int countDeletedRows = relationTable.delete(
                new Condition[]{
                        new RelationCondition(
                                "Column1",
                                2,
                                Operator.EQ)
                });
        Assert.assertEquals(1000, countDeletedRows);
    }
}
