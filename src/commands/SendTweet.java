package commands;

import io.github.redouane59.twitter.TwitterClient;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class SendTweet extends Command{
	public SendTweet(MessageChannelUnion channel, String tweet, TwitterClient client) {
		super(jda, twitter);
		channel.sendMessage("The tweet has been sent B)").queue();
		client.postTweet(tweet.substring(tweet.indexOf(" ")));
	}
}
