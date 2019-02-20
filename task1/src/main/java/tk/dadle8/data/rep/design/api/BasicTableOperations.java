package tk.dadle8.data.rep.design.api;

import tk.dadle8.data.rep.design.datamodel.RelationTable;
import tk.dadle8.data.rep.design.datamodel.query.Condition;
import tk.dadle8.data.rep.design.datamodel.structure.Attribute;
import tk.dadle8.data.rep.design.datamodel.structure.Column;
import tk.dadle8.data.rep.design.datamodel.structure.Component;
import tk.dadle8.data.rep.design.datamodel.structure.Row;

public interface BasicTableOperations {

    Row insert(Attribute[] attributes, Component[] components);
    RelationTable select(Condition[] relationConditions, Column[] columns, Row[] rows);
    int update(Condition[] relationConditions, Column[] columns, Row[] rows);
    int delete(Condition[] relationConditions);
}
