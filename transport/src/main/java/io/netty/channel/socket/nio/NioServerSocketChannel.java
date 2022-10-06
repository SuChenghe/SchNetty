/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.socket.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.util.internal.SocketUtils;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SuppressJava6Requirement;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;
import java.util.Map;

/**
 * A {@link io.netty.channel.socket.ServerSocketChannel} implementation which uses
 * NIO selector based implementation to accept new connections.
 */
public class NioServerSocketChannel extends AbstractNioMessageChannel
                             implements io.netty.channel.socket.ServerSocketChannel {

    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);

    private static ServerSocketChannel newSocket(SelectorProvider provider) {
        try {
            /**
             *  Use the {@link SelectorProvider} to open {@link SocketChannel} and so remove condition in
             *  {@link SelectorProvider#provider()} which is called by each ServerSocketChannel.open() otherwise.
             *
             *  See <a href="https://github.com/netty/netty/issues/2308">#2308</a>.
             */
            //return provider.openServerSocketChannel();
            logger.debug("NioServerSocketChannel() : DEFAULT_SELECTOR_PROVIDER 定义为 : private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();");
            logger.debug("NioServerSocketChannel() : DEFAULT_SELECTOR_PROVIDER 实际值为 : {}" , provider);
            ServerSocketChannel serverSocketChannel = provider.openServerSocketChannel();
            logger.debug("NioServerSocketChannel() newSocket(DEFAULT_SELECTOR_PROVIDER) 核心方法为 : {}" , "ServerSocketChannel serverSocketChannel = provider.openServerSocketChannel()  return serverSocketChannel;");
            logger.debug("NioServerSocketChannel() newSocket(DEFAULT_SELECTOR_PROVIDER) 实现业务为 : {}" , "create A ServerSocketChannel and return");
            logger.debug("NioServerSocketChannel() The ServerSocketChannel is : {}" , serverSocketChannel.toString());


            logger.debug("NioServerSocketChannel() public NioServerSocketChannel() {this(newSocket(DEFAULT_SELECTOR_PROVIDER));} -> 调用方法为");
            logger.debug("NioServerSocketChannel() : public NioServerSocketChannel(ServerSocketChannel channel) {\n" +
                    "                super(null, channel, SelectionKey.OP_ACCEPT);\n" +
                    "                config = new NioServerSocketChannelConfig(this, javaChannel().socket());\n" +
                    "            }");
            logger.debug("NioServerSocketChannel() : NioServerSocketChannel extend AbstractNioMessageChannel extend AbstractNioChannel extend AbstractChannel");
            logger.debug("NioServerSocketChannel() : super(null, channel, SelectionKey.OP_ACCEPT); -> 最终调用了");
            logger.debug("NioServerSocketChannel() : AbstractChannel -> protected AbstractChannel(Channel parent) {...} : parent 为null，即传入的一个参数");
            logger.debug("NioServerSocketChannel() : AbstractNioChannel -> protected AbstractNioChannel(Channel parent, SelectableChannel ch, int readInterestOp)");
            logger.debug("NioServerSocketChannel() : AbstractNioChannel -> 逻辑为 : 将 ch 赋值给本类的 private final SelectableChannel ch ;");
            logger.debug("NioServerSocketChannel() : AbstractNioChannel -> 逻辑为 : 将 readInterestOp 赋值给本类的 protected final int readInterestOp;");
            logger.debug("NioServerSocketChannel() : AbstractNioChannel -> 逻辑为 : 将 ch 设置为非阻塞 ch.configureBlocking(false);");
            return serverSocketChannel;
        } catch (IOException e) {
            throw new ChannelException(
                    "Failed to open a server socket.", e);
        }
    }

    private final ServerSocketChannelConfig config;

    /**
     * Create a new instance
     */
    public NioServerSocketChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    /**
     * Create a new instance using the given {@link SelectorProvider}.
     */
    public NioServerSocketChannel(SelectorProvider provider) {
        this(newSocket(provider));
    }

    /**
     * Create a new instance using the given {@link ServerSocketChannel}.
     */
    public NioServerSocketChannel(ServerSocketChannel channel) {
        super(null, channel, SelectionKey.OP_ACCEPT);
        config = new NioServerSocketChannelConfig(this, javaChannel().socket());
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    public ServerSocketChannelConfig config() {
        return config;
    }

    @Override
    public boolean isActive() {
        // As java.nio.ServerSocketChannel.isBound() will continue to return true even after the channel was closed
        // we will also need to check if it is open.
        return isOpen() && javaChannel().socket().isBound();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override
    protected ServerSocketChannel javaChannel() {
        return (ServerSocketChannel) super.javaChannel();
    }

    @Override
    protected SocketAddress localAddress0() {
        return SocketUtils.localSocketAddress(javaChannel().socket());
    }

    @SuppressJava6Requirement(reason = "Usage guarded by java version check")
    @Override
    protected void doBind(SocketAddress localAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().bind(localAddress, config.getBacklog());
        } else {
            javaChannel().socket().bind(localAddress, config.getBacklog());
        }
    }

    @Override
    protected void doClose() throws Exception {
        javaChannel().close();
    }

    @Override
    protected int doReadMessages(List<Object> buf) throws Exception {
        logger.debug("NioServerSocketChannel doReadMessages(List<Object> buf) start to invoke");
        //SocketChannel ch = SocketUtils.accept(javaChannel());
        ServerSocketChannel serverSocketChannel = javaChannel();
        SocketChannel ch = SocketUtils.accept(serverSocketChannel);
        logger.debug("NioServerSocketChannel doReadMessages(...)" +
                " : SocketChannel ch = SocketUtils.accept(javaChannel()) , javaChannel() : {} , ch : {}" ,serverSocketChannel,ch);
        try {
            if (ch != null) {
                //buf.add(new NioSocketChannel(this, ch));
                logger.debug("NioServerSocketChannel doReadMessages(...) : 封装成一个NioSocketChannel : " +
                        "new NioSocketChannel(this, ch) , this : {} ,ch : {} " , this , ch);
                NioSocketChannel nioSocketChannel = new NioSocketChannel(this, ch);
                logger.debug("NioServerSocketChannel doReadMessages(...) : 封装成的NioSocketChannel : {}" , nioSocketChannel);
                buf.add(nioSocketChannel);
                return 1;
            }
        } catch (Throwable t) {
            logger.warn("Failed to create a new channel from an accepted socket.", t);

            try {
                ch.close();
            } catch (Throwable t2) {
                logger.warn("Failed to close a socket.", t2);
            }
        }

        return 0;
    }

    // Unnecessary stuff
    @Override
    protected boolean doConnect(
            SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }

    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected final Object filterOutboundMessage(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }

    private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
        private NioServerSocketChannelConfig(NioServerSocketChannel channel, ServerSocket javaSocket) {
            super(channel, javaSocket);
        }

        @Override
        protected void autoReadCleared() {
            clearReadPending();
        }

        @Override
        public <T> boolean setOption(ChannelOption<T> option, T value) {
            if (PlatformDependent.javaVersion() >= 7 && option instanceof NioChannelOption) {
                return NioChannelOption.setOption(jdkChannel(), (NioChannelOption<T>) option, value);
            }
            return super.setOption(option, value);
        }

        @Override
        public <T> T getOption(ChannelOption<T> option) {
            if (PlatformDependent.javaVersion() >= 7 && option instanceof NioChannelOption) {
                return NioChannelOption.getOption(jdkChannel(), (NioChannelOption<T>) option);
            }
            return super.getOption(option);
        }

        @Override
        public Map<ChannelOption<?>, Object> getOptions() {
            if (PlatformDependent.javaVersion() >= 7) {
                return getOptions(super.getOptions(), NioChannelOption.getOptions(jdkChannel()));
            }
            return super.getOptions();
        }

        private ServerSocketChannel jdkChannel() {
            return ((NioServerSocketChannel) channel).javaChannel();
        }
    }

    // Override just to to be able to call directly via unit tests.
    @Override
    protected boolean closeOnReadError(Throwable cause) {
        return super.closeOnReadError(cause);
    }
}
