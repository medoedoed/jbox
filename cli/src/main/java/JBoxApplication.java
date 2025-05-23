import configService.ConfigCommand;
import picocli.CommandLine;
import util.CoreLoader;

@CommandLine.Command(
        name = "jbox",
        version = "jbox 1.0",
        description = "cli client for sing-box core",
        subcommands = {
                ConfigCommand.class
        }
)
public class JBoxApplication implements Runnable {
    @Override
    public void run() {
        System.out.println("sing-box cli client");
    }

    public static void main(String[] args) {
        try {
            CoreLoader.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int exitCode = new CommandLine(new JBoxApplication()).execute(args);
        System.exit(exitCode);
    }
}


