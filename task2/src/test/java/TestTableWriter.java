import org.junit.Test;
import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
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
                        new Column("Column10", Integer.class, 9),
                },
                new Row[]{});

        TableWriter tableWriter = new TableWriter();
        tableWriter.writeTable(table);
        tableWriter.close();

        TableReader tableReader = new TableReader("table.td");
        RelationTable tableRead = tableReader.readTable();
        tableReader.close();

        System.out.println(tableRead);
    }

    @Test
    public void test_table_with_rows() throws ClassNotFoundException, IOException {
        RelationTable table = new RelationTable("Test",
                new Column[]{
                        new Column("Column1", Integer.class, 0),
                        new Column("Column2", Integer.class, 1),
                        new Column("Column3", Integer.class, 2),
                        new Column("Column4", String.class, 3),
                },
                new Row[]{
                        new Row(new Component[]{
                                new Component(0),
                                new Component(10),
                                new Component(2),
                                new Component("Test1")
                        }),
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test2")
                        }),
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test3")
                        }),
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test4")
                        }),
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test5")
                        })
                });

        TableWriter tableWriter = new TableWriter();
        tableWriter.writeTable(table);
        tableWriter.close();

        TableReader tableReader = new TableReader("table.td");
        RelationTable readiedTable = tableReader.readTable();
        tableReader.close();

        System.out.println(readiedTable);
    }

    @Test
    public void test_read_write_two_tables() throws ClassNotFoundException, IOException {
        RelationTable table1 = new RelationTable("Test1",
                new Column[]{
                        new Column("Column1", Integer.class, 0),
                        new Column("Column2", Integer.class, 1),
                        new Column("Column3", Integer.class, 2),
                        new Column("Column4", String.class, 3),
                },
                new Row[]{
                        new Row(new Component[]{
                                new Component(0),
                                new Component(10),
                                new Component(2),
                                new Component("Test1")
                        }),
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test2")
                        })
                });

        RelationTable table2 = new RelationTable("Test2",
                new Column[]{
                        new Column("Column1", Integer.class, 0),
                        new Column("Column2", Integer.class, 1),
                        new Column("Column3", Integer.class, 2),
                        new Column("Column4", String.class, 3),
                },
                new Row[]{
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test3")
                        }),
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test4")
                        }),
                        new Row(new Component[]{
                                new Component(0),
                                new Component(1),
                                new Component(2),
                                new Component("Test5")
                        })
                });

        TableWriter tableWriter = new TableWriter();
        tableWriter.writeTable(table1);
        tableWriter.writeTable(table2);
        tableWriter.close();


        TableReader tableReader = new TableReader("table.td");
        RelationTable readiedTable1 = tableReader.readTable();
        RelationTable readiedTable2 = tableReader.readTable();
        tableReader.close();

        System.out.println(readiedTable1);
        System.out.println(readiedTable2);
    }
}
