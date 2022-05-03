package com.devh.common.netty.channel;

import io.netty.channel.Channel;
import lombok.Getter;

import java.util.HashMap;

/*
 * <pre>
 * Description :
 *     연결된 세션 관리 맵
 *     IP, 채널 쌍으로 기록
 * ===============================
 * Memberfields :
 *
 * ===============================
 *
 * Author : HeonSeung Kim
 * Date   : 2021. 11. 8.
 * </pre>
 */
@Getter
public class ConnectedChannelMap {
    private HashMap<String, Channel> channelMap;

    /* Singleton */
    private static ConnectedChannelMap instance;
    public static ConnectedChannelMap getInstance() {
        if(instance == null)
            instance = new ConnectedChannelMap();
        return instance;
    }
    /* Singleton */

    /* Constructor */
    public ConnectedChannelMap() {
        this.channelMap = new HashMap<String, Channel>();
    }
    /* Constructor */

    public void add(String ip, Channel ch) {
        this.channelMap.put(ip, ch);
    }
    public void remove(String ip) {
        this.channelMap.remove(ip);
    }
    public Channel get(String ip) {
        return this.channelMap.get(ip);
    }
}
