package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.cli.*;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import server.service.ServerExecutor;
import server.service.ServerHandler;


/**
 * Created by denisobodzinskiy on 11.09.17.
 */
public class ServerMain {

    private static int portNumber = 3000;
    private static String dataFolder;
    private static int procCount = 2;

    private static void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new ServerHandler(ServerExecutor.configure(dataFolder)));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            bootstrap.bind(portNumber).sync(); // (7)

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        Option port = new Option("port", true, "server port (optional)");
        Option data = new Option("data", true, "location of folder");
        Option procCount = new Option("proc_count", "worker count");

        Options options = new Options();
        options.addOption(port);
        options.addOption(data);
        options.addOption(procCount);

        CommandLineParser commandLineParser = new BasicParser();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            portNumber = commandLine.hasOption("port") ? Integer.parseInt(commandLine.getOptionValue("port")) : 3000;
            dataFolder = commandLine.hasOption("data") ? commandLine.getOptionValue("data") : System.getProperty("user.home") + "/serverdata";

        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.print("Port option should be -port <integer between 1 and 65535>. Reason: " + e.getMessage());
        }

        run();
    }
}
