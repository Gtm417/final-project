package ua.training.cruise.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.training.cruise.entity.port.Excursion;
import ua.training.cruise.exception.EntityNotFound;
import ua.training.cruise.repository.ExcursionRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ExcursionService.class)
public class ExcursionServiceTest {
    public static final Excursion EXCURSION = Excursion.builder()
            .excursionName("name")
            .price(100L)
            .duration(4)
            .build();

    @Autowired
    ExcursionService excursionService;

    @MockBean
    ExcursionRepository excursionRepository;

    @Test
    public void findByIdIfExist() throws EntityNotFound {
        long id = 1L;
        when(excursionRepository.findById(id)).thenReturn(Optional.of(EXCURSION));
        Excursion excursion = excursionService.findById(id);

        verify(excursionRepository, times(1)).findById(ArgumentMatchers.eq(id));
        Assert.assertEquals(EXCURSION, excursion);
    }

    @Test(expected = EntityNotFound.class)
    public void findByIdIfNotFound() throws EntityNotFound {
        when(excursionRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        excursionService.findById(1L);
    }

    @Test
    public void getAllExcursionsByCruiseId() {
        long id = 1L;
        excursionService.getAllExcursionsByCruiseId(id);
        verify(excursionRepository, times(1))
                .findAllByCruiseID(ArgumentMatchers.eq(id));
    }
}