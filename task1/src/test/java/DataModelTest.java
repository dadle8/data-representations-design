import org.junit.After;
import org.junit.Before;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.service.TableService;

public class DataModelTest {

    protected RelationTable relationTable;
    protected TableService tableService;

    protected String[] columnNames = new String[]{"Column1", "Column2", "Column3"};
    protected int rowCount = 10;

    @Before
    public void init() {
        relationTable = new RelationTable("Test Table",
                new Column[]{
                        new Column("Column1", Integer.class, 0),
                        new Column("Column2", Integer.class, 1),
                        new Column("Column3", Integer.class, 2)
                },
                new Row[]{});
        tableService = new TableService();
    }

    @After
    public void after() {
        System.out.println(relationTable + "\n");
    }
}
