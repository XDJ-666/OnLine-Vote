package com.cg.controller;

import com.cg.entity.MsgResult;
import com.cg.entity.Player;
import com.cg.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@SessionAttributes("path")
public class PlayerController {
    @Autowired
    private UserServiceImpl service;
    /**
     * 添加选手信息
     * @param myfile
     * @param request
     * @param phone
     * @param playerName
     * @return
     */
    @ResponseBody
    @PostMapping("/main/doAddPlayer")
    public MsgResult addPlayer(MultipartFile myfile, HttpServletRequest request,String phone,String playerName,Model model){
    //指定文件上传到服务器的位置（/uploadfiles）
        String realPath = "D://IDEA-Work/online-vote/src/main/resources/static/images";
        model.addAttribute("path",realPath);
        System.out.println(realPath);
    //为上传到服务器的文件取名，使用UUID防止文件名重复
        String filename= UUID.randomUUID().toString()+"-"+myfile.getOriginalFilename();
        System.out.println("filename"+filename);
        Player player = new Player();
        player.setImage(filename);
        player.setPhone(phone);
        player.setPlayerName(playerName);
        MsgResult result=new MsgResult();
        int flag = service.selectPlayerByPhone(phone);
    //将文件名和描述传递到成功页显示
        File targetFile=new File(realPath,filename);
    //判断路径中的文件夹是否存在，如果不存在，则创建
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try{
            //文件传输
            myfile.transferTo(targetFile);
            if(flag>=1){
                System.out.println("已经被注册");
                result.setMsg("该选手已经被添加！");
            }else{
                int flag1 = service.insertUser(player);
                result.setMsg("添加成功！");
            }
        }catch(Exception ex){
                result.setMsg("文件上传失败");
        }
        return result;
    }
}
