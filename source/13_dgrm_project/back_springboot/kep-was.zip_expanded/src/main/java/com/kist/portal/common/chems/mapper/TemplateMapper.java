package com.kist.portal.common.chems.mapper;

import com.kist.portal.common.chems.dto.TemplateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateMapper {

	public List<TemplateDto.response> selectTemplateList(TemplateDto.param param);

	public int insertTemplate(TemplateDto.insertParam param);

	public int updateTemplate(TemplateDto.insertParam param);
}
