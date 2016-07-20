
package x.y.z.bill.adapter.util;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReaderHelper {
    private static final Logger logger = LoggerFactory.getLogger(ReaderHelper.class);

    private static final int SIZE = 1024;

    private ReaderHelper() {
    }

    /**
     * 方法说明：<br> 从文件路径中获取Reader.默认字符: UTF-8
     * 
     * @param path 文件路径
     */
    public static BufferedReader getReader(String path) throws Exception {
        return getReader(path, "utf-8");
    }

    /**
     * 方法说明：<br> 从文件路径中获取Reader
     * 
     * @param path 文件路径
     * @param encoding 字符编码
     */
    public static BufferedReader getReader(String path, String encoding) throws Exception {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
    }

    /**
     * 方法说明：<br> 从文件中获取Reader.默认字符: UTF-8
     * 
     * @param file 文件
     */
    public static BufferedReader getReader(File file) throws Exception {
        return getReader(file, "utf-8");
    }

    /**
     * 方法说明：<br> 从文件中获取Reader
     * 
     * @param file 文件
     * @param encoding 字符编码
     */
    public static BufferedReader getReader(File file, String encoding) throws Exception {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
    }

    /**
     * 方法说明：<br> 从输入流中获取Reader.默认字符: UTF-8
     * 
     * @param is 输入流
     */
    public static BufferedReader getReader(InputStream is) throws Exception {
        return getReader(is, "utf-8");
    }

    /**
     * 方法说明：<br> 从输入流中获取Reader
     * 
     * @param is 输入流
     * @param encoding 字符编码
     */
    public static BufferedReader getReader(InputStream is, String encoding) throws Exception {
        return new BufferedReader(new InputStreamReader(is, encoding));
    }

    /**
     * 方法说明：<br> 从输入流中读取文本字符.默认字符: UTF-8
     * 
     * @param is 输入流
     */
    public static String getPlain(InputStream is) throws Exception {
        return getPlain(is, "utf-8");
    }

    /**
     * 方法说明：<br> 从输入流中读取文本字符
     * 
     * @param is 输入流
     * @param encoding 字符编码
     */
    public static String getPlain(InputStream is, String encoding) throws Exception {
        BufferedReader reader = null;
        StringBuilder meta = new StringBuilder(SIZE);
        try {
            reader = getReader(is, encoding);
            String line = null;
            while (null != (line = reader.readLine())) {
                meta.append(line);
            }
        } finally {
            close(reader);
        }
        return meta.toString();
    }

    /**
     * 方法说明：<br> 从文件路径中读取文本字符.默认字符: UTF-8
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static String getPlain(String path) throws Exception {
        return getPlain(path, "utf-8");
    }

    /**
     * 方法说明：<br> 从文件路径中读取文本字符
     * 
     * @param path
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String getPlain(String path, String encoding) throws Exception {
        String line = null;
        BufferedReader reader = null;
        StringBuilder xml = new StringBuilder(SIZE);
        try {
            reader = getReader(path, encoding);
            while (null != (line = reader.readLine())) {
                xml.append(line);
            }
        } finally {
            close(reader);
        }
        return xml.toString();
    }

    public static void close(Reader reader) {
        try {
            if (null != reader) {
                reader.close();
            }
        } catch (Exception e) {
            logger.error("关闭Reader异常", e);
        }
    }

}
