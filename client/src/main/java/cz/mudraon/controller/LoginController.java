package cz.mudraon.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String getLogin(ModelMap model, HttpSession session) {
        model.addAttribute("badText", "");
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, params = {"user", "pass"})
    public String getLogin(@RequestParam String user, @RequestParam String pass, ModelMap model, HttpSession session) {

//        session.setAttribute("login", listOfOsoby.get(0).getLoginname());
//        session.setAttribute("oscis", (new Integer(listOfOsoby.get(0).getOscis())).toString());
//        session.setAttribute("prihlaseno", listOfOsoby.get(0).getJmJmeno() + " " + listOfOsoby.get(0).getJmPrijm());
//        model.addAttribute("dazarList", dazarList);
//        checkAndAddLoginAttrib(session, model);
//        return "index";
//        model.addAttribute("badText", texts.get("login.err.badNameOrPass"));
        return "login";
    }
}
