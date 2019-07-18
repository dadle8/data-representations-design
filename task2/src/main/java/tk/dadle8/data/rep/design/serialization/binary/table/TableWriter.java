package tk.dadle8.data.rep.design.serialization.binary.table;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageWriter;

import java.io.File;
import java.io.IOException;

public class TableWriter {

    private Page page;
    private RelationTable table;

    public TableWriter(RelationTable table) {
        this.page = new Page();
        this.table = table;
    }

    public void writeTable() {
        writeTableName();
    }

    protected void writeTableName() {
        byte[] name = table.getName().getBytes();
        createNewPageIfNoFreeSpace(name);

        page.getData().writeData(name);
        page.reduceSpace(name.length + 2 * Integer.BYTES);
    }

    private void createNewPageIfNoFreeSpace(byte[] data) {
        if (page.getHeader().getPageFreeSpace() + 2 * Integer.BYTES < data.length) {
            Page newPage = new Page(page.getDataLength());
            newPage.getHeader().setPageNumber(page.getHeader().getPageNumber());
            newPage.getHeader().setPageType(page.getHeader().getPageType());
            newPage.getHeader().setPageFreeSpace(page.getDataLength());
            newPage.getHeader().setPageId(page.getHeader().getPageId());

            try {
                PageWriter pageWriter = new PageWriter(page, new File("table.td"));
            } catch (IOException exc) {
                throw new RuntimeException("Can not write page =(");
            }

            page = newPage;
        }
    }
}
