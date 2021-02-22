package br.com.mkcf.personapi.convert.mocks;

import br.com.mkcf.personapi.data.model.Person;
import br.com.mkcf.personapi.data.vo.PersonVO;

import java.util.ArrayList;
import java.util.List;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }

    public PersonVO mockVO() {
        return mockVO(0);
    }

    public List<Person> mockEntityList() {
        List<Person> personVOs = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            personVOs.add(mockEntity(i));
        }
        return personVOs;
    }

    public List<PersonVO> mockVOList() {
        List<PersonVO> personVOs = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            personVOs.add(mockVO(i));
        }
        return personVOs;
    }

    private Person mockEntity(Integer number) {
        Person personVO = new Person();
        personVO.setAddress("Addres Test" + number);
        personVO.setFirstName("First Name Test" + number);
        personVO.setGender(((number % 2)==0) ? "Male" : "Female");
        personVO.setKey(number.longValue());
        personVO.setLastName("Last Name Test" + number);
        return personVO;
    }

    private PersonVO mockVO(Integer number) {
        PersonVO personVO = new PersonVO();
        personVO.setAddress("Addres Test" + number);
        personVO.setFirstName("First Name Test" + number);
        personVO.setGender(((number % 2)==0) ? "Male" : "Female");
        personVO.setKey(number.longValue());
        personVO.setLastName("Last Name Test" + number);
        return personVO;
    }

}
