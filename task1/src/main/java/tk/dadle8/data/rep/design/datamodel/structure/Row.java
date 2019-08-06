package tk.dadle8.data.rep.design.datamodel.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.HeapByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

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
        ByteBuffer buffer = ByteBuffer.allocate(32);

        for(Column column : columns) {
            Object value = getComponentValue(column.getOrder());
            buffer = createNewBufferIfNeeded(buffer, );



        }

        return buffer.array();
    }

    public ByteBuffer createNewBufferIfNeeded(ByteBuffer buffer, int dataLength) {
        if (buffer.capacity() - buffer.position() < dataLength) {
            ByteBuffer larger = ByteBuffer.allocateDirect(buffer.capacity() * 2 + dataLength);
            buffer.rewind();
            larger.put(buffer);
            buffer = larger;
        }
        return buffer;
    }
}
