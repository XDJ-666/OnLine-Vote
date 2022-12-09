package com.cg.controller;

import com.cg.Dao.BattleDao;
import com.cg.entity.*;
import com.cg.serviceImpl.BattleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class BattleController {
    @Autowired
    private BattleServiceImpl service;
    @Autowired
    private BattleDao battleDao;
    /**
     * 添加比赛信息
     * @param playerA
     * @param playerB
     * @param judgeId
     * @param model
     * @param songA
     * @param songB
     * @return
     */
    @PostMapping("/battle/addBattle")
    public String inserBattleInfo(String playerA,String playerB,String judgeId, Model model,String songA,String songB,String type){
        int flag = 0;//标识
        Battle battle = new Battle();
        battle.setPlayerA(Integer.valueOf(playerA));
        battle.setPlayerB(Integer.valueOf(playerB));
        battle.setJudgeId(Integer.valueOf(judgeId));
        battle.setSongA(songA);
        battle.setSongB(songB);
        battle.setType(type);
        System.out.println(battle);
        if(battle.getPlayerA()== battle.getPlayerB()){
            flag = 1;
            model.addAttribute("flag",flag);
        }else{
            int result = service.insertBattleInfo(battle);
            model.addAttribute("flag",flag);
        }
        return"main/settingBattle";
    }

    /**
     * 设置通道状态
     * @param flag
     * @param Id
     * @return
     */
    @GetMapping("/battle/updateStatus/{flag}/{Id}")
    public String updateStatus(@PathVariable String flag, @PathVariable String Id){
        int battleId =Integer.parseInt(Id) ;
        Battle battle = new Battle();
        battle.setBattleId(battleId);
        if("0".equals(flag)){
            int status = 0;
            battle.setStatus(status);
           int tag = service.updateStatus(battle);
           if(tag==0){

           }else{
               battleDao.updateStatus(battle);
           }
        }else if("1".equals(flag)){
            int status = 1;
            battle.setStatus(status);
            battleDao.updateStatus(battle);
        }else if("2".equals(flag)){
            int status = 2;
            battle.setStatus(status);
            battleDao.updateStatus(battle);
        }
        return"main/manageVote";
    }

    /**
     * 投票
     * @param session
     * @param model
     * @param tag
     * @param battleid
     * @param PlayerId
     * @return
     */
    @GetMapping("/poll/addPoll/{tag}/{battleid}/{PlayerId}")
    public String addPoll(HttpSession session, Model model, @PathVariable String tag, @PathVariable String battleid, @PathVariable String PlayerId){
        int resultType = 0;
        Battle battle = new Battle();
        //1.获取数据
        int playerId= Integer.parseInt(PlayerId);
        int battleId = Integer.parseInt(battleid);
        int tag1 = Integer.parseInt(tag);
        Object audId = session.getAttribute("audId");
        int audId1= Integer.parseInt(audId==null?"":audId.toString());//观众ID
        Vote vote = new Vote();
        vote.setBattleId(battleId);
        vote.setAudId(audId1);
        vote.setPlayerId(playerId);
        //2.判断是否已经投票
        int count = service.isVote(vote);
        //3.对战
        if(count==0){
            battle.setBattleId(battleId);
            service.updatePoll(battle,tag1);
            service.addPoll(vote);
//            System.out.println("投的"+tag1);
            resultType=0;
        }else{
            resultType = 1;
        }
        List<Battle_Player> list = service.getGamePlayerInfo();
        model.addAttribute("players",list);
        model.addAttribute("resultType",resultType);
      return "main/vote";
    }

    /**
     * 大屏显示
     * @param model
     * @return
     */
    @GetMapping("/showResult")
    public String show(Model model){
        List<Battle_Player> list = service.getShowPlayerInfo();
        if(list==null){
            return "main/errResult";
        }else {
            model.addAttribute("players", list);
            return "main/result";
        }
    }
    @GetMapping("/endResult")
    public String result(Model model){
        List<BattleTall> battleTalls = battleDao.getAllBattleInfo();
        int flag = 0;
        for (BattleTall battle:battleTalls) {
            if(battle.getStatus()!=2){
                flag = 1;
            }
        }
        if(flag == 0){
            List<Winner> list = service.getWinnerInfo();
            model.addAttribute("players", list);
            return "main/sucessScore";
        }else{
            return "main/errResult2";
        }


    }

}
