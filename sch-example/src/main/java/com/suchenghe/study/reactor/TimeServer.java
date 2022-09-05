package com.suchenghe.study.reactor;

/**
 * @author SuChenghe
 * @date 2020/6/13 18:31
 */
public class TimeServer {

    public static void main(String[] args) throws Exception {
        Reactor reactor = new Reactor(61005);
        reactor.run();
    }
}
