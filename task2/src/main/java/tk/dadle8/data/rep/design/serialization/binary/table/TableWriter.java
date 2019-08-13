package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.PageHeader;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageWriter;
import tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class TableWriter implements Closeable {

    private Page page;
    private PageWriter pageWriter;

    public TableWriter() {
        try {
            this.pageWriter = new PageWriter(new File("table.td"));
        } catch (IOException e) {
            throw new RuntimeException("Can not create page writer =(", e);
        }
    }

    public void writeTable(RelationTable table) {
        preparePage(table);

        writeTableName(table.getName());
        writeTableColumns(table.getColumns().values().toArray(new Column[0]));
        writeTableRows(table.getRows().toArray(new Row[0]), table.getColumns().values().toArray(new Column[0]));
    }

    public void close() {
        try {
            pageWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Can not close page =(", e);
        }
    }

    public void preparePage(RelationTable table) {
        this.page = new Page(PageHeader.builder()
                .pageNumber(0)
                .pageType(PageUtils.pageTypeNameColumns)
                .pageId(table.getId())
                .pageFreeSpace(PageUtils.pageDataLength)
                .build()
        );
    }

    protected void writeTableName(String tableName) {
        writeDataToPage(tableName.getBytes());
    }

    protected void writeTableColumns(Column[] columns) {
        Stream.of(columns).forEach(this::writeTableColumn);
        try {
            pageWriter.writePage(page);
        } catch (IOException e) {
            throw new RuntimeException("Can not write page =(", e);
        }
    }

    private void writeTableColumn(Column column) {
        writeDataToPage(column.getBytes());
    }

    private void writeDataToPage(byte[] data) {
        createNewPageIfNoFreeSpace(data.length);

        page.writeData(data);
        page.reduceSpace(data.length);
    }

    protected void writeTableRows(Row[] rows, Column[] columns) {
        page = new Page(PageHeader.builder()
                .pageNumber(page.getHeader().getPageNumber() + 1)
                .pageType(PageUtils.pageTypeRows)
                .pageId(page.getHeader().getPageId())
                .pageFreeSpace(PageUtils.pageDataLength)
                .build());

        Stream.of(rows).forEach(row -> writeTableRow(row, columns));

        try {
            pageWriter.writePage(page);
        } catch (IOException e) {
            throw new RuntimeException("Can not write page =(", e);
        }
    }

    private void writeTableRow(Row row, Column[] columns) {
        writeDataToPage(row.getBytes(columns));
    }

    private void createNewPageIfNoFreeSpace(int dataLength) {
        if (PageUtils.pageDataLength - PageUtils.sizeOffFullPointer < dataLength) {
            throw new RuntimeException("Too much data size =(");
        }
        if (page.getFreeSpace() - PageUtils.sizeOffFullPointer < dataLength) {
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
                .pageType(PageUtils.getPageType(page.getHeader().getPageType()))
                .pageId(page.getHeader().getPageId())
                .pageFreeSpace(PageUtils.pageDataLength)
                .build());
    }
}
