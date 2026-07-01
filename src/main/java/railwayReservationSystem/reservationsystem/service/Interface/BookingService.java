package reservationsystem.service.Interface;


import reservationsystem.dto.BookingRequestDto;
import reservationsystem.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto bookTicket(BookingRequestDto bookingRequestDto);

    List<BookingResponseDto> viewAllBookings();

    BookingResponseDto viewBookingById(Long id);

    void deleteBookingById(Long id);



}
