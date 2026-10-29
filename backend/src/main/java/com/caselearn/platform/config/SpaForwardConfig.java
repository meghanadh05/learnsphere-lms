package com.caselearn.platform.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaForwardConfig {
    @RequestMapping(value = {"/{path:^(?!api$)[^\\.]*}", "/{path1:^(?!api$)[^\\.]*}/{path2:[^\\.]*}"})
    public String forward() { return "forward:/index.html"; }
}
