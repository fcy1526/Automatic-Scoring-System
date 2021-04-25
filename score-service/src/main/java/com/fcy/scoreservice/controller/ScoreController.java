package com.fcy.scoreservice.controller;

import com.fcy.scoreservice.entity.StudentScore;
import com.fcy.scoreservice.entity.dto.GroupScoreDto;
import com.fcy.scoreservice.entity.dto.ProportionDto;
import com.fcy.scoreservice.entity.dto.ScoreClassInfoDto;
import com.fcy.scoreservice.entity.dto.StudentScoreDto;
import com.fcy.scoreservice.service.ScoreService;
import com.fcy.scoreservice.util.ResultUtil;
import com.fcy.scoreservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/31 15:40
 */
@RestController
@RequestMapping("score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    /**
     * 获取学生成绩信息
     * @param scoreClassInfo
     * @return
     */
    @GetMapping("student")
    public Result<Object> getStudentList(ScoreClassInfoDto scoreClassInfo) {
        return ResultUtil.success(scoreService.getStudentList(scoreClassInfo));
    }

    /**
     * 获取最终项目评分学生列表
     * @param scoreClassInfo
     * @return
     */
    @GetMapping("final")
    public Result<Object> getFinalStudentList(ScoreClassInfoDto scoreClassInfo) {
        return ResultUtil.success(scoreService.getFinalStudentList(scoreClassInfo));
    }

    /**
     * 获取小组成员信息
     * @param stageId
     * @return
     */
    @GetMapping("group/{stageId}")
    public Result<Object> getStudentGroup(@PathVariable("stageId") Integer stageId) {
        return ResultUtil.success(scoreService.getStudentGroup(stageId));
    }

    /**
     * 获取小组长信息
     * @param courseId
     * @return
     */
    @GetMapping("teacher/{courseId}")
    public Result<Object> getStudentLeader(@PathVariable("courseId") Integer courseId) {
        return ResultUtil.success(scoreService.getStudentLeader(courseId));
    }

    /**
     * 获取小组成员信息
     * @param courseId
     * @param groupId
     * @return
     */
    @GetMapping("leader/{courseId}/{groupId}")
    public Result<Object> getStudentList(@PathVariable("courseId") Integer courseId,
                                         @PathVariable("groupId") Integer groupId) {
        return ResultUtil.success(scoreService.getStudentList(courseId, groupId));
    }

    /**
     * 获取小组评分详细信息
     * @return
     */
    @GetMapping("groupScore/{stageId}/{leaderId}")
    public Result<Object> getGroupScore(@PathVariable("stageId") Integer stageId,
                                               @PathVariable("leaderId") String leaderId) {
        return ResultUtil.success(scoreService.getGroupScore(stageId, leaderId));
    }

    /**
     * 学生成绩查询
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("userScore")
    public Result<Object> getUserScore(@RequestParam("name") String name,
                                       @RequestParam("pagenum") String pageNum,
                                       @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("name", name);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(scoreService.getUserScore(map));
    }

    @GetMapping("course/{courseId}")
    public Result<Object> getScore(@PathVariable("courseId") Integer courseId) {
        return ResultUtil.success(scoreService.getScore(courseId));
    }

    /**
     * 保存学生表现成绩
     * @param studentScoreDto
     * @return
     */
    @PostMapping("student")
    public Result<Object> saveStudentScore(@RequestBody StudentScoreDto studentScoreDto) {
        scoreService.saveStudentScore(studentScoreDto);
        return ResultUtil.success();
    }

    /**
     * 保存小组评分成绩
     * @return
     */
    @PostMapping("group/{stageId}/{leaderId}/{courseId}")
    public Result<Object> saveGroupScore(@PathVariable("stageId") Integer stageId,
                                         @PathVariable("leaderId") String leaderId,
                                         @PathVariable("courseId") Integer courseId,
                                         @RequestBody GroupScoreDto groupScoreDto) {
        scoreService.saveGroupScore(stageId, leaderId, courseId, groupScoreDto);
        return ResultUtil.success();
    }

    /**
     * 保存学生互评成绩
     * @return
     */
    @PostMapping("mutual/{stageId}")
    public Result<Object> saveGroupMutual(@PathVariable("stageId") Integer stageId,
                                          @RequestBody List<StudentScore> studentScores) {
        scoreService.saveGroupMutual(stageId, studentScores);
        return ResultUtil.success();
    }

    /**
     * 保存小组长评分
     * @return
     */
    @PostMapping("leader/{courseId}")
    public Result<Object> saveLeaderScore(@PathVariable("courseId") Integer courseId,
                                          @RequestBody List<StudentScore> studentScores) {
        scoreService.saveLeaderScore(courseId, studentScores);
        return ResultUtil.success();
    }

    /**
     * 保存最终项目评分
     * @return
     */
    @PostMapping("final/{courseId}")
    public Result<Object> saveTeacherScore(@PathVariable("courseId") Integer courseId,
                                           @RequestBody List<StudentScore> studentScores) {
        scoreService.saveTeacherScore(courseId, studentScores);
        return ResultUtil.success();
    }

    /**
     * 统计总分
     * @param courseId
     * @return
     */
    @PostMapping("count/{courseId}")
    public Result<Object> count(@PathVariable("courseId") Integer courseId,
                                @RequestBody ProportionDto proportionDto) {
        scoreService.count(courseId, proportionDto);
        return ResultUtil.success();
    }
}
