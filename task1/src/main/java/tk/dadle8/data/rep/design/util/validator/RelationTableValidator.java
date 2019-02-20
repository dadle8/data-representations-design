package tk.dadle8.data.rep.design.util.validator;

public class RelationTableValidator {

    public void columnLengthValidate(int columnCountInTable, int columnCountInQuery) {
        if (columnCountInTable != columnCountInQuery) {
            throw new RuntimeException("The number of columnOrderMap does not match");
        }
    }
}
