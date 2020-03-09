package com.pan.blog.realm;

import com.pan.blog.entities.User;
import com.pan.blog.service.UserService;
import java.util.Date;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthRealm extends AuthorizingRealm {

  @Autowired
  private UserService userService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    return null;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    String username = (String) authenticationToken.getPrincipal();
    User user = userService.findUserByUsername(username);

    if (user == null) {
      throw new UnknownAccountException();
    }
    user.setLastLogin(new Date());
    ByteSource credentialSalt = ByteSource.Util.bytes(username);
    return new SimpleAuthenticationInfo(username,
        user.getPassword(), credentialSalt, getName());
  }
}
