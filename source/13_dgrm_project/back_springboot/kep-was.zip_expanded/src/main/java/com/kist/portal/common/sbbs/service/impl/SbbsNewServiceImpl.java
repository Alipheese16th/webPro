package com.kist.portal.common.sbbs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kist.portal.common.sbbs.dto.SbbsNewDto;
import com.kist.portal.common.sbbs.dto.SbbsNewSearchDto;
import com.kist.portal.common.sbbs.mapper.SbbsNewMapper;
import com.kist.portal.common.sbbs.service.SbbsNewService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SbbsNewServiceImpl implements SbbsNewService {

	@NonNull
	private final SbbsNewMapper sbbsNewMapper;

	@Override
	public List<SbbsNewDto> selectSbbsNewList(SbbsNewSearchDto sbbsNewSearchDto) {
		// TODO Auto-generated method stub
		return sbbsNewMapper.selectSbbsNewList(sbbsNewSearchDto);
	}
	
	@Override
	public SbbsNewDto selectSbbsNewDetail(SbbsNewDto sbbsNewDto) {
		// TODO Auto-generated method stub
		return sbbsNewMapper.selectSbbsNewDetail(sbbsNewDto);
	}
	
	@Override
	public void selectSbbsNewKey(SbbsNewDto sbbsNewDto) {
		// TODO Auto-generated method stub
		sbbsNewMapper.selectSbbsNewKey(sbbsNewDto);
	}
	
	@Override
	public void insertSbbsNewBoard(SbbsNewDto sbbsNewDto) {
		// TODO Auto-generated method stub
		sbbsNewMapper.insertSbbsNewBoard(sbbsNewDto);
	}
	
	@Override
	public void updateSbbsNewBoard(SbbsNewDto sbbsNewDto) {
		// TODO Auto-generated method stub
		sbbsNewMapper.updateSbbsNewBoard(sbbsNewDto);
	}
	
	@Override
	public void deleteSbbsNewBoard(SbbsNewDto sbbsNewDto) {
		// TODO Auto-generated method stub
		sbbsNewMapper.deleteSbbsNewBoard(sbbsNewDto);
	}

}
