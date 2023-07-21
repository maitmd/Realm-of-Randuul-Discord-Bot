package commands;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Whitelist extends Command {
    public Whitelist(MessageChannelUnion channel, String message, Member mem) {
        super(jda, twitter);

        if (!(mem.getId().equals("673294793966616576") || mem.getId().equals("595923572837318658")
                || mem.getId().equals("132603306382983169"))) {
            channel.sendMessage("Silly little person, you can't do that :skull:").queue();
            return;
        }

        ArrayList<String> args = getArgs(message, 1);
        HttpURLConnection connection = setConnectionSettings("POST");
        String body;
        ByteArrayOutputStream os;
        String username;
        String uuid;
        String response = "";

        if (!args.isEmpty()) {
            if (args.get(0).equals("delete")) {
                args = getArgs(message, 2);
                username = args.get(1);
                uuid = getUUID(username);
                connection = setConnectionSettings("DELETE");
                body = "uuid=" + uuid + "&name=" + username;

                try {
                    os = (ByteArrayOutputStream) connection.getOutputStream();
                    os.write(body.getBytes());
                    response = connection.getResponseMessage();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                channel.sendMessage("Bye bye :)").queue();
            } else {
                username = args.get(0);
                uuid = getUUID(username);
                connection = setConnectionSettings("POST");
                body = "uuid=" + uuid + "&name=" + username;

                try {
                    os = (ByteArrayOutputStream) connection.getOutputStream();
                    os.write(body.getBytes());
                    response = connection.getResponseMessage();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response.equals("")) {
                    channel.sendMessage("Can't connect to the server. Is it online?").queue();
                } else if (response.contains("failed")) {
                    channel.sendMessage("Could not white list " + username + "! Is the username right?").queue();
                } else if (response.contains("duplicate")) {
                    channel.sendMessage("Looks like they are already whitelisted :hypers:").queue();
                } else if (response.contains("no whitelist")) {
                    channel.sendMessage("No whitelist? :thinking:").queue();
                } else {
                    channel.sendMessage("Welcome " + username + " to the server :sunglasses:").queue();
                }
            }

            return;
        }

        StringBuilder builder = new StringBuilder();
        String inputLine;
        connection = setConnectionSettings("GET");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((inputLine = br.readLine()) != null) {
                builder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArr = new JSONArray(builder.toString());
        StringBuilder returnMsg = new StringBuilder();
        for (int i = 0; i < jsonArr.length(); i++) {
            returnMsg.append(jsonArr.getJSONObject(i).getString("name") + "\n");
        }

        channel.sendMessage("```" + returnMsg.toString() + "```").queue();
    }

    private String getUUID(String username) {
        URL endpoint;
        String content;
        JSONArray jsonArr;
        JSONObject jsonObj = new JSONObject();
        String inputLine;
        StringBuilder builder = new StringBuilder();
        BufferedReader br;
        HttpURLConnection connection;
        try {
            endpoint = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((inputLine = br.readLine()) != null) {
                builder.append(inputLine);
            }

            content = "[" + builder.toString() + "]";
            jsonArr = new JSONArray(content);
            jsonObj = jsonArr.getJSONObject(0);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObj.getString("id");
    }

    private HttpURLConnection setConnectionSettings(String method) {
        HttpURLConnection connection = null;
        List<String> key;

        try {
            key = Files.readAllLines(Path.of("./key.env"));
            URL endpoint = new URL("http://192.168.254.156:4567/v1/server/whitelist");
            connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("key", key.get(6));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
