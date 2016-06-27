package pro.server;

import uti.MyLogger;

/**
 * Chứa các đối tượng đã table mẫu ở Database Các đối tượng này sẽ được load khi
 * chương trình bắt đầu chạy
 * 
 * @author Administrator
 * 
 */
public class CurrentData
{
	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, Program.class.toString());
}
