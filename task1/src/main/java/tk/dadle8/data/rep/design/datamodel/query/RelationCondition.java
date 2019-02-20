package tk.dadle8.data.rep.design.datamodel.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.datamodel.structure.Attribute;
import tk.dadle8.data.rep.design.datamodel.structure.Component;

@Getter
@Setter
@AllArgsConstructor
public class RelationCondition extends Condition {

    private Attribute attribute;
    private Component component;
    private Operator operator;
}
