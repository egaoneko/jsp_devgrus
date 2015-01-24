package net.devgrus.controller;

import net.devgrus.handler.CommandHandler;
import net.devgrus.handler.NullHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-22
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class URIController extends HttpServlet {

    // <커맨드, 핸들러인스턴스> 매핑 정보 저장
    // [/xxxx.do], [xxxxHandler] 형태로 저장
    private Map<String, CommandHandler> commandHandlerMap = new HashMap<String, CommandHandler>();

    public void init() throws ServletException{
        String configFile = getInitParameter("propertyConfig");
        Properties prop = new Properties();
        FileInputStream fis =null;
        try{
            String configFilePath = getServletContext().getRealPath(configFile);
            fis = new FileInputStream(configFilePath);
            prop.load(fis);
        } catch (IOException e){
            throw new ServletException(e);
        } finally {
            if(fis != null)
                try{
                    fis.close();
                } catch (IOException ex){}
        }

        Iterator keyIter = prop.keySet().iterator();
        while (keyIter.hasNext()){
            String command = (String) keyIter.next();
            String handlerClassName = prop.getProperty(command);

            try{
                Class<?> handlerClass = Class.forName(handlerClassName);
                CommandHandler handlerInstance = (CommandHandler)handlerClass.newInstance();
                commandHandlerMap.put(command, handlerInstance);
            } catch (ClassNotFoundException e){
                throw new ServletException(e);
            } catch (InstantiationException e){
                throw new ServletException(e);
            } catch (IllegalAccessException e){
                throw new ServletException(e);
            }
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        process(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        process(request,response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");      // 한글이 깨지지 않도록 UTF-8로 인코딩
        String command = request.getRequestURI();   // 요청 주소만 저장
                                                    // ex)http://xxx.xxx.xxx/portfolio/xxxx.do ==> /portfolio/xxxx.do
        if(command.indexOf(request.getContextPath())==0){
            command = command.substring(request.getContextPath().length());    // /portfolio/xxxx.do 에서 /xxxx.do만 저장
        }

        CommandHandler handler = commandHandlerMap.get(command);    // /xxxx.do에 해당하는 클래스, 즉 xxxxHandler 반환
        if(handler == null){
            handler = new NullHandler();
        }
        String viewPage = null;
        try{
            viewPage = handler.process(request, response);
        } catch (Throwable e){
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);  // 해당 클래스를 거쳐 반환된 view 페이지로 이동
        dispatcher.forward(request, response);
    }
}
