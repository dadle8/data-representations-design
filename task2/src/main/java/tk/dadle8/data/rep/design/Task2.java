package tk.dadle8.data.rep.design;

import tk.dadle8.data.rep.design.serialization.binary.page.datamodel.Page;
import tk.dadle8.data.rep.design.serialization.binary.page.impl.PageReader;

import java.io.File;
import java.io.IOException;

public class Task2 {

    public static void main(String[] args) throws IOException {
        System.out.println("Task2");

//        PageHeader header = new PageHeader();
//        header.setPageNumber(0);
//        header.setPageType(0);
//        header.setPageId(111);
//
//        PageData data = new PageData();
//        data.setData(new byte[8176]);
//
//        header.setPageFreeSpace(8176);
//
//        Page page = new Page();
//        page.setHeader(header);
//        page.setData(data);
//
//        PageWriter pageWriter = new PageWriter(page, new File("table.td"));
//        pageWriter.writePage();

        PageReader pageReader = new PageReader(new File("table.td"));
        Page newPage = pageReader.readPage();
    }
}
