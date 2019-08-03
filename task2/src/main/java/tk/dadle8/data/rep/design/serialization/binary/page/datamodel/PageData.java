package tk.dadle8.data.rep.design.serialization.binary.page.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.offLength;
import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.sizeOffFullPointer;

@Getter
@Setter
public class PageData {
    private ByteBuffer data;
    private int offStart;
    private int offEnd;

    public PageData(int dataLength) {
        this.data = ByteBuffer.wrap(new byte[dataLength]);
        this.offEnd = dataLength;
    }

    public PageData(byte[] data) {
        this.data = ByteBuffer.wrap(data);
        this.offEnd = data.length;
    }

    public void writeData(byte[] src) {
        data.put(src);
        data.putInt(offEnd - offLength, src.length);

        offEnd -= sizeOffFullPointer;
    }

    public byte[] readData() {
        int length = data.getInt(offEnd - offLength);

        offEnd -= sizeOffFullPointer;

        byte[] dst = new byte[length];
        data.get(dst);

        return dst;
    }
}
