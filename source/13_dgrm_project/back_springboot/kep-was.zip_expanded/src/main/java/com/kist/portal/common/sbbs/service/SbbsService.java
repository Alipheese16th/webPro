package com.kist.portal.common.sbbs.service;

import java.util.List;

import com.kist.portal.common.sbbs.dto.SbbsDto;

public interface SbbsService {

  SbbsDto.info info(String boardId);

  List<SbbsDto.response> selectList(SbbsDto.param param);

  void insertBoard(SbbsDto.insertParam param);

  String boardSelectKeyInsert(SbbsDto.insertParam param);

  void updateBoard(SbbsDto.updateParam param);

  void deleteBoard(String boardId);

}
