package com.sprhib.controller.interceptor;

import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * This class is responsible for handling exceptions<br>
 * For POST request error message appears in the same view<br>
 * For GET request eroor message appears in error view.
 * @author mehmetsinan.sahin
 *
 */
@ControllerAdvice
public class DefaultControlAdvice {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(DefaultControlAdvice.class.getName());
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {

		//If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it 
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;
		
		String url = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		StringBuilder message = new StringBuilder(getMessage(e));
		LOGGER.log(Level.SEVERE, "Exception occur", e);			

		//redirect if the method just is POST
		if ("post".equalsIgnoreCase(req.getMethod())) {
			Map<String, Object> flash = RequestContextUtils.getOutputFlashMap(req);
			flash.put("message", message.toString());
			return new ModelAndView("redirect:" + url);
		} else {
			ModelAndView result = new ModelAndView ("error");
			result.addObject("message", message);
			return result;
		}
	}
	
	private String getMessage (Exception e) {
		if (e instanceof NullPointerException) {
			return "NullPointerException occured";
		}
		return e.getMessage() == null ? e.getClass().getName() : e.getMessage();
	}
}