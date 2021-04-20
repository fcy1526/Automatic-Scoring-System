package com.fcy.uaaservice.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @Describe: 令牌增强 在jwtToken中添加userid
 * @Author: fuchenyang
 * @Date: 2021/3/2 22:39
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
//        User user = (User) oAuth2Authentication.getPrincipal();
//        Map<String, Object> additionMap = new HashMap<>();
//        暂时弃用这种方式，因为单独从jwt中获取额外添加的数据比较麻烦，且security本身有提供获取Authentication（当前用户信息）对象的方法，
//        因此直接通过username来获取相关数据更为简洁优雅。同时也更符合security权限验证的思路（通过用户名来验证权限：loadUserByUsername）
//        additionMap.put("userid", user.getUserid());
//        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionMap);
        return oAuth2AccessToken;
    }
}
