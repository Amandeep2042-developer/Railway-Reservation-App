package reservationsystem.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reservationsystem.dto.PassengerRequestDto;
import reservationsystem.dto.PassengerResponseDto;
import reservationsystem.entity.AppUser;
import reservationsystem.service.Interface.PassengerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/getPassenger/id/{id}")
    public ResponseEntity<PassengerResponseDto> getPassengerById(@PathVariable Long id){
        AppUser users = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return  ResponseEntity.ok(passengerService.getPassengerById(users.getId()));
}

@DeleteMapping("/deletePassenger/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
    passengerService.deletePassengerById(id);
    return ResponseEntity.noContent().build();
}
@PutMapping("/updatePassenger/{id}")
    public ResponseEntity<PassengerResponseDto> updatePassengerById(@PathVariable Long id, @RequestBody PassengerRequestDto userRequestDto){
    return ResponseEntity.ok(passengerService.updatePassengerById(id , userRequestDto));
}


@PatchMapping("/partialUpdatePassenger/{id}")
    public ResponseEntity<PassengerResponseDto> partiallyUpdatePassenger(@PathVariable Long id , @RequestBody PassengerRequestDto userRequestDto){
    return ResponseEntity.ok(passengerService.partiallyUpdatePassenger(id, userRequestDto));
}

}
