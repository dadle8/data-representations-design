import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.serialization.binary.table.TableWriter;

public class TestTableWriter {

    @Test
    public void test() {
        RelationTable table = new RelationTable("Test");

        TableWriter tableWriter = new TableWriter(table);
        tableWriter.writeTable();
        tableWriter.writeTable();
        tableWriter.writeTable();
        System.out.println(tableWriter);
    }
}
