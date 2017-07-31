package org.egov.egf.master.domain.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.domain.model.Pagination;
import org.egov.egf.master.TestConfiguration;
import org.egov.egf.master.domain.model.Bank;
import org.egov.egf.master.domain.model.BankSearch;
import org.egov.egf.master.domain.repository.BankRepository;
import org.egov.egf.master.domain.repository.FundRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

@Import(TestConfiguration.class)
@RunWith(SpringRunner.class)
public class BankServiceTest {

    @InjectMocks
    private BankService bankService;

    @Mock
    private SmartValidator validator;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private FundRepository fundRepository;

    private BindingResult errors = new BeanPropertyBindingResult(null, null);

    private List<Bank> banks = new ArrayList<>();

    @Before
    public void setup() {

    }

    @Test
    public final void testFetchRelated() {
        banks.add(getBank());
        bankService.fetchRelated(banks);
    }

    @Test
    public final void testAdd() {
        banks.add(getBank());
        bankService.add(banks, errors);
    }

    @Test
    public final void testUpdate() {
        banks.add(getBank());
        bankService.update(banks, errors);
    }

    @Test
    public final void testSearch() {
        List<Bank> search = new ArrayList<>();
        search.add(getBank());
        Pagination<Bank> expectedResult = new Pagination<>();
        expectedResult.setPagedData(search);
        when(bankRepository.search(any(BankSearch.class))).thenReturn(expectedResult);
        Pagination<Bank> actualResult = bankService.search(getBankSearch());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public final void testSave() {
        Bank expectedResult = getBank();
        when(bankRepository.save(any(Bank.class))).thenReturn(expectedResult);
        final Bank actualResult = bankService.save(getBank());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public final void test_Update() {
        Bank expectedResult = getBank();
        when(bankRepository.update(any(Bank.class))).thenReturn(expectedResult);
        final Bank actualResult = bankService.update(getBank());
        assertEquals(expectedResult, actualResult);
    }

    private Bank getBank() {
        return Bank.builder().id("1").code("code").description("description").build();
    }

    private BankSearch getBankSearch() {
        BankSearch bankSearch = new BankSearch();
        bankSearch.setPageSize(0);
        bankSearch.setOffset(0);
        bankSearch.setSortBy("Sort");
        return bankSearch;
    }
}