package com.fcy.scoreservice.controller;

import com.fcy.scoreservice.entity.dto.CourseAddRequestDto;
import com.fcy.scoreservice.entity.dto.CourseGetListRequestDto;
import com.fcy.scoreservice.entity.dto.StageStartDto;
import com.fcy.scoreservice.entity.dto.StageStopDto;
import com.fcy.scoreservice.service.CourseService;
import com.fcy.scoreservice.service.StageService;
import com.fcy.scoreservice.util.ResultUtil;
import com.fcy.scoreservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/28 20:23
 */
@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StageService stageService;

    /**
     * 根据条件获取课程列表
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("")
    public Result<Object> getCourseList(CourseGetListRequestDto query,
                                                 @RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("query", query);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(courseService.getCourseList(map));
    }

    /**
     * 根据条件获取课程列表
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("process")
    public Result<Object> getCourseListProcess(CourseGetListRequestDto query,
                                        @RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("query", query);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(courseService.getCourseListProcess(map));
    }

    /**
     * 获取小组评分课程列表
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("stageGroup")
    public Result<Object> getCourseListToGroupScore(CourseGetListRequestDto query,
                                                    @RequestParam("pagenum") String pageNum,
                                                    @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("query", query);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(courseService.getCourseListToGroupScore(map));
    }

    /**
     * 根据条件获取课程列表
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("group")
    public Result<Object> getCourseListAndGroup(CourseGetListRequestDto query,
                                        @RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("query", query);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(courseService.getCourseListAndGroup(map));
    }

    /**
     * 获取需要互评的课程列表
     * @return
     */
    @GetMapping("processMutual")
    public Result<Object> getCourseProcessMutual(@RequestParam("name") String name,
                                                 @RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("name", name);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(courseService.getCourseProcessMutual(map));
    }

    /**
     * 获取小组长需要评分的课程列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("leader")
    public Result<Object> getCourseLeader(@RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(courseService.getCourseLeader(map));
    }

    /**
     * 获取最终项目评分课程列表
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("teacher")
    public Result<Object> getCourseTeacher(CourseGetListRequestDto query,
                                           @RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("query", query);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(courseService.getCourseTeacher(map));
    }

    /**
     * 添加新课程
     * @return
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> addCourse(@RequestBody CourseAddRequestDto courseAddRequest) {
        courseService.addCourse(courseAddRequest);
        return ResultUtil.success();
    }

    /**
     * 开启互评阶段
     * @param map
     * @return
     */
    @PostMapping("openMutual")
    public Result<Object> openMutual(@RequestBody Map<String, Object> map) {
        Integer courseId = (Integer) map.get("courseId");
        courseService.openMutual(courseId);
        return ResultUtil.success();
    }

    /**
     * 结束当前阶段
     * @return
     */
    @PutMapping("stop")
    public Result<Object> stopCurrentStage(@RequestBody StageStopDto stageStop) {
        stageService.stopCurrentStage(stageStop);
        return ResultUtil.success();
    }

    /**
     * 开启新阶段
     * @param stageStart
     * @return
     */
    @PutMapping("start")
    public Result<Object> startNewStage(@RequestBody StageStartDto stageStart) {
        stageService.startNewStage(stageStart);
        return ResultUtil.success();
    }

}
