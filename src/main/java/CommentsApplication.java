public class CommentsApplication {
    public static void main(String[] args) throws Exception {
        YoutubeCommentParser parser = new YoutubeCommentParser();
        parser.init();
        parser.getCommentsFromVideos("8n-qYptCU0A", "tsQsovPB3pU");
        for (YoutubeVideo video : parser.getListOfVideos()) {
            for (Comment comment : video.getComments()) {
                System.out.println("Author: " + comment.getAuthor());
                System.out.println("Comment: " +comment.getCommentText());
                System.out.println("Upload date: " + comment.getUploadDate());
                System.out.println("Likes: " + comment.getLikes());
                System.out.println("Edited: " + comment.isEdited());
                System.out.println("----------------------------------------");
            }
        }
    }
}
