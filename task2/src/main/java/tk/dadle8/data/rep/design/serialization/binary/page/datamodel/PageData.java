package tk.dadle8.data.rep.design.serialization.binary.page.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
@Setter
public class PageData {
    private ByteBuffer data;
    private int offStart;
    private int offEnd;

    public PageData(int dataLength) {
        this.data = ByteBuffer.wrap(new byte[dataLength]);
        this.offStart = 0;
        this.offEnd = dataLength;
    }

    public PageData(byte[] data) {
        this.data = ByteBuffer.wrap(data);
    }

    public void writeData(byte[] src) {
        data.put(src);

        data.putInt(offEnd - 2 * Integer.BYTES, offStart);
        data.putInt(offEnd - Integer.BYTES, src.length);

        offEnd -= 2 * Integer.BYTES;
        offStart += src.length;
    }
}
