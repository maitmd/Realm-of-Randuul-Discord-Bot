package commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.MediaCategory;
import io.github.redouane59.twitter.dto.tweet.TweetParameters;
import io.github.redouane59.twitter.dto.tweet.TweetType;
import io.github.redouane59.twitter.dto.tweet.UploadMediaResponse;
import io.github.redouane59.twitter.dto.tweet.Attachments.AttachmentsBuilder;
import io.github.redouane59.twitter.dto.tweet.TweetParameters.Media;
import io.github.redouane59.twitter.dto.tweet.TweetParameters.TweetParametersBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.utils.AttachedFile;
import net.dv8tion.jda.api.utils.AttachmentProxy;
import net.dv8tion.jda.api.utils.FileUpload;

public class SendTweet extends Command{
	public SendTweet(MessageChannelUnion channel, Message tweet, TwitterClient client) {
		super(jda, twitter);
		String parsedTweet = tweet.getContentDisplay().substring(tweet.getContentDisplay().indexOf(" "));
		List<Attachment> attachments = tweet.getAttachments();

		if(parsedTweet.length() > 250) {
			parsedTweet = parsedTweet.substring(0, 249);
			channel.sendMessage("That is too long! Sending `" + parsedTweet + "` instead.").queue();
		}
		
		
		InputStream is;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			is = attachments.get(0).getProxy().download().get();
			byte[] buf = new byte[1024];
			int k;
			while((k = is.read(buf)) > 0){
				baos.write(buf, 0, k);
			}
		} catch (IOException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UploadMediaResponse response = client.uploadMedia(attachments.get(0).getFileName(), baos.toByteArray(), MediaCategory.TWEET_IMAGE);
		TweetParameters tweetParams = TweetParameters.builder().media(Media.builder().mediaIds(Arrays.asList(response.getMediaId())).build()).text(parsedTweet).build();
		client.postTweet(tweetParams);
	}
}
