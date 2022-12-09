package com.cg;

import com.cg.entity.Battle_Player;
import com.cg.entity.Winner;
import com.cg.serviceImpl.BattleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OnlineVoteApplicationTests {
    @Autowired
    private BattleServiceImpl service;
    @Test
    void contextLoads() {
        List<Winner> list  = service.getWinnerInfo();
        for (Winner winner:list) {
            System.out.println(winner);
        }

    }

}
