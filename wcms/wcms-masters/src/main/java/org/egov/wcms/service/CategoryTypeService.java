/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.wcms.service;

import java.util.List;

import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.egov.wcms.model.CategoryType;
import org.egov.wcms.repository.CategoryTypeRepository;
import org.egov.wcms.web.contract.CategoryTypeGetRequest;
import org.egov.wcms.web.contract.CategoryTypeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryTypeService {

    public static final Logger logger = LoggerFactory.getLogger(CategoryTypeService.class);

    @Autowired
    private LogAwareKafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private CategoryTypeRepository categoryRepository;

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    public CategoryTypeRequest create(final CategoryTypeRequest categoryRequest) {
        return categoryRepository.persistCreateCategory(categoryRequest);
    }

    public CategoryTypeRequest update(final CategoryTypeRequest categoryRequest) {
        return categoryRepository.persistModifyCategory(categoryRequest);
    }

    public CategoryType createCategory(final String topic, final String key,
            final CategoryTypeRequest categoryRequest) {
        categoryRequest.getCategoryType().setCode(codeGeneratorService.generate(CategoryType.SEQ_CATEGORY));
        try {
            kafkaTemplate.send(topic, key, categoryRequest);
        } catch (final Exception ex) {
            log.error("Exception Encountered : " + ex);
        }
        return categoryRequest.getCategoryType();
    }

    public CategoryType updateCategory(final String topic, final String key,
            final CategoryTypeRequest categoryRequest) {
        try {
            kafkaTemplate.send(topic, key, categoryRequest);
        } catch (final Exception ex) {
            log.error("Exception Encountered : " + ex);
        }
        return categoryRequest.getCategoryType();
    }

    public boolean getCategoryByNameAndCode(final String code, final String name, final String tenantId) {
        return categoryRepository.checkCategoryByNameAndCode(code, name, tenantId);
    }

    public List<CategoryType> getCategories(final CategoryTypeGetRequest categoryGetRequest) {
        return categoryRepository.findForCriteria(categoryGetRequest);

    }

}
