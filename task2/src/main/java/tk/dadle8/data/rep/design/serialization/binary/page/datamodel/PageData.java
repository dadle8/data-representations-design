package tk.dadle8.data.rep.design.serialization.binary.page.datamodel;

import lombok.Getter;
import lombok.Setter;
import tk.dadle8.data.rep.design.serialization.binary.page.utils.PageUtils;

import java.nio.ByteBuffer;

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
        data.putInt(offEnd - PageUtils.offLength, src.length);

        offEnd -= PageUtils.sizeOffFullPointer;
    }

    public byte[] readData() {
        int length = data.getInt(offEnd - PageUtils.offLength);

        offEnd -= PageUtils.sizeOffFullPointer;

        byte[] dst = new byte[length];
        data.get(dst);

        return dst;
    }
}
