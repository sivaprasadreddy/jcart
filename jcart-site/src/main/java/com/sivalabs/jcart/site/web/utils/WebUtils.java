/**
 * 
 */
package com.sivalabs.jcart.site.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 *
 */
public class WebUtils
{
	private WebUtils()
	{
	}
	public static final String IMAGES_PREFIX = "/products/images/";
	public static final String IMAGES_DIR_SUFFIX = "/jcart/products/";

	public static final String getImagesDirectory() {
		return System.getProperty("user.home") + IMAGES_DIR_SUFFIX;
	}

	public static String getURLWithContextPath(HttpServletRequest request)
	{
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}
}
