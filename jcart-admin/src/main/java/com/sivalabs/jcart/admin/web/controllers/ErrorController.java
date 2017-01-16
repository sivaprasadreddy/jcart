/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siva
 *
 */
@Controller
public class ErrorController
{
    private static final String VIEWPREFIX = "error/";

    @RequestMapping("/403")
    public String accessDenied()
    {
        return VIEWPREFIX + "accessDenied";
    }

}
