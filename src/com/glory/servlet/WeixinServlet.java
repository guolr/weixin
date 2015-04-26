package com.glory.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glory.po.Image;
import com.glory.po.MediaMessage;
import com.glory.po.Shortvideo;
import com.glory.po.Video;
import com.glory.po.Voice;
import com.glory.util.CheckUtil;
import com.glory.util.MessageUtil;

public class WeixinServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		resp.setCharacterEncoding("UTF-8");
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		String[] tempStrs = {timestamp,nonce,echostr};
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkStr(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
//			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
//			String msgId = map.get("MsgId");
			
			String message = null;
			System.out.println("=======================================================");
			this.mapToString(map);
			System.out.println("=======================================================");
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initTextMessage(fromUserName, toUserName, MessageUtil.firstTextStr());
				}else if("2".equals(content)){
					message = MessageUtil.initTextMessage(fromUserName, toUserName, MessageUtil.secondTextStr());
				}else if("？".equals(content) || "?".equals(content)){
					message = MessageUtil.initTextMessage(fromUserName, toUserName, MessageUtil.getSubscribeText());
				}else{
					message = MessageUtil.initTextMessage(fromUserName, toUserName, MessageUtil.getSubscribeText());
				}
				out.print(message);
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				if(MessageUtil.MESSAGE_EVENT_SUBSCRIBE.equals(map.get("Event"))){//关注事件
					message = MessageUtil.initTextMessage(fromUserName, toUserName, MessageUtil.getSubscribeText());
					out.print(message);
				}
			}else if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){
				MediaMessage text = new MediaMessage();
				text.setToUserName(fromUserName);
				text.setFromUserName(toUserName);
				text.setCreateTime(new Date().getTime());
				text.setMsgType(MessageUtil.MESSAGE_IMAGE);
				Image image = new Image();
				image.setMediaId(map.get("MediaId"));
				text.setImage(image);
				message = MessageUtil.mediaMessageToXml(text);
				out.print(message);
			}else if(MessageUtil.MESSAGE_VOICE.equals(msgType)){
				MediaMessage text = new MediaMessage();
				text.setToUserName(fromUserName);
				text.setFromUserName(toUserName);
				text.setCreateTime(new Date().getTime());
				text.setMsgType(MessageUtil.MESSAGE_VOICE);
				Voice voice = new Voice();
				voice.setMediaId(map.get("MediaId"));
				text.setVoice(voice);
				message = MessageUtil.mediaMessageToXml(text);
				out.print(message);
			}else if(MessageUtil.MESSAGE_VIDEO.equals(msgType)){	
				MediaMessage text = new MediaMessage();
				text.setToUserName(fromUserName);
				text.setFromUserName(toUserName);
				text.setCreateTime(new Date().getTime());
				text.setMsgType(MessageUtil.MESSAGE_VOICE);
				Video video = new Video();
				video.setMediaId(map.get("MediaId"));
				video.setTitle(map.get("Title"));
				video.setDescription(map.get("Description"));
				text.setVideo(video);
				message = MessageUtil.mediaMessageToXml(text);
				out.print(message);
			}else if(MessageUtil.MESSAGE_SHORTVIDEO.equals(msgType)){	
				MediaMessage text = new MediaMessage();
				text.setToUserName(fromUserName);
				text.setFromUserName(toUserName);
				text.setCreateTime(new Date().getTime());
				text.setMsgType(MessageUtil.MESSAGE_VOICE);
				Shortvideo video = new Shortvideo();
				video.setMediaId(map.get("MediaId"));
//				video.setThumbMediaId(map.get("ThumbMediaId"));
				text.setShortvideo(video);
				message = MessageUtil.mediaMessageToXml(text);
				out.print(message);
			}
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	private void mapToString(Map<String,String> map){
		Iterator<Entry<String,String>> it = map.entrySet().iterator();
		Entry<String,String> item = null;
		while(it.hasNext()){
			item = it.next();
			System.out.println(item.getKey()+"的值是【"+item.getValue()+"】");
		}
	}
}
