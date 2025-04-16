
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import commands.Command;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import services.DataHandler;
import services.TimeTracker;
import twitter4j.TwitterException;

public class GensoRanduul{
	public static void main(String[] args) throws LoginException, InterruptedException, IOException, TwitterException {
		DataHandler.loadEnv();
		
		// Building the JDA, logging the bot in, adding a Command class as a listener, and reading stored member/player data.
		JDA api = JDABuilder.createDefault(DataHandler.getDiscordToken()).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
		TwitterClient client = new TwitterClient(TwitterCredentials.builder()
                .accessToken(DataHandler.getTwitterOauth())
                .accessTokenSecret(DataHandler.getTwitterOauthSecret())
                .apiKey(DataHandler.getTwitterKey())
                .apiSecretKey(DataHandler.getTwitterSecret())
                .build());
				
		api.addEventListener(new Command(api, client));
		
		DataHandler.getStoredData();
		startTimeTracker();
	}
	
	public static void startTimeTracker(){
		TimeTracker timeTracker = new TimeTracker();
		ScheduledExecutorService timeExec = Executors.newSingleThreadScheduledExecutor();
		timeExec.scheduleAtFixedRate(timeTracker, 0, 5, TimeUnit.SECONDS);
	}
}
