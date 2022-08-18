package one.widebox.showdemo.internal;

import java.util.Arrays;
import java.util.List;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.util.TextStreamResponse;

public interface ApplicationConstants {

	public static final HttpError HTTP_ERROR_400 = new HttpError(400, "Bad Request");

	public static final HttpError HTTP_ERROR_404 = new HttpError(404, "Page Not Found");

	public static final StreamResponse INVALID_HTTP_METHOD_PAGE = new TextStreamResponse("text/plain",
			"invalid HTTP reuqest method.");

	public static final String DEFAULT_DATE_FORMAT = "YYYY-MM-DD";

	public static final List<String> FILE_TYPES = Arrays.asList("pdf");

	public static final Long FILE_SIZE_LIMIT = 10 * 1024 * 1024L;

}
