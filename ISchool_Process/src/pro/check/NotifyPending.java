package pro.check;

import java.util.Calendar;
import java.util.List;

import pro.server.LocalConfig;
import pro.server.Program;
import uti.MyConfig;
import uti.MyDate;
import uti.MyLogger;
import uti.MySeccurity;
import db.DefineMt;
import db.Mtqueue;
import db.Subscriber;
import db.DefineMt.MTType;

/**
 * Thead này dùng để push tin thông báo cho khách hàng nếu thuê bao gia hạn
 * không thành công trong lần gia hạn thứ 3 trong ngày. Trả tin gia hạn không
 * thành công: <br>
 * Trả 1 lần duy nhất trong ngày đầu tiên charge kg thành công, sau khung giờ
 * charge thứ 3 trong ngày.
 * 
 * @author Administrator
 *
 */
public class NotifyPending extends Thread
{

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	// Cho biết thread đang push tin xuống KH;

	public boolean isPushing = false;
	public NotifyPending()
	{
	}
	public void run()
	{
		while (Program.processData)
		{
			try
			{
				Calendar calCurrent = Calendar.getInstance();
				// Vì lần charge thứ 3 của dịch vụ là 19h nên thread sẽ chạy vào
				// 20h hàng ngày
				if (calCurrent.get(Calendar.HOUR_OF_DAY) == LocalConfig.NOTIFY_PENDING_HOUR && calCurrent.get(Calendar.MINUTE) == 17 && !isPushing)
				{
					isPushing = true;

					String MT = Program.GetDefineMT_Message(MTType.NotifyRenewFail);

					if (MT != null && MT.length() > 0)
					{

						Subscriber subDB = new Subscriber();
						List<Subscriber> mList;

						Calendar calExpiryDate = Calendar.getInstance();

						calExpiryDate.set(Calendar.MILLISECOND, 0);
						calExpiryDate.set(calCurrent.get(Calendar.YEAR), calCurrent.get(Calendar.MONTH),
								calCurrent.get(Calendar.DATE), 23, 59, 59);

						calExpiryDate.add(Calendar.DATE, -1);

						int MaxOrderID = 0;
						int RowCount = 10;
						String PhoneNumber = "";

						try
						{
							for (short PID = 0; PID <= LocalConfig.MAX_PID; PID++)
							{
								// Nếu bị dừng đột ngột
								if (!Program.processData)
								{
									mLog.log.debug("Bi dung PushMT NotifyRenewFail: PushMT Info:"
											+ MyLogger.GetLog(this));
									return;
								}
								
								MaxOrderID =0;
								
								mList = subDB.getSubPending(PID, MaxOrderID, Subscriber.Status.Pending, RowCount,
										calExpiryDate);

								while (Program.processData && mList != null && mList.size() > 0)
								{
									for (Subscriber mSubObj : mList)
									{
										// Nếu bị dừng đột ngột
										if (!Program.processData)
										{
											mLog.log.debug("Bi dung PushMT NotifyRenewFail: PushMT Info:"
													+ MyLogger.GetLog(this));
											return;
										}
										MaxOrderID = mSubObj.getOrderId();
										PhoneNumber = mSubObj.getId().getPhoneNumber();
										sendMT(mSubObj,DefineMt.MTType.NotifyRenewFail, MT);

									}

									mList = subDB.getSubPending(PID, MaxOrderID, Subscriber.Status.Pending, RowCount,
											calExpiryDate);
								}
							}
						}
						catch (Exception ex)
						{
							mLog.log.error(ex);
						}
						finally
						{
							// để đảm báo thread chỉ chạy 1 lần trong ngày. thì cho
							// sleep hơn 1h
							Thread.sleep(61 * 60 * 1000);
						}
					}
					else
					{
						mLog.log.warn("MT khong ton tai de push tin NotifyRenewFail");
					}
				}

				Thread.sleep(60 * 1000);
				isPushing = false;
			}
			catch (Exception ex)
			{
				isPushing = false;
				mLog.log.error(ex);
			}
		}
	}


	private void sendMT(Subscriber mSubObj, MTType mMTTypeID, String MT)
	{
		Mtqueue mtqueueObj = new Mtqueue();
		try
		{
			mtqueueObj.setChannelId(MyConfig.ChannelType.SYSTEM.GetValue().shortValue());
			mtqueueObj.setContentTypeId(Mtqueue.ContentType.LongMessage.GetValue());
			mtqueueObj.setMt(MT);
			mtqueueObj.setMtInsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			mtqueueObj.setMttypeId(mMTTypeID.GetValue());
			mtqueueObj.setPhoneNumber(mSubObj.getId().getPhoneNumber());
			mtqueueObj.setPid(mSubObj.getId().getPid());
			mtqueueObj.setRequestId(MySeccurity.GenUniqueueID());
			mtqueueObj.setRetryCount((short) 0);
			mtqueueObj.setSendTypeID(Mtqueue.SendType.SendToUser.GetValue());
			mtqueueObj.setShoreCode(LocalConfig.SHORT_CODE);
			mtqueueObj.setStatusId(Mtqueue.Status.WaitingSendMT.GetValue());
			mtqueueObj.setTelcoId(MyConfig.Telco.VIETTEL.GetValue().shortValue());

			Integer TotalSesment = MT.length() / 160;
			if (MT.length() % 160 > 0)
				TotalSesment++;

			mtqueueObj.setTotalSegment(TotalSesment.shortValue());

			if (mtqueueObj.Save())
			{
				mLog.log.info("Pust MT Nofity Renew Fail OK -->" + MyLogger.GetLog(mtqueueObj));
			}
			else
			{
				mLog.log.info("Pust MT Fail -->" + MyLogger.GetLog(mtqueueObj));
				MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_PushMT_NotSend",
						"PUSH MT FAIL --> " + MyLogger.GetLog(mtqueueObj));
			}
		}
		catch (Exception ex)
		{
			mLog.log.error("Gui MT Nofity Renew Fail khong thanh cong:", ex);
			mLog.log.info(MyLogger.GetLog(mtqueueObj));
			mLog.log.info(MyLogger.GetLog(this));
		}
	}
}
