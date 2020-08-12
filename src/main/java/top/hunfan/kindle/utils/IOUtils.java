package top.hunfan.kindle.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;

import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 * Provide some I/O operations.
 * @author hefan
 * @date 2019/1/30 15:47
 */
public final class IOUtils {

	private static ClassLoader classLoader = IOUtils.class.getClassLoader();

	private static final String UTF_8_CHATSET = "UTF-8";

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	static {
		HttpsUtils.doTrustAllHttpsCertificates();
	}

	private IOUtils() {
	}

	/**
	 * 读文件
	 * 
	 * @param filepath
	 *            文件路径
	 * @param charset
	 *            字符编码
	 * @param bufferSize
	 *            buffer
	 * @return 文件内容
	 */
	public static String read(String filepath, String charset, int bufferSize) {
		File file = new File(filepath);
		return read(file, charset, bufferSize);
	}

	/**
	 * 读文件
	 * 
	 * @param filepath
	 *            文件路径
	 * @param charset
	 *            字符编码
	 * @return 文件内容
	 */
	public static String read(String filepath, String charset) {
		File file = new File(filepath);
		return read(file, charset, DEFAULT_BUFFER_SIZE);
	}

    /**
     * 读取资源文件
     * @param filepath
     *              文件路径
     * @return
     */
	public static String readResource(String filepath) {
		InputStream inputStream = classLoader.getResourceAsStream(filepath);
		InputStreamReader inputStreamReader = null;

		try {
			inputStreamReader = new InputStreamReader(inputStream, UTF_8_CHATSET);

			int length = 0;
			char[] buffer = new char[DEFAULT_BUFFER_SIZE];
			StringBuilder result = new StringBuilder();

			while ((length = inputStreamReader.read(buffer, 0, DEFAULT_BUFFER_SIZE)) > -1) {
				result.append(buffer, 0, length);
			}

			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(inputStreamReader);
			close(inputStream);
		}
	}

	/**
	 * 读文件
	 * 
	 * @param file
	 *            文件
	 * @param charset
	 *            字符编码
	 * @return String 文件内容
	 */
	public static String read(File file, String charset, int bufferSize) {
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;

		try {
			inputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(inputStream, charset);

			int length = 0;
			char[] buffer = new char[bufferSize];
			StringBuilder result = new StringBuilder();

			while ((length = inputStreamReader.read(buffer, 0, bufferSize)) > -1) {
				result.append(buffer, 0, length);
			}

			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(inputStreamReader);
			close(inputStream);
		}
	}

	/**
	 * 逐行读取文件
	 * 
	 * @param stream
	 *            文件流{@link InputStream}
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] readLine(InputStream stream) throws IOException {
		int b = -1;
		ByteArrayOutputStream bos = new ByteArrayOutputStream(4096);

		while ((b = stream.read()) != -1) {
			String ls = SeparatorUtils.getLineSeparator();
			if (ls.equals("\r\n") || ls.equals("\n")) {// WIN & UNIX-like
				if (b == '\n') {
					bos.write(b);
					break;
				}
			} else {// MAC
				if (b == '\r') {
					bos.write(b);
					break;
				}
			}

			bos.write(b);
		}

		return bos.toByteArray();
	}

	/**
	 * 
	 * 读取文件
	 * 
	 * @param input
	 *            文件流
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] read(InputStream input) throws IOException {
		return org.apache.commons.io.IOUtils.toByteArray(input);
	}

	/**
     * 读文件
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] read(File file) throws IOException {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
			return read(fs);
		} finally {
			close(fs);
		}
	}

	/**
     * 读文件
	 * @param filepath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] read(String filepath) throws IOException {
		File file = new File(filepath);
		return read(file);
	}

	/**
	 * 向文件写入内容，如果文件不存在则创建
	 * 
	 * @param file
	 * @param content
	 * @param charset
	 *            - 字符编码
	 * @param append
	 *            - 如果为true则追加在文件的后面，否则覆盖整个文件。
	 * @throws IOException
	 */
	public static void write(File file, String content, String charset, boolean append) throws IOException {

		FileWriterWithEncoding fileWriter = null;
		try {

			fileWriter = new FileWriterWithEncoding(file, charset, append);
			fileWriter.write(content);

		} finally {
			close(fileWriter);
		}
	}

	/**
     * 写文件
	 * @param filepath
	 * @param content
	 * @param charset
	 * @param append
	 * @throws IOException
	 */
	public static void write(String filepath, String content, String charset, boolean append) throws IOException {
		File file = new File(filepath);
		write(file, content, charset, append);
	}

