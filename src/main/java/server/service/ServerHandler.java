package server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.dto.CommandType;
import common.response.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ServerExecutor executor;
    private ObjectMapper objectMapper;

    public ServerHandler(ServerExecutor executor) {
        this.executor = executor;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response = executor.execute((String) msg);
        if (CommandType.QUIT.equals(response.getCommandType())) {
            executor.saveDataToFile();
            ctx.channel().close();
            ctx.channel().parent().close();
        } else {
            try {
                ctx.channel().writeAndFlush(objectMapper.writeValueAsString(executor.execute((String) msg)));
            } catch (IOException e) {
                System.err.print("Server error. Reason: " + e.getMessage());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
