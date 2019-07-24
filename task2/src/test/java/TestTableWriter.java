import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.serialization.binary.table.TableReader;
import tk.dadle8.data.rep.design.serialization.binary.table.TableWriter;

public class TestTableWriter {

    @Test
    public void test() {
        RelationTable table = new RelationTable("Test",
                new Column[]{
                        new Column("Column1", Integer.class, 0),
                        new Column("Column2", Integer.class, 1),
                        new Column("Column3", Integer.class, 2)
                },
                new Row[]{});
        RelationTable tableRead = new RelationTable("");

        TableWriter tableWriter = new TableWriter(table);
        tableWriter.writeTable();

        TableReader tableReader = new TableReader(tableRead);
        tableReader.readTable();
        System.out.println(tableRead);
    }
}
