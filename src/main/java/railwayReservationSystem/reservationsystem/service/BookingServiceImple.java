package reservationsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reservationsystem.dto.BookingRequestDto;
import reservationsystem.dto.BookingResponseDto;
import reservationsystem.entity.Passenger;
import reservationsystem.entity.Booking;
import reservationsystem.entity.Train;
import reservationsystem.repository.PassengerRepo;
import reservationsystem.repository.BookingRepo;
import reservationsystem.repository.TrainRepo;
import reservationsystem.service.Interface.BookingService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImple implements BookingService {

private final ModelMapper modelMapper;
private final BookingRepo bookingRepo;
private  final PassengerRepo appUserRepo;
private  final TrainRepo trainRepo ;

    @Override
    public BookingResponseDto bookTicket(BookingRequestDto bookingRequestDto) {
        Passenger appUser = appUserRepo.findById(bookingRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found "));
    Train train = trainRepo.findById(bookingRequestDto.getTrainId()).orElseThrow(() -> new IllegalArgumentException("Train not found "));
Booking booking = new Booking();
booking.setAppUser(appUser);
booking.setTrain(train);

booking.setNumberOfSeats(bookingRequestDto.getNumberOfSeats());
booking.setTotalFare(bookingRequestDto.getTotalFare());
booking.setSeatNumber(bookingRequestDto.getSeatNumber());
bookingRepo.save(booking);
return modelMapper.map(booking, BookingResponseDto.class);


    }

    @Override
    public List<BookingResponseDto> viewAllBookings() {
      List<Booking> booking = bookingRepo.findAll();
      return booking.stream().map(booking1 -> modelMapper.map(booking1, BookingResponseDto.class)).toList();

    }

    @Override
    public BookingResponseDto viewBookingById(Long id) {
        if(!bookingRepo.existsById(id)){
            throw new IllegalArgumentException("No booking available by id "+id);
        }
        Optional<Booking> booking = bookingRepo.findById(id);
        return modelMapper.map(booking, BookingResponseDto.class);

    }

    @Transactional
    @Override
    public void deleteBookingById(Long id) {
        if (!bookingRepo.existsById(id)){
            throw new IllegalArgumentException("Booking not found by id "+id);
        }
        bookingRepo.deleteById(id);
    }






}
