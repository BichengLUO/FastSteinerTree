package com.faststeinertree;

import java.io.*;

/**
 * Created by nobodycrackme on 2016/6/14.
 */
public class GraphLoader {
    public static int[][] readArray(String filePath) {
        String s = readString(filePath);
        String[] rows = s.split("\r\n");// note different dilemeter on different platform
        int length = rows.length;
        int[][] result = new int[length][length];
        for (int i = 0; i < length; i++) {
            String[] row = rows[i].split(" ");
            for (int j = 0; j < length; j++) {
                result[i][j] = Integer.parseInt(row[j]);
            }
        }
        return result;
    }

    public static String readString(String filePath) {
        File f = new File(filePath);
        FileInputStream fin;
        String s = null;
        try {
            fin = new FileInputStream(f);
            byte[] buffer = new byte[(int) f.length()];
            new DataInputStream(fin).readFully(buffer);
            fin.close();
            s = new String(buffer, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
