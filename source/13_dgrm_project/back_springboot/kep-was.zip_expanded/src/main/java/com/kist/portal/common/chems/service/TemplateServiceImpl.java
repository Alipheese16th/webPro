package com.kist.portal.common.chems.service;

import com.kist.portal.common.chems.dto.TemplateDto;
import com.kist.portal.common.chems.mapper.TemplateMapper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	TemplateMapper mapper;

	@Override
	public List<TemplateDto.response> selectTemplateList(TemplateDto.param param) {
		return mapper.selectTemplateList(param);
	}

	@Override
	public int insertTemplate(TemplateDto.insertParam param) {
		return mapper.insertTemplate(param);
	}

	@Override
	public int updateTemplate(TemplateDto.insertParam param) {
		return mapper.updateTemplate(param);
	}

	@Override
	public TemplateDto selectTemplateContent(TemplateDto.param param) {
		String templateContent = null;
		if (param.getTmptId() != null && !"".equals(param.getTmptId())) {
			List<TemplateDto.response> templateList = mapper.selectTemplateList(param);
			if (templateList != null && templateList.size() > 0) {
				templateContent = (String) templateList.get(0).getTmptCntn();
			}
		}

		TemplateDto result = new TemplateDto();

		if (templateContent != null && !"".equals(templateContent) && param.getContent() != null) {
			// Initialize the engine.
			VelocityEngine engine = new VelocityEngine();
			engine.setProperty(Velocity.RESOURCE_LOADER, "string");
			engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
			engine.addProperty("string.resource.loader.repository.static", "false");
			engine.init();

			// Initialize my template repository. You can replace the "Hello $w" with your String.
			StringResourceRepository repo = (StringResourceRepository) engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
			repo.putStringResource("template", templateContent);

			// Set parameters for my template.
			VelocityContext context = new VelocityContext();
			context.put("param", param.getContent());

			// Get and merge the template with my parameters.
			Template template = engine.getTemplate("template");
			StringWriter writer = new StringWriter();
			template.merge(context, writer);

			result.setContent(writer.toString());
		}

		return result;
	}
}
