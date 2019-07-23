package tk.dadle8.data.rep.design.serialization.binary.page.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.offOffset;
import static tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils.sizeOffFullPointer;

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
        this.offStart = 0;
        this.offEnd = data.length;
    }

    public void writeData(byte[] src) {
        data.put(src);

        data.putInt(offEnd - sizeOffFullPointer, src.length);
        data.putInt(offEnd - offOffset, offStart);

        offEnd -= sizeOffFullPointer;
        offStart += src.length;
    }

    public byte[] readData() {
        int off = data.getInt(offEnd - offOffset);
        int length = data.getInt(offEnd - sizeOffFullPointer);

        offEnd -= sizeOffFullPointer;

        byte[] dst = new byte[length];
        data.get(dst, off, length);
        return dst;
    }
}
