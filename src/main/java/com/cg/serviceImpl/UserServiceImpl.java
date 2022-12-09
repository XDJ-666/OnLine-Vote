package com.cg.serviceImpl;

import com.cg.Dao.UserDao;
import com.cg.entity.Admin;
import com.cg.entity.Audience;
import com.cg.entity.Judge;
import com.cg.entity.Player;
import com.cg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    /**
     * 选手注册
     * @param player
     * @return
     */
    @Override
    public int insertUser(Player player) {
        int flag = userDao.insertPlayer(player);
        if(flag == 1){
            System.out.println("注册成功");
        }else{
            System.out.println("注册失败");
        }
        return flag;
    }

    @Override
    public int selectPlayerByPhone(String phone) {
        int flag = userDao.selectPlayerByPhone(phone);
        if(flag == 1){
            System.out.println("已经被添加");
        }else{
            System.out.println("未被添加");
        }
        return flag;
    }

    @Override
    public int deleteUser(int playerId) {
        int flag = userDao.deletePlayer(playerId);
        if(flag == 1){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
        return flag;
    }

    @Override
    public int insertAudience(Audience audience) {
        int flag = userDao.insertAudience(audience);
        if(flag == 1){
            System.out.println("观众注册成功");
        }else{
            System.out.println("观众注册失败");
        }
        return flag;
    }

    @Override
    public int selectAudienceByPhone(String phone) {
        Audience audience = userDao.selectAudienceByPhone(phone);
        if(audience==null){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int insertJudge(Judge judge) {
        int flag = userDao.insertJudge(judge);
        if(flag == 1){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }
        return flag;
    }

    @Override
    public Audience selectAudience(Audience audience) {
        Audience detail = userDao.selectAudience(audience);
        return detail;
    }

    @Override
    public Admin selectAdmin(Admin admin) {
        Admin result = userDao.selectAdmin(admin);
        return result;
    }

    @Override
    public Judge selectJudge(Judge judge) {
        Judge result = userDao.selectJudge(judge);
        return result;
    }

    @Override
    public List<Player> getAllPlayer() {
        List<Player> list = userDao.getAllPlayer();
        return list;
    }

    @Override
    public List<Judge> getAllJudge() {
        List<Judge> list = userDao.getAllJudge();
        return list;
    }

}
