package com.cch.programme.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 
 * @author cch
 *
 */
public class RichFreeMarkerView extends FreeMarkerView {
	/**
     * ����·����������
     */
    public static final String CONTEXT_PATH = "base";

    /**
     * ��model�����Ӳ���·��base�����㴦����·�����⡣
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected void exposeHelpers(Map model, HttpServletRequest request)
                    throws Exception {
            super.exposeHelpers(model, request);
            model.put(CONTEXT_PATH, request.getContextPath());
    }
}
