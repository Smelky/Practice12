import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public class YoutubeVideo {
    private List<Comment> comments = Lists.newArrayList();
}
