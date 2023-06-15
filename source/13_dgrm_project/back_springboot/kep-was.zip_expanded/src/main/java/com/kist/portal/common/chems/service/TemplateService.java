package com.kist.portal.common.chems.service;

import com.kist.portal.common.chems.dto.TemplateDto;

import java.util.List;
import java.util.Map;

public interface TemplateService {
	
	public List<TemplateDto.response> selectTemplateList(TemplateDto.param param);

	public int insertTemplate(TemplateDto.insertParam param);

	public int updateTemplate(TemplateDto.insertParam param);

	public TemplateDto selectTemplateContent(TemplateDto.param param);
}
