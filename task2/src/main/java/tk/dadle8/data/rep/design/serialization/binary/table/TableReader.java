package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageReader;

import java.io.File;
import java.io.IOException;

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

    }

    private void readTableColumn(Column column) {

    }

    protected void readTableRows() {

    }
}
