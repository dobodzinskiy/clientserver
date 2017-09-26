package client;

import client.service.ResponseExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private ResponseExecutor executor;

    ClientHandler() {
        this.executor = new ResponseExecutor();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        executor.execute(msg);
        ctx.channel().close();
        ctx.channel().parent().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
