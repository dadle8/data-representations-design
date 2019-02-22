package tk.dadle8.data.rep.design.api;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.structure.Attribute;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

import java.util.List;

public interface BasicTableOperations {

    Row insert(String[] columnNames, Object[] values);
    List<Row> select(Condition[] conditions);
    int update(Condition[] conditions, String[] columnNames, Object[] values);
    int delete(Condition[] conditions);
}
