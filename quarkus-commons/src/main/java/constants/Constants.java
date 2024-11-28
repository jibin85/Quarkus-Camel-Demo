package constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final String MEDIA_TYPE_TEXT = "text/plain";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_REPOSITORY = "userRepository";
}
