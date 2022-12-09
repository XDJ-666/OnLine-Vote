package com.cg.service;

import com.cg.entity.*;

import java.util.List;

public interface BattleService {
    /**
     * 添加比赛信息
     * @param battle
     * @return
     */
    int insertBattleInfo(Battle battle);

    /**
     * 获取比赛信息
     * @return
     */
    List<BattleTall> getAllBattleInfo();

    /**
     * 获取比赛成绩
     * @return
     */
    List<BattleTall> getTotalScore();
    /**
     * 修改通道状态
     * @param battle
     * @return
     */
    Integer updateStatus(Battle battle);

    /**
     * 获取正在比赛的选手信息
     * @return
     */
    List<Battle_Player> getGamePlayerInfo();
    /**
     * 评委评分
     * @param battle
     * @return
     */
    int updateScore(Battle battle);
    /**
     * 修改投票
     * @param battle
     * @return
     */
    int updatePoll(Battle battle,int tag);
    /**
     * 查询是否投票
     * @param vote
     * @return
     */
    int isVote(Vote vote);
    /**
     * 添加投票记录
     * @param vote
     * @return
     */
    int addPoll(Vote vote);

    /**
     * 获取正在比赛的选手信息
     * @return
     */
    List<Battle_Player> getShowPlayerInfo();

    /**
     * 获取胜利者信息
     * @return
     */
    List<Winner> getWinnerInfo();

}
