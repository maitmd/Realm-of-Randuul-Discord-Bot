package commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import data.Server;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.utils.FileUpload;
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
                ServerStatusEnum status = server.getStatus();
                channel.sendMessage("**" + server.getServerName() + "** ```Current Status: " + status + "```").queue();
                break;
            case "crashreport":
                channel.sendMessage("The latest crash-report..").queue();
                File crashReport = server.getCrashReport();
                channel.sendFiles(FileUpload.fromData(crashReport)).complete();
                break;
            case "log":
                channel.sendMessage("The latest log file..").queue();
                File logFile = server.getLog();
                channel.sendFiles(FileUpload.fromData(logFile)).complete();
                break;
            case "start":
                channel.sendMessage("Will try to start server.. Use '/server " + server.getServerName() + " status' to check current status.").queue();
                try {
                    String[] commands = {"cmd /c \"" + DataHandler.getBaseServerPath() + "start-all.bat\""};
                    Runtime.getRuntime().exec(commands);
                } catch (IOException e) {
                    System.out.println("Unable to start server\n" + e);
                    channel.sendMessage("Failed to start server..").queue();
                }
                break;
            case "sendcommand":
                break;
        }
    }
}
