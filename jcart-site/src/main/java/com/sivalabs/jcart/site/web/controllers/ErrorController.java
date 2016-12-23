package com.sivalabs.jcart.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siva
 *
 */
@Controller
public class ErrorController extends AbstractJCartSiteController
{
    private static final String VIEWPREFIX = "error/";

    @Override
    protected String getHeaderTitle()
    {
        return "Error";
    }

    @RequestMapping("/403")
    public String accessDenied()
    {
        return VIEWPREFIX + "accessDenied";
    }

}
