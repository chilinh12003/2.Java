package my.base.cms.control.data;
import java.lang.reflect.Method;
import java.util.List;

import my.base.cms.control.controlBase;
import my.base.cms.control.input.inputRadio;

public class select extends controlBase
{
	String className;
	String idMethod;
	String textMethod;

	String selectedValue;

	List<?> listObj;

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getIdMethod()
	{
		return idMethod;
	}

	public void setIdMethod(String idMethod)
	{
		this.idMethod = idMethod;
	}

	public String getTextMethod()
	{
		return textMethod;
	}

	public void setTextMethod(String textMethod)
	{
		this.textMethod = textMethod;
	}

	public String getSelectedValue()
	{
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue)
	{
		this.selectedValue = selectedValue;
	}

	public List<?> getListObj()
	{
		return listObj;
	}

	public void setListObj(List<?> listObj)
	{
		this.listObj = listObj;
	}
	/**
	 * 
	 * @param id
	 * @param listObj
	 *            : danh sách đối tượng cần load lên select
	 * @param className
	 *            : Tên class của đối tượng VD: my.com.org.subscriber
	 * @param idMethod
	 *            : tên hàm lấy id của đối tượng
	 * @param textMethod
	 *            : tên hàm lấy text (name: tên) của đối tượng
	 */
	public select(String id, List<?> listObj, String className, String idMethod, String textMethod)
	{
		this.id = id;
		this.listObj = listObj;
		this.className = className;
		this.idMethod = idMethod;
		this.textMethod = textMethod;
		this.name = id;
	}

	@Override
	public String buildHTML()
	{
		String html = "";
		try
		{
			if (listObj == null)
				return html;

			Class<?> cls = Class.forName(className);

			StringBuilder builderOption = new StringBuilder("");
			for (Object obj : listObj)
			{
				// call the printIt method
				Method metID = cls.getDeclaredMethod(idMethod);
				Method metText = cls.getDeclaredMethod(textMethod);
				Object idValue = metID.invoke(obj);
				Object textValue = metText.invoke(obj);

				if (selectedValue != null
						&& (selectedValue.equalsIgnoreCase(idValue.toString()) || selectedValue
								.equalsIgnoreCase(textValue.toString())))
				{
					builderOption.append("<option value=\"" + idValue + "\" selected >" + textValue + "</option>");
				}
				else
				{
					builderOption.append("<option value=\"" + idValue + "\">" + textValue + "</option>");
				}
			}

			return "<select id=\"" + id + "\"  name=\"" + name + "\" class=\"mar-right-5  " + classCSS + "\" "
					+ otherAttribute + " >" + builderOption.toString() + "</select>";

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return html;
		}

	}

	/**
	 * lấy lại dữ liệu trước khi submit
	 */
	void regetValue()
	{
		if (currentCMSPage == null)
			return;

		String temp = currentCMSPage.request.getParameter(this.name);

		if (temp != null)
		{
			this.selectedValue = temp;
		}

	}

	@Override
	protected void begin()
	{
		regetValue();
	}

	@Override
	protected void end()
	{
		// TODO Auto-generated method stub

	}

}
