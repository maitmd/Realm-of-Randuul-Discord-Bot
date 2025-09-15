package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import services.CommandService;
import services.DataHandler;
import utils.ServerStatusEnum;

public class Server implements Serializable{

    public final String FINISH_BOOT_STRING = "[Server thread/INFO] [net.minecraft.server.dedicated.DedicatedServer/]: Done";
    public final String CRASHED_STRING = "Preparing crash report with UUID";
    public final String STOPPED_STRING = "Stopping server";

    private String serverName;
    private int port = -1;
    private int pid = -1;
    private boolean serverStarting = false;

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
    
    public int getPort() throws IOException {
        String serverPropertiesPath = new File(DataHandler.getBaseServerPath() + getServerName() + "\\server.properties").getAbsolutePath();
        
        if (port != -1) {
            return port;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(serverPropertiesPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("server-port")) {
                    port = Integer.parseInt(line.substring(line.indexOf("=")+1));
                }
            }
        }
        
        return port;
    }
    
    public int getPID() throws IOException {
        if (pid != -1) {
            return pid;
        }

        setPID(searchForPIDByPort(getPort()));
        return pid;
    }
    
    public void setPID(int pid) {
        this.pid = pid;
    }

    public int searchForPIDByPort(int port) throws IOException {
        Process process = CommandService.runPowershellCommand("netstat -ano | findstr :" + port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length > 4 && parts[1].endsWith(":" + port)) {
                return Integer.parseInt(parts[parts.length - 1]);
            }
        }

        return -1;
    }

    public void startServer() throws IOException {
        serverStarting = true;
        if (pid != -1) {
            setPID(-1);
        }
        
        CommandService.runCommand("\"" + DataHandler.getBaseServerPath() + "start-all.bat\"");
    }

    public ServerStatusEnum getStatus() {
        File logFile = new File(DataHandler.getBaseServerPath() + getServerName() + "\\logs\\latest.log");
        System.out.println("Start getStatus");
        if (logFile.exists()) {
            System.out.println(logFile + " exists.");

            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                if (getPID() != -1) {
                    serverStarting = false;
                    return ServerStatusEnum.ONLINE;
                }

                if (serverStarting) {
                    return ServerStatusEnum.BOOTING;
                }

                if (content.toString().contains(CRASHED_STRING)) {
                    return ServerStatusEnum.CRASHED;
                }

                int currentPID = searchForPIDByPort(port);
                if (currentPID == -1) {
                    if (currentPID != getPID()) {
                        setPID(currentPID);
                    }

                    return ServerStatusEnum.OFFLINE;
                }

            } catch (IOException e) {
                System.err.println("Unable to read one of the streams. \n" + e);
                return ServerStatusEnum.UNKNOWN;
            }
        }

        return ServerStatusEnum.UNKNOWN;
    }
}
