package commands;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
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
		List<String> response = buildAttachments(attachments, client);
		TweetParameters tweetParams = buildTweet(parsedTweet, response, attachments);

		channel.sendMessage("Tweet Sent B)\nhttps://twitter.com/TweetsByAether/status/" + client.postTweet(tweetParams).getId()).queue();
	}

	//test
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

	private List<String> buildAttachments (List<Attachment> attachments, TwitterClient client){
		InputStream is;
		FileOutputStream fs;
		List<File> mediaFiles = new ArrayList<>();
		List<String> mediaIds = new ArrayList<>();

		try {
			for(int i = 0; i < attachments.size(); i++){
				is = attachments.get(i).getProxy().download().get();
				mediaFiles.add(new File("files/" + attachments.get(i).getFileName() + "tweet." + attachments.get(i).getFileExtension()));
				fs = new FileOutputStream(mediaFiles.get(i));
				is.transferTo(fs);

				is.close();
				fs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		
		for(File att : mediaFiles){
			if(att.getName().contains("gif")){
				 mediaIds.add(client.uploadChunkedMedia(att, MediaCategory.TWEET_GIF).get().getMediaId());
			}else if(att.getName().contains("mp4") || att.getName().contains("mov") || att.getName().contains("mkv")){
				mediaIds.add(client.uploadChunkedMedia(att, MediaCategory.TWEET_IMAGE).get().getMediaId());
			}else{
				mediaIds.add(client.uploadChunkedMedia(att, MediaCategory.TWEET_VIDEO).get().getMediaId());
			}
		}

		return mediaIds;
	}

	private TweetParameters buildTweet(String parsedTweet, List<String> mediaIds, List<Attachment> attachments){
		TweetParameters tweetParams = TweetParameters.builder().build();

		tweetParams.setText(parsedTweet);
		if(attachments.size() > 0) tweetParams.setMedia(Media.builder().mediaIds(mediaIds).build());

		return tweetParams;
	}
}
