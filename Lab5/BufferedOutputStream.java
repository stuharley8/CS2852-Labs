/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 5 - BufferedIO
 * Name: Stuart Harley
 * Created: 4/1/19
 */

package harleys;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents a Buffered Output Stream
 */
public class BufferedOutputStream implements Closeable, Flushable {

    private static final int DEFAULT_BUFFER_SIZE = 32;

    /**
     * The underlying output stream
     */
    private OutputStream out;

    /**
     * The internal buffer array where the data is stored
     */
    private byte[] buf;

    /**
     * The number of valid bytes in the buffer
     */
    private int count;

    /**
     * The number of times the method writeBit() has been called.
     * Max of 7, resets to 0 once reaching 8 since 8 bits represents
     * a full byte
     */
    private int countNumWriteBits;

    /**
     * Keeps track of the byte being written in by bits through writeBit()
     */
    private byte partialByte;

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        flush();
        out.close();
    }

    /**
     * Flushes this buffered output stream. This forces any buffered output bytes to be written
     * out to the underlying output stream.
     *
     * @throws IOException If an I/O error occurs
     * @throws IllegalStateException if called when a byte in the output stream has only been
     * partially written. I.e., writeBit() has been called at least once but not a number of
     * times that is evenly divisible by 8.
     */
    @Override
    public void flush() throws IOException, IllegalStateException {
        if(countNumWriteBits != 0) {
            throw new IllegalStateException("Partial byte of data contained, must complete first");
        }
        for (int i = 0; i < count; i++) {
            out.write(buf[i]);
        }
        count = 0;
    }

    /**
     * Creates a new buffered output stream to write data to the specified underlying output stream.
     * @param out the underlying output stream
     */
    public BufferedOutputStream(OutputStream out) {
        this(out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Creates a new buffered output stream to write data to the specified underlying output stream
     * with the specified buffer size.
     * @param out the underlying output stream
     * @param size the buffer size
     * @throws IllegalArgumentException if size <= 0
     */
    public BufferedOutputStream(OutputStream out, int size) throws IllegalArgumentException {
        if(size <= 0) {
            throw new IllegalArgumentException("Size <= 0");
        }
        this.out = out;
        buf = new byte[size];
    }

    /**
     * Writes the specified byte to this buffered output stream
     * @param b the byte to be written
     * @throws IOException if an I/O error occurs.
     * @throws IllegalStateException if called when a byte in the output stream has only been
     * partially written. I.e., writeBit() has been called at least once but not a number of
     * times that is evenly divisible by 8.
     */
    public void write(int b) throws IOException, IllegalStateException {
        if(countNumWriteBits != 0) {
            throw new IllegalStateException("Partial byte of data written, must complete first");
        }
        buf[count] = (byte)b;
        count++;
        if(count >= buf.length) {
            flush();
            count = 0;
        }
    }

    /**
     * Writes bytes from the specified byte array to this buffered output stream
     * @param b the data
     * @throws IOException if an I/O error occurs.
     * @throws IllegalStateException if called when a byte in the output stream has only been
     * partially written. I.e., writeBit() has been called at least once but not a number of
     * times that is evenly divisible by 8.
     */
    public void write(byte[] b) throws IOException, IllegalStateException {
        if(countNumWriteBits != 0) {
            throw new IllegalStateException("Partial byte of data written, must complete first");
        }
        for(byte bi : b) {
            write((int)bi);
        }
    }

    /**
     * This method writes the specified bit to this output stream. The bit to be written is
     * the lowest-order bit of the argument bit. The 31 high-order bits of bit are ignored.
     * E.g., if the integer passed to the method has a value of
     * 0b11110000111100001111000011110001, all but the last bit is ignored.
     * The first bit in a partially written byte is located in the most significant bit position.
     * @param bit the value of the bit as an int from 0 to 1
     */
    public void writeBit(int bit) {
        countNumWriteBits++;
        partialByte = (byte)(bit << 8-countNumWriteBits | partialByte);
        countNumWriteBits %= 8;
        if(countNumWriteBits == 0) {
            buf[count] = partialByte;
            partialByte = 0;
            count++;
        }
    }
}