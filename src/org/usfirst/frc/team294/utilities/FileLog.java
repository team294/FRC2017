package org.usfirst.frc.team294.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileLog {
	private FileWriter fileStream;
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * Creates a new log file called "/home/lvuser/logfile.date.time.txt"
	 */
	public FileLog() {
		this("/home/lvuser/logfile");
	}
	
	/**
	 * Creates a new log file.
	 * @param filename Path and name of log file.  ".date.time.txt" will automatically be added to the end of the file name.
	 */
	public FileLog(String filename) {
		final SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss");

		try {
			fileStream = new FileWriter(filename + "." + fileDateFormat.format(System.currentTimeMillis()) + ".txt", true);
			fileStream.write("----------------------------\n");
			fileStream.write(format.format(System.currentTimeMillis()) + ":  FileLog open.\n");
			fileStream.flush();
		} catch (IOException e) {
			System.out.println("Could not open log file: " + e);
		}
	}
	
	/**
	 * Writes a message to the log file.  The message will be timestamped.  Does not echo the message to the screen.
	 * @param msg
	 */
	public void writeLog(String msg) {
		if (fileStream == null) return;
		try {
			fileStream.write(format.format(System.currentTimeMillis()) + ": " + msg + "\n");
			fileStream.flush();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Writes a message to the log file.  The message will be timestamped.  Also echos the message to the screen.
	 * @param msg
	 */
	public void writeLogEcho(String msg) {
		writeLog(msg);
		System.out.println(msg);
	}
	
	/**
	 * Closes the log file.  All writes after closing the log file will be ignored.
	 */
	public void close() {
		try {
			fileStream.close();
			fileStream = null;
		} catch (IOException e) {
		}
	}
}
