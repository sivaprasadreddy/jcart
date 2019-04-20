package com.sivalabs.jcart.admin.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 */
public class WebUtils {
    private WebUtils() {
    }

    private static final String IMAGES_DIR_SUFFIX = "/jcart/products/";

    public static String getImagesDirectory() {
        return System.getProperty("user.home") + IMAGES_DIR_SUFFIX;
    }

    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }
}
