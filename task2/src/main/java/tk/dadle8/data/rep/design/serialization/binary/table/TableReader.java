package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageReader;
import tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.pageDataLength;

public class TableReader {

    private Page page;
    private PageReader pageReader;
    private int pageOccupiedSpace = 0;
    private int readDataLength = 0;

    public TableReader(String pathName) {
        try {
            this.pageReader = new PageReader(new File(pathName));
        } catch (IOException e) {
            throw new RuntimeException("Can not create page reader =(", e);
        }
    }

    public RelationTable readTable() throws ClassNotFoundException, IOException {
        prepareStartPage();

        String name = readTableName();
        Column[] columns = readTableColumns();
        Row[] rows = readTableRows(columns);
        return new RelationTable(name, columns, rows);
    }

    private void prepareStartPage() {
        if (page == null) {
            try {
                page = readPageFromReader();
            } catch (IOException e) {
                throw new RuntimeException("Can not read page =(", e);
            }
        }
    }

    protected String readTableName() {
        if (page == null) {
            throw new RuntimeException("Can not read table name, because page is NULL");
        }
        return new String(readRawData());
    }

    protected Column[] readTableColumns() throws ClassNotFoundException, IOException {
        List<Column> columns = new ArrayList<>();
        while (page != null && page.getHeader().getPageType() != PageUtils.pageTypeRows) {
            while (pageOccupiedSpace != readDataLength) {
                columns.add(readTableColumn(readRawData()));
            }

            page = readPageFromReader();
        }
        return columns.toArray(new Column[0]);
    }

    public void close() {
        try {
            pageReader.close();
        } catch (IOException e) {
            throw new RuntimeException("Can not close page =(", e);
        }
    }

    private Page readPageFromReader() throws IOException {
        Page newPage = pageReader.readPage();
        if (newPage != null) {
            pageOccupiedSpace = pageDataLength - newPage.getFreeSpace();
            readDataLength = 0;
        }
        return newPage;
    }

    private byte[] readRawData() {
        byte[] data = page.readData();
        readDataLength += data.length + PageUtils.sizeOffFullPointer;
        return data;
    }

    private Column readTableColumn(byte[] columnData) throws ClassNotFoundException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(columnData);

        int nameLength = byteBuffer.getInt();
        byte[] nameBytes = new byte[nameLength];
        byteBuffer.get(nameBytes);

        int typeLength = byteBuffer.getInt();
        byte[] typeBytes = new byte[typeLength];
        byteBuffer.get(typeBytes);

        int order = byteBuffer.getInt();

        return new Column(new String(nameBytes), Class.forName(new String(typeBytes)), order);
    }

    protected Row[] readTableRows(Column[] columns) throws IOException {
        List<Row> rows = new ArrayList<>();
        while (page != null && page.getHeader().getPageType() != PageUtils.pageTypeNameColumns) {
            while (pageOccupiedSpace != readDataLength) {
                rows.add(readTableRow(readRawData(), columns));
            }

            page = readPageFromReader();
        }
        return rows.toArray(new Row[0]);
    }

    private Row readTableRow(byte[] rowData, Column[] columns) {
        Row row;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(rowData);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            Component[] components = new Component[columns.length];
            for (var i = 0; i < columns.length; i++) {
                if (columns[i].getType().equals(Integer.class)) {
                    components[i] = new Component(objectInputStream.readInt());
                } else if (columns[i].getType().equals(Long.class)) {
                    components[i] = new Component(objectInputStream.readLong());
                } else if (columns[i].getType().equals(Short.class)) {
                    components[i] = new Component(objectInputStream.readShort());
                } else if (columns[i].getType().equals(Float.class)) {
                    components[i] = new Component(objectInputStream.readFloat());
                } else if (columns[i].getType().equals(Double.class)) {
                    components[i] = new Component(objectInputStream.readDouble());
                } else if (columns[i].getType().equals(String.class)) {
                    components[i] = new Component(objectInputStream.readUTF());
                }
            }
            row = new Row(components);
        } catch (IOException e) {
            throw new RuntimeException("Can not get byte array from Row =(", e);
        }
        return row;
    }
}
