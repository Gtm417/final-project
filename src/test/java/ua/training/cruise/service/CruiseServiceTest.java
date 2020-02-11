package ua.training.cruise.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import ua.training.cruise.dto.CruiseDescriptionsDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.exception.UnsupportedCruise;
import ua.training.cruise.repository.CruiseRepository;
import ua.training.cruise.repository.ExcursionRepository;
import ua.training.cruise.repository.TicketRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CruiseServiceTest {

    public static final Cruise CRUISE = Cruise.builder().id(1L).cruiseName("test").description_eng("english").description_ru("russian").build();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Autowired
    CruiseService cruiseService;
    @MockBean
    CruiseRepository cruiseRepository;
    @MockBean
    ExcursionRepository excursionRepository;
    @MockBean
    TicketRepository ticketRepository;

    @Test
    public void getAllCruises() {
        cruiseService.getAllCruises();
        verify(cruiseRepository, times(1)).findAll();
    }

    @Test
    public void changeCruiseDescription() {
        CruiseDescriptionsDTO descriptionDTO = new CruiseDescriptionsDTO("First", "Second");
        Cruise cruise = Cruise.builder().id(1L).cruiseName("test").description_eng("english").description_ru("russian").build();


        when(cruiseRepository.save(cruise)).thenReturn(cruise);
        Cruise cruiseAfter = cruiseService.changeCruiseDescription(cruise, descriptionDTO);

        verify(cruiseRepository, times(1)).save(cruise);
        Assert.assertEquals(cruise.getDescription_eng(), cruiseAfter.getDescription_eng());
        Assert.assertEquals(cruise.getDescription_ru(), cruiseAfter.getDescription_ru());
    }

    @Test
    public void addNewTicketToCruise() throws DataBaseDuplicateConstraint {
        Ticket ticketDTO = Ticket.builder().ticketName("Test").price(1000L).discount(10).build();
        Cruise cruise = Cruise.builder().id(1L).cruiseName("test").description_eng("english").description_ru("russian").build();

        when(ticketRepository.save(ticketDTO)).thenReturn(ticketDTO);
        Ticket ticket = cruiseService.addNewTicketToCruise(ticketDTO, cruise);

        verify(ticketRepository, times(1)).save(ArgumentMatchers.eq(ticketDTO));
        Assert.assertEquals(ticketDTO.getCruise(), ticket.getCruise());
        Assert.assertEquals(900, ticket.getPriceWithDiscount());
    }


    @Test(expected = DataBaseDuplicateConstraint.class)
    public void addNewTicketToCruiseWhenThatTicketAlreadyExist() throws DataBaseDuplicateConstraint {
        Ticket ticketDTO = Ticket.builder().ticketName("Test").price(1000L).discount(10).build();
        Cruise cruise = Cruise.builder().id(1L).cruiseName("test").description_eng("english").description_ru("russian").build();

        when(ticketRepository.save(ticketDTO)).thenThrow(DataIntegrityViolationException.class);
        cruiseService.addNewTicketToCruise(ticketDTO, cruise);
    }

    @Test
    public void showAllTicketsForCruise() {
        cruiseService.showAllTicketsForCruise(CRUISE);
        verify(ticketRepository, times(1))
                .findAllByCruise(ArgumentMatchers.eq(CRUISE));
    }

    @Test
    public void findCruiseByIdIfExist() throws UnsupportedCruise {
        long id = 1L;
        when(cruiseRepository.findById(id)).thenReturn(Optional.of(CRUISE));
        Cruise cruise = cruiseService.findCruiseById(id);
        verify(cruiseRepository, times(1)).findById(ArgumentMatchers.eq(id));
        Assert.assertEquals(CRUISE, cruise);
    }

    @Test(expected = UnsupportedCruise.class)
    public void findCruiseByIdIfNotFound() throws UnsupportedCruise {
        when(cruiseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        cruiseService.findCruiseById(1L);
    }


}