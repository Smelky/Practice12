import com.google.api.client.util.DateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Comment {
    private String author;
    private String commentText;
    private long likes;
    private DateTime uploadDate;
    private boolean edited;
}
