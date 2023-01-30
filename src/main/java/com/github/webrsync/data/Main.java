package com.github.webrsync.data;

public class Main {
    public static void main(String[] args) {
        byte[] medBytes = {1, 2, 3};
        int mask = 0x000000;
        int rightMost = (medBytes[0] | mask) << 16;
        int mid = (medBytes[1] | mask) << 8;
        int leftMost = medBytes[2] | mask;
        int medInt = rightMost | mid | leftMost;
        System.out.println(medInt);
    }
}
