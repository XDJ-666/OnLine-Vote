package com.cg.service;

import com.cg.entity.Admin;
import com.cg.entity.Audience;
import com.cg.entity.Judge;
import com.cg.entity.Player;

import java.util.List;

public interface UserService {
    /**
     * 注册用户
     * @param player
     * @return
     */
    int insertUser(Player player);
    /**
     * 根据号码查询选手是否被添加
     * @param phone
     * @return
     */
    int selectPlayerByPhone(String phone);

    /**
     * 删除
     * @param playerId
     * @return
     */
    int deleteUser(int playerId);
    /**
     * 观众注册
     * @param audience
     * @return
     */
    int insertAudience(Audience audience);
    /**
     * 查找是否存在该guanz
     * @param phone
     * @return
     */
    int selectAudienceByPhone(String phone);
    /**
     * 添加评委账号
     * @param judge
     * @return
     */
    int insertJudge(Judge judge);
    /**
     * 查询观众账号信息
     * @param audience
     * @return
     */
    Audience selectAudience(Audience audience);
    /**
     * 查询管理员信息
     * @param admin
     * @return
     */
    Admin selectAdmin(Admin admin);
    /**
     * 查询评委信息
     * @param judge
     * @return
     */
    Judge selectJudge(Judge judge);
    /**
     * 获取所有选手信息
     * @return
     */
    List<Player> getAllPlayer();

    /**
     * 获取所有评委信息
     * @return
     */
    List<Judge> getAllJudge();
}
