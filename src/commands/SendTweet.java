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
import io.github.redouane59.twitter.dto.tweet.UploadMediaResponse;
import io.github.redouane59.twitter.dto.tweet.TweetParameters.Media;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class SendTweet extends Command{
	public SendTweet(MessageChannelUnion channel, Message content, TwitterClient client, Member mem) {
		super(jda, twitter);

		if(!isAuthorized(mem, "843923356494856192")){
            channel.sendMessage("Silly little person, you can't do that :skull:").queue();
            return;
        }

		List<Attachment> attachments = content.getAttachments();
		String parsedTweet = buildText(content.getContentDisplay(), channel);
		UploadMediaResponse response = buildAttachments(attachments, client);
		TweetParameters tweetParams = buildTweet(parsedTweet, response, attachments);

		
		channel.sendMessage("Tweet Sent B)\nhttps://twitter.com/TweetsByAether/status/" + client.postTweet(tweetParams).getId()).queue();
	}

	private String buildText(String str, MessageChannelUnion channel){
		String returnString = str;
		
		if(!str.contains(" ")){
			returnString = "";
		}else{
			returnString = str.substring(str.indexOf(" "));
		}
		
		
		if(returnString.length() > 250) {
			returnString = returnString.substring(0, 249);
			channel.sendMessage("That is too long! Sending `" + returnString + "` instead.").queue();
		}

		return returnString;
	}

	private UploadMediaResponse buildAttachments (List<Attachment> attachments, TwitterClient client){
		InputStream is;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UploadMediaResponse response = new UploadMediaResponse();

		try {
			if(attachments.size() > 0){
				is = attachments.get(0).getProxy().download().get();
				attachments.get(0).getProxy().download().get().transferTo(baos);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		if(attachments.size() > 0){
			if(attachments.get(0).getFileExtension().contains("gif")){
				response = client.uploadMedia(attachments.get(0).getFileName(), baos.toByteArray(), MediaCategory.TWEET_GIF);
			}else if(attachments.get(0).getFileExtension().contains("mp4") || attachments.get(0).getFileExtension().contains("mov") || attachments.get(0).getFileExtension().contains("mkv")){
				response = client.uploadMedia(attachments.get(0).getFileName(), baos.toByteArray(), MediaCategory.TWEET_VIDEO);
			}else{
				response = client.uploadMedia(attachments.get(0).getFileName(), baos.toByteArray(), MediaCategory.TWEET_IMAGE);
			}
		}

		return response;
	}

	private TweetParameters buildTweet(String parsedTweet, UploadMediaResponse response, List<Attachment> attachments){
		TweetParameters tweetParams = TweetParameters.builder().build();

		tweetParams.setText(parsedTweet);
		if(attachments.size() > 0) tweetParams.setMedia(Media.builder().mediaIds(Arrays.asList(response.getMediaId())).build());
		
		return tweetParams;
	}
}
