package com.pan.blog.config;

import com.pan.blog.realm.AuthRealm;
import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

  @Bean
  public AuthRealm authRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
    AuthRealm authRealm = new AuthRealm();
    authRealm.setCredentialsMatcher(hashedCredentialsMatcher);
    return authRealm;
  }

  @Bean
  public SessionManager sessionManager() {
    return new MySessionManager();
  }

  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(authRealm(hashedCredentialsMatcher()));
    securityManager.setSessionManager(sessionManager());
    securityManager.setRememberMeManager(null);
    return securityManager;
  }

  @Bean
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    hashedCredentialsMatcher.setHashAlgorithmName("MD5");
    hashedCredentialsMatcher.setHashIterations(1024);
    return hashedCredentialsMatcher;
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    Map<String, String> filterChain = new HashMap<>();
    filterChain.put("/admin/login", "anon");
    filterChain.put("/admin/**", "authc");
    filterChain.put("/**", "anon");
    shiroFilterFactoryBean.setLoginUrl("/unauthorized");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
    return shiroFilterFactoryBean;
  }
}
