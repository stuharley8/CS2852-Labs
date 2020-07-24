/*
 * Course: CS2852
 * Spring 2019
 * Lab: Lab 5 - BufferedIO
 * Name: Stuart Harley
 * Created: 4/1/19
 */

package harleys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class contains tests for BufferedIO streams
 */
public class Lab5Tests {
    /**
     * The byte array to be written and read
     */
    private static final byte[] BYTES = {0, 2, 4, 8, 16, 32, 64, (byte)128};

    public static void main(String[] ignored) {
        try {
            writeBytesToByteArrayOutputStream();
            writeBytesArrayFromByteArrayOutputStream();
            testWriteBit();
            readBytesFromByteArrayInputStream();
            readBytesArrayFromByteArrayInputStream();
            testReadBit();
            testInputIllegalStateException();
        } catch(IOException e) {
            System.out.println("Somehow an IOException was thrown.");
        }
    }

    /**
     * Reads bytes from a BufferedInputStream.
     * @throws IOException if an IOException is encountered
     */
    private static void readBytesFromByteArrayInputStream() throws IOException {
        try(ByteArrayInputStream bin = new ByteArrayInputStream(BYTES);
                BufferedInputStream in = new BufferedInputStream(bin)) {
            // Read bytes from input stream
            byte[] bytesRead = new byte[BYTES.length];
            for (int i = 0; i < BYTES.length; i++) {
                int number = in.read();
                if (number == -1) {
                    System.out.println("Error: read too many bytes");
                }
                bytesRead[i] = (byte) number;
            }
            // Confirm that bytes read match what was in the input stream
            if (0 != Arrays.compare(bytesRead, BYTES)) {
                System.out.println("Error: bytes read don't match");
            }
        }
    }

    /**
     * Reads a byte array out of a BufferedInputStream.
     * Also tests constructor that specifies the length of the buffer
     * @throws IOException if an IOException is encountered
     */
    private static void readBytesArrayFromByteArrayInputStream() throws IOException {
        try(ByteArrayInputStream bin = new ByteArrayInputStream(BYTES);
                BufferedInputStream in = new BufferedInputStream(bin)) {
            // Read BYTES.length-1 bytes from input stream into a new bytes array
            byte[] tempBytesRead = new byte[BYTES.length-1];
            in.read(tempBytesRead);
            byte[] bytesRead = new byte[BYTES.length];
            //Reads the last byte from input stream
            System.arraycopy(tempBytesRead, 0, bytesRead, 0, tempBytesRead.length);
            bytesRead[bytesRead.length-1] = (byte)in.read();
            // Confirm that bytes read match what was in the input stream
            if (0 != Arrays.compare(bytesRead, BYTES)) {
                System.out.println("Error: bytes read don't match");
            }
        }
    }

    /**
     * Tests read bit by reading out the entire input stream as bits
     * @throws IOException if an IOException is encountered
     */
    private static void testReadBit() throws IOException {
        try(ByteArrayInputStream bin = new ByteArrayInputStream(BYTES);
                BufferedInputStream in = new BufferedInputStream(bin, 3)) {
            // Read and Prints all bits from input stream
            //Should print out all of BYTES in binary in the console, one on each line
            for(int i = 0; i < BYTES.length; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(in.readBit());
                }
                System.out.print("\n");
            }
            //Confirms that the stream is empty
            if(in.read() != -1) {
                System.out.println("Stream should be empty");
            }
        }
    }

    /**
     * Tests to see if after reading out one bit, read throws an IllegalStateException
     * @throws IOException if an IOException is encountered
     */
    private static void testInputIllegalStateException() throws IOException {
        try(ByteArrayInputStream bin = new ByteArrayInputStream(BYTES);
                BufferedInputStream in = new BufferedInputStream(bin)) {
            // Reads 1 bit and then tries to read a full byte, which should throw an exception
            in.readBit();
            in.read();
            //Should catch and print out an error
        } catch (IllegalStateException e) {
            if(!e.getMessage().equals("Partial byte of data contained, must complete first")) {
                System.out.println("Should have thrown an error");
            }
        }
    }

    /**
     * Writes bytes to a BufferedOutputStream.
     * @throws IOException if an IOException is encountered
     */
    private static void writeBytesToByteArrayOutputStream() throws IOException {
        try(ByteArrayOutputStream bout = new ByteArrayOutputStream(BYTES.length);
                BufferedOutputStream out = new BufferedOutputStream(bout)) {
            // Write bytes to output stream
            for(byte number : BYTES) {
                out.write(number);
            }
            out.flush();
            // Get bytes out of the output stream byte array
            byte[] bytesWritten = bout.toByteArray();

            // Confirm that bytes in the output stream match what was written
            if(0!=Arrays.compare(bytesWritten, BYTES)) {
                System.out.println("Error: bytes written don't match");
            }
        }
    }

    /**
     * Writes a byte array into a output stream
     * Also test the constructor that sets the length of the buffer
     * Because of this, also tests flush since it will have to flush the buffer since it is length 3
     * @throws IOException if an IOException is encountered
     */
    private static void writeBytesArrayFromByteArrayOutputStream() throws IOException {
        try(ByteArrayOutputStream bout = new ByteArrayOutputStream(BYTES.length);
                BufferedOutputStream out = new BufferedOutputStream(bout, 3)) {
            // Writes bytes from a bytes array to the output stream
            out.write(BYTES);
            out.flush();

            // Get bytes out of the output stream byte array
            byte[] bytesWritten = bout.toByteArray();

            // Confirm that bytes in the output stream match what was written
            if(0!=Arrays.compare(bytesWritten, BYTES)) {
                System.out.println("Error: bytes written don't match");
            }
        }
    }

    /**
     * Tests the writeBit method as well as tests the IllegalStateException being thrown
     * if a partial byte is written and then write is called
     * @throws IOException if an IOException is encountered
     */
    private static void testWriteBit() throws IOException {
        try(ByteArrayOutputStream bout = new ByteArrayOutputStream(4);
                BufferedOutputStream out = new BufferedOutputStream(bout, 3)) {
            out.write(73);
            //Writes in byte by bits which represent the number 87 (chosen at random)
            out.writeBit(0);
            out.writeBit(1);
            out.writeBit(0);
            out.writeBit(1);
            out.writeBit(0);
            out.writeBit(1);
            out.writeBit(1);
            out.writeBit(1);
            out.write(37);
            out.write(52);
            out.flush();

            // Get bytes out of the output stream byte array
            byte[] bytesWritten = bout.toByteArray();

            // Confirm that bytes in the output stream match what was written
            byte[] match = {73, 87, 37, 52};
            if(0!=Arrays.compare(bytesWritten, match)) {
                System.out.println("Error: bytes written don't match");
            }
            //Tries to write() after writing 1 bit, so should throw an exception
            out.writeBit(0);
            out.write(67);
        } catch(IllegalStateException e) {
            if(!e.getMessage().equals("Partial byte of data written, must complete first")) {
                System.out.println("Should have thrown an error");
            }
        }
    }
}