package reservationsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservationsystem.dto.TrainResponseDto;
import reservationsystem.service.Interface.TrainService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trains")
public class TrainController {

    private final TrainService trainService;


@GetMapping("/getAllTrains")
    public ResponseEntity<List<TrainResponseDto>> viewAllTrains(){
        return ResponseEntity.ok(trainService.viewAllTrains());
}

@GetMapping("/getTrainById/{id}")
    public ResponseEntity<TrainResponseDto> getTrainById(@PathVariable Long id){
        return  ResponseEntity.ok(trainService.getTrainById(id));
}


}
