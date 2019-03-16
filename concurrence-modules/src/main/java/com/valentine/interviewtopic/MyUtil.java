package com.valentine.interviewtopic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Valentine
 * @since 2019/3/16 16:05
 */
public final class MyUtil {

    /**
     * 工具类中的方法都是静态方式访问的因此将构造器私有不允许创建对象(绝对好习惯)
     */
    private MyUtil() {
        throw new AssertionError();
    }

    /**
     * 实现文件拷贝
     */
    public static void fileCopy(String source, String target) throws IOException {
        try (InputStream in = new FileInputStream(source)) {
            try (OutputStream out = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                int bytesToRead;
                while ((bytesToRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }
            }
        }
    }

    /**
     * 实现文件拷贝
     */
    public static void fileCopyNIO(String source, String target) throws IOException {
        try (FileInputStream in = new FileInputStream(source)) {
            try (FileOutputStream out = new FileOutputStream(target)) {
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                while (inChannel.read(buffer) != -1) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            }
        }
    }


    /**
     * 写一个方法，输入一个文件名和一个字符串，统计这个字符串在这个文件中出现的次数。
     *
     * @param filename 文件名
     * @param word     字符串
     * @return 字符串在文件中出现的次数
     */
    public static int countWordInFile(String filename, String word) {
        int counter = 0;
        try (FileReader fr = new FileReader(filename)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    int index = -1;
                    while (line.length() >= word.length() && (index = line.indexOf(word)) >= 0) {
                        counter++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return counter;
    }

    /**
     * 列出当前文件夹下的文件
     * "/Users/Hao/Downloads"
     */
    public static void showFiles(String filePath) {
        File f = new File(filePath);
        for (File temp : f.listFiles()) {
            if (temp.isFile()) {
                System.out.println(temp.getName());
            }
        }

        // 如果需要对文件夹继续展开
        showDirectory(new File(filePath));
    }

    /**
     * 再继续对文件夹进行展开
     */
    public static void showDirectory(File f) {
        walkDirectory(f, 0);
    }

    private static void walkDirectory(File f, int level) {
        if (f.isDirectory()) {
            for (File temp : f.listFiles()) {
                walkDirectory(temp, level + 1);
            }
        } else {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("\t");
            }
            System.out.println(f.getName());
        }
    }

    /**
     * nio列出当前文件夹下的文件
     * "/Users/Hao/Downloads"
     */
    private static void nioShowFiles(String path) {
        Path initPath = Paths.get(path);

        try {
            Files.walkFileTree(initPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    System.out.println(file.getFileName().toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}