package br.com.mkcf.personapi.controller;

import br.com.mkcf.personapi.data.vo.PersonVO;
import br.com.mkcf.personapi.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path= "/api/persons")
@Tag(name  =  "Person Endpoint")
public class PersonController {

    @Autowired
    private PersonServices personServices;

    @GetMapping
    @Operation(summary ="Find all people recorded")
    public List<PersonVO> findAllBookVOs(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "limit",defaultValue = "12") int limit,
                                         @RequestParam(value = "direction", defaultValue = "asc") String direction){
        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC:Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page,limit, Sort.by(sortDirection,"firstName"));

        //var persons = personServices.findAll();
        var persons = personServices.findAll(pageable);
        persons.forEach( p ->  p.add(linkTo(methodOn(this.getClass()).findPerson(p.getKey())).withSelfRel()) );
        return persons;
    }

    @GetMapping("/{id}")
    @Operation(summary ="Find by id person recorded")
    public PersonVO findPerson(@PathVariable Long id){
        var personV0 =  personServices.findById(id);
        personV0.add(linkTo(methodOn(this.getClass()).findPerson(id)).withSelfRel());

        return personV0;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary ="Save people")
    public PersonVO saved(@RequestBody @Valid PersonVO person){
        var personV0 = personServices.save(person);
        personV0.add(linkTo(methodOn(this.getClass()).findPerson(personV0.getKey())).withSelfRel());

        return personV0;
    }

    @PutMapping("/{id}")
    @Operation(summary ="Update by id person recorded")
    public PersonVO update(@PathVariable(value = "id") Long id, @RequestBody @Valid PersonVO person){
        var personV0 = personServices.update(id,person);

        personV0.add(linkTo(methodOn(this.getClass()).findPerson(person.getKey())).withSelfRel());

        return personV0;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary ="Delete by id person recorded")
    public void delete(@PathVariable(value = "id") Long id){
        personServices.delete(id);
    }


    @PatchMapping("/{id}/disable")
    @Operation(summary ="Disable Person by id")
    public PersonVO disablePerson(@PathVariable(value = "id") Long id){
        var personV0 = personServices.disablePerson(id);

        personV0.add(linkTo(methodOn(this.getClass()).findPerson(id)).withSelfRel());

        return personV0;
    }
}
