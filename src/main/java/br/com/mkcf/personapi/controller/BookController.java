package br.com.mkcf.personapi.controller;

import br.com.mkcf.personapi.data.vo.BookVO;
import br.com.mkcf.personapi.services.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path= "/api/books")
@Api(value = "Book Endpoint")
public class BookController {

    @Autowired
    private BookServices bookServices;

    @GetMapping
    @ApiOperation(value = "Find all book recorded")
    public List<BookVO> findAllBookVOs(){
        var books = bookServices.findAll();
        books.forEach( p ->  p.add(linkTo(methodOn(this.getClass()).findBook(p.getKey())).withSelfRel()) );
        return books;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find by Id book recorded")
    public BookVO findBook(@PathVariable Long id){
        var bookVO =  bookServices.findById(id);
        bookVO.add(linkTo(methodOn(this.getClass()).findBook(id)).withSelfRel());

        return bookVO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "save book")
    public BookVO saved(@RequestBody @Valid BookVO person){
        var bookVO = bookServices.save(person);
        bookVO.add(linkTo(methodOn(this.getClass()).findBook(bookVO.getKey())).withSelfRel());

        return bookVO;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update by Id book recorded")
    public BookVO update(@PathVariable(value = "id") Long id, @RequestBody @Valid BookVO person){
        var bookVO = bookServices.update(id,person);

        bookVO.add(linkTo(methodOn(this.getClass()).findBook(bookVO.getKey())).withSelfRel());

        return bookVO;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by Id book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id){
        bookServices.delete(id);
    }
}
