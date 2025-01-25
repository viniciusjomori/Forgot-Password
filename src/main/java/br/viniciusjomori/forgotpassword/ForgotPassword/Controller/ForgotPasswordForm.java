package br.viniciusjomori.forgotpassword.ForgotPassword.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("forgot-password")
public class ForgotPasswordForm {
    
    @GetMapping("/{id}")
    public String getForm(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "reset-password-form";
    }

}