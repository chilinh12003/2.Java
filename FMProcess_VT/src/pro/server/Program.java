package pro.server;

//import java.io.BufferedReader;

import java.io.File;
import java.util.List;
import java.util.Vector;

import pro.check.BeginSession;
import pro.check.CheckFinishDay;
import pro.check.CheckFinishSession;
import pro.check.CheckPushMT;
import db.DefineMt;
import db.HibernateSessionFactory;
import db.Moqueue;
import db.Mtqueue;
import uti.*;

public class Program extends Thread
{
	static
	{
		String currentPath = System.getProperty("user.dir");
		File mFile = new File(currentPath + "/log4j.properties");
		
		LocalConfig.LogConfigPath = mFile.getAbsolutePath();
		
		LocalConfig.LogDataFolder = currentPath + "/LogFile/";
		mFile = new File(currentPath + "/config.properties");
		LocalConfig.ConfigPath = mFile.getAbsolutePath();
		
		mFile = new File(currentPath + "/hibernate.cfg.xml");
		HibernateSessionFactory.ConfigPath = mFile.getAbsolutePath();
		HibernateSessionFactory.init();
	}
	
	static MyLogger mLog = null;
	public static boolean getData = true;
	public static boolean processData = true;

	public static boolean isAllThreadStarted = false;
	public static LoadKeyword mLoadKeyword = null;

	public static MyQueue queueMO = new MyQueue();
	public static MyQueue queueMTRetry = new MyQueue();

	// Chứa các thead đang pushmt. nếu trường hợp đang pushmt mà tắt process thì
	// sau khi start lại process các PushMT này phải được chạy tiếp
	public static MyQueue queuePushMT = new MyQueue();

	public static LoadMO[] loadMO = null;
	public static LoadMT[] loadMT = null;

	public static ExecuteQueue[] executeQueue = new ExecuteQueue[20];

	// cac khai bao them
	/**
	 * Danh sách các MT đã được định nghĩa (được lấy trong DB khi chươn trinh
	 * bắt đầu chạy
	 */
	public static Vector<DefineMt> listDefineMT = new Vector<DefineMt>();

	public Program()
	{
		try
		{

			LocalConfig.loadProperties(LocalConfig.ConfigPath);
			mLog = new MyLogger(LocalConfig.LogConfigPath, Program.class.toString());

			executeQueue = new ExecuteQueue[LocalConfig.NUM_THREAD_EXECUTEQUEUE];
			loadMO = new LoadMO[LocalConfig.NUM_THREAD_LOAD_MO];
			loadMT = new LoadMT[LocalConfig.NUM_THREAD_LOAD_MT];

			// Lấy DefineMT trong DB
			DefineMt mDefineMT = new DefineMt();

			@SuppressWarnings("unchecked")
			List<DefineMt> Temp_List = (List<DefineMt>) mDefineMT.Get();
			for (DefineMt item : Temp_List)
				listDefineMT.add(item);

			Init();

		}
		catch (Exception e)
		{
			mLog.log.error(e);
		}
	}

	private void Init() throws Exception
	{
		System.out.println("Loading...");

		// Tạo đối tượng lấy dữ liệu trong table Keyword_Config
		// Mỗi 1 phút lại lấy 1 lần
		mLoadKeyword = new LoadKeyword();
		mLoadKeyword.setPriority(Thread.MAX_PRIORITY);
		mLoadKeyword.start();

		while (!mLoadKeyword.isLoaded)
		{
			try
			{
				sleep(50);
				System.out.print(".");
			}
			catch (InterruptedException e)
			{
				mLog.log.error(e);
			}
		}
		mLog.log.debug("Loaded.");

		mLog.log.debug("Start: LoadMO");

		// Tạo các thread loadMO

		for (int j = 0; j < loadMO.length; j++)
		{

			loadMO[j] = new LoadMO(queueMO, loadMO.length, j);
			loadMO[j].setPriority(Thread.MAX_PRIORITY);
			loadMO[j].start();
		}

		// Lấy từ MTqueue gửi sang telco
		for (int j = 0; j < loadMT.length; j++)
		{
			loadMT[j] = new LoadMT(queueMTRetry, loadMT.length, j, LocalConfig.LOADMT_ROWCOUNT);
			loadMT[j].setPriority(Thread.MAX_PRIORITY);
			loadMT[j].start();
		}

		mLog.log.debug("Start: ExecuteQueue");
		for (int i = 0; i < executeQueue.length; i++)
		{
			executeQueue[i] = new ExecuteQueue(queueMO, i);
			executeQueue[i].setPriority(Thread.MAX_PRIORITY);
			executeQueue[i].start();
		}

		isAllThreadStarted = true;

		CheckFinishSession mCheckFinishSession = new CheckFinishSession();
		mCheckFinishSession.setPriority(MAX_PRIORITY);
		mCheckFinishSession.start();

		CheckFinishDay mCheckFinishDay = new CheckFinishDay();
		mCheckFinishDay.setPriority(MAX_PRIORITY);
		mCheckFinishDay.start();

		CheckPushMT mCheckPushMT = new CheckPushMT();
		mCheckPushMT.setPriority(MAX_PRIORITY);
		mCheckPushMT.start();

		RetrySendMT mRetrySendMT = new RetrySendMT(queueMTRetry);
		mRetrySendMT.setPriority(Thread.MAX_PRIORITY);
		mRetrySendMT.start();
		
		BeginSession mBeginSession = new BeginSession();
		mBeginSession.setPriority(Thread.MAX_PRIORITY);
		mBeginSession.start();
	}

