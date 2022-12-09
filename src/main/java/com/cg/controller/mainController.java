package com.cg.controller;

import com.cg.entity.*;
import com.cg.serviceImpl.BattleServiceImpl;
import com.cg.serviceImpl.UserServiceImpl;
import com.sun.deploy.util.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes({"judgeId","audId","adId"})
public class mainController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BattleServiceImpl service;

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login/login";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "register/register";
    }

    @GetMapping("/main/manager")
    public String toManager(){
        return"main/manager";
    }

    @GetMapping("/main/addPlayer")
    public String toAddPlayer(){
        return "main/addPlayer";
    }

    /**
     * 设置比赛信息
     * @param model
     * @return
     */
    @GetMapping("/main/setBattle")
    public String toSetBattle(Model model){
        List<Player> list = userService.getAllPlayer();
        model.addAttribute("players",list);
        List<Judge> judges = userService.getAllJudge();
        model.addAttribute("judges",judges);
        return "main/settingBattle";
    }

    @GetMapping("/main/main")
    public String main(){
        return "main/main";
    }

    /**
     * 获取比赛信息并跳转
     * @param model
     * @return
     */
    @GetMapping("/main/manageVote")
    public String manageVote(Model model){
        List<BattleTall> list = service.getAllBattleInfo();
        model.addAttribute("battleList",list);
        return "main/manageVote";
    }

    /**
     * 获取成绩信息
     * @return
     */
    @GetMapping("/main/totalScore")
    public String showScore(Model model){
        List<BattleTall> list = service.getTotalScore();
        model.addAttribute("scores",list);
        return "main/totalScore";
    }
    @GetMapping("/main/toAddJudge")
    public String toAddJudge(){
        return "main/addJudge";
    }

    /**
     * 观众注册
     * @param audience
     * @param model
     * @return
     */
    @PostMapping("/doRegister")
    public String Register(Audience audience, Model model){
       int flag = userService.selectAudienceByPhone(audience.getPhone());
       if(flag==0){
           userService.insertAudience(audience);
           return "login/login";
       }else {
           model.addAttribute("flag",flag);
           return "register/register";
       }
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @param tag
     * @param model
     * @return
     */
    @PostMapping("/doLogin")
    public String Login(String userName,String password,String tag,Model model){
        int flag=0;
        if("0".equals(tag)){
            //观众
            Audience audience = new Audience();
            audience.setPhone(userName);
            audience.setPassword(password);
           Audience result = userService.selectAudience(audience);
           if(result!=null){
               //选手信息
               List<Battle_Player> list = service.getGamePlayerInfo();
               int audId = result.getAudId();
               model.addAttribute("audId",audId);
               model.addAttribute("players",list);
               if (list == null) {
                   return "main/errResult";
               }else{
                   return "main/vote";
               }
//               System.out.println("audId:"+audId);


           }else{
               flag = 1;
               model.addAttribute("flag",flag);
               return "login/login";
           }
        }else if("1".equals(tag)){
            //管理员
            Admin admin = new Admin();
            admin.setAccount(userName);
            admin.setPassword(password);
            Admin result = userService.selectAdmin(admin);
            model.addAttribute("account",userName);
            if(result!=null){
                int adId = result.getAdId();
                model.addAttribute("adId",adId);
                return "main/manager";
            }else{
                flag = 1;
                model.addAttribute("flag",flag);
                return "login/login";
            }
        } else if ("2".equals(tag)) {
            Judge judge = new Judge();
            judge.setAccount(userName);
            judge.setPassword(password);
            Judge result = userService.selectJudge(judge);
            if(result!=null){
                int judgeId = result.getJudgeId();
                System.out.println("maincontroller"+judgeId);
                List<Battle_Player> list = service.getGamePlayerInfo();
                if(list==null){
                    return "main/errResult";
                }else {
                    model.addAttribute("judgeId",judgeId);
                    model.addAttribute("players",list);
                    return "main/judge";
                }

            }else{
                flag = 1;
                model.addAttribute("flag",flag);
                return "login/login";
            }
        }
        return "login/login";
    }
    @GetMapping("/loginout")
    public String logOut(SessionStatus status){
        status.setComplete();
        return "login/login";
    }
}
