package tk.dadle8.data.rep.design;

import tk.dadle8.data.rep.design.serialization.binary.*;

import java.io.File;
import java.io.IOException;

public class Task2 {

    public static void main(String[] args) throws IOException {
        System.out.println("Task2");

        PageHeader header = new PageHeader();
        header.setPageNumber(0);
        header.setPageType(0);
        header.setPageId(111);

        PageData data = new PageData();
        data.setData(new byte[8176]);

        header.setPageFreeSpace(8176);

        Page page = new Page();
        page.setHeader(header);
        page.setData(data);

        PageWriter pageWriter = new PageWriter(page, new File("table.td"));
        pageWriter.writePage();

        PageReader pageReader = new PageReader(new File("table.td"));
        Page newPage = pageReader.readPage();

//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("table.td"))) {
//            RelationTable table = new RelationTable("Test Table",
//                    new Column[]{
//                            new Column("Column1", Integer.class, 0),
//                            new Column("Column2", Integer.class, 1),
//                            new Column("Column3", Integer.class, 2)
//                    },
//                    new Row[]{
//                            new Row(
//                                    new Component[]{
//                                            new Component(1),
//                                            new Component(2),
//                                            new Component(3)
//                                    }
//                            )
//                    }
//            );
//            oos.writeUTF(table.getName());
//            oos.writeInt(table.getColumnsLength());
//            for(Column column: table.getColumns().values()) {
//                oos.writeUTF(column.getName());
//                oos.writeInt(column.getOrder());
//                oos.writeUTF(column.getType().getCanonicalName());
//            }
//        } catch (Exception ex) {
//
//            System.out.println(ex.getMessage());
//        }
//
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("table.td"))) {
//            String name = ois.readUTF();
//
//            int columnsLength = ois.readInt();
//            Map<String, Column> columnMap = new LinkedHashMap<>();
//            for(var i = 0; i < columnsLength; i++) {
//                String columnName = ois.readUTF();
//                int order = ois.readInt();
//                String className = ois.readUTF();
//
//                columnMap.put(columnName, new Column(columnName, Class.forName(className), order));
//            }
//            System.out.println(name + "\n" + columnMap.values().stream().map(Column::toString).collect(Collectors.joining(" ")));
//        } catch (Exception ex) {
//
//            System.out.println(ex.getMessage());
//        }
    }
}
