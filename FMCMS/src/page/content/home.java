package page.content;

import java.util.List;
import java.util.Vector;

import page.common.configWeb;
import db.DefineMt;
import db.Member;
import db.Page;
import load.fix.LFooter;
import load.fix.LGridData;
import load.fix.LHeader;
import load.fix.LMenu;
import load.fix.LSearchBox;
import load.fix.LToolBox;
import my.base.cms.*;
import my.base.cms.control.controlBase;
import my.base.cms.control.button.buttonActive;
import my.base.cms.control.button.buttonAdd;
import my.base.cms.control.button.buttonBack;
import my.base.cms.control.button.buttonBase;
import my.base.cms.control.button.buttonDeactive;
import my.base.cms.control.button.buttonDelete;
import my.base.cms.control.button.buttonEdit;
import my.base.cms.control.button.buttonSearch;
import my.base.cms.control.data.gridView;
import my.base.cms.control.data.select;
import my.base.cms.control.input.inputCheckbox;
import my.base.cms.control.input.inputRadio;
import my.base.cms.control.input.inputText;

public class home extends cmsBase
{

	buttonAdd btnAdd = new buttonAdd();
	buttonEdit btnEdit = new buttonEdit();
	buttonDelete btnDelete = new buttonDelete();
	buttonActive btnActive = new buttonActive();
	buttonDeactive btnDeactive = new buttonDeactive();
	buttonBack btnBack = new buttonBack();

	inputText tbxSearch = new inputText("tbxSearch", "");
	inputRadio radBoth = new inputRadio("radBoth", "Cả hai", "active", "2");
	inputRadio radActive = new inputRadio("radActive", "Kích hoạt", "active", "1");
	inputRadio radDeactive = new inputRadio("radDeactive", "Hủy kích hoạt", "active", "0");

	inputCheckbox chkActive = new inputCheckbox("chkActive", "Kích hoạt", "chkActive", "1");
	inputCheckbox chkDeactive = new inputCheckbox("chkDeactive", "Hủy kích hoạt", "chkDeactive", "0");

	buttonSearch btnSearch = new buttonSearch();

	select selPage = new select("selPage", null, Page.class.getName(), "getPageId", "getPageName");

	gridView gridData;

	public void buildAndWriteHTML()
	{
		try
		{
			Member memberDB = new Member();
			try
			{
				Member memObj = (Member) memberDB.Get(1);
				configWeb.setLoginedUser(this, memObj);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			LHeader mHeader = new LHeader(this, this.getURL());
			write(mHeader.getHTML());

			LMenu mMenu = new LMenu(this);
			write(mMenu.getHTML());

			LToolBox mToolBox = new LToolBox(this, createButton());
			write(mToolBox.getHTML());

			LSearchBox mSearchBox = new LSearchBox(this, createControl());
			write(mSearchBox.getHTML());

			buildGridView();
			LGridData mGridData = new LGridData(this, gridData);
			write(mGridData.getHTML());

			LFooter mFooter = new LFooter(this);
			write(mFooter.getHTML());
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	void buildGridView()
	{
		String[] ListHeaderName = new String[]{"ID", "MTTypeID", "MTTypeName", "MT", "IsActive", "Order"};

		String[] ListMethod = new String[]{"getDefineMtid", "getMttypeId", "getMttypeName", "getMt", "getIsActive",
				"getOrderId"};
		gridData = new gridView(DefineMt.class.getName(), ListHeaderName, ListMethod, "getDefineMtid")
		{

			@Override
			public Integer getTotalRow()
			{
				return 49;
			}

			@Override
			public List<?> getData()
			{
				try
				{
					DefineMt defineMtDB = new DefineMt();
					return defineMtDB.Get(gridData.beginRow(), gridData.getPageSize());
				}
				catch (Exception ex)
				{
					this.mLog.log.error(ex);
				}
				return null;
			}
		};

		gridData.pageLink = "http://localhost:8080/FMCMS/home?i={0}";
		String tempIndex = this.request.getParameter("i");
		if (tempIndex != null && tempIndex.length() > 0)
		{
			gridData.currentPageIndex = Integer.parseInt(tempIndex);
		}
		else
		{
			gridData.currentPageIndex = 1;
		}
		gridData.setPageSize(10);
		gridData.setCurrentCMSPage(this);

	}
	List<controlBase> createControl() throws Exception
	{
		List<controlBase> listControl = new Vector<controlBase>();
		tbxSearch.setOtherAttribute("placeholder=\"Từ cần tìm\"");
		tbxSearch.setCurrentCMSPage(this);
		radActive.setCurrentCMSPage(this);
		radDeactive.setCurrentCMSPage(this);
		radBoth.setCurrentCMSPage(this);

		chkActive.setCurrentCMSPage(this);
		chkDeactive.setCurrentCMSPage(this);

		radBoth.setChecked("checked");
		listControl.add(tbxSearch);
		listControl.add(radActive);
		listControl.add(radDeactive);
		listControl.add(radBoth);

		chkActive.setChecked("checked");
		chkDeactive.setChecked("checked");

		listControl.add(chkActive);
		listControl.add(chkDeactive);

		List<Page> listPage = (List<Page>) (new Page()).Get();

		selPage.setCurrentCMSPage(this);
		selPage.setListObj(listPage);
		if (!this.getLoadedPage())
		{
			selPage.setSelectedValue("3");
		}
		listControl.add(selPage);

		listControl.add(btnSearch);
		return listControl;

	}
	List<buttonBase> createButton()
	{
		List<buttonBase> listButton = new Vector<buttonBase>();
		btnAdd.setVisible(false);

		listButton.add(btnAdd);
		listButton.add(btnEdit);
		listButton.add(btnDelete);
		listButton.add(btnActive);
		listButton.add(btnDeactive);
		listButton.add(btnBack);
		return listButton;

	}

}
