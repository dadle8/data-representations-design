package tk.dadle8.data.rep.design.api;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

import java.util.List;

public interface BasicTableOperations {
    Row insert(RelationTable table, String[] columnNames, Object[] values);

    List<Row> select(RelationTable table, Condition[] conditions);

    int update(RelationTable table, Condition[] conditions, String[] columnNames, Object[] values);

    int delete(RelationTable table, Condition[] conditions);
}
