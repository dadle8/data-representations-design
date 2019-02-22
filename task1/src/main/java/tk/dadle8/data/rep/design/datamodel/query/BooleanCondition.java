package tk.dadle8.data.rep.design.datamodel.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BooleanCondition extends Condition {

    private BooleanOperator operator;
}
