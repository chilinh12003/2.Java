package th;

import java.util.List;
import java.util.Random;

import db.Category;

public class PushMT extends Thread
{
	public int Pindex = 0;
	public PushMT()
	{

	}

	public void run()
	{

		try
		{
			for (int i = 0; i < 200; i++)
			{
				Random mRand = new Random();
				int Check = mRand.nextInt(100) % 3;
				if (Check == 0)
				{
					InsertCate();
				}
				else if (Check == 1)
				{
					Select();
				}
				else
				{
					SelectAndDelete();
				}

				Thread.sleep(mRand.nextInt(100));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	void InsertCate() throws Exception
	{
		Category mCate = new Category();

		String CateName = "Thread id" + ((Long) this.getId()).toString();
		mCate.setCateName(CateName);
		mCate.setIsActive((short) 1);
		mCate.setLevel(0);
		mCate.setDescription("");
		mCate.setPriority(0);

		mCate.Save(mCate);

		System.out.println("--1--Insert catename:" + CateName);
	}
	void Select() throws Exception
	{
		Category mCate = new Category();
		mCate = (Category) mCate.Get(5);

	}
	void SelectAndDelete() throws Exception
	{
		Category mCate = new Category();
		List<Category> mListCate = (List<Category>) mCate.Get();

		System.out.println("--2--select Cate Count:" + ((Integer) mListCate.size()).toString());

		if (mListCate.size() > 1)
		{
			Random mRand = new Random();

			int deleteCount = mRand.nextInt(mListCate.size() - 1);

			for (Category mItem : mListCate)
			{
				if (deleteCount-- <= 0)
					break;
				String CateName = mItem.getCateName();
				mItem.Delete();
				System.out.println("--3--Delete catename:" + CateName);
			}

		}

	}
}
