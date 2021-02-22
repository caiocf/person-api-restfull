package br.com.mkcf.personapi.repository;

import br.com.mkcf.personapi.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}