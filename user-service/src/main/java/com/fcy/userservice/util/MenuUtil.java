package com.fcy.userservice.util;

import com.fcy.userservice.entity.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Describe: menu工具类
 * @Author: fuchenyang
 * @Date: 2021/3/3 17:18
 */
public class MenuUtil {

    /**
     * 将menuList转换为treeList
     * @param menuList
     * @return
     */
    public static List<Map<String, Object>> convertMenuListToTreeList(List<Menu> menuList) {
        // 用于返回树形数据的结构。
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        // 树节点
        Map<String, Object> treeNode = null;
        // 节点map，用于保存已处理过的menu节点
        Map<String, Map<String, Object>> id_nodeMap = new HashMap<>();

        for (Menu menu : menuList) {
            String menuId = menu.getMenuId();
            String menuName = menu.getMenuName();
            String parentId = menu.getParentId();
            String menuHref = menu.getMenuHref();

            treeNode = new HashMap<String, Object>();
            treeNode.put("menuId", menuId);
            treeNode.put("menuName", menuName);
            treeNode.put("menuHref", menuHref);

            id_nodeMap.put(menuId, treeNode);

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
