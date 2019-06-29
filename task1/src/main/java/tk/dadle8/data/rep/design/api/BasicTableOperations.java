package tk.dadle8.data.rep.design.api;

import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

import java.util.List;

public interface BasicTableOperations {
    void insert(String[] columnNames, Object[] values);

    List<Row> select(Condition[] conditions);

    int update(Condition[] conditions, String[] columnNames, Object[] values);

    int delete(Condition[] conditions);
}
