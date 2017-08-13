package org.egov.tradelicense.web.controller;

import javax.validation.Valid;

import org.egov.tl.commons.web.requests.CategoryRequest;
import org.egov.tl.commons.web.requests.CategoryResponse;
import org.egov.tl.commons.web.requests.RequestInfoWrapper;
import org.egov.tradelicense.domain.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller have the all api's related to trade license category master
 * 
 * @author Pavan Kumar Kamma
 *
 */
@RestController
@RequestMapping(path = "/category/v1")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	/**
	 * Description : This api for creating category master
	 * 
	 * @param CategoryRequest
	 * @return CategoryResponse
	 * @throws Exception
	 */
	@RequestMapping(path = "/_create", method = RequestMethod.POST)
	public CategoryResponse createCategoryMaster(@Valid @RequestBody CategoryRequest categoryRequest) throws Exception {

		return categoryService.createCategoryMaster(categoryRequest);
	}

	/**
	 * Description : This api for updating category master
	 * 
	 * 
	 * @param CategoryRequest
	 * @return CategoryResponse
	 * @throws Exception
	 */
	@RequestMapping(path = "/_update", method = RequestMethod.POST)
	public CategoryResponse updateCategoryMaster(@Valid @RequestBody CategoryRequest categoryRequest) throws Exception {

		return categoryService.updateCategoryMaster(categoryRequest);
	}

	/**
	 * Description : This api for searching category master
	 * 
	 * @param requestInfo
	 * @param tenantId
	 * @param ids
	 * @param name
	 * @param code
	 * @param type
	 * @param categoryId
	 * @param pageSize
	 * @param offSet
	 * @return CategoryResponse
	 * @throws Exception
	 */
	@RequestMapping(path = "/_search", method = RequestMethod.POST)
	public CategoryResponse getCategoryMaster(@RequestBody RequestInfoWrapper requestInfo,
			@RequestParam(required = true) String tenantId, @RequestParam(required = false) Integer[] ids,
			@RequestParam(required = false) String name, @RequestParam(required = false) String code,
			@RequestParam(required = false) String active, @RequestParam(required = false) String type,
			@RequestParam(required = false) String businessNature, @RequestParam(required = false) Integer categoryId, 
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer offSet) throws Exception {

		return categoryService.getCategoryMaster(requestInfo.getRequestInfo(), tenantId.trim(), ids, name, code, active, type,
				businessNature, categoryId, pageSize, offSet);
	}
}