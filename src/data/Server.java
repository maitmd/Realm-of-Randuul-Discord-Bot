package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import services.DataHandler;
import utils.ServerStatusEnum;

public class Server implements Serializable{

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
        File log = new File(DataHandler.getBaseServerPath() + getServerName() + "\\logs\\latest.log");
        if (log.exists()) {
            return log;
        }

        return null;
    }

    public ServerStatusEnum getStatus() {
        File logFile = new File(DataHandler.getBaseServerPath() + getServerName() + "\\logs\\debug.log");
        System.out.println("Start getStatus");
        if (logFile.exists()) {
            System.out.println(logFile + " exists.");
            long currentTime = System.currentTimeMillis();
            long lastModified = logFile.lastModified();

            System.out.println("Current time: " + currentTime);
            System.out.println("Modified time: " + lastModified);
            System.out.println("Difference: " + (currentTime - lastModified));

            if ((currentTime - lastModified) < 30000) {
                try (ObjectInputStream ois = new ObjectInputStream(logFile)) {
                    String contents = ois.readUTF();
                    System.out.println("Contents: " + contents);
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
