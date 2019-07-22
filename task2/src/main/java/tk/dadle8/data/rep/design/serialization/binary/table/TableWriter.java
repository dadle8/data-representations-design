package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.*;

public class TableWriter {

    private Page page;
    private RelationTable table;

    public TableWriter(RelationTable table) {
        this.page = new Page(pageTypeNameColumns);
        this.table = table;
    }

    public void writeTable() {
        writeTableName();
        writeTableColumns();
    }

    protected void writeTableName() {
        byte[] name = table.getName().getBytes();
        createNewPageIfNoFreeSpace(name.length);

        page.writeData(name);
        page.reduceSpace(name.length + sizeOffFullPointer);
    }

    protected void writeTableColumns() {
        table.getColumns().values().forEach(this::writeTableColumn);
    }

    private void writeTableColumn(Column column) {
        byte[] name = column.getName().getBytes();
        byte[] type = column.getType().getCanonicalName().getBytes();
        byte[] order = intToBytes(column.getOrder());
        int allDataLength = name.length + type.length + order.length;
        createNewPageIfNoFreeSpace(allDataLength);

        ByteBuffer columnData = ByteBuffer.wrap(new byte[allDataLength]);
        columnData.put(name);
        columnData.put(type);
        columnData.put(order);

        page.writeData(columnData.array());
        page.reduceSpace(allDataLength + sizeOffFullPointer);
    }

    protected void writeTableRows() {

    }

    private void createNewPageIfNoFreeSpace(int dataLength) {
        if (page.getDataLength() - pageHeaderSize - sizeOffFullPointer < dataLength) {
            throw new RuntimeException("Too much data size =(");
        }
        if (page.getFreeSpace() + sizeOffFullPointer < dataLength) {
            Page newPage = new Page(page.getDataLength());
            newPage.getHeader().setPageNumber(page.getHeader().getPageNumber());
            newPage.getHeader().setPageType(page.getHeader().getPageType());
            newPage.getHeader().setPageId(page.getHeader().getPageId());

//            TODO: need to write page to file, when no free space on old page
//            try {
//                PageWriter pageWriter = new PageWriter(page, new File("table.td"));
//            } catch (IOException exc) {
//                throw new RuntimeException("Can not write page =(");
//            }

            page = newPage;
        }
    }
}
