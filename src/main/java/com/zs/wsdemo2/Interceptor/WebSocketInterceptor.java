package com.zs.wsdemo2.Interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {

    //在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        log.info("--------------------beforeHandshake--------------");
        if (request instanceof ServletServerHttpRequest) {

            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            //userId为自定义的ws连接参数字符串，比如 ws://localhost:8888/ws?userid=XXX 中的userid
            String userId = (String) servletRequest.getParameter("userId");
            //TODO： 判断userId是否允许连接websocket ,对userId要进行身份认证
            if (userId != null) {
                //这里存入，在处理消息的时候在WebSocketSession对象中可以(String)session.getAttributes().get("userId")取出（其中session是WebSocketSession对象）
                attributes.put("userId", userId);
            }

        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("coming webSocketInterceptor afterHandshake method...");
    }

}