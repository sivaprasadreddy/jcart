/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import static com.sivalabs.jcart.admin.web.utils.MessageCodes.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.admin.web.utils.WebUtils;
import com.sivalabs.jcart.common.services.EmailService;
import com.sivalabs.jcart.security.SecurityService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */
@Slf4j
@Controller
public class UserAuthController extends AbstractJCartAdminController
{
    private static final String viewPrefix = "public/";

    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected EmailService emailService;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected TemplateEngine templateEngine;

    @Override
    protected String getHeaderTitle()
    {
        return "User";
    }

    @RequestMapping(value = "/forgotPwd", method = RequestMethod.GET)
    public String forgotPwd()
    {
        return viewPrefix + "forgotPwd";
    }

    @RequestMapping(value = "/forgotPwd", method = RequestMethod.POST)
    public String handleForgotPwd(HttpServletRequest request,
            RedirectAttributes redirectAttributes)
    {
        String email = request.getParameter("email");
        try
        {
            String token = securityService.resetPassword(email);
            String resetPwdURL = WebUtils.getURLWithContextPath(request)
                    + "/resetPwd?email=" + email + "&token=" + token;
            log.debug(resetPwdURL);
            this.sendForgotPasswordEmail(email, resetPwdURL);
            redirectAttributes.addFlashAttribute("msg",
                    getMessage(INFO_PASSWRD_RESET_LINK_SENT));
        }
        catch (JCartException e)
        {
            log.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }
        return "redirect:/forgotPwd";
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
    public String resetPwd(HttpServletRequest request, Model model,
            RedirectAttributes redirectAttributes)
    {
        String email = request.getParameter("email");
        String token = request.getParameter("token");

        boolean valid = securityService.verifyPasswordResetToken(email, token);
        if (valid)
        {
            model.addAttribute("email", email);
            model.addAttribute("token", token);
            return viewPrefix + "resetPwd";
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg",
                    getMessage(ERROR_INVALID_PASSWRD_RESET_REQUEST));
            return "redirect:/login";
        }

    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public String handleResetPwd(HttpServletRequest request, Model model,
            RedirectAttributes redirectAttributes)
    {
        try
        {
            String email = request.getParameter("email");
            String token = request.getParameter("token");
            String password = request.getParameter("password");
            String confPassword = request.getParameter("confPassword");
            if (!password.equals(confPassword))
            {
                model.addAttribute("email", email);
                model.addAttribute("token", token);
                model.addAttribute("msg",
                        getMessage(ERROR_PASSWRD_CONF_PASSWRD_MISMATCH));
                return viewPrefix + "resetPwd";
            }
            String encodedPwd = passwordEncoder.encode(password);
            securityService.updatePassword(email, token, encodedPwd);

            redirectAttributes.addFlashAttribute("msg",
                    getMessage(INFO_PASSWRD_UPDATED_SUCCESS));
        }
        catch (JCartException e)
        {
            log.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("msg",
                    getMessage(ERROR_INVALID_PASSWRD_RESET_REQUEST));
        }
        return "redirect:/login";
    }

    protected void sendForgotPasswordEmail(String email, String resetPwdURL)
    {
        try
        {

            // Prepare the evaluation context
            final Context ctx = new Context();
            ctx.setVariable("resetPwdURL", resetPwdURL);

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.templateEngine
                    .process("forgot-password-email", ctx);

            emailService.sendEmail(email, getMessage(LABEL_PASSWRD_RESET_EMAIL_SUBJECT),
                    htmlContent);
        }
        catch (JCartException e)
        {
            log.error(e.getMessage(), e);
        }
    }
}
