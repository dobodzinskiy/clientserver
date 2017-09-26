package client;

import client.service.RequestExecutor;
import common.dto.CommandType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.cli.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMain {

    private static int serverPort;
    private static CommandType runCommand;

    public static void main(String[] args) {
        configure(args);
        run();
    }

    private static void configure(String[] args) {
        Option port = new Option("serverPort", true, "serverPort (optional)");
        Option addBird = new Option("addbird", false, "adding a bird to storage");
        Option addSighting = new Option("addsighting", false, "adding a sighting to storage");
        Option remove = new Option("remove", false, "remove bird from storage");
        Option quit = new Option("quit", false, "shutdown server");
        Option listBirds = new Option("listbirds", false, "list all birds");
        Option listSightings = new Option("listsightings", false, "list all sighting of a bird");

        Options options = new Options();
        OptionGroup commands = new OptionGroup();

        commands
                .addOption(addBird)
                .addOption(addSighting)
                .addOption(remove)
                .addOption(quit)
                .addOption(listBirds)
                .addOption(listSightings);
        options
                .addOption(port)
                .addOptionGroup(commands);

        CommandLineParser parser = new BasicParser();
        try {
            CommandLine commandLine = parser.parse(options, args);

            serverPort = commandLine.hasOption("serverPort")
                    ? Integer.parseInt(commandLine.getOptionValue("serverPort"))
                    : 3000;

            if (commandLine.hasOption("addbird")) {
                runCommand = CommandType.ADDBIRD;
            } else if (commandLine.hasOption("addsighting")) {
                runCommand = CommandType.ADDSIGHTING;
            } else if (commandLine.hasOption("remove")) {
                runCommand = CommandType.REMOVE;
            } else if (commandLine.hasOption("quit")) {
                runCommand = CommandType.QUIT;
            } else if (commandLine.hasOption("listbirds")) {
                runCommand = CommandType.LISTBIRDS;
            } else if (commandLine.hasOption("listsightings")) {
                runCommand = CommandType.LISTSIGHTINGS;
            } else {
                System.err.print("Error: No command specified.");
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }

    private static void run() {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ClientHandler());
                        }
                    });

            // Start the connection attempt.
            Channel ch = b.connect(InetAddress.getLocalHost(), serverPort).sync().channel();
            RequestExecutor executor = new RequestExecutor(ch);

            executor.execute(runCommand);

        } catch (InterruptedException | UnknownHostException e) {
            System.err.print("Error. Reason: " + e.getMessage());
        }

    }
}
