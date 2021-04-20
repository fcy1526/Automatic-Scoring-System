package com.fcy.userservice.util;

import com.fcy.userservice.entity.Authority;
import com.fcy.userservice.exception.AutomaticScoringSystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @Describe: 权限工具类
 * @Author: fuchenyang
 * @Date: 2021/3/15 15:57
 */
public class AuthUtil {

    /**
     * 获取当前用户信息
     * @return
     */
    public static String getCurrentUserid() {
        // 获取用户认证信息。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 认证信息可能为空，因此需要进行判断。
        String principal = null;
        if (Objects.nonNull(authentication)) {
            principal = (String) authentication.getPrincipal();
        } else {
            throw new AutomaticScoringSystemException(INTERNAL_SERVER_ERROR, "获取当前用户信息失败!");
        }
        return principal;
    }

    /**
     * 将authList转换为treeList
     * @param authorities
     * @return
     */
    public static List<Map<String, Object>> convertAuthorityListToTreeList(List<Authority> authorities) {
        // 用于返回树形数据的结构。
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        // 树节点
        Map<String, Object> treeNode = null;
        // 节点map，用于保存已处理过的menu节点
        Map<String, Map<String, Object>> id_nodeMap = new HashMap<>();

        for (Authority auth : authorities) {
            String authId = auth.getAuthId();
            String authName = auth.getAuthName();
            String path = auth.getPath();
            String parentId = auth.getParentId();

            treeNode = new HashMap<String, Object>();
            treeNode.put("authId", authId);
            treeNode.put("authName", authName);
            treeNode.put("path", path);

            id_nodeMap.put(authId, treeNode);

            if (parentId.equals("0")) {
                //表示是最大的类，直接加到树形的list列表中
                treeList.add(treeNode);
            } else {
                //说明parentid不是0，寻找对应的父节点，将自身挂到父节点的children中。
                Map<String, Object> parentNode = id_nodeMap.get(parentId);
                List<Map<String, Object>> children = null;
                if (parentNode.get("children") == null) {
                    children = new ArrayList<>();
                } else {
                    children = (List<Map<String, Object>>) parentNode.get("children");
                }
                children.add(treeNode);
                parentNode.put("children", children);
            }
        }
        return treeList;
    }
}