	/**
     * 写文件
	 * @param file
	 * @param content
	 * @param append
	 * @throws IOException
	 */
	public static void write(File file, String content, boolean append) throws IOException {

		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(file, append);
			fileWriter.write(content);
		} finally {
			close(fileWriter);
		}
	}

	/**
     * 写文件
	 * @param filepath
	 * @param content
	 * @param append
	 * @throws IOException
	 */
	public static void write(String filepath, String content, boolean append) throws IOException {
		File file = new File(filepath);
		write(file, content, append);
	}

	/**
	 * 
	 * 向文件末尾追加一行内容。
	 * 
	 * @param file
	 * @param line
	 * @param charset
	 * @throws IOException
	 */
	public static void writeLine(File file, String line, String charset) throws IOException {
		write(file, SeparatorUtils.getLineSeparator() + line, charset, true);
	}

	/**
     * 写一行
	 * @param filepath
	 * @param line
	 * @param charset
	 * @throws IOException
	 */
	public static void writeLine(String filepath, String line, String charset) throws IOException {
		write(filepath, SeparatorUtils.getLineSeparator() + line, charset, true);
	}

	/**
	 * 
	 * 向文件末尾追加一行内容。
	 * 
	 * @param file
	 * @param line
	 * @param charset
	 * @throws IOException
	 */
	public static void writeLine(File file, String line) throws IOException {
		write(file, SeparatorUtils.getLineSeparator() + line, true);
	}

	/**
     * 写一行
	 * @param filepath
	 * @param line
	 * @throws IOException
	 */
	public static void writeLine(String filepath, String line) throws IOException {
		write(filepath, SeparatorUtils.getLineSeparator() + line, true);
	}

	/**
	 * 将输入的字符串转换为文件输出，默认为UTF-8编码
	 * 
	 * @param input
	 *            输入字符串
	 * @param file
	 *            输出文件
	 * @throws IOException
	 */
	public static void write(String input, File file) {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			write(input, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(os);
		}

	}

    /**
     * 写文件
     * @param input
     * @param filePath
     */
	public static void write(String input, String filePath) {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(new File(filePath));
			write(input, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(os);
		}

	}

	/**
	 * 将输入的字符串转换为输出流，默认为UTF-8编码
     *
	 * @param input
	 *            输入字符串
	 * @param outputStream
	 *            {@link OutputStream}
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void write(String input, OutputStream outputStream) throws IOException {
		write(input, outputStream, "UTF-8");
	}

	/**
	 * 将输入的字符串转换为输出流
     *
	 * @param input
	 *            {@link InputStream}
	 * @param outputStream
	 *            {@link OutputStream}
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void write(String input, OutputStream outputStream, String encoding)
			throws IOException {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(input.getBytes(encoding));
			write(bais, outputStream);
		} finally {
			close(bais);
		}

	}

	/**
	 * 将IO输入流写入输出流
     *
	 * @param inputStream
	 *            {@link InputStream}
	 * @param outputStream
	 *            {@link OutputStream}
	 * @throws IOException
	 */
	public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
		org.apache.commons.io.IOUtils.write(read(inputStream), outputStream);
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static OutputStream openOutputStream(File file) throws IOException {
		return new FileOutputStream(file);
	}

	/**
	 * @param f1
	 * @param f2
	 * @throws IOException
	 */
	public static void copy(String f1, String f2) throws IOException {
		copy(new File(f1), new File(f2), true);
	}

	/**
	 * @param f1
	 * @param f2
	 * @param b
	 * @throws IOException
	 */
	public static boolean copy(File f1, File f2, boolean b) throws IOException {
		if (f1 == null) {
			throw new NullPointerException("Source must not be null");
		}

		if (f2 == null) {
			throw new NullPointerException("Destination must not be null");
		}

		if (!f1.exists() || !f1.isFile()) {
			throw new IOException(f1.getAbsolutePath() + " must be a file !");
		}

		if (f1.getCanonicalPath().equals(f2.getCanonicalPath())) {
			throw new IOException("Source '" + f1 + "' and destination '" + f2 + "' are the same");
		}

		if (f2.getParentFile() != null && !f2.getParentFile().exists() && !f2.getParentFile().mkdirs()) {
			throw new IOException("Destination '" + f2 + "' directory cannot be created");
		}

		if (f2.exists() && !f2.canWrite()) {
			throw new IOException("Destination '" + f2 + "' exists but is read-only");
		}

		FileInputStream fin = null;
		FileOutputStream fos = null;

		try {
			fin = new FileInputStream(f1.getAbsolutePath());
			fos = new FileOutputStream(f2.getAbsolutePath());
			copy(fin, fos);
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
				}
			}

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}

		if (b) {
			return f2.setLastModified(f1.lastModified());
		}

		return true;
	}

	/**
	 * @param inputStream
	 * @param file
	 * @throws IOException
	 */
	public static void copy(InputStream inputStream, File file) throws IOException {
		if (inputStream != null) {
			OutputStream outputStream = null;

			try {
				outputStream = IOUtils.openOutputStream(file);

				IOUtils.copy(inputStream, outputStream);
			} finally {
				IOUtils.close(outputStream);
			}
		} else {
			throw new IOException("inputStream is null !");
		}
	}

	/**
	 * 
	 * @param file
	 * @param out
	 * @throws IOException
	 */
	public static void copy(File file, OutputStream out) throws IOException {
		FileInputStream fin = null;

		try {
			fin = new FileInputStream(file);

			copy(fin, out);
		} finally {
			close(fin);
		}
	}

	/**
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
		copy(inputStream, outputStream, 4096);
	}

	/**
	 * @param inputStream
	 * @param outputStream
	 * @param bufferSize
	 * @throws IOException
	 */
	public static void copy(InputStream inputStream, OutputStream outputStream, int bufferSize) throws IOException {
		int length = 0;
		byte[] bytes = new byte[Math.max(bufferSize, 4096)];

		while ((length = inputStream.read(bytes)) > -1) {
			outputStream.write(bytes, 0, length);
		}

		outputStream.flush();
	}

	/**
	 * @param inputStream
	 * @param outputStream
	 * @param bufferSize
	 * @param size
	 * @throws IOException
	 */
	public static void copy(InputStream inputStream, OutputStream outputStream, int bufferSize, long size)
			throws IOException {
		if (size > 0) {
			int readBytes = 0;
			long count = size;
			int length = Math.min(bufferSize, (int) (size));
			byte[] buffer = new byte[length];

			while (count > 0) {
				if (count > length) {
					readBytes = inputStream.read(buffer, 0, length);
				} else {
					readBytes = inputStream.read(buffer, 0, (int) count);
				}

				if (readBytes > 0) {
					outputStream.write(buffer, 0, readBytes);
					count -= readBytes;
				} else {
					break;
				}
			}

			outputStream.flush();
		}
	}

	/**
	 * @param reader
	 * @param writer
	 * @throws IOException
	 */
	public static void copy(Reader reader, Writer writer) throws IOException {
		copy(reader, writer, 4096);
	}

	/**
	 * @param reader
	 * @param writer
	 * @param bufferSize
	 * @throws IOException
	 */
	public static void copy(Reader reader, Writer writer, int bufferSize) throws IOException {
		int len = 0;
		int buf = Math.max(bufferSize, 4096);
		char[] chars = new char[buf];

		while ((len = reader.read(chars)) > -1) {
			writer.write(chars, 0, len);
		}

		writer.flush();
	}

	/**
	 * @param inputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
		copy(inputStream, bos);
		return bos.toByteArray();
	}

	/**
	 * @param inputStream
	 * @return ByteArrayInputStream
	 * @throws IOException
	 */
	public static ByteArrayInputStream toByteArrayInputStream(InputStream inputStream) throws IOException {
		return new ByteArrayInputStream(toByteArray(inputStream));
	}

	/**
	 * @param resource
	 */
	public static void close(java.io.Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * InputStream
	 * 
	 * @param ins
	 *            - java.io.InputStream
	 * 
	 */
	public static void close(InputStream ins) {
		if (ins != null) {
			try {
				ins.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	/**
	 * OutputStream
	 * 
	 * @param out
	 *            java.io.OutputStream
	 * 
	 */
	public static void close(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	/**
	 * BufferedReader
	 * 
	 * @param buf
	 *            java.io.Reader
	 */
	public static void close(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	/**
	 * BufferedReader
	 * 
	 * @param writer
	 *            java.io.Reader
	 */
	public static void close(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	/**
	 * delete dir/file
	 */
	public static void delete(File dir){
	    if(dir.isDirectory()){
            String[] children = dir.list();
            for (String child : children) {
                delete(new File(dir, child));
            }
        }
        dir.delete();
    }

	/**
	 * download file
	 * @param url
	 * @param path
	 * @throws IOException
	 */
	public static void downloadFile(URL url, String path) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = url.openStream();
			os = new FileOutputStream(new File(path));
			IOUtils.write(is, os);
		} catch (Exception e){
			throw e;
		} finally {
			IOUtils.close(is);
			IOUtils.close(os);
		}
	}

	/**
	 * delete and create
	 * @param path
	 * @return
	 */
	public static File deleteAndCreate(String path){
		File file = new File(path);
		file.deleteOnExit();
		file.mkdirs();
		return file;
	}

}
