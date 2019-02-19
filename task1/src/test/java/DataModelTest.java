import org.junit.Before;
import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Attribute;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

public class DataModelTest {

    private RelationTable relationTable;

    @Before
    public void init() {
        relationTable = new RelationTable();
        relationTable.setName("Test Table");
        relationTable.setColumns(new Column[] {
                new Column(new Attribute("column1")),
                new Column(new Attribute("column2")),
                new Column(new Attribute("column3"))
        });
        relationTable.setRows(new Row[] {
                new Row(new Component[]{
                        new Component(1),
                        new Component(2),
                        new Component(3)
                }),
                new Row(new Component[]{
                        new Component(4),
                        new Component(5),
                        new Component(6)
                }),
                new Row(new Component[]{
                        new Component(7),
                        new Component(8),
                        new Component(9)
                })
        });
    }

    @Test
    public void test_creation_relation_table() {
        System.out.println(relationTable.toString());
    }
}
