/**
 * 
 */
package com.sivalabs.jcart.site.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 * @author RajaDileep
 *
 */
public class WebUtils
{
    private WebUtils()
    {
    }

    public static final String IMAGES_PREFIX = "/products/images/";
    public static final String IMAGES_DIR = WebUtils.class.getClassLoader().getResource("static/assets/img/products/").getPath();

    public static String getURLWithContextPath(HttpServletRequest request)
    {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
    }

}
