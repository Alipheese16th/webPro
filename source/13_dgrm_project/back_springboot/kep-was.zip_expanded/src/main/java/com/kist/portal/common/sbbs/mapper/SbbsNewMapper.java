package com.kist.portal.common.sbbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kist.portal.common.sbbs.dto.SbbsNewDto;
import com.kist.portal.common.sbbs.dto.SbbsNewSearchDto;

@Mapper
public interface SbbsNewMapper {

	List<SbbsNewDto> selectSbbsNewList(SbbsNewSearchDto sbbsNewSearchDto);

	SbbsNewDto selectSbbsNewDetail(SbbsNewDto sbbsNewDto);

	void selectSbbsNewKey(SbbsNewDto sbbsNewDto);

	void insertSbbsNewBoard(SbbsNewDto sbbsNewDto);

	void updateSbbsNewBoard(SbbsNewDto sbbsNewDto);

	void deleteSbbsNewBoard(SbbsNewDto sbbsNewDto);
}
