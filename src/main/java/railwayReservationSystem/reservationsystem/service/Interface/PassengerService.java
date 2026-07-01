package reservationsystem.service.Interface;


import reservationsystem.dto.PassengerRequestDto;
import reservationsystem.dto.PassengerResponseDto;

import java.util.List;


public interface PassengerService {







    PassengerResponseDto getPassengerById(Long id);





    List<PassengerResponseDto> getAllUser();

    void deletePassengerById(Long id);

    PassengerResponseDto updatePassengerById(Long id, PassengerRequestDto userRequestDto);





    PassengerResponseDto partiallyUpdatePassenger(Long id, PassengerRequestDto userRequestDto);

    PassengerResponseDto onBoardNewAdmin(PassengerRequestDto passengerRequestDto, Long id);
}
