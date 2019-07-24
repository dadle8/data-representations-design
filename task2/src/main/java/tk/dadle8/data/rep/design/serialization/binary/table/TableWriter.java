package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageWriter;

import java.io.File;
import java.io.IOException;

import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.*;

public class TableWriter {

    private Page page;
    private RelationTable table;
    private PageWriter pageWriter;

    public TableWriter(RelationTable table) {
        this.page = new Page(pageTypeNameColumns);
        this.table = table;
        try {
            this.pageWriter = new PageWriter(new File("table.td"));
        } catch (IOException e) {
            throw new RuntimeException("Can not create page writer =(", e);
        }
    }

    public void writeTable() {
        writeTableName();
        writeTableColumns();
        try {
            pageWriter.writePage(page);
            pageWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Can not write page =(", e);
        }
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
        byte[] columnBytes = column.getBytes();
        createNewPageIfNoFreeSpace(columnBytes.length);

        page.writeData(columnBytes);
        page.reduceSpace(columnBytes.length + sizeOffFullPointer);
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

            try {
                pageWriter.writePage(page);
            } catch (IOException e) {
                throw new RuntimeException("Can not write page =(", e);
            }

            page = newPage;
        }
    }
}
