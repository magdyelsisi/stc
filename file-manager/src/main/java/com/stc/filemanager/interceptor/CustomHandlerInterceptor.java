package com.stc.filemanager.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.stc.filemanager.exception.ValidationException;
import com.stc.filemanager.util.Constants;

@Component
public class CustomHandlerInterceptor implements HandlerInterceptor {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9_.-]+@[A-Z0-9]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String emailHeader = request.getHeader(Constants.EMAIL_HEADER);
		if (emailHeader == null || emailHeader.isEmpty()) {
			throw new ValidationException("Missing X-User-Email header");
		}
		if (!validate(emailHeader)) {
			throw new ValidationException("Invalid Email address");
		}
		return true;
	}

	private boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.matches();
	}

}
