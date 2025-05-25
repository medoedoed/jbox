package util;


import com.google.inject.Inject;
import config.CoreSettings;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class CoreLoader {
    private final Path CORE_BINARY;
    private final Path PID_FILE;
    private final CoreSettings settings;

    @Inject
    public CoreLoader(CoreSettings coreSettings) {
        this.settings = coreSettings;
        this.CORE_BINARY = resolveBinaryPath();
        this.PID_FILE = CORE_BINARY.getParent().resolve("pid");
    }

    public void start() {
        if (!Files.exists(CORE_BINARY)) throw new RuntimeException("Core binary not found: " + CORE_BINARY);
        if (isRunning()) throw new RuntimeException("Core is already running.");

        try {
            Process process = new ProcessExecutor()
                    .command(CORE_BINARY.toString())
                    .directory(CORE_BINARY.getParent().toFile())
                    .start()
                    .getProcess();

            long pid = process.pid();
            Files.createDirectories(PID_FILE.getParent());
            Files.writeString(PID_FILE, Long.toString(pid), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Core daemon started with PID " + pid);

        } catch (IOException e) {
            throw new RuntimeException("Failed to start core", e);
        }
    }

    public void stop() {
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
            boolean success = isWindows()
                    ? new ProcessExecutor().command("taskkill", "/PID", String.valueOf(pid), "/F").execute().getExitValue() == 0
                    : new ProcessExecutor().command("kill", String.valueOf(pid)).execute().getExitValue() == 0;

            if (success) {
                cleanupPid();
                System.out.println("Core stopped.");
            } else {
                System.out.println("Failed to stop core.");
            }

        } catch (IOException | InterruptedException | TimeoutException e) {
            throw new RuntimeException("Failed to stop process", e);
        }
    }

    public boolean isRunning() {
        return readPid().filter(CoreLoader::isProcessAlive).isPresent();
    }

    // ===== Helpers =====

    private Path resolveBinaryPath() {
        var basePath = Path.of(settings.getApp().getDirectory(), "bin", "sing-box");

        if (isWindows() && !basePath.toString().toLowerCase().endsWith(".exe")) {
            basePath = Path.of(basePath + ".exe");
        }

        return basePath.toAbsolutePath();
    }

    private Optional<Long> readPid() {
        try {
            if (!Files.exists(PID_FILE)) return Optional.empty();
            return Optional.of(Long.parseLong(Files.readString(PID_FILE).trim()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private void cleanupPid() {
        try {
            Files.deleteIfExists(PID_FILE);
        } catch (IOException ignored) {
        }
    }

    private static boolean isProcessAlive(long pid) {
        return ProcessHandle.of(pid).map(ProcessHandle::isAlive).orElse(false);
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
