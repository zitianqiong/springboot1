package pers.zitianqiong.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.zitianqiong.utils.StringUtils;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/12/9
 */
//userId：地址的111就是这个userId"ws://localhost:port/ws/111"
@ServerEndpoint(value = "/ws/{userId}")
@Component
@Slf4j
public class WebSocketServer {
    
    /**
     * 用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";
    
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            //加入set中
        } else {
            webSocketMap.put(userId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("用户连接:{},当前在线人数为:{}",userId,getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:{},网络异常",userId);
        }
    }
    
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:{},当前在线人数为:{}", userId,getOnlineCount());
    }
    
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:{},报文:{}" , userId , message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message) && JSON.isValidObject(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
                    log.error("请求的userId:{}不在该服务器上",toUserId );
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (JSONException ignored){
            
            } catch (Exception e) {
                log.error("webSocket接收消息错误",e);
            }
        }
    }
    
    /**
     * 错误
     *
     * @param session session
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
//        System.out.println("用户错误:"+this.userId+",原因:"+error.getMessage());
        log.error("webSocket发生错误,用户：{}，原因", this.userId, error);
    }
    
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    
    /**
     * 广播自定义消息
     */
    public static void sendInfo(String message) {
        log.info("广播消息，报文:{}", message);
        for (WebSocketServer client : webSocketMap.values()) {
            try {
                client.sendMessage(message);
            } catch (IOException e) {
                log.error("广播消息失败", e);
                throw new RuntimeException(e.getMessage());
            }
        }
    }
    
    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) {
        log.info("发送消息到:{}，报文:{}", userId, message);
        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            try {
                webSocketMap.get(userId).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            log.info("用户{},不在线！", userId);
        }
    }
    
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
