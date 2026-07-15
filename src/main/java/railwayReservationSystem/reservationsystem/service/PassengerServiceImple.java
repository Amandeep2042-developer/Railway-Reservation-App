package reservationsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reservationsystem.Enum.RoleType;
import reservationsystem.dto.PassengerRequestDto;
import reservationsystem.dto.PassengerResponseDto;
import reservationsystem.entity.AppUser;
import reservationsystem.entity.Passenger;

import reservationsystem.repository.PassengerRepo;
import reservationsystem.repository.UserRepo;
import reservationsystem.service.Interface.PassengerService;

import java.util.List;
import java.util.Optional;

@EnableMethodSecurity
@Service
@RequiredArgsConstructor
public class PassengerServiceImple implements PassengerService {

    private final ModelMapper modelMapper;

    private final PassengerRepo passengerRepo;
    private final UserRepo userRepo;


    @Override
    public PassengerResponseDto getPassengerById(Long id) {
        if(!passengerRepo.existsById(id)){
            throw  new IllegalArgumentException("Passenger not found by id "+ id);
        }
        Optional<Passenger> appUser = passengerRepo.findById(id);
        return modelMapper.map(appUser, PassengerResponseDto.class);
    }

@PreAuthorize("hasRole('ADMIN')  || #userId == authentication.principal.id")
    @Override
    public List<PassengerResponseDto> getAllUser() {
        List<Passenger> appUser = passengerRepo.findAll();

        return appUser.stream().map(appUser1 -> modelMapper.map(appUser1 , PassengerResponseDto.class)).toList();
    }

    @Transactional
    @Override
    public void deletePassengerById(Long id) {
        if(!passengerRepo.existsById(id)){
            throw  new IllegalArgumentException("Passenger not found by id "+ id);
        }
        passengerRepo.deleteById(id);
    }

    @Transactional
    @Override
    public PassengerResponseDto updatePassengerById(Long id, PassengerRequestDto userRequestDto) {
        Passenger appUsers = passengerRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("User not found by id "+id));
        modelMapper.map(userRequestDto,appUsers);
        passengerRepo.save(appUsers);
        return   modelMapper.map(appUsers, PassengerResponseDto.class);
    }

    @Transactional
    @Override
    public PassengerResponseDto partiallyUpdatePassenger(Long id, PassengerRequestDto userRequestDto) {
        Passenger appUser = passengerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found by id "+id));

        if(userRequestDto.getName() != null)
        {
            appUser.setName(userRequestDto.getName());
        }

        if(userRequestDto.getUsername() != null){
            appUser.setUsername(userRequestDto.getUsername());
        }
        if(userRequestDto.getPassword() != null){
            appUser.setPassword(userRequestDto.getPassword());
        }


        passengerRepo.save(appUser);
        return modelMapper.map(appUser, PassengerResponseDto.class);
    }

    @Transactional
    @Override
    public PassengerResponseDto onBoardNewAdmin(PassengerRequestDto passengerRequestDto , Long id) {
      AppUser appUser = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found "));

if(appUser.getRole().contains(RoleType.ADMIN)){
throw  new IllegalArgumentException("Already an admin");
}

appUser.getRole().add(RoleType.ADMIN);
 userRepo.save(appUser);

        return  modelMapper.map(appUser , PassengerResponseDto.class);
    }

}



