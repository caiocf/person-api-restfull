package br.com.mkcf.personapi.services;

import br.com.mkcf.personapi.convert.ModelMapperConvert;
import br.com.mkcf.personapi.convert.custom.PersonConverter;
import br.com.mkcf.personapi.data.model.Book;
import br.com.mkcf.personapi.data.vo.BookVO;
import br.com.mkcf.personapi.data.vo.PersonVO;
import br.com.mkcf.personapi.exception.EntityUseException;
import br.com.mkcf.personapi.exception.ResourceNotFoundException;
import br.com.mkcf.personapi.repository.BookRepository;
import br.com.mkcf.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServices {

    public static final String BOOK_WITH_ID_D_NOT_FOUND = "Book with Id %d not found";

    @Autowired
    private BookRepository bookRepository;

    public BookVO findById(Long id) {
        var book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(BOOK_WITH_ID_D_NOT_FOUND, id)));
        return ModelMapperConvert.parseObject(book,BookVO.class);
    }

    public List<BookVO> findAll() {
        return ModelMapperConvert.parseListObjects(bookRepository.findAll(),BookVO.class);
    }

    public BookVO save(BookVO bookVO){
        var book = ModelMapperConvert.parseObject(bookVO,Book.class);
        return ModelMapperConvert.parseObject(bookRepository.save(book),BookVO.class);
    }

    @Transactional
    public BookVO update(Long id, BookVO bookVO){
        var book = ModelMapperConvert.parseObject(bookVO,Book.class);

        book.setAuthor(bookVO.getAuthor());
        book.setLaunchDate(bookVO.getLaunchDate());
        book.setPrice(bookVO.getPrice());
        book.setTitle(bookVO.getTitle());

        return ModelMapperConvert.parseObject(bookRepository.save(book),BookVO.class);
    }

    @Transactional
    public void delete(Long id){
        try {
            bookRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
           throw  new ResourceNotFoundException(String.format(BOOK_WITH_ID_D_NOT_FOUND, id));
        }catch (DataIntegrityViolationException e) {
            throw new EntityUseException(String.format("Registry in use"));
        }
    }

}
