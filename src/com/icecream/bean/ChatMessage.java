package com.icecream.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 主要作用用于保存消息，记录。并无太多其它的操作
 */
public class ChatMessage
{

	/**
	 * 消息的类型
	 */
	private Type type ;
	/**
	 * 用于保存记录聊天消息的内容
	 */
	private String msg;
	/**
	 * 用于保存消息的发送或接受的具体时间
	 */
	private Date date;
	/**
	 * 用于保存消息的发送或接受的具体时间的字符串形式（便于显示，直接使用）
	 */
	private String dateStr;
	/**
	 * 用于保存消息发送者的名字
	 */
	private String name;

	
	/**
	 * 通过类型记录消息的类别，用于显示的需要（左侧或是右侧）
	 */
	public enum Type
	{
		INPUT, OUTPUT
	}

	public ChatMessage()
	{
		
	}

	public ChatMessage(Type type, String msg)
	{
		super();
		this.type = type;
		this.msg = msg;
		setDate(new Date());
	}

	public String getDateStr()
	{
		return dateStr;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.dateStr = df.format(date);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

}
