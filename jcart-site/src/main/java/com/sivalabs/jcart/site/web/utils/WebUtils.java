/**
 * 
 */
package com.sivalabs.jcart.site.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 * @author rajakolli
 *
 */
public final class WebUtils
{
    public static final String IMAGES_PREFIX = "/products/images/";
    public static final String IMAGES_DIR = WebUtils.class.getClassLoader()
            .getResource("static/assets/img/products/").getPath();
    
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
