package com.sk.manage.web;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.sk.manage.ext.UserDto;
import com.sk.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
public class IndexController {

	@Autowired
	private UserService userService;


	@GetMapping(value = {"/","login","index"})
    public String index(){
    	log.info("login page");
		return "login";
	}

	@GetMapping(value = "logout")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}


	// 登录页面
	@PostMapping(value = "login/form")
	public String login(UserDto userVo, RedirectAttributes redirectAttributes, HttpSession session,
						HttpServletRequest request){

		log.info("user req is {}", JSONObject.toJSONString(userVo));
		Subject subject = SecurityUtils.getSubject();

		//当前用户已经登录,如果返回登录界面则退出
		if (subject.isAuthenticated()) {
			// subject.logout();
			return "redirect:/home";
		}

		String username = userVo.getUsername();
		String password = userVo.getPassword();

		try {
			password = SecureUtil.md5(password);
			log.info("password {}",password );
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			subject.login(token);
			String ip = request.getRemoteAddr();
			UserDto user = userService.queryUserExtByUserName(username);
			session.setAttribute("userId", String.valueOf(user.getId()));
			log.debug("ip is {}", ip);

			return "redirect:/home";
		} catch (LockedAccountException exception) {
			log.info("lock err is {}",exception.getMessage(),exception );
			redirectAttributes.addFlashAttribute("message", "该账户已经被冻结");
			redirectAttributes.addFlashAttribute("style", "alert-danger");
			return "redirect:/login";
		} catch (AuthenticationException exception) {
			log.info("auth err is {}",exception.getMessage(),exception );
			redirectAttributes.addFlashAttribute("message", "账号或密码错误");
			redirectAttributes.addFlashAttribute("style", "alert-danger");
			return "redirect:/login";
		}


	}
	
	
}
