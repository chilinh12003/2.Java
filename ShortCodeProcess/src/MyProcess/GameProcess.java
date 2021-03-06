package MyProcess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import db.define.MyTableModel;

import MyProcessServer.ContentAbstract;
import MyProcessServer.Keyword;
import MyProcessServer.LocalConfig;
import MyProcessServer.MsgObject;
import MyStore.Game;
import WS.GetLinkMedia.GetMedia;

import uti.utility.MyConvert;
import uti.utility.MyLogger;
import uti.utility.MyText;

public class GameProcess extends ContentAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	Game mGame;

	public GameProcess()
	{
		try
		{
			mGame = new Game(LocalConfig.mDBConfig_MSSQL_HBStore);
		}
		catch (Exception ex)
		{
			mLog.log.error("Ringback_Error", ex);
		}
	}

	Collection<MsgObject> MessOject = new ArrayList<MsgObject>();

	@Override
	protected Collection<MsgObject> getMessages(MsgObject msgObject, Keyword keyword) throws Exception
	{

		String MTContent = "Tin nhan sai cu phap";
		try
		{
			String Info = msgObject.getUsertext().trim();
			String GameID = Info.substring(msgObject.getKeyword().length(), Info.length()).trim();

			String GameName = "";

			GameID = MyText.RemoveSpecialLetter(1, GameID);
			if (GameID == "")
			{
				MTContent = "Game dang bi khoa hoac khong ton tai tren he thong, xin vui long chon mot Game khac.";
				return AddToQueue(msgObject, MTContent, "");
			}

			MyTableModel mTable = mGame.Select(1, GameID);

			// Nếu bài hát không tồn tại trong hệ thống
			if (mTable.GetRowCount() < 1)
			{
				MTContent = "Game dang bi khoa hoac khong ton tai tren he thong, xin vui long chon mot game khac.";
				return AddToQueue(msgObject, MTContent, "");
			}

			GameName = mTable.GetValueAt(0, "GameName").toString();
			GameName = MyText.RemoveSignVietnameseString(GameName);

			WS.GetLinkMedia.GetMedia mGetMedia = new GetMedia();
			double Price = MyConvert.ShortCodeToPrice(msgObject.getServiceid());

			SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddhhmmss");

			Date mDate = (Date) Calendar.getInstance().getTime();

			String RequestTime = mFormat.format(mDate);
			RequestTime += "00";

			String Result = mGetMedia.getGetMediaSoap().getLinkMedia("6x83", "6x83sdadaffasasxa", msgObject.getUserid(), 1, GameID, 3, Price, RequestTime);

			String Link = "";
			if (!Result.startsWith("0"))
			{
				MTContent = "Game dang bi khoa hoac khong ton tai tren he thong, xin vui long chon mot game khac.";
				return AddToQueue(msgObject, MTContent, "");
			}
			Link = Result.split("\\|")[1];

			if (!Link.startsWith("http"))
			{
				MTContent = "Game dang bi khoa hoac khong ton tai tren he thong, xin vui long chon mot game khac.";

				return AddToQueue(msgObject, MTContent, "");
			}

			MTContent = "De tai Game " + GameName + " hay truy cap vao link:" + Link;

			msgObject.setUsertext(MTContent);
			msgObject.setContenttype(8);
			msgObject.setMsgtype(1);

		}
		catch (Exception ex)
		{
			mLog.log.error("Error", ex);
			MTContent = "Game dang bi khoa hoac khong ton tai tren he thong, xin vui long chon mot game khac.";
			msgObject.setUsertext(MTContent);
			msgObject.setContenttype(21);
			msgObject.setMsgtype(1);
		}

		MessOject.add(new MsgObject(msgObject));

		mLog.log.info("Game-->UserID:" + msgObject.getUserid() + "||MT:" + msgObject.getUsertext());

		return MessOject;

	}

	private Collection<MsgObject> AddToQueue(MsgObject msgObject, String MTContent, String MTPromo)
	{
		msgObject.setUsertext(MTContent + MTPromo);
		msgObject.setContenttype(21);
		msgObject.setMsgtype(1);

		MessOject.add(new MsgObject(msgObject));

		return MessOject;
	}

}
