package tk.dadle8.data.rep.design.util.validator;

public class ValidatorUtils {

    public void columnLengthValidate(int columnCountInTable, int columnCountInQuery) {
        if (columnCountInTable != columnCountInQuery) {
            throw new RuntimeException("The number of columnOrderMap does not match");
        }
    }

    public void valueTypeInRow(Object newValue, Class typeInColumn) {
        if (newValue == null) {
            return;
        }
        if (!newValue.getClass().equals(typeInColumn)) {
            throw new RuntimeException("Another type for adding value");
        }
    }
}
