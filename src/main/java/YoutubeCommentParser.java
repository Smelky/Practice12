import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class YoutubeCommentParser {
    private static YouTube youtube;
    private List<YoutubeVideo> listOfVideos = Lists.newArrayList();
    private static List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.force-ssl");

    public void init() throws Exception {
        Credential credential = null;
        try {
            credential = Auth.authorize(scopes, "commentThreads");
        } catch (IOException e) {
            e.printStackTrace();
        }
        youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                .setApplicationName("youtube-comments-parser").build();
    }

    public void getCommentsFromVideos(String... videoIds) {
        for (String videoId : videoIds) {
            List<CommentThread> videoComments = getCommentThreads(videoId);
            CommentSnippet snippet;
            YoutubeVideo youtubeVideo = new YoutubeVideo();

            for (CommentThread videoComment : videoComments) {
                Comment comment = new Comment();
                snippet = videoComment.getSnippet().getTopLevelComment().getSnippet();
                comment.setAuthor(snippet.getAuthorDisplayName());
                comment.setCommentText(snippet.getTextDisplay());
                comment.setLikes(snippet.getLikeCount());
                comment.setUploadDate(snippet.getPublishedAt());
                comment.setEdited(!(snippet.getUpdatedAt().equals(snippet.getPublishedAt())));
                youtubeVideo.getComments().add(comment);
            }
            listOfVideos.add(youtubeVideo);
        }
    }

    private static List<CommentThread> getCommentThreads(String videoId) {
        CommentThreadListResponse videoCommentsListResponse = null;
        try {
            videoCommentsListResponse = youtube.commentThreads().list("snippet")
                    .setVideoId(videoId).setTextFormat("plainText").execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoCommentsListResponse.getItems();
    }

    public List<YoutubeVideo> getListOfVideos() {
        return listOfVideos;
    }
}
