package com.cg.serviceImpl;

import com.cg.Dao.BattleDao;
import com.cg.Dao.UserDao;
import com.cg.entity.*;
import com.cg.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BattleServiceImpl implements BattleService {
    @Autowired
    private BattleDao battleDao;
    @Autowired
    private UserDao userDao;

    /**
     * 插入比赛信息
     * @param battle
     * @return
     */
    @Override
    public int insertBattleInfo(Battle battle) {
       int flag = battleDao.insertBattleInfo(battle);
        return flag;
    }

    /**
     * 获取所有比赛信息
     * @return
     */
    @Override
    public List<BattleTall> getAllBattleInfo() {
        List<BattleTall> battleList = battleDao.getAllBattleInfo();//对战表信息
        for (BattleTall battle: battleList) {
            Player playerA = userDao.getPlayerById(battle.getPlayerA());
            battle.setPlayerNameA(playerA.getPlayerName());
            Player playerB = userDao.getPlayerById(battle.getPlayerB());
            battle.setPlayerNameB(playerB.getPlayerName());
        }
        return battleList;
    }

    /**
     * 计算总成绩
     * @return
     */
    @Override
    public List<BattleTall> getTotalScore() {
        List<BattleTall> list = battleDao.getAllBattleInfo();
        Battle battle2 = new Battle();
        Winner winner =new Winner();
        for (BattleTall battle: list) {
            //1.获取选手姓名
            Player playerA = userDao.getPlayerById(battle.getPlayerA());
            battle.setPlayerNameA(playerA.getPlayerName());
            Player playerB = userDao.getPlayerById(battle.getPlayerB());
            battle.setPlayerNameB(playerB.getPlayerName());
            //2.获取选手得票以及评委评分
            float scoreA = battle.getScoreA();
            float scoreB = battle.getScoreB();
            int pollA = battle.getPollA();
            int pollB = battle.getPollB();
            float totalA = 0;
            float totalB = 0;
            int totalPoll = pollA+pollB;//总票数
            //3.判断投票是否结束 status=2为结束投票
            int status = battle.getStatus();
            if(status==2){
                float parta = ((float) pollA/totalPoll)*100;
                float partb = ((float) pollB/totalPoll)*100;
                DecimalFormat df = new DecimalFormat("0.0");
                float partA =Float.parseFloat(df.format(parta)) ;
                float partB =Float.parseFloat(df.format(partb)) ;//保留一位小数
                totalA = scoreA+partA;
                totalB = scoreB+partB;
                battle2.setTotalA(totalA);
                battle2.setTotalB(totalB);
                winner.setBattleId(battle.getBattleId());
                if(totalA>totalB){
                    battle.setWinner(battle.getPlayerA());
                    battle2.setBattleId(battle.getBattleId());
                    battle2.setWinner(battle.getPlayerA());
                    winner.setWinnerScore(totalA);
                    battleDao.updateWinnerScore(winner);
                    battleDao.updateWinner(battle2);
                    System.out.println("A获胜");
                }else{
                    battle.setWinner(battle.getPlayerB());
                    battle2.setBattleId(battle.getBattleId());
                    battle2.setWinner(battle.getPlayerB());
                    winner.setWinnerScore(totalB);
                    battleDao.updateWinnerScore(winner);
                    battleDao.updateWinner(battle2);
                    System.out.println("B获胜");
                }
                System.out.println("A"+partA+","+totalA);
                System.out.println("B"+partB+","+totalB);
            }else{
                System.out.println("投票未结束或未开始");
            }

        }
        return list;
    }

    /**
     * 修改比赛通道状态
     * @param battle
     * @return
     */
    @Override
    public Integer updateStatus(Battle battle) {
        Integer flag = 1;
        List<BattleTall> list = battleDao.getAllBattleInfo();
        for (BattleTall ba : list) {
           if(ba.getStatus()==0){
               flag = 0;
           }
        }
        return flag;
    }

    @Override
    public List<Battle_Player> getGamePlayerInfo() {
        //1.获取正在比赛的选手ID
        Battle battle  = battleDao.getPlayerByStatus();
        if(battle==null){
            return  null;
        }else{
            int playera = battle.getPlayerA();
            int playerb = battle.getPlayerB();
            int battleid = battle.getBattleId();
            Battle_Player playerA =userDao.getBattlePlayerById(playera);
            playerA.setBattleId(battleid);
            Battle_Player playerB =userDao.getBattlePlayerById(playerb);
            playerB.setBattleId(battleid);
            List<Battle_Player> list = new ArrayList<Battle_Player>();
            list.add(playerA);
            list.add(playerB);
            for (Battle_Player player:list) {
                System.out.println(player);
            }
            System.out.println();
            return list;

        }

    }

    @Override
    public int updateScore(Battle battle) {
        Battle battle1 = new Battle();
        float scorea = battle.getScoreA();
        float scoreb = battle.getScoreB();
        DecimalFormat df = new DecimalFormat("0.0");
        float scoreA =Float.parseFloat(df.format(scorea)) ;
        float scoreB =Float.parseFloat(df.format(scoreb)) ;
        battle1.setScoreA(scoreA);
        battle1.setScoreB(scoreB);
        int flag = battleDao.updateScore(battle1);
        if(flag==1){
            System.out.println("打分成功");
        }else{
            System.out.println("打分失败");
        }
        return flag;
    }

    @Override
    public int updatePoll(Battle battle,int tag) {
        //获取票数
        Battle battle1 = battleDao.getPlayerByStatus();
        int pollA = battle1.getPollA();
        int pollB = battle1.getPollB();
        if(tag ==1){
            System.out.println("serviceImplA:"+tag);
            int pollA1 = pollA+1;
            battle.setPollA(pollA1);
            battleDao.updatePollA(battle);
        }else if(tag==2){
            System.out.println("serviceImplB:"+tag);
            int pollB1 = pollB+1;
            battle.setPollB(pollB1);
            battleDao.updatePollB(battle);
        }
        return 0;
    }

    @Override
    public int isVote(Vote vote) {
        int result = battleDao.isVote(vote);
        if(result>=1){
            return 1;//已投
        }else{
            return 0;//未投
        }
    }

    @Override
    public int addPoll(Vote vote) {
        battleDao.addPoll(vote);
        return 0;
    }

    /**
     * 大屏显示
     * @return
     */
    @Override
    public List<Battle_Player> getShowPlayerInfo() {
        //1.获取正在比赛的选手ID
        Battle battle  = battleDao.getPlayerByStatus();
        if(battle==null){
            return null;
        }else{
            int playera = battle.getPlayerA();
            int playerb = battle.getPlayerB();
            int battleid = battle.getBattleId();
            Battle_Player playerA =userDao.getBattlePlayerById(playera);
            playerA.setBattleId(battleid);
            playerA.setScoreA(battle.getScoreA());
            playerA.setPollA(battle.getPollA());
            Battle_Player playerB =userDao.getBattlePlayerById(playerb);
            playerB.setBattleId(battleid);
            playerB.setScoreB(battle.getScoreB());
            playerB.setPollB(battle.getPollB());
            List<Battle_Player> list = new ArrayList<Battle_Player>();
            list.add(playerA);
            list.add(playerB);
            return list;
        }

    }

    /**
     * 获取胜利者信息
     * @return
     */
    @Override
    public List<Winner> getWinnerInfo() {
        List<Winner> winnerList= battleDao.getWinnerInfo();
        List<Winner> result = new ArrayList<>();
        List<BattleTall> battleList = battleDao.getAllBattleInfo();
        for (BattleTall battle:battleList) {
            for (Winner winner:winnerList) {
                if(winner.getBattleId()==battle.getBattleId()){
                    Player player =  userDao.getPlayerById(winner.getWinner());//获取胜者名称
                    winner.setPlayerName(player.getPlayerName());
                }
                if(result.size()<8){
                    result.add(winner);
                }
            }
        }
        return result;
    }


}
