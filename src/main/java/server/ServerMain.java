package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.cli.*;
import server.service.ServerExecutor;
import server.service.ServerHandler;

import java.nio.file.Files;
import java.nio.file.Paths;


public class ServerMain {

    private static int portNumber = 3000;
    private static String dataFolder;
    private static int procCount = 2;

    public static void main(String[] args) throws Exception {
        configure(args);
        run();
    }

    private static void configure(String[] args) {
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

            if (commandLine.hasOption("data") && Files.isDirectory(Paths.get(commandLine.getOptionValue("data")))) {
                dataFolder = commandLine.getOptionValue("data");
            } else {
                System.err.print("Data Folder doesn't exist. Server will use default folder.");
                dataFolder = System.getProperty("user.home") + "/serverdata";
            }

        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.print("Port option should be -port <integer between 1 and 65535>. Reason: " + e.getMessage());
        }
    }

    private static void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(procCount);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerExecutor executor = ServerExecutor.configure(dataFolder);
            executor.loadDataFromFile();

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new ServerHandler(executor));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            bootstrap.bind(portNumber).sync(); // (7)
        } catch (Exception e) {
            System.err.print("Server start error. Reason: " + e.getMessage());
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }));
    }
}
