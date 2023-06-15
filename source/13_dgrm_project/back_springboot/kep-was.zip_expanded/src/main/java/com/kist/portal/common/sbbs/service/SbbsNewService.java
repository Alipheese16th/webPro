package com.kist.portal.common.sbbs.service;

import java.util.List;

import com.kist.portal.common.sbbs.dto.SbbsNewDto;
import com.kist.portal.common.sbbs.dto.SbbsNewSearchDto;

public interface SbbsNewService {

  List<SbbsNewDto> selectSbbsNewList(SbbsNewSearchDto sbbsNewSearchDto);

  SbbsNewDto selectSbbsNewDetail(SbbsNewDto sbbsNewDto);
  
  void selectSbbsNewKey(SbbsNewDto sbbsNewDto);
  
  void insertSbbsNewBoard(SbbsNewDto sbbsNewDto);

  void updateSbbsNewBoard(SbbsNewDto sbbsNewDto);

  void deleteSbbsNewBoard(SbbsNewDto sbbsNewDto);

}
