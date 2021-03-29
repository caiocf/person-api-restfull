package br.com.mkcf.personapi.controller;

import br.com.mkcf.personapi.data.vo.BookVO;
import br.com.mkcf.personapi.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path= "/api/books")
@Tag(name  = "Book Endpoint")
public class BookController {

    @Autowired
    private BookServices bookServices;

    @GetMapping
    @Operation(summary ="Find all book recorded")
    public List<BookVO> findAllBookVOs(){
        var books = bookServices.findAll();
        books.forEach( p ->  p.add(linkTo(methodOn(this.getClass()).findBook(p.getKey())).withSelfRel()) );
        return books;
    }

    @GetMapping("/{id}")
    @Operation(summary ="Find by Id book recorded")
    public BookVO findBook(@PathVariable Long id){
        var bookVO =  bookServices.findById(id);
        bookVO.add(linkTo(methodOn(this.getClass()).findBook(id)).withSelfRel());

        return bookVO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary ="save book")
    public BookVO saved(@RequestBody @Valid BookVO person){
        var bookVO = bookServices.save(person);
        bookVO.add(linkTo(methodOn(this.getClass()).findBook(bookVO.getKey())).withSelfRel());

        return bookVO;
    }

    @PutMapping("/{id}")
    @Operation(summary ="Update by Id book recorded")
    public BookVO update(@PathVariable(value = "id") Long id, @RequestBody @Valid BookVO person){
        var bookVO = bookServices.update(id,person);

        bookVO.add(linkTo(methodOn(this.getClass()).findBook(bookVO.getKey())).withSelfRel());

        return bookVO;
    }

    @DeleteMapping("/{id}")
    @Operation(summary ="Delete by Id book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id){
        bookServices.delete(id);
    }
}
