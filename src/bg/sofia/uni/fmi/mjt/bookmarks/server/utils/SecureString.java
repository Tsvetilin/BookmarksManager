package bg.sofia.uni.fmi.mjt.bookmarks.server.utils;


import java.util.List;

public class SecureString {

    public SecureString() {

    }

    public SecureString(String string) {
        data = string.toCharArray().clone();
    }

    private static final int MAX_CAPACITY = 8192;
    private char[] data = new char[MAX_CAPACITY];

    public void clear() {
    }

    public void append() {

    }

    public boolean startsWith(String cmd) {
        return false;
    }

    public char[] substring() {
        return null;
    }

    public boolean isBlank() {
        return false;
    }

    public String toString() {
        return "";
    }

    public char[] toCharArray() {
        return data;
    }

    public List<SecureString> split(char... c) {
        return null;
    }
}
