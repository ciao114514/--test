package com.example.hospital.utils;

import java.io.*;

public class FileUtil {

    /**
     * 根据文件路径读取文件内容
     *
     * @param fileInPath
     * @throws IOException
     */
    public static void getFileContent(String fileInPath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(fileInPath);
             FileOutputStream outputStream = new FileOutputStream(fileInPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
