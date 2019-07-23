package tk.dadle8.data.rep.design.datamodel.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
@Setter
@AllArgsConstructor
public class Column {

    private String name;
    private Class<?> type;
    private int order;
//    private Attribute attribute;
//    private Restriction[] restrictions

    public byte[] getBytes() {
        byte[] nameBytes = name.getBytes();
        byte[] typeBytes = type.getCanonicalName().getBytes();
        int allDataLength = nameBytes.length + typeBytes.length + Integer.BYTES;

        ByteBuffer columnData = ByteBuffer.wrap(new byte[allDataLength]);
        columnData.put(nameBytes);
        columnData.put(typeBytes);
        columnData.putInt(order);

        return columnData.array();
    }

    @Override
    public String toString() {
        return getName();
    }
}
