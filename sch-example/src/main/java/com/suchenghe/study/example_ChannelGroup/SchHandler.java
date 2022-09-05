package com.suchenghe.study.example_ChannelGroup;

import com.suchenghe.example_IdleStateHandler.ChannelUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2022/07/27 20:21
 */
public class SchHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchHandler.class);

    private static ChannelGroup allRecipients = null;

    public SchHandler(ChannelGroup recipients) {
        if(allRecipients == null){
            allRecipients = recipients;
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        LOGGER.info(msg.toString());

        //ByteBuf byteBuf = ctx.alloc().buffer();
        //byteBuf.writeBytes(msg);

        allRecipients.write(Unpooled.copiedBuffer(
                ChannelUtils.remoteAddress(ctx.channel()) + " has read data",
                CharsetUtil.UTF_8));
        allRecipients.flush();

        //ctx.writeAndFlush(byteBuf);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}
