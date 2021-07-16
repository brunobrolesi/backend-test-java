package br.com.brunobrolesi.parking.service;

import br.com.brunobrolesi.parking.model.Vehicle;
import br.com.brunobrolesi.parking.model.VehicleType;
import br.com.brunobrolesi.parking.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public List<Vehicle> findAll() {
        List <Vehicle> vehicleList = repository.findAll();

        if (vehicleList.isEmpty()) throw new EmptyResultDataAccessException("Nenhum resultado encontrado", 1);

        return vehicleList;
    }

    public Vehicle findById(Integer id) {
        Optional<Vehicle> optional = repository.findById(id);

        if(optional.isEmpty()) throw new EmptyResultDataAccessException("Nenhum resultado encontrado", 1);

        return optional.get();
    }

    public Vehicle create(Vehicle obj) {
        return repository.save(obj);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Vehicle update(Integer id, Vehicle obj) {
        Optional<Vehicle> entity = Optional.of(repository.getOne(id));
        if (entity.isPresent()){
            updateData(entity.get(), obj);
            return repository.save(entity.get());
        }
        return null;
    }

    private void updateData(Vehicle entity, Vehicle obj) {

        entity.setManufacturer(obj.getManufacturer());
        entity.setModel(obj.getModel());
        entity.setYear(obj.getYear());
        entity.setColor(obj.getColor());
        entity.setLicensePlate(obj.getLicensePlate());
        entity.setType(obj.getType());
    }


}
