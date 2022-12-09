package com.cg.controller;

import com.cg.entity.Battle;
import com.cg.entity.Battle_Player;
import com.cg.entity.Judge;
import com.cg.entity.Player;
import com.cg.serviceImpl.BattleServiceImpl;
import com.cg.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class JudgeController {
    @Autowired
    private UserServiceImpl service;
    @Autowired
    private BattleServiceImpl battleService;
    @PostMapping("/main/addJudge")
    public String addJudge(Judge judge){
        service.insertJudge(judge);
        return "main/addJudge";
    }
    @PostMapping("/judge/addScore")
    public String addScore(Battle battle, HttpSession session, Model model){
        Object judgeid = session.getAttribute("judgeId");
        int judgeId= Integer.parseInt(judgeid==null?"":judgeid.toString());
        System.out.println("judge"+judgeId);
        battle.setJudgeId(judgeId);
        battleService.updateScore(battle);
        List<Battle_Player> list = battleService.getGamePlayerInfo();
        model.addAttribute("players",list);
        return "main/judge";
    }
}
