package br.com.mkcf.personapi.controller.v2;

import br.com.mkcf.personapi.controller.PersonController;
import br.com.mkcf.personapi.data.vo.PersonVO;
import br.com.mkcf.personapi.data.vo.v2.PersonVOV2;
import br.com.mkcf.personapi.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path= "/api/persons/v2")
@Api(value = "Person Endpoint")
public class PersonV2Controller {

    @Autowired
    private PersonServices personServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save people")
    public PersonVOV2 saved(@RequestBody @Valid PersonVOV2 person){
        var personV0 = personServices.saveV2(person);
        personV0.add(linkTo(methodOn(PersonController.class).findPerson(personV0.getKey())).withSelfRel());

        return personV0;
    }


    @GetMapping
    @ApiOperation(value = "Find all people recorded PagedModel")
    public ResponseEntity<PagedModel<PersonVO>> findAllBookVOs(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "limit",defaultValue = "12") int limit,
                                                               @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                               @RequestParam(value = "firstName") String firstName,
                                                               PagedResourcesAssembler assembler){
        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC:Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page,limit, Sort.by(sortDirection,"firstName"));

        var persons = personServices.findAllPage(pageable,firstName);

        persons.forEach( p ->  p.add(linkTo(methodOn(PersonController.class).findPerson(p.getKey())).withSelfRel()) );
        return new ResponseEntity<PagedModel<PersonVO>>(assembler.toModel(persons),HttpStatus.OK);
    }


}
