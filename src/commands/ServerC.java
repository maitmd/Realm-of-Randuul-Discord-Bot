package commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import data.Server;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.utils.FileUpload;
import services.CommandService;
import services.DataHandler;
import utils.ServerStatusEnum;

// !server [add/delete/list/status/crashreport/start] [Server Name] 
// !server command [Server Name] [command] *Tenative
// !server list
public class ServerC extends Command {

    public ServerC(MessageChannelUnion channel, String content) {
        super(jda, twitter);

        ArrayList<String> args;
        try {
            args = getArgs(content, 2);
        } catch (StringIndexOutOfBoundsException e) {
            args = getArgs(content, 1);
        }

        Server server = null;

        if (args.size() > 1) {
            server = DataHandler.getServer(args.get(1));
        }

        switch (args.get(0)) {
            case "add":
                server = new Server(args.get(1));
                DataHandler.addServer(server);
                channel.sendMessage(server.getServerName() + " has been added.").queue();
                DataHandler.save();
                break;
            case "delete":
                if (!serverExists(channel, server, args.get(1))) {
                    return;
                }

                String serverName = server.getServerName();

                if (DataHandler.removeServer(server)) {
                    channel.sendMessage(serverName + " has been deleted.").queue();
                } else {
                    channel.sendMessage("Could not find" + serverName + " are you sure that's the right name?").queue();
                }

                DataHandler.save();
                break;
            case "list":
                String servers = " ";
                for (Server temp : DataHandler.getAllServers()) {
                    servers = temp.getServerName() + ", " + servers;
                }

                if (!servers.equals(" ")) {
                    servers = servers.substring(0, servers.lastIndexOf(","));
                }

                channel.sendMessage("**Servers** ```" + servers + " ```").queue();
                break;
            case "status":
                if (!serverExists(channel, server, args.get(1))) {
                    return;
                }

                ServerStatusEnum status = server.getStatus();
                channel.sendMessage("**" + server.getServerName() + "** ```Current Status: " + status + "```").queue();
                break;
            case "crashreport":
                if (!serverExists(channel, server, args.get(1))) {
                    return;
                }

                channel.sendMessage("The latest crash-report..").queue();
                File crashReport = server.getCrashReport();
                channel.sendFiles(FileUpload.fromData(crashReport)).complete();
                break;
            case "log":
                if (!serverExists(channel, server, args.get(1))) {
                    return;
                }

                channel.sendMessage("The latest log file..").queue();
                File logFile = server.getLog();
                channel.sendFiles(FileUpload.fromData(logFile)).complete();
                break;
            case "restart":
                if (!serverExists(channel, server, args.get(1))) {
                    return;
                }

                channel.sendMessage("Attempting to reboot server.. Use '!server status " + server.getServerName() + "' to check current status.").queue();
                try {
                    String pid = Integer.toString(server.getPID());
                    CommandService.runPowershellCommand("taskkill /PID " + pid + " /F");
                    
                    Thread.sleep(2);
                    server.startServer();
                } catch (IOException | InterruptedException e) {
                    System.out.println("Unable to start server\n" + e);
                    channel.sendMessage("Failed to start server..").queue();
                }
                break;
            case "start":
                if (!serverExists(channel, server, args.get(1))) {
                    return;
                }

                channel.sendMessage("Attempting to start server.. Use '!server status " + server.getServerName() + "' to check current status.").queue();
                try {
                    server.startServer();
                } catch (IOException e) {
                    System.out.println("Unable to start server\n" + e);
                    channel.sendMessage("Failed to start server..").queue();
                }
                break;
            case "sendcommand":
                break;
        }
    }

    private boolean serverExists(MessageChannelUnion channel, Server server, String serverName) {
        if (server == null) {
            channel.sendMessage("Could not find " + serverName + " are you sure that's the right name?").queue();
            return false;
        }

        return true;
    }
}
