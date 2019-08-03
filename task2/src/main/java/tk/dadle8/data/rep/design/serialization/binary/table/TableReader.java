package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageReader;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.*;

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
        try {
            page = pageReader.readPage();
            pageOccupiedSpace = pageDataLength - page.getFreeSpace();
        } catch (IOException e) {
            throw new RuntimeException("Can not read page =(", e);
        }
        return new RelationTable(readTableName(), readTableColumns(), new Row[]{});
    }

    protected String readTableName() {
        byte[] data = page.readData();
        readDataLength += data.length + sizeOffFullPointer;
        return new String(data);
    }

    protected Column[] readTableColumns() throws ClassNotFoundException, IOException {
        List<Column> columns = new ArrayList<>();
        while (page != null) {
            // check exist data for read
            if (pageOccupiedSpace - readDataLength == 0) {
                page = pageReader.readPage();
                if (page == null) {
                    break;
                }
                pageOccupiedSpace = pageDataLength - page.getFreeSpace();
                readDataLength = 0;
            }
            while (pageOccupiedSpace != readDataLength) {
                byte[] data = page.readData();
                readDataLength += data.length + sizeOffFullPointer;
                columns.add(readTableColumn(data));
            }
        }
        return columns.toArray(new Column[0]);
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

    protected void readTableRows() {

    }
}
