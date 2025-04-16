package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import services.DataHandler;
import utils.ServerStatusEnum;

public class Server {

    public final String FINISH_BOOT_STRING = "[Server thread/INFO] [net.minecraft.server.dedicated.DedicatedServer/]: Done";
    public final String START_BOOT_STRING = "[main/DEBUG] [net.minecraftforge.fml.loading.FMLLoader/CORE]: FML 1.0 loading";
    private String serverName;

    public Server(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public File getCrashReport() {
        File crashReport = new File(DataHandler.getBaseServerPath() + getServerName() + "\\crash-reports");
        if (crashReport.exists() && crashReport.isDirectory()) {
            Optional<File> opFile = Arrays.stream(crashReport.listFiles(File::isFile))
                    .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

            if (opFile.isPresent()) {
                return opFile.get();
            }
        }

        return null;
    }

    public File getLog() {
        File crashReport = new File(DataHandler.getBaseServerPath() + getServerName() + "\\logs\\latest.log");
        if (crashReport.exists() && crashReport.isDirectory()) {
            Optional<File> opFile = Arrays.stream(crashReport.listFiles(File::isFile))
                    .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

            if (opFile.isPresent()) {
                return opFile.get();
            }
        }

        return null;
    }

    public ServerStatusEnum getStatus() {
        File logFile = new File(DataHandler.getBaseServerPath() + getServerName() + "\\logs\\debug.log");

        if (logFile.exists()) {
            long currentTime = System.currentTimeMillis();
            long lastModified = logFile.lastModified();

            if (Instant.ofEpochMilli(currentTime - 60000).isBefore(Instant.ofEpochMilli((lastModified)))) {
                try (FileInputStream fis = new FileInputStream(logFile)) {
                    String contents = fis.toString();
                    if (contents.contains(FINISH_BOOT_STRING)) {
                        return ServerStatusEnum.ONLINE;
                    } else if (contents.contains(START_BOOT_STRING)) {
                        return ServerStatusEnum.BOOTING;
                    }
                } catch (IOException e) {
                    return ServerStatusEnum.UNKNOWN;
                }
            } else {
                return ServerStatusEnum.OFFLINE;
            }
        }

        return ServerStatusEnum.UNKNOWN;
    }
}
