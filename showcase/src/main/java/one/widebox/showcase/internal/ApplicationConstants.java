package one.widebox.showcase.internal;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.util.TextStreamResponse;

public interface ApplicationConstants {

	public static final HttpError HTTP_ERROR_400 = new HttpError(400, "Bad Request");

	public static final HttpError HTTP_ERROR_404 = new HttpError(404, "Page Not Found");

	public static final StreamResponse INVALID_HTTP_METHOD_PAGE = new TextStreamResponse("text/plain",
			"invalid HTTP reuqest method.");

}
