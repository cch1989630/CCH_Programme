package com.cch.programme.weixin.vo;

import com.cch.programme.weixin.util.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本信息
 * @author cch
 *
 */
public class TextMessage extends BaseMessage {
	@XStreamAlias("Content")
	@XStreamCDATA
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String Content) {
		this.Content = Content;
	}
	
}