	public void windowClosing()
	{
		int nCount = 0;
		getData = false;
		processData = false;

		System.out.print("\nWaiting .....");
		mLog.log.info("\nWaiting .....");

		try
		{
			Thread.sleep(500);

		}
		catch (InterruptedException ex)
		{
			System.out.println(ex.toString());
		}

		while ((queueMO.getSize() > 0) && nCount < 5)
		{
			nCount++;
			try
			{
				System.out.println("...Queue(" + queueMO.getSize() + ")");

				Thread.sleep(100);
			}
			catch (InterruptedException ex)
			{
				System.out.println(ex.toString());
			}
		}

		saveQueue();

		mLog.log.debug("Shutdown");

		System.out.print("\nExit");

	}

	public static void main(String[] args)
	{
		System.out.println("Starting ProcessServer");
		Program smsConsole = new Program();
		ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(smsConsole);
		Runtime.getRuntime().addShutdownHook(shutdownInterceptor);
		smsConsole.start();

	}

	public void run()
	{
		mLog.log.debug("Listen MO...............................................................");
	}

	public static void checkstatus_thread()
	{
		try
		{
			for (int i = 0; i < loadMO.length; i++)
			{

				if (loadMO[i].isInterrupted() || !loadMO[i].isAlive())
				{
					restartthread_loadmo(i);
				}
			}

			for (int i = 0; i < executeQueue.length; i++)
			{
				if (executeQueue[i].isInterrupted() || !executeQueue[i].isAlive())
				{
					restartThread_executeQueue(i);
				}
			}

		}
		catch (Exception e)
		{
			mLog.log.error(e.toString(), e);
		}

	}

	public static void restartThread_executeQueue(int i)
	{
		try
		{
			mLog.log.info("executequeue[" + i + "] is alive:" + executeQueue[i].isAlive() + "@executequeue[" + i
					+ "] is Interrupted:" + executeQueue[i].isInterrupted());
			mLog.log.info("restart executequeue[" + i + "]");

			executeQueue[i].join();

			executeQueue[i] = new ExecuteQueue(queueMO, i);
			executeQueue[i].setPriority(Thread.MAX_PRIORITY);
			executeQueue[i].start();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void restartthread_loadmo(int i)
	{
		try
		{
			mLog.log.info("loadMO[" + i + "] is alive:" + loadMO[i].isAlive() + "@loadMO[" + i + "] is Interrupted:"
					+ loadMO[i].isInterrupted());
			mLog.log.info("restart loadMO[" + i + "]");

			loadMO[i].join();
			loadMO[i] = new LoadMO(queueMO, loadMO.length, loadMO[i].threadIndex);
			loadMO[i].setPriority(Thread.MAX_PRIORITY);
			loadMO[i].start();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void saveQueue()
	{
		mLog.log.debug("Save MOQueue to file queueMO.dat");
		MyFile.SaveObjectToFile("queueMO.dat", queueMO.getVector());

		mLog.log.debug("Save MTQueueRetry to file queueMTRetry.dat");
		MyFile.SaveObjectToFile("queueMTRetry.dat", queueMTRetry.getVector());
	}

	void loadQueue()
	{

		mLog.log.debug("Load MOQueue from file queueMO.dat");
		List<Moqueue> mListMO = MyFile.LoadObjectFromFile("queueMO.dat");
		for (Moqueue item : mListMO)
		{
			queueMO.add(item);
		}
		mLog.log.debug("Load queueMTRetry from file queueMTRetry.dat");
		List<Mtqueue> mListMT = MyFile.LoadObjectFromFile("queueMTRetry.dat");
		for (Mtqueue item : mListMT)
		{
			queueMTRetry.add(item);
		}

	}
	/*
	 * Lấy MT đã được định nghĩa trong DB, nếu ko có thì lấy MT mặc định
	 * 
	 * @param mMTType
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public static String GetDefineMT_Message(DefineMt.MTType mMTType) throws Exception
	{
		try
		{
			String MT = DefineMt.getMTContent(Program.listDefineMT, mMTType);
			MT = MyText.RemoveSpecialLetter(2, MT, ".,;?:-_/[]{}()@!%&*=+ ");
			return MT;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static String GetDefineMT_Message(DefineMt.MTType mMTType, String FreeTime) throws Exception
	{
		try
		{
			String MT = DefineMt.getMTContent(Program.listDefineMT, mMTType);
			MT = MyText.RemoveSpecialLetter(2, MT, ".,;?:-_/[]{}()@!%&*=+ ");
			MT = MT.replace("[FreeTime]", FreeTime);
			return MT;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
