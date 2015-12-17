package uti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class MyFile
{

	public static Vector<File> GetAllFiles(File location, String fileExt)
	{
		Vector<File> mVector = new Vector<File>();
		File[] list = location.listFiles();
		if (list != null && list.length > 0)
		{
			for (int i = 0; i < list.length; i++)
			{
				if (list[i].toString().endsWith(fileExt))
				{
					mVector.addElement(list[i]);
				}

			}
		}
		return mVector;
	}

	public static void Copy(String source_name, String dest_name) throws IOException
	{
		try
		{
			File source_file = new File(source_name);
			File dest_file = new File(dest_name);
			Copy(source_file, dest_file);
		}
		catch (IOException ex)
		{
			throw ex;
		}
	}

	public static void Copy(File source_file, File dest_file) throws IOException
	{
		FileInputStream source = null;
		FileOutputStream destination = null;

		byte[] buffer;
		int bytes_read;

		// First make sure the specified source file
		// exists, is a file, and is readable.
		if (!source_file.exists() || !source_file.isFile())
			throw new IOException("FileCopy: no such source file: " + source_file.getPath());
		if (!source_file.canRead())
			throw new IOException("FileCopy: source file is unreadable: " + source_file.getPath());

		// If we've gotten this far, then everything is okay;
		// we can copy the file.
		source = new FileInputStream(source_file);
		destination = new FileOutputStream(dest_file);
		buffer = new byte[1024];
		while ((bytes_read = source.read(buffer)) != -1)
		{
			destination.write(buffer, 0, bytes_read);
		}
		// No matter what happens, always close any streams we've opened.
		try
		{
			if (source != null)
				source.close();
			if (destination != null)
				destination.close();
		}
		catch (IOException ex)
		{
			throw ex;
		}
	}

	public static String readFile(String fileName) throws IOException
	{

		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);
		String line;
		StringBuilder mBuilder = new StringBuilder("");

		while ((line = br.readLine()) != null)
		{
			mBuilder.append(line);
		}
		br.close();
		return mBuilder.toString();
	}

	public static boolean existFile(String fullPath)
	{
		try
		{
			File file = new File(fullPath);
			return file.exists();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Kiểm tra file là lấy đường dẫn chính xác của file đó. tránh trường hợp
	 * nhầm lần giữa / và \
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String ConvertPath(String fullPath)
	{
		try
		{
			File file = new File(fullPath);
			if (file.exists())
				return file.getAbsolutePath();
			else return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static void WriteToFile(String FileName, String Content)
	{
		BufferedWriter bufferedWriter = null;

		try
		{
			// Construct the BufferedWriter object
			bufferedWriter = new BufferedWriter(new FileWriter(FileName, true));

			// Start writing to the output stream
			bufferedWriter.write(Content);
			bufferedWriter.newLine();

		}
		catch (FileNotFoundException ex)
		{
			System.out.print("WriteToFile Error:" + ex.getMessage());
		}
		catch (IOException ex)
		{
			System.out.print("WriteToFile Error:" + ex.getMessage());
		}
		finally
		{
			// Close the BufferedWriter
			try
			{
				if (bufferedWriter != null)
				{
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			}
			catch (IOException ex)
			{
				System.out.print("WriteToFile Error:" + ex.getMessage());
			}
		}

	}

	public static <T> List<T> LoadObjectFromFile(String fileName)
	{
		Vector<T> mList = new Vector<T>();

		boolean flag = true;
		FileInputStream fin = null;
		ObjectInputStream objIn = null;
		FileOutputStream fout = null;
		try
		{
			File mFile = new File(fileName);
			if (!mFile.exists())
				return mList;

			fin = new java.io.FileInputStream(fileName);

			if (fin.available() <= 0)
			{
				return mList;
			}

			objIn = new ObjectInputStream(fin);

			while (flag)
			{
				try
				{
					Object mObject = objIn.readObject();
					mList.add((T) mObject);
				}
				catch (Exception ex)
				{
					flag = false;
				}
			}
			return mList;

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (fin != null)
					fin.close();
				fout = new java.io.FileOutputStream(fileName, false);
				fout.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return mList;
	}

	public static <T> void SaveObjectToFile(String fileName, Vector<T> mList)
	{
		FileOutputStream fout = null;
		ObjectOutputStream objOut = null;

		try
		{
			fout = new java.io.FileOutputStream(fileName, false);
			objOut = new ObjectOutputStream(fout);

			for (Iterator<T> mItem = mList.listIterator(); mItem.hasNext();)
			{
				T mObject = mItem.next();
				objOut.writeObject(mObject);
				objOut.flush();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (objOut != null)
					objOut.close();
				if (fout != null)
					fout.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public static Vector<String> ReadFileToList(String FileName) throws Exception
	{
		InputStream fis;
		BufferedReader br = null;
		String line;
		Vector<String> mList = new Vector<String>();
		try
		{
			fis = new FileInputStream(FileName);
			br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null)
			{
				mList.add(line.trim());
			}
			br.close();
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			// Done with the file
			br = null;
			fis = null;
		}
		return mList;
	}

}
