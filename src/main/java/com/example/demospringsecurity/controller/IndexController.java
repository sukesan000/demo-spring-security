package com.example.demospringsecurity.controller;

import com.example.demospringsecurity.entity.SignupForm;
import com.example.demospringsecurity.entity.User;
import com.example.demospringsecurity.service.AccountService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class IndexController {
    private final AccountService accountService;

    public IndexController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value="/")
    public String index(@ModelAttribute("signup") SignupForm signupForm, Model model){
        List<User> userList = accountService.findAll();
        model.addAttribute("users", userList);
        return "index";
    }

    /**
     * アカウント登録処理
     *
     * @param signupForm サインアップフォームデータ
     * @param redirectAttributes リダイレクト先へメッセージを送るため
     * @return index (redirect)
     */
    @PostMapping(value="signup")
    public String signup(@ModelAttribute("signup") SignupForm signupForm, RedirectAttributes redirectAttributes){
        String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
        accountService.register(signupForm.getName(), signupForm.getEmail(), signupForm.getPassword(), roles);
        //リダイレクト先に渡したいパラメータを指定
        redirectAttributes.addFlashAttribute("successMessage", "アカウントの登録が完了しました");
        return "redirect:/";
    }
}
