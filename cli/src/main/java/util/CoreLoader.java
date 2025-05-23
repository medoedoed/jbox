package util;

import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

public class CoreLoader {
    private static final File CORE_BINARY = resolveBinaryPath();
    private static final Path PID_FILE = CORE_BINARY.toPath().getParent().resolve("pid");

    public static void start() {
        if (!CORE_BINARY.exists()) throw new RuntimeException("Core binary not found: " + CORE_BINARY);
        if (isRunning()) throw new RuntimeException("Core is already running.");

        try {
            ProcessBuilder builder = isWindows()
                    ? new ProcessBuilder("cmd", "/c", "start", "\"\"", CORE_BINARY.getAbsolutePath())
                    : new ProcessBuilder("nohup", CORE_BINARY.getAbsolutePath());

            builder.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            builder.redirectError(ProcessBuilder.Redirect.DISCARD);
            builder.directory(CORE_BINARY.getParentFile());

            Process process = builder.start();

            long pid = isWindows() ? ProcessHandle.current().pid() : process.pid();
            Files.createDirectories(PID_FILE.getParent());
            Files.writeString(PID_FILE, Long.toString(pid), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Core daemon started.");

        } catch (IOException e) {
            throw new RuntimeException("Failed to start core daemon", e);
        }
    }

    public static void stop() {
        Optional<Long> pidOpt = readPid();
        if (pidOpt.isEmpty()) {
            System.out.println("Core is not running.");
            return;
        }

        long pid = pidOpt.get();
        if (!isProcessAlive(pid)) {
            cleanupPid();
            System.out.println("Stale PID removed.");
            return;
        }

        try {
            ProcessBuilder builder = isWindows()
                    ? new ProcessBuilder("taskkill", "/PID", Long.toString(pid), "/F")
                    : new ProcessBuilder("kill", Long.toString(pid));

            Process kill = builder.start();
            kill.waitFor();

            if (kill.exitValue() == 0) {
                cleanupPid();
                System.out.println("Core daemon stopped.");
            } else {
                System.out.println("Failed to stop core daemon.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error stopping daemon", e);
        }
    }

    public static boolean isRunning() {
        return readPid().filter(CoreLoader::isProcessAlive).isPresent();
    }

    // ===== Helpers =====

    private static File resolveBinaryPath() {
        String path = ConfigFactory.load().getString("cli.core.binary-path");
        if (isWindows() && !path.toLowerCase().endsWith(".exe")) path += ".exe";
        return new File(path).getAbsoluteFile();
    }

    private static Optional<Long> readPid() {
        if (!Files.exists(PID_FILE)) return Optional.empty();
        try {
            return Optional.of(Long.parseLong(Files.readString(PID_FILE).trim()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static void cleanupPid() {
        try {
            Files.deleteIfExists(PID_FILE);
        } catch (IOException ignored) {}
    }

    private static boolean isProcessAlive(long pid) {
        return ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
