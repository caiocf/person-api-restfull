package br.com.mkcf.personapi.services;

import br.com.mkcf.personapi.convert.ModelMapperConvert;
import br.com.mkcf.personapi.convert.custom.PersonConverter;
import br.com.mkcf.personapi.data.model.Person;
import br.com.mkcf.personapi.data.vo.PersonVO;
import br.com.mkcf.personapi.data.vo.v2.PersonVOV2;
import br.com.mkcf.personapi.exception.EntityUseException;
import br.com.mkcf.personapi.exception.ResourceNotFoundException;
import br.com.mkcf.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonServices {

    public static final String PERSON_WITH_ID_D_NOT_FOUND = "Person with Id %d not found";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonConverter personConverter;

    public PersonVO findById(Long id) {
        var person = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(PERSON_WITH_ID_D_NOT_FOUND, id)));
        return ModelMapperConvert.parseObject(person,PersonVO.class);
    }

    public List<PersonVO> findAll() {
        return ModelMapperConvert.parseListObjects(personRepository.findAll(),PersonVO.class);
    }

    public PersonVO save(PersonVO personVO){
        var person = ModelMapperConvert.parseObject(personVO,Person.class);
        return ModelMapperConvert.parseObject(personRepository.save(person),PersonVO.class);
    }

    public PersonVOV2 saveV2(PersonVOV2 personVOV2){
        var person = personConverter.convertVOToEntity(personVOV2);
        return personConverter.convertEntityToVO(personRepository.save(person));
    }

    @Transactional
    public PersonVO update(Long id, PersonVO ppersonVO){
        var person = ModelMapperConvert.parseObject(ppersonVO,Person.class);

        person.setGender(ppersonVO.getGender());
        person.setFirstName(ppersonVO.getFirstName());
        person.setLastName(ppersonVO.getLastName());
        person.setAddress(ppersonVO.getAddress());

        return ModelMapperConvert.parseObject(personRepository.save(person),PersonVO.class);
    }

    @Transactional
    public void delete(Long id){
        try {
            personRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
           throw  new ResourceNotFoundException(String.format(PERSON_WITH_ID_D_NOT_FOUND, id));
        }catch (DataIntegrityViolationException e) {
            throw new EntityUseException(String.format("Registry in use"));
        }
    }

    @Transactional
    public PersonVO disablePerson(Long id){
        personRepository.disablePerson(id);
        return findById(id);
    }

    public List<PersonVO> findAll(Pageable pageable) {
        var persons = personRepository.findAll(pageable).getContent();
        return ModelMapperConvert.parseListObjects(persons,PersonVO.class);
    }

    public Page<PersonVO> findAllPage(Pageable pageable, String firstName) {
        var page = personRepository.findByFirstNameContainingIgnoreCase(firstName, pageable);
        return page.map(this::convertToPerson);
    }

    private PersonVO convertToPerson(Person person) {
        return ModelMapperConvert.parseObject(person,PersonVO.class);
    }

}
