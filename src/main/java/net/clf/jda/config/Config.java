package net.clf.jda.config;

public class Config {
    private static int bufferSize = 200;

    public static void setBufferSize(int size) {
        bufferSize = size;
    }

    public static int getBufferSize() {
        return bufferSize;
    }
}