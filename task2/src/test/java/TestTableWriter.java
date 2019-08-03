import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.serialization.binary.table.TableReader;
import tk.dadle8.data.rep.design.serialization.binary.table.TableWriter;

import java.io.IOException;

public class TestTableWriter {

    @Test
    public void test() throws ClassNotFoundException, IOException {
        RelationTable table = new RelationTable("Test",
                new Column[]{
                        new Column("Column1", Integer.class, 0),
                        new Column("Column2", Integer.class, 1),
                        new Column("Column3", Integer.class, 2),
                        new Column("Column4", Integer.class, 3),
                        new Column("Column5", Integer.class, 4),
                        new Column("Column6", Integer.class, 5),
                        new Column("Column7", Integer.class, 6),
                        new Column("Column8", Integer.class, 7),
                        new Column("Column9", Integer.class, 8),
                },
                new Row[]{});

        TableWriter tableWriter = new TableWriter(table);
        tableWriter.writeTable();

        TableReader tableReader = new TableReader("table.td");
        RelationTable tableRead = tableReader.readTable();
        System.out.println(tableRead);
    }
}
