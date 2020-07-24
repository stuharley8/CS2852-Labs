/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 5 - BufferedIO
 * Name: Stuart Harley
 * Created: 4/1/19
 */

package harleys;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a Buffered Input Stream
 */
public class BufferedInputStream implements Closeable {

    private static final int DEFAULT_BUFFER_SIZE = 32;

    /**
     * Underlying input stream
     */
    private InputStream in;

    /**
     * The internal buffer array where the data is stored
     */
    private byte[] buf;

    /**
     * The number of times the method readBit() has been called.
     * Max of 7, resets to 0 once reaching 8 since 8 bits represents
     * a full byte
     */
    private int countNumReadBits;

    /**
     * The current position in the buffer
     */
    private int pos = -1;

    /**
     * The index one greater than the index of the last valid byte in the buffer
     */
    private int count;

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
        buf = null;
        in.close();
    }

    /**
     * Creates a BufferedInputStream and saves its argument, the input stream in, for later use.
     * An internal buffer array is created and stored in buf with size 32.
     * @param in the underlying input stream
     */
    public BufferedInputStream(InputStream in) {
        this(in, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Creates a BufferedInputStream with the specified buffer size, and saves its argument,
     * the input stream in, for later use.
     * An internal buffer array of length size is created and stored in buf
     * @param in the underlying input stream
     * @param size the buffer size
     * @throws IllegalArgumentException if size <= 0
     */
    public BufferedInputStream(InputStream in, int size) throws IllegalArgumentException {
        if(size <= 0) {
            throw new IllegalArgumentException("Size <= 0");
        }
        this.in = in;
        buf = new byte[size];
    }

    /**
     * Reads the next byte of data from the input stream. The value byte is returned as an int
     * in the range 0 to 255. If no byte is available because the end of the stream
     * has been reached,the value -1 is returned.
     * @return the next byte of data, or -1 if the end of the stream is reached
     * @throws IOException if this input stream has been closed by invoking its
     * close method, or an I/O error occurs
     * @throws IllegalStateException if called when a byte from the input stream has only
     * been partially read. I.e., readBit() has been called at least once but not a number
     * of times that is evenly divisible by 8.
     */
    public int read() throws IOException, IllegalStateException {
        if(countNumReadBits != 0) {
            throw new IllegalStateException("Partial byte of data contained, must complete first");
        }
        addToBuffer();
        int returnValue;
        if(count == 0 || pos == count) {
            returnValue = -1;
        } else {
            returnValue = buf[pos++];
        }
        return returnValue;
    }

    /**
     * Reads bytes from this byte-input stream into the specified byte array
     * @param b - destination buffer.
     * @return the number of bytes read, or -1 if the end of the stream has been reached.
     * @throws IOException if this input stream has been closed by invoking its
     * close() method, or an I/O error occurs.
     * @throws IllegalStateException if called when a byte from the input stream has only
     * been partially read. I.e., readBit() has been called at least once but not a number
     * of times that is evenly divisible by 8.
     */
    public int read(byte[] b) throws IOException, IllegalStateException {
        int counter = 0;
        for(int i = 0; i<b.length; i++) {
            b[i] = (byte)read();
            if(b[i] == -1) {
                b[i] = 0;
                break;
            }
            counter++;
        }
        if(counter == 0) {
            counter = -1;
        }
        return counter;
    }

    /**
     * This method reads the next bit of data from the input stream. The value of the bit
     * is returned as an int in the range 0 to 1. If no bit is available because the end
     * of the stream has been reached, the value -1 is returned. This method blocks until
     * input data is available, the end of the stream is detected, or an exception is thrown.
     * Bits are returned from most to least significant. E.g., if the input stream contains
     * one byte with the value 0b10110000, this method will return bit values in the following
     * order: 1, 0, 1, 1, 0, 0, 0, 0
     * @return the value of the bit returned as an int in the range 0 to 1, or -1 if the end
     * of the stream has been reached
     * @throws IOException if there is a problem reading from the file
     */
    public int readBit() throws IOException{
        addToBuffer();
        if(count == 0 || pos == count) {
            return -1;
        }
        countNumReadBits++;
        int value = (buf[pos] >> (8-countNumReadBits)) & 0b00000001;
        countNumReadBits %= 8;
        if(countNumReadBits == 0) {
            pos++;
        }
        return value;
    }

    /**
     * Helper method that adds the next segment from the inputStream to the buffer if the buffer is
     * empty or if it has been read through completely already
     */
    private void addToBuffer() throws IOException {
        if(pos == -1 || pos == buf.length) {
            count = in.readNBytes(buf, 0, buf.length);
            pos = 0;
        }
    }
}
