package dev.donutquine.exporter;

public record VideoFormat(String name, String codec, String pixelFormat,
                          boolean requiresSizeDividableByTwo) {

}
