package fr.eni.ecole.trocenchere.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST}
, urlPatterns = { "/Connected/*" })
public class FilterConnected implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// get the request and response objects
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// avoid css and images being filtered

		String path = ((HttpServletRequest) request).getRequestURI();
		//        System.out.println("path : " + path);

		if (path.contains("/css") || path.contains( "/images" )) {
			//            System.out.println("SKIPPED : " + path);

			chain.doFilter( request, response );
			return;
		}

		// get session info
		HttpSession session = ((HttpServletRequest) request).getSession();
		String userName = (String) session.getAttribute("user");
		//		System.out.println("Session of " + userName);

		// Filter
		if (userName == null) {
			httpRequest.getRequestDispatcher("/Home").forward(request, response);
		}
		else {
			//laisser passer la requete
			chain.doFilter(httpRequest, httpResponse);
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
