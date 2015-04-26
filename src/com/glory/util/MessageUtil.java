package com.glory.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glory.po.MediaMessage;
import com.glory.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT = "text";//文本
	public static final String MESSAGE_IMAGE = "image";//图片
	public static final String MESSAGE_VOICE = "voice";//声音
	public static final String MESSAGE_VIDEO = "video";//视频
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";//小视频
	public static final String MESSAGE_LOCATION = "location";//位置
	public static final String MESSAGE_LINK = "link";//链接
	public static final String MESSAGE_EVENT = "event";//时间
	public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";//关注事件
	public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";//取消关注事件
	public static final String MESSAGE_EVENT_LOCATION = "LOCATION";//位置
	public static final String MESSAGE_EVENT_SCAN = "SCAN";//上报地理位置事件
	public static final String MESSAGE_EVENT_CLICK = "CLICK";//自定义菜单事件
	public static final String MESSAGE_EVENT_VIEW = "VIEW";//点击菜单跳转链接时的事件推送
	
	
	/**
	 * 将xml格式的字符串转为Map
	 * @param req
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static LinkedHashMap<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException {
		LinkedHashMap<String,String> returnMap = new LinkedHashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = req.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element el : list){
			returnMap.put(el.getName(), el.getText());
		}
		ins.close();
		return returnMap;
	}
	
	public static String textMessageToXml(TextMessage textMessage){
		XStream stream = new XStream();
		stream.alias("xml", textMessage.getClass());
		return stream.toXML(textMessage);
	}
	public static String mediaMessageToXml(MediaMessage mediaMessage){
		XStream stream = new XStream();
		stream.alias("xml", mediaMessage.getClass());
		return stream.toXML(mediaMessage);
	}
	
	public static String initTextMessage(String fromUserName,String toUserName,String content){
		TextMessage text = new TextMessage();
		text.setToUserName(fromUserName);
		text.setFromUserName(toUserName);
		text.setContent(content);
		text.setCreateTime(new Date().getTime());
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		return MessageUtil.textMessageToXml(text);
	}
	
	public static String getSubscribeText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您关注IT特种兵，回复以下内容操作：\r\n");
		sb.append("1、特种兵介绍\r\n");
		sb.append("2、参加IT特种部队训练\r\n");
		sb.append("回复“？”返回主菜单");
		return sb.toString();
	}
	
	public static String firstTextStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("特种兵，组成特种部队单位人员，是世界一些国家军队中担负破袭敌方重要的政治、经济、军事目标和执行其他特殊任务的特殊兵种。单兵作战能力极强，适合在各种恶劣条件下，完成作战任务。往往是战争中决定战局的重要因素。\r\n");
		return sb.toString();
	}
	
	public static String secondTextStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("（1）现有一四则运算表达式，仅包含+-*/()和0~9数字，请设计一个算法，计算该表达式的值。 其中+-只作为运算符出现，不作为正负符号出现在表达式中。\r\n");
		sb.append("（2）请详细描述Spring3.0的连接池机制如何处理？\r\n");
		sb.append("（3）用最快方法判断所有String2的字母在String1里是否存在，如：string2=\"abx\",string1=\"abcdef\",ab在string1中，x不在。\r\n");
		return sb.toString();
	}

}
