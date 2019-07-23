package tk.dadle8.data.rep.design.serialization.binary.page.utils;

public class PageUtils {
    public static int pageHeaderSize = 16;
    public static int pageLength = 128;
    public static int pageDataLength = pageLength - pageHeaderSize;
    public static int offLength = Integer.BYTES;
    public static int offOffset = Integer.BYTES;
    public static int sizeOffFullPointer = offOffset + offLength;
    public static int pageTypeNameColumns = 0;
    public static int pageTypeRows = 1;
    public static int pageTypeNameColumnsContinue = 2;
    public static int pageTypeRowsContinue = 3;

    public static byte[] intToBytes(final int data) {
        return new byte[] {
                (byte)((data >> 24) & 0xff),
                (byte)((data >> 16) & 0xff),
                (byte)((data >> 8) & 0xff),
                (byte)((data >> 0) & 0xff),
        };
    }
}
