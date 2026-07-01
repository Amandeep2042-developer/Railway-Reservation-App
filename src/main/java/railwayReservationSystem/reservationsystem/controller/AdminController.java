package reservationsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import reservationsystem.dto.PassengerRequestDto;
import reservationsystem.dto.TrainRequestDto;
import reservationsystem.dto.TrainResponseDto;
import reservationsystem.dto.PassengerResponseDto;
import reservationsystem.service.Interface.PassengerService;
import reservationsystem.service.Interface.TrainService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

private  final PassengerService passengerService;
private final TrainService trainService;


    @GetMapping("/viewAllUsers")
    public ResponseEntity<List<PassengerResponseDto>> getAllUser(){
        return  ResponseEntity.ok(passengerService.getAllUser());
    }

@PostMapping("/addTrain")
    public  ResponseEntity<TrainResponseDto> addTrain(@RequestBody TrainRequestDto trainRequestDto){
        return ResponseEntity.ok(trainService.addTrains(trainRequestDto));
}

@PutMapping("/updateTrain/id/{id}")
    public ResponseEntity<TrainResponseDto> updateTrain(@RequestBody TrainRequestDto trainRequestDto , @PathVariable Long id){
        return ResponseEntity.ok(trainService.updateTrain(trainRequestDto, id));
}

@PatchMapping("/trainPartialUpdate/id/{id}")
    public  ResponseEntity<TrainResponseDto> trainPartialUpdate(@RequestBody TrainRequestDto trainRequestDto, @PathVariable Long id){
        return ResponseEntity.ok(trainService.trainPartialUpdate(trainRequestDto , id));
}

@DeleteMapping("/deleteTrain/id/{id}")

public  ResponseEntity<Void> deleteTrainById(@PathVariable Long id){
         trainService.deleteTrainById(id);

        return ResponseEntity.noContent().build();
}


@PostMapping
    public ResponseEntity<PassengerResponseDto> onBoardNewAdmin(@RequestBody PassengerRequestDto passengerRequestDto , @PathVariable Long id){
return  ResponseEntity.ok(passengerService.onBoardNewAdmin(passengerRequestDto , id));

}


}
