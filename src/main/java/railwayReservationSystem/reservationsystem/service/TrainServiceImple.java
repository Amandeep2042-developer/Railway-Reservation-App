package reservationsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reservationsystem.dto.TrainRequestDto;
import reservationsystem.dto.TrainResponseDto;
import reservationsystem.entity.Train;
import reservationsystem.repository.TrainRepo;
import reservationsystem.service.Interface.TrainService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainServiceImple implements TrainService {
    private final TrainRepo trainRepo;
    private  final  ModelMapper modelMapper;


    @Override
    public List<TrainResponseDto> viewAllTrains() {
        List<Train> train = trainRepo.findAll();
        return train.stream().map(train1 -> modelMapper.map(train1, TrainResponseDto.class)).toList();

    }

    @Override
    public TrainResponseDto getTrainById(Long id) {
        if (!trainRepo.existsById(id)){
            throw  new IllegalArgumentException("Train not found by id "+id);
        }
        Optional<Train> train = trainRepo.findById(id);

        return modelMapper.map(train, TrainResponseDto.class);

    }

    @Transactional
    @Override
    public TrainResponseDto addTrains(TrainRequestDto trainRequestDto) {
        if(trainRepo.existsById(trainRequestDto.getTrainNumber())){
            throw  new IllegalArgumentException("Train already exist with trainNumber"+ trainRequestDto.getTrainName());
        }

        Train train = modelMapper.map(trainRequestDto, Train.class);
         Train train1 = trainRepo.save(train);
          return modelMapper.map(train1 , TrainResponseDto.class);
    }

    @Transactional
    @Override
    public TrainResponseDto updateTrain(TrainRequestDto trainRequestDto, Long id) {
        Train train = trainRepo.findById(id).orElseThrow( () -> new IllegalArgumentException("Train not found by id "+id));
        modelMapper.map(trainRequestDto , train);
        trainRepo.save(train);
        return modelMapper.map(train , TrainResponseDto.class);
    }


    @Transactional
    @Override
    public TrainResponseDto trainPartialUpdate(TrainRequestDto trainRequestDto, Long id) {

        Train train = trainRepo.findById(id).orElseThrow( () -> new IllegalArgumentException("Train not found by id "+id));
 if(trainRequestDto.getTrainNumber() != null){
     train.setTrainNumber(trainRequestDto.getTrainNumber());
 }

 if (trainRequestDto.getTrainName() != null){
     train.setTrainName(trainRequestDto.getTrainName());
 }

 if(trainRequestDto.getSource() != null ){
     train.setSource(trainRequestDto.getSource());
 }

 if(trainRequestDto.getDestination() != null){
     train.setDestination(trainRequestDto.getDestination());
 }
  if(trainRequestDto.getTotalSeats() != null){
      train.setTotalSeats(trainRequestDto.getTotalSeats());

  }

 if(trainRequestDto.getAvailableSeats() != null ){
     train.setAvailableSeats(trainRequestDto.getAvailableSeats());
 }

 trainRepo.save(train);

  return modelMapper.map(train , TrainResponseDto.class);


    }

    @Transactional
    @Override
    public void deleteTrainById(Long id) {
        Train train = trainRepo.findById(id).orElseThrow( () -> new IllegalArgumentException("Train not found by id "+id));
trainRepo.deleteById(id);
    }


}
