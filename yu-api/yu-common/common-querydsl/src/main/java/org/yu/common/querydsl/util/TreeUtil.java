package org.yu.common.querydsl.util;

import org.yu.common.querydsl.api.TreeDTO;
import org.yu.common.querydsl.api.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangxd
 * @date 2021-08-15 16:41
 */
public class TreeUtil {
    public static <T extends TreeDTO<T>> List<T> buildTreeByExtends(List<T> treeList) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeList) {
            if ("0".equals(treeNode.getPid())) {
                trees.add(treeNode);
            }
            for (T it : treeList) {
                if (it.getPid().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    public static <T extends TreeNode<T>> List<T> buildTree(List<T> treeList) {
        /*List<T> trees = new ArrayList<>();
        for (T treeNode : treeList) {
            if (treeNode.isRoot()) {
                trees.add(treeNode);
            }
            for (T it : treeList) {
                if (it.getPid().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }*/
        Map<String, List<T>> zoneByParentIdMap = treeList.stream().sorted(Comparator.comparing(TreeNode::getSort)).collect(Collectors.groupingBy(TreeNode::getPid));
        treeList.forEach(zone -> {
            zone.setChildren(zoneByParentIdMap.get(zone.getId()));
        });
        return treeList.stream().filter(TreeNode::isRoot).collect(Collectors.toList());
    }
}
