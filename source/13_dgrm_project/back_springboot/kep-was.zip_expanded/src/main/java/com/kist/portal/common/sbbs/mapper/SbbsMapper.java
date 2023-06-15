package com.kist.portal.common.sbbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kist.portal.common.sbbs.dto.SbbsDto;

@Mapper
public interface SbbsMapper {

  SbbsDto.info info(String boardId);

  List<SbbsDto.response> selectList(SbbsDto.param param);

  void insertBoard(SbbsDto.insertParam param);

  void boardSelectKeyInsert(SbbsDto.insertParam param);

  void updateBoard(SbbsDto.updateParam param);

  void deleteBoard(String boardId);
}
