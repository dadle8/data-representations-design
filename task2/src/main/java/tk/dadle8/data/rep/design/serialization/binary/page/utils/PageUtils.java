package tk.dadle8.data.rep.design.serialization.binary.page.utils;

public class PageUtils {
    public static final int pageHeaderSize = 16;
    public static final int pageLength = 128;
    public static final int pageDataLength = pageLength - pageHeaderSize;
    public static final int offLength = Integer.BYTES;
    public static final int offOffset = Integer.BYTES;
    public static final int sizeOffFullPointer = offLength;
    public static final int pageTypeNameColumns = 0;
    public static final int pageTypeRows = 1;
    public static final int pageTypeNameColumnsContinue = 2;
    public static final int pageTypeRowsContinue = 3;

    public static int getPageType(int currentPageType) {
        switch (currentPageType) {
            case pageTypeNameColumns:
            case pageTypeNameColumnsContinue:
                return pageTypeNameColumnsContinue;
            case pageTypeRows:
            case pageTypeRowsContinue:
                return pageTypeRowsContinue;
            default:
                return -1;
        }
    }
}
