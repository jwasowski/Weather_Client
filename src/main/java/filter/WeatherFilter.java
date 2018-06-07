package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/weather")
public class WeatherFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String city = request.getParameter("city");
		String country = request.getParameter("country");

		if (city == null || city.equals("") || country == null || country.equals("")) {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect("/restapp/index.jsp");
		} else
			chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
