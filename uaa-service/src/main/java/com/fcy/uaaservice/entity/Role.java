package com.fcy.uaaservice.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Describe: 
 * @Author: fuchenyang 
 * @Date: 2021/2/24 16:08
 */
@Data
public class Role implements GrantedAuthority {
    
    private Integer id;
    
    private String name;

    private String describe;
    
    @Override
    public String getAuthority() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
