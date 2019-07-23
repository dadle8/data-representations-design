import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.serialization.binary.table.TableReader;
import tk.dadle8.data.rep.design.serialization.binary.table.TableWriter;

public class TestTableWriter {

    @Test
    public void test() {
        RelationTable table = new RelationTable("Test");
        RelationTable tableRead = new RelationTable("");

        TableWriter tableWriter = new TableWriter(table);
        tableWriter.writeTable();

        TableReader tableReader = new TableReader(tableRead);
        tableReader.readTable();
        System.out.println(tableRead);
    }
}
