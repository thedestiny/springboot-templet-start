package com.platform.itcast.util;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Formatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatrixClient {

    /**
     * Driver function.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {

        // Create two matrices with random values
        Matrix matA = new Matrix(1000, 1200, 100);
        Matrix matB = new Matrix(1200, 1500, 100);

//        Matrix matA = new Matrix(2, 2, 100);
//        Matrix matB = new Matrix(2, 2, 100);

        // We request the server to compute the product first
        long start = System.currentTimeMillis();
        Matrix multiProduct = remoteMultiply("127.0.0.1", 33333, matA, matB);
        double multiTime = System.currentTimeMillis() - start;

        // Then we compute the product locally using the built-in mutliply() method
        start = System.currentTimeMillis();
        Matrix singleProduct = matA.multiply(matB);
        double singleTime = System.currentTimeMillis() - start;

        // Finally, we check if they are the same and their runtime ratio.
        if (singleProduct.equals(multiProduct)) {
            System.out.println("The computation is correct.");
            System.out.println("Single-to-Multi Ratio: " + singleTime/multiTime);
        } else {
            System.out.println("The computation is NOT correct.");
        }
    }

    /**
     * Connect to the matrix server at the specified host and port, and then
     * request the server to compute the matrix product of AxB.
     *
     * @param host IP address of the server
     * @param port port number used by the server
     * @param matA matrix A
     * @param matB matrix B
     * @return the matrix product of AxB
     * @throws IOException
     * @throws UnknownHostException
     * @throws ClassNotFoundException
     */
    public static Matrix remoteMultiply(String host, int port, Matrix matA, Matrix matB) throws IOException, IOException, ClassNotFoundException {

        Matrix product = null;

        // your implementation here
        Socket s= new Socket(host,port);
        ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
        //Send the Matrix object to the server

        oos.writeObject(matA);
        oos.writeObject(matB);
        //Receive result Matrix object form server
        ObjectInputStream ios=new ObjectInputStream(s.getInputStream());
        product=(Matrix)ios.readObject();
        System.out.println(product);
        return product;

    }

}

//------------------------------------------------------------------------------------------------------

/**
 * Immutable Integer Matrix. Do not modify this class.
 *
 * @author vanting
 */
class Matrix implements Serializable {

    private long[][] nums;

    public Matrix() {
        this(1, 1);
    }

    public Matrix(int row, int col) {
        nums = new long[row][col];
    }

    // Initialize this matrix with random numbers between 0 to n (exclusive)
    public Matrix(int row, int col, int n) {
        this(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                nums[i][j] = (int) (Math.random() * n);
            }
        }
    }

    public Matrix(long[][] n) {
        this(n.length, n[0].length);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                nums[i][j] = n[i][j];
            }
        }
    }

    // Copy constructor
    public Matrix(Matrix other) {
        this(other.row(), other.col());
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                nums[i][j] = other.at(i, j);
            }
        }
    }

    //-----------------------------------------------------------------

    public long at(int row, int col) {
        return nums[row][col];
    }

    public int row() {
        return nums.length;
    }

    public int col() {
        return nums[0].length;
    }

    // Return the product of this multiplying other; return null on fail
    public Matrix multiply(Matrix other) {

        int row1 = this.nums.length;
        int col1 = this.nums[0].length;
        int row2 = other.nums.length;
        int col2 = other.nums[0].length;

        if (col1 == row2) {

            long sum = 0;
            long[][] product = new long[row1][col2];

            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < col2; j++) {
                    for (int k = 0; k < col1; k++) {
                        sum = sum + nums[i][k] * other.nums[k][j];
                    }
                    product[i][j] = sum;
                    sum = 0;
                }
            }
            return new Matrix(product);
        } else {
            return null;
        }
    }

    public void print() {
        System.out.println(this);
    }

    // Return a string representation of this matrix
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        int row = this.nums.length;
        int col = this.nums[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                fmt.format("[%4d]", this.nums[i][j]);
            }
            fmt.format("%n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (o != null && o instanceof Matrix) {
            Matrix other = (Matrix) o;

            if (nums.length != other.row()) {
                return false;
            }

            if (nums[0].length != other.col()) {
                return false;
            }

            for (int i = 0; i < nums.length; i++) {
                for (int j = 0; j < nums[0].length; j++) {
                    if (nums[i][j] != other.at(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Arrays.deepHashCode(this.nums);
        return hash;
    }




}
