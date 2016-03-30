package com.cch.programme.weixin.vo;

import com.cch.programme.weixin.util.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信消息推送基类
 * @author cch
 *
 */
public class BaseMessage {
	//发送者微信号
	@XStreamAlias("ToUserName")
	@XStreamCDATA
	private String ToUserName;
	//发送方帐号(一个OpenId)
	@XStreamAlias("FromUserName")
	@XStreamCDATA
	private String FromUserName;
	//消息创建时间(整形)
	private long CreateTime;
	//消息类型(text/image/location/link)
	@XStreamAlias("MsgType")
	@XStreamCDATA
	private String MsgType;
	//消息ID，64位整形
	private long MsgId;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}

	
}
