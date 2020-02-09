package ua.training.cruise.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ua.training.cruise.repository.ExcursionRepository;

class ExcursionServiceTest {
    @Mock
    private ExcursionRepository excursionRepository;

    @Test
    void findById() {
        System.out.println(excursionRepository.findById(1l).get());
    }
}