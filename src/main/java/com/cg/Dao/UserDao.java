package com.cg.Dao;

import com.cg.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface UserDao {
    /**
     * 添加选手
     * @param player
     * @return
     */
    int insertPlayer(Player player);

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
    int deletePlayer(int playerId);

    /**
     * 观众注册
     * @param audience
     * @return
     */
    int insertAudience(Audience audience);

    /**
     * 查找是否存在该观众
     * @param phone
     * @return
     */
   Audience selectAudienceByPhone(String phone);

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
     * 根据ID查询选手
     * @param playerId
     * @return
     */
    Player getPlayerById(int playerId);

    /**
     *
     * @param playerId
     * @return
     */
    Battle_Player getBattlePlayerById(int playerId);
    /**
     * 获取所有评委信息
     * @return
     */
   List<Judge> getAllJudge();


}
