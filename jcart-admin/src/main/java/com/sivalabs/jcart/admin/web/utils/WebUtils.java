/**
 * 
 */
package com.sivalabs.jcart.admin.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 *
 */
public final class WebUtils
{
    public static final String IMAGES_PREFIX = "/products/images/";
    public static final String IMAGES_DIR = "D:/jcart/products/";

    private WebUtils()
    {
        super();
    }

    public static String getURLWithContextPath(HttpServletRequest request)
    {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }

}
