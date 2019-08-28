package com.zhq;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: activitispringboot
 * @description: 工作流启动类
 * @author: hq.zheng
 * @create: 2019-08-27 21:03
 */
@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration(exclude = {
org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
org.activiti.spring.boot.SecurityAutoConfiguration.class,
org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
public class ActivitiApplication {
  /*  private final static String GROUP_NAME = "request_user_group";           // 组名
    private final static String GROUP_TYPE = "security-role";                 // 组类型
    private final static String REQUEST_USER_NAME = "xz";                // 用户名*/
    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class,args);
    }

    /**
     * 设置 security 登陆的账号密码
     * @param identityService
     * @return
     */
   /* @Bean
    CommandLineRunner seedUsersAndGroups(final IdentityService identityService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

                // 创建组
                Group group = identityService.newGroup(GROUP_NAME);
                group.setName(GROUP_NAME);
                group.setType(GROUP_TYPE);
                identityService.saveGroup(group);

                // 创建用户
                User josh = identityService.newUser(REQUEST_USER_NAME);
                josh.setFirstName(REQUEST_USER_NAME);
                josh.setLastName(REQUEST_USER_NAME);
                josh.setPassword(REQUEST_USER_NAME);
                identityService.saveUser(josh);
                identityService.createMembership(REQUEST_USER_NAME, GROUP_NAME);
            }
        };
    }*/
}
