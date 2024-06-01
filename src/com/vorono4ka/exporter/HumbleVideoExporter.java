package com.vorono4ka.exporter;

import com.vorono4ka.utilities.ImageUtils;
import io.humble.video.*;
import io.humble.video.awt.MediaPictureConverter;
import io.humble.video.awt.MediaPictureConverterFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class HumbleVideoExporter implements AutoCloseable {
    private final Rational frameRate;
    private final Encoder encoder;
    private final Muxer muxer;
    private final PixelFormat.Type pixelFormat;
    private final int targetPixelType;

    private MediaPictureConverter converter;
    private MediaPicture picture;
    private MediaPacket packet;
    private boolean isOpen;

    public HumbleVideoExporter(Path path, String formatName, String codecName, int fps, int width, int height) {
        this.frameRate = Rational.make(1, fps);

        this.muxer = Muxer.make(path.toString(), null, formatName);
        final MuxerFormat format = this.muxer.getFormat();
        Codec codec = createCodec(codecName, format);

        this.pixelFormat = PixelFormat.Type.PIX_FMT_YUV420P;
        this.targetPixelType = BufferedImage.TYPE_3BYTE_BGR;
        this.encoder = createEncoder(width, height, pixelFormat, frameRate, codec, format);
    }

    private static Codec createCodec(String codecName, MuxerFormat format) {
        final Codec codec;
        if (codecName != null) {
            codec = Codec.findEncodingCodecByName(codecName);
        } else {
            codec = Codec.findEncodingCodec(format.getDefaultVideoCodecId());
        }

        return codec;
    }

    private static Encoder createEncoder(int width, int height, PixelFormat.Type pixelFormat, Rational frameRate, Codec codec, MuxerFormat format) {
        Encoder encoder = Encoder.make(codec);
        encoder.setWidth(width);
        encoder.setHeight(height);
        encoder.setPixelFormat(pixelFormat);
        encoder.setTimeBase(frameRate);

        if (format.getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
            encoder.setFlag(Encoder.Flag.FLAG_GLOBAL_HEADER, true);

        return encoder;
    }

    public void startEncoding() {
        openStream();

        this.picture = MediaPicture.make(
            this.encoder.getWidth(),
            this.encoder.getHeight(),
            this.pixelFormat
        );
        this.picture.setTimeBase(this.frameRate);
        this.packet = MediaPacket.make();
    }

    public void encodeFrame(BufferedImage image, int frameIndex) {
        image = ImageUtils.convertToType(image, this.targetPixelType);

        if (this.converter == null)
            this.converter = MediaPictureConverterFactory.createConverter(image, this.picture);
        this.converter.toPicture(this.picture, image, frameIndex);

        encodePacket(this.packet, this.picture);
    }

    @Override
    public void close() {
        if (!isOpen) return;

        flush();
        this.muxer.close();
        this.isOpen = false;
    }

    public void flush() {
        encodePacket(this.packet, null);
    }

    private void openStream() {
        this.encoder.open(null, null);

        this.muxer.addNewStream(this.encoder);
        try {
            this.muxer.open(null, null);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        this.isOpen = true;
    }

    private void encodePacket(MediaPacket packet, MediaPicture picture) {
        do {
            this.encoder.encode(packet, picture);
            if (packet.isComplete())
                this.muxer.write(packet, false);
        } while (packet.isComplete());
    }
}
