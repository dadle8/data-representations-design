package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageHeader;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageWriter;

import java.io.File;
import java.io.IOException;

import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.*;

public class TableWriter {

    private Page page;
    private RelationTable table;
    private PageWriter pageWriter;

    public TableWriter(RelationTable table) {
        this.page = new Page(PageHeader.builder()
                .pageNumber(0)
                .pageType(pageTypeNameColumns)
                .pageId(table.getId())
                .pageFreeSpace(pageDataLength)
                .build()
        );
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
        page.reduceSpace(name.length);
    }

    protected void writeTableColumns() {
        table.getColumns().values().forEach(this::writeTableColumn);
    }

    private void writeTableColumn(Column column) {
        byte[] columnBytes = column.getBytes();
        createNewPageIfNoFreeSpace(columnBytes.length);

        page.writeData(columnBytes);
        page.reduceSpace(columnBytes.length);
    }

    protected void writeTableRows() {

    }

    private void createNewPageIfNoFreeSpace(int dataLength) {
        if (pageDataLength - sizeOffFullPointer < dataLength) {
            throw new RuntimeException("Too much data size =(");
        }
        if (page.getFreeSpace() - sizeOffFullPointer < dataLength) {
            try {
                pageWriter.writePage(page);
            } catch (IOException e) {
                throw new RuntimeException("Can not write page =(", e);
            }

            page = createNewPage();
        }
    }

    private Page createNewPage() {
        return new Page(PageHeader.builder()
                .pageNumber(page.getHeader().getPageNumber() + 1)
                .pageType(page.getHeader().getPageType())
                .pageId(page.getHeader().getPageId())
                .pageFreeSpace(pageDataLength)
                .build());
    }
}
