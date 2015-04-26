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
	
	public static final String MESSAGE_TEXT = "text";//�ı�
	public static final String MESSAGE_IMAGE = "image";//ͼƬ
	public static final String MESSAGE_VOICE = "voice";//����
	public static final String MESSAGE_VIDEO = "video";//��Ƶ
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";//С��Ƶ
	public static final String MESSAGE_LOCATION = "location";//λ��
	public static final String MESSAGE_LINK = "link";//����
	public static final String MESSAGE_EVENT = "event";//ʱ��
	public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";//��ע�¼�
	public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";//ȡ����ע�¼�
	public static final String MESSAGE_EVENT_LOCATION = "LOCATION";//λ��
	public static final String MESSAGE_EVENT_SCAN = "SCAN";//�ϱ�����λ���¼�
	public static final String MESSAGE_EVENT_CLICK = "CLICK";//�Զ���˵��¼�
	public static final String MESSAGE_EVENT_VIEW = "VIEW";//����˵���ת����ʱ���¼�����
	
	
	/**
	 * ��xml��ʽ���ַ���תΪMap
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
		sb.append("��ӭ����עIT���ֱ����ظ��������ݲ�����\r\n");
		sb.append("1�����ֱ�����\r\n");
		sb.append("2���μ�IT���ֲ���ѵ��\r\n");
		sb.append("�ظ��������������˵�");
		return sb.toString();
	}
	
	public static String firstTextStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("���ֱ���������ֲ��ӵ�λ��Ա��������һЩ���Ҿ����е�����Ϯ�з���Ҫ�����Ρ����á�����Ŀ���ִ���������������������֡�������ս������ǿ���ʺ��ڸ��ֶ��������£������ս����������ս���о���ս�ֵ���Ҫ���ء�\r\n");
		return sb.toString();
	}
	
	public static String secondTextStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("��1������һ����������ʽ��������+-*/()��0~9���֣������һ���㷨������ñ��ʽ��ֵ�� ����+-ֻ��Ϊ��������֣�����Ϊ�������ų����ڱ��ʽ�С�\r\n");
		sb.append("��2������ϸ����Spring3.0�����ӳػ�����δ���\r\n");
		sb.append("��3������췽���ж�����String2����ĸ��String1���Ƿ���ڣ��磺string2=\"abx\",string1=\"abcdef\",ab��string1�У�x���ڡ�\r\n");
		return sb.toString();
	}

}
