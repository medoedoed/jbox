package cli.command;

import cli.service.GrpcPingService;
import picocli.CommandLine;

import javax.inject.Inject;

@CommandLine.Command(
        name = "ping",
        description = "ping to check connection")
public class PingCommand implements Runnable {
    private final GrpcPingService pingService;

    @Inject
    public PingCommand(GrpcPingService pingService) {
        this.pingService = pingService;
    }

    @Override
    public void run() {
        System.out.println(pingService.getPing());
    }
}
