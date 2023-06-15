package com.kist.portal.common.sbbs.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.sbbs.dto.SbbsNewDto;
import com.kist.portal.common.sbbs.dto.SbbsNewSearchDto;
import com.kist.portal.common.sbbs.service.SbbsNewService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/sbbsNew")
@RequiredArgsConstructor
public class SbbsNewRestController {
	
	@NonNull
	private final SbbsNewService sbbsNewService;

	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResponseEntity<List<SbbsNewDto>> list(@RequestParam(value = "title",required = false) String title,
								 @RequestParam(value = "user",required = false) String user) {
	  
		SbbsNewSearchDto sbbsNewSearchDto = new SbbsNewSearchDto();
		sbbsNewSearchDto.setTitle(title);
		sbbsNewSearchDto.setUser(user);
		
		List<SbbsNewDto> list = sbbsNewService.selectSbbsNewList(sbbsNewSearchDto);
		
		return new ResponseEntity<List<SbbsNewDto>>(list, HttpStatus.OK);
	}

	@RequestMapping(value="info/{boardId}", method=RequestMethod.GET)
	public ResponseEntity<SbbsNewDto> selectSbbsNewDetail(@PathVariable(name = "boardId") String boardId) {
	  
	  	SbbsNewDto sbbsNewDto = new SbbsNewDto();
	  	sbbsNewDto.setBoardId(boardId);
	  	
	  	SbbsNewDto info = sbbsNewService.selectSbbsNewDetail(sbbsNewDto);
	  	
	  	return new ResponseEntity<SbbsNewDto>(info, HttpStatus.OK);
	}
  	
	@RequestMapping(value="insert", method=RequestMethod.GET)
	public void insert(SbbsNewDto sbbsNewDto) {
		sbbsNewService.insertSbbsNewBoard(sbbsNewDto);
	}

	@RequestMapping(value="insertSelectKey", method=RequestMethod.POST)
	public void selectSbbsNewKey(@RequestBody SbbsNewDto sbbsNewDto) {
		sbbsNewService.selectSbbsNewKey(sbbsNewDto);
	}

	@RequestMapping(value="update", method=RequestMethod.POST)
	public void update(@RequestBody SbbsNewDto sbbsNewDto) {
		sbbsNewService.updateSbbsNewBoard(sbbsNewDto);
	}

	@RequestMapping(value="delete", method=RequestMethod.POST)
	public void delete(@RequestBody SbbsNewDto sbbsNewDto) {
		sbbsNewService.deleteSbbsNewBoard(sbbsNewDto);
	}
}
