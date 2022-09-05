package com.suchenghe.example_IdleStateHandler;

import io.netty.channel.Channel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Optional;

public class ChannelUtils {

    public static Optional<String> remoteIP(Channel channel) {
        Optional<InetAddress> mayRemoteAddress = remoteAddress(channel);
        if (!mayRemoteAddress.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(mayRemoteAddress.get().getHostAddress());
    }

    public static Optional<InetAddress> remoteAddress(Channel channel) {
        Optional<SocketAddress> maySocketAddress = Optional.ofNullable(channel.remoteAddress());
        if (!maySocketAddress.isPresent()) {
            return Optional.empty();
        }
        SocketAddress socketAddress = maySocketAddress.get();
        if (socketAddress instanceof InetSocketAddress) {
            return Optional.ofNullable(((InetSocketAddress) socketAddress).getAddress());
        }
        return Optional.empty();
    }

}
