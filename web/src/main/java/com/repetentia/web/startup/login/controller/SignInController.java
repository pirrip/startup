package com.repetentia.web.startup.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {

    @RequestMapping("/system/login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("/system/login");
        return mav;
    }

//	@Autowired
//	AuthenticationManager authenticationManager;
//
//	@RequestMapping("/system/process")
//	public String customLoginProcess(String username, String password) {
//        String status = null;
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//        try {
//            // AuthenticationManager 에 token 을 넘기면 UserDetailsService 가 받아 처리하도록 한다.
//            Authentication authentication = authenticationManager.authenticate(token);
//            // 실제 SecurityContext 에 authentication 정보를 등록한다.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (DisabledException | LockedException | BadCredentialsException e) {
//
//            if (e.getClass().equals(BadCredentialsException.class)) {
//                status = "invalid-password";
//            } else if (e.getClass().equals(DisabledException.class)) {
//                status = "locked";
//            } else if (e.getClass().equals(LockedException.class)) {
//                status = "disable";
//            } else {
//                status = "unknown";
//            }
//        }
//        return "redirect:/login?flag=" + status;
//	}
}