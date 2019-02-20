import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Attribute;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;


public class DataModelTest {

    private RelationTable relationTable;

    private Attribute[] attributes = new Attribute[]{
            new Attribute("Column1"),
            new Attribute("Column2"),
            new Attribute("Column3")
    };

    @Before
    public void init() {
        relationTable = new RelationTable("Test Table", attributes, new Row[]{});
    }

    @After
    public void after() {
        System.out.println(relationTable + "\n");
    }

    @Test
    public void test_insert_rows() {
        Component[] components = new Component[]{
                new Component(1),
                new Component(2),
                new Component(3)
        };

        relationTable.insert(attributes, components);
        relationTable.insert(attributes, components);
        relationTable.insert(attributes, components);

        Component[] componentsWithNull = new Component[]{
                new Component(1),
                new Component(2),
                null
        };
        relationTable.insert(attributes, componentsWithNull);
    }

    @Test
    public void test_insert_rows_with_null() {
        Component[] componentsWithNull = new Component[]{
                new Component(1),
                new Component(2),
                null
        };
        relationTable.insert(attributes, componentsWithNull);
    }
}
