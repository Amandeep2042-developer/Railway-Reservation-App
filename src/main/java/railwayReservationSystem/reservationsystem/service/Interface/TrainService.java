package reservationsystem.service.Interface;


import reservationsystem.dto.TrainRequestDto;
import reservationsystem.dto.TrainResponseDto;

import java.util.List;

public interface TrainService {





    List<TrainResponseDto> viewAllTrains();

    TrainResponseDto getTrainById(Long id);


    TrainResponseDto addTrains(TrainRequestDto trainRequestDto);

    TrainResponseDto updateTrain(TrainRequestDto trainRequestDto, Long id);

    TrainResponseDto trainPartialUpdate(TrainRequestDto trainRequestDto, Long id);

    void deleteTrainById(Long id);
}
