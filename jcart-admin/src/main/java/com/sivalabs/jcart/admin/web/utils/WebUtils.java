/**
 * 
 */
package com.sivalabs.jcart.admin.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 *
 */
public class WebUtils
{
    private WebUtils()
    {
        super();
    }

    public static final String IMAGES_PREFIX = "/products/images/";
    public static final String IMAGES_DIR = "D:/jcart/products/";

    public static String getURLWithContextPath(HttpServletRequest request)
    {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }

}
