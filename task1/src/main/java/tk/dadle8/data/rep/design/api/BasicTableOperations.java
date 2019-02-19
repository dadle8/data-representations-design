package tk.dadle8.data.rep.design.api;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

public interface BasicTableOperations {

    int insert(Column[] columns, Row[] rows);
    RelationTable select(Condition[] conditions, Column[] columns, Row[] rows);
    int update(Condition[] conditions, Column[] columns, Row[] rows);
    int delete(Condition[] conditions);
}
