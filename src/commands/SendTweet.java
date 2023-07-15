package commands;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.TweetParameters;
import io.github.redouane59.twitter.dto.tweet.TweetType;
import io.github.redouane59.twitter.dto.tweet.TweetParameters.Media;
import io.github.redouane59.twitter.dto.tweet.TweetParameters.TweetParametersBuilder;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class SendTweet extends Command{
	public SendTweet(MessageChannelUnion channel, String tweet, TwitterClient client) {
		super(jda, twitter);
		String parsedTweet = tweet.substring(tweet.indexOf(" "));
		
		if(parsedTweet.length() > 250) {
			parsedTweet = parsedTweet.substring(0, 249);
			channel.sendMessage("That is too long! Sending `" + parsedTweet + "` instead.").queue();
		}
		
		channel.sendMessage("The tweet has been sent B)").queue();
		TweetParameters tweetParams = TweetParameters.builder().text(parsedTweet).build();

		client.postTweet(tweetParams);
	}
}
