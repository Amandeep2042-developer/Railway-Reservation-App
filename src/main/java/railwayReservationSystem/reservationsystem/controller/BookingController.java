package reservationsystem.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservationsystem.dto.BookingRequestDto;
import reservationsystem.dto.BookingResponseDto;
import reservationsystem.service.Interface.BookingService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
private final BookingService bookingService;

@PostMapping("/ticketBooking")
    public ResponseEntity<BookingResponseDto> bookTicket(@RequestBody BookingRequestDto bookingRequestDto){
    return  ResponseEntity.ok(bookingService.bookTicket(bookingRequestDto));
}

@GetMapping("/getAllBookings")
    public ResponseEntity<List<BookingResponseDto>> viewAllBookings(){
    return ResponseEntity.ok(bookingService.viewAllBookings());
}

@GetMapping("/viewBookingById/{id}")
public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id){
    return ResponseEntity.ok(bookingService.viewBookingById(id));
}

@DeleteMapping("/deleteBooking/{id}")
    public  ResponseEntity<Void> deleteBookingById(@PathVariable Long id){
    bookingService.deleteBookingById(id);
    return ResponseEntity.noContent().build();
}



}
