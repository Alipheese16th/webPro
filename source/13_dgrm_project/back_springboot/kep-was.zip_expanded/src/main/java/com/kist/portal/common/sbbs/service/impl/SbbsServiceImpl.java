package com.kist.portal.common.sbbs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kist.portal.common.sbbs.dto.SbbsDto;
import com.kist.portal.common.sbbs.dto.SbbsDto.insertParam;
import com.kist.portal.common.sbbs.dto.SbbsDto.response;
import com.kist.portal.common.sbbs.dto.SbbsDto.updateParam;
import com.kist.portal.common.sbbs.mapper.SbbsMapper;
import com.kist.portal.common.sbbs.service.SbbsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SbbsServiceImpl implements SbbsService {

  @NonNull
  private final SbbsMapper sampleMapper;

  @Override
  public SbbsDto.info info(String boardId) {
    return sampleMapper.info(boardId);
  }

  @Override
  public List<response> selectList(SbbsDto.param param) {
    return sampleMapper.selectList(param);
  }

  @Override
  public void insertBoard(insertParam param) {
    sampleMapper.insertBoard(param);
  }

  @Override
  public String boardSelectKeyInsert(insertParam param) {
    sampleMapper.boardSelectKeyInsert(param);

    return param.getBoardId();
  }

  @Override
  public void updateBoard(updateParam param) {
    sampleMapper.updateBoard(param);
  }

  @Override
  public void deleteBoard(String boardId) {
    sampleMapper.deleteBoard(boardId);
  }

}
