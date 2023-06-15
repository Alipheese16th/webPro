package com.kist.portal.common.sbbs.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.sbbs.dto.SbbsDto;
import com.kist.portal.common.sbbs.service.SbbsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/sbbs")
@RequiredArgsConstructor
public class SbbsRestController {

  @NonNull
  private final SbbsService sampleService;

  @GetMapping("list")
  public List<SbbsDto.response> list(SbbsDto.param param) {
    return sampleService.selectList(param);
  }

  @GetMapping("info/{boardId}")
  public SbbsDto.info info(@PathVariable("boardId") String boardId) {
    return sampleService.info(boardId);
  }

  @GetMapping("insert")
  public void insert(SbbsDto.insertParam param) {
    sampleService.insertBoard(param);
  }

  @PostMapping("insertSelectKey")
  public String insertSelectKey(@RequestBody SbbsDto.insertParam param) {
    return sampleService.boardSelectKeyInsert(param);
  }

  @PostMapping("update")
  public void update(@RequestBody SbbsDto.updateParam param) {
    sampleService.updateBoard(param);
  }

  @DeleteMapping("delete/{boardId}")
  public void delete(@PathVariable("boardId") String boardId) {
    sampleService.deleteBoard(boardId);
  }

}
