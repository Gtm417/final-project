package ua.training.cruise.service;

import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import ua.training.cruise.entity.port.Excursion;
import ua.training.cruise.dto.CruiseDescriptionsDTO;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.exception.UnsupportedCruise;
import ua.training.cruise.repository.CruiseRepository;
import ua.training.cruise.repository.ExcursionRepository;
import ua.training.cruise.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CruiseService {

    private static final int ONE_HUNDRED_PERCENT = 100;

    private final CruiseRepository cruiseRepository;
    private final ExcursionRepository excursionRepository;
    private  final TicketRepository ticketRepository;

    @Autowired
    public CruiseService(CruiseRepository cruiseRepository,ExcursionRepository excursionRepository, TicketRepository ticketRepository) {
        this.cruiseRepository = cruiseRepository;
        this.excursionRepository = excursionRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Cruise> getAllCruises(){
        return cruiseRepository.findAll();
    }

    public List<Excursion> getAllExcursionsByCruiseId(Long id){
        return excursionRepository.findAllByCruiseID(id);
    }

    public Cruise changeCruiseDescription(Cruise cruise, CruiseDescriptionsDTO cruiseDescriptionsDTO){
        cruise.setDescription_ru(cruiseDescriptionsDTO.getDescription_ru());
        cruise.setDescription_eng(cruiseDescriptionsDTO.getDescription_eng());
        return cruiseRepository.save(cruise);
    }

    public Ticket addNewTicketToCruise(Ticket ticketDTO ,Cruise cruise) throws DataBaseDuplicateConstraint {
        try{
            ticketDTO.setPriceWithDiscount(calcTicketPriceWithDiscount(ticketDTO));
            ticketDTO.setCruise(cruise);
            return ticketRepository.save(ticketDTO);
        } catch (DataIntegrityViolationException ex){
            throw new DataBaseDuplicateConstraint(cruise.getCruiseName() + "already has ticket with name: ", ticketDTO.getTicketName());
        }
    }


    private long calcTicketPriceWithDiscount(Ticket ticket){
        return ticket.getPrice() -  Math.round(((double)ticket.getPrice() * ticket.getDiscount()/ONE_HUNDRED_PERCENT));
    }

    public List<Ticket> showAllTicketsForCruise(Cruise cruise){
        return ticketRepository.findAllByCruise(cruise);

    }

    public Cruise findCruiseById(Long id) throws UnsupportedCruise {
        return cruiseRepository.findById(id)
                .orElseThrow(() -> new UnsupportedCruise(id.toString()));
    }
}
