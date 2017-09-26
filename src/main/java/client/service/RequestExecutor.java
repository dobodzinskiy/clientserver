package client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.dto.Bird;
import common.dto.CommandType;
import common.dto.Sighting;
import common.request.AddBirdRequest;
import common.request.AddSightingRequest;
import common.request.Request;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class RequestExecutor {

    private Channel channel;
    private Scanner scanner;
    private ObjectMapper mapper;

    public RequestExecutor(Channel channel) {
        this.channel = channel;
        this.scanner = new Scanner(System.in);
        this.mapper = new ObjectMapper();
    }

    public void execute(CommandType commandType) {
        try {

            switch (commandType) {
                case ADDBIRD:
                    this.addBird();
                    break;
                case ADDSIGHTING:
                    this.addSighting();
                    break;
                case LISTBIRDS:
                    this.listBirds();
                    break;
                case LISTSIGHTINGS:
                    this.listSightings();
                    break;
                case REMOVE:
                    this.remove();
                    break;
                case QUIT:
                    break;
            }
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
    }

    private void addBird() throws IOException {
        Bird bird = new Bird();
        System.out.printf("Bird name = ");
        bird.setName(scanner.nextLine());

        System.out.printf("Bird color = ");
        bird.setColor(scanner.nextLine());

        System.out.printf("Bird weight = ");
        bird.setWeight(scanner.nextDouble());

        System.out.printf("Bird height = ");
        bird.setHeight(scanner.nextDouble());

        AddBirdRequest request = new AddBirdRequest();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(CommandType.ADDBIRD);
        request.setBird(bird);

        channel.writeAndFlush(mapper.writeValueAsString(request));
    }

    private void addSighting() throws IOException {
        Sighting sighting = new Sighting();

        System.out.printf("Bird name = ");
        sighting.setBirdName(scanner.nextLine());

        System.out.printf("Location = ");
        sighting.setSighting(scanner.nextLine());

        System.out.printf("Date & time = ");
        sighting.setDate(new Date(scanner.nextLine()));

        AddSightingRequest request = new AddSightingRequest();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(CommandType.ADDSIGHTING);
        request.setSighting(sighting);

        channel.writeAndFlush(mapper.writeValueAsString(request));
    }

    private void listBirds() throws IOException {
        Request request = new Request();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(CommandType.LISTBIRDS);

        channel.writeAndFlush(mapper.writeValueAsString(request));
    }

    private void listSightings() throws IOException {
        Request request = new Request();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(CommandType.LISTSIGHTINGS);

        System.out.printf("Bird name = ");
        request.setParams(scanner.nextLine());

        channel.writeAndFlush(mapper.writeValueAsString(request));
    }

    private void remove() throws IOException {
        Request request = new Request();
        request.setId(UUID.randomUUID().toString());
        request.setMethod(CommandType.REMOVE);

        System.out.printf("Bird name = ");
        request.setParams(scanner.nextLine());

        channel.writeAndFlush(mapper.writeValueAsString(request));
    }
}
