package server.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ServerExecutor serverExecutor;

    public ServerHandler(ServerExecutor serverExecutor) {
        this.serverExecutor = serverExecutor;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
