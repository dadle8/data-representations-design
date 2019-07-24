package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageReader;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TableReader {

    private Page page;
    private RelationTable table;
    private PageReader pageReader;
    private int off = 0;
    private int pageFreeSpace;

    public TableReader(RelationTable table) {
        this.table = table;
        try {
            this.pageReader = new PageReader(new File("table.td"));
        } catch (IOException e) {
            throw new RuntimeException("Can not create page reader =(", e);
        }
    }

    public void readTable() {
        try {
            page = pageReader.readPage();
            pageFreeSpace = page.getFreeSpace();
        } catch (IOException e) {
            throw new RuntimeException("Can not read page =(", e);
        }
        readTableName();
        readTableColumns();
    }

    protected void readTableName() {
        table.setName(new String(page.readData()));
    }

    protected void readTableColumns() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(page.readData());
        int offset = 0;

        int nameLength = byteBuffer.getInt();
        byte[] nameBytes = new byte[nameLength];
        byteBuffer.get(nameBytes, offset, nameLength);

        int typeLength = byteBuffer.get(Integer.BYTES + nameLength);
        offset = 2 * Integer.BYTES + nameLength;
        byte[] typeBytes = new byte[typeLength];
        byteBuffer.get(typeBytes, offset, typeLength);

        int order = byteBuffer.get(2 * Integer.BYTES + nameLength + typeLength);

        try {
            System.out.println(new Column(new String(nameBytes), Class.forName(new String(typeBytes)), order));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not read column =(", e);
        }
    }

    private void readTableColumn(Column column) {

    }

    protected void readTableRows() {

    }
}
