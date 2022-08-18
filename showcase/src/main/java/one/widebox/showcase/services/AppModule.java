package one.widebox.showcase.services;

import java.io.IOException;

import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.hibernate.HibernateSymbols;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.internal.services.RequestPageCache;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import one.widebox.foggyland.tapestry5.DateTranslator;
import one.widebox.foggyland.tapestry5.RedirectException;

public class AppModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(PasswordService.class);
		binder.bind(UserService.class);
	}

	// public static void contributeHibernateEntityPackageManager(
	// Configuration<String> configuration) {
	// configuration.add("one.widebox.showcase.entities");
	// }

	@Match(value = { "*Dao", "*Service", "*Sender", "*Logger" })
	public static void adviseTransactions(HibernateTransactionAdvisor advisor, MethodAdviceReceiver receiver) {
		advisor.addTransactionCommitAdvice(receiver);
	}

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0");
		configuration.override(SymbolConstants.COMPRESS_WHITESPACE, false);
		// ref: https://tapestry.apache.org/https.html
		// This page describes Tapestry's mechanism for automatically switching between
		// HTTP and HTTPS URLs. With the recent trend to have all web sites use HTTPS,
		// you will likely want to disable this behavior. To do so, set the
		// tapestry.secure-enabled configuration symbol to false (counter-intuitively).
		configuration.override(SymbolConstants.SECURE_ENABLED, false);
		configuration.override(SymbolConstants.PRODUCTION_MODE, false);
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "zh_TW");
		configuration.add(HibernateSymbols.EARLY_START_UP, "true");
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "showcase2019");
		configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, "false");
		configuration.add(ComponentParameterConstants.GRIDPAGER_PAGE_RANGE, "3");
	}

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void setupEnvironment(MappedConfiguration<String, Object> configuration) {
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "context:mybootstrap");
		configuration.add(SymbolConstants.OMIT_GENERATOR_META, true);
		configuration.add(SymbolConstants.MINIFICATION_ENABLED, false);
	}

	public RequestFilter buildTimingFilter(final Logger log) {
		return new RequestFilter() {
			public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
				long startTime = System.currentTimeMillis();
				try {
					return handler.service(request, response);
				} finally {
					long elapsed = System.currentTimeMillis() - startTime;
					log.trace("Request time: {} ms", elapsed);
				}
			}
		};
	}

	@Contribute(RequestHandler.class)
	public void addTimingFilter(OrderedConfiguration<RequestFilter> configuration, @Local RequestFilter filter) {
		configuration.add("Timing", filter);
	}

	// handle RedirectException
	public static RequestExceptionHandler decorateRequestExceptionHandler(final Object delegate,
			final ComponentSource componentSource, final Response response, final RequestPageCache requestPageCache,
			final PageRenderLinkSource linkSource, final ComponentClassResolver resolver) {
		return new RequestExceptionHandler() {
			public void handleRequestException(Throwable exception) throws IOException {
				String message = exception.getMessage();

				// 處理 CSRF Attack detected. Invalid client token: xxxxxxx
				if (message != null && message.contains("CSRF Attack detected.")) {
					response.sendError(403, "Forbidden");
					return;
				}
				
				// 處理 Forms require that the request method be POST and that the t:formdata
				// query parameter have values.
				// ref. URL:
				// https://tapestry.apache.org/specific-errors-faq.html#SpecificErrorsFAQ-Whydomylogscontain%22java.lang.RuntimeException:FormsrequirethattherequestmethodbePOSTandthatthet:formdataqueryparameterhavevalues%22?
				if (message != null && message.contains(
						"Forms require that the request method be POST and that the t:formdata query parameter have values")) {
					Link link = componentSource.getActivePage().getComponentResources().createEventLink("");
					String uri = link.toRedirectURI().replaceAll(":", "");
					response.sendRedirect(uri);
					return;
				}

				// 處理訪問 abstract page
				// 例子情況：Exception assembling root component of page Admin: Class
				// one.widebox.usjpayment.pages.AdminPage is abstract and can not be
				// instantiated.
				if (message != null && message.contains("Exception assembling root component of page")
						&& message.contains("is abstract and can not be instantiated.")) {
					response.sendError(404, "Page not found");
					return;
				}

				// 處理URL不合理參數
				// Coercion of XXXXXX to type java.lang.Long (via String --> Long) failed: For
				// input string: "XXXXXX"
				if (message != null && message.contains("Coercion of")
						&& message.contains("failed: For input string:")) {
					response.sendError(400, "Bad Request");
					return;
				}

				// 處理URL不合理參數 (枚舉類)
				// Coercion of XXXX to type one.widebox.dsedjqa.entities.enums.Language (via
				// String --> one.widebox.dsedjqa.entities.enums.Language) failed: Input 'XXXX'
				// does not identify a value from enumerated type
				// one.widebox.dsedjqa.entities.enums.Language.
				if (message != null && message.contains("Coercion of ")
						&& message.contains("does not identify a value from enumerated type")) {
					response.sendError(400, "Bad Request");
					return;
				}

				// check if wrapped
				/*
				 * Throwable cause = exception; if (exception.getCause() instanceof
				 * RedirectException) { cause = exception.getCause(); }
				 */

				// Better way to check if the cause is RedirectException.
				// Sometimes it's wrapped pretty deep..
				Throwable cause = exception;
				int i = 0;
				while (true) {
					if (cause == null || cause instanceof RedirectException || i > 1000) {
						break;
					}
					i++;
					cause = cause.getCause();
				}

				// check for redirect
				if (cause instanceof RedirectException) {
					// check for class and string
					RedirectException redirect = (RedirectException) cause;
					Link pageLink = redirect.getPageLink();
					if (pageLink == null) {
						// handle Class (see ClassResultProcessor)
						String pageName = redirect.getMessage();
						Class<?> pageClass = redirect.getPageClass();
						if (pageClass != null) {
							pageName = resolver.resolvePageClassNameToPageName(pageClass.getName());
						}

						// handle String (see StringResultProcessor)
						// Page page = requestPageCache.get(pageName);
						// pageLink = linkFactory
						// .createPageRenderLink(page, false);
						pageLink = linkSource.createPageRenderLink(pageName);
					}

					// handle Link redirect
					if (pageLink != null) {
						String redirectURI = pageLink.toRedirectURI();
						response.sendRedirect(redirectURI);
						return;
					}
				}

				// no redirect so pass on the exception
				((RequestExceptionHandler) delegate).handleRequestException(exception);
			}
		};
	}

	public static void contributeTranslatorSource(MappedConfiguration<Class<?>, Translator<?>> configuration) {
		configuration.add(java.util.Date.class, new DateTranslator());
	}

}
