package tk.dadle8.data.rep.design.datamodel.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class Row {

    private Component[] components;

    @Override
    public String toString() {
        return Arrays.toString(components);
    }

    public Object getComponentValue(int orderInRow) {
        return components[orderInRow].getValue();
    }

    public void setComponentValue(int orderInRow, Object value) {
        components[orderInRow].setValue(value);
    }

    public byte[] getBytes(Column[] columns) {
        byte[] data;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            for (Column column : columns) {
                Object value = getComponentValue(column.getOrder());
                if (value instanceof Integer) {
                    objectOutputStream.writeInt((Integer) value);
                } else if (value instanceof Long) {
                    objectOutputStream.writeLong((Long) value);
                } else if (value instanceof Short) {
                    objectOutputStream.writeShort((Short) value);
                } else if (value instanceof Float) {
                    objectOutputStream.writeFloat((Float) value);
                } else if (value instanceof Double) {
                    objectOutputStream.writeDouble((Double) value);
                } else if (value instanceof String) {
                    objectOutputStream.writeUTF((String) value);
                }
            }
            objectOutputStream.flush();
            data = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Can not get byte array from Row =(", e);
        }

        return data;
    }

}
