package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import services.DataHandler;
import utils.ServerStatusEnum;

public class Server implements Serializable{

    public final String FINISH_BOOT_STRING = "[Server thread/INFO] [net.minecraft.server.dedicated.DedicatedServer/]: Done";
    public final String START_BOOT_STRING = "[main/DEBUG] [net.minecraftforge.fml.loading.FMLLoader/CORE]: FML 1.0 loading";
    public final String CRASHED_STRING = "[net.minecraftforge.common.ForgeMod/]: Preparing crash report with UUID";
    public final String STOPPED_STRING = "[Server thread/INFO] [net.minecraft.server.MinecraftServer/]: Stopping server";

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
    
    public int getPort() {
        String serverPropertiesPath new File(DataHandler.getBaseServerPath() + getServerName() + "\\server.properties");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(serverPropertiesPath))) {
            String line;
            while (line = reader.readLine()) {
                if (line.contains("server-port")) {
                    return Integer.parseInt(line.substring(line.indexOf("=")));
                }
            }
        }
    }
    
    public int getPID() {
        String[] commands = {"netstat -ano | findstr :" + getPort()};
        Process process = Runtime.getRuntime().exec(commands);
        BufferedReader reader = new BufferedReader(process.getInputStream());
        String line = reader.readLine();
        if (line.contains(port)) {
            //get PID
        } else {
            return -1;
        }
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
                try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    
                    if (content.toString().contains(CRASHED_STRING)) {
                        return ServerStatusEnum.CRASHED;
                    }

                    if (content.toString().contains(STOPPED_STRING)) {
                        return ServerStatusEnum.OFFLINE;
                    }

                    if (getPID() != -1) {
                        return ServerStatusEnum.ONLINE;
                    }
                    
                    if (content.toString().contains(START_BOOT_STRING)) {
                        return ServerStatusEnum.BOOTING;
                    }
                } catch (IOException e) {
                    System.err.println("Unable to read one of the streams. \n" + e);
                    return ServerStatusEnum.UNKNOWN;
                }
            } else {
                return ServerStatusEnum.OFFLINE;
            }
        }

        return ServerStatusEnum.UNKNOWN;
    }
}
