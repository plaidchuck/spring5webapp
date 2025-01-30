package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author rjordan = new Author("Robert", "Jordan");
        Book wot = new Book("The Wheel of Time", "0312850093");

        rjordan.getBooks().add(wot);
        wot.getAuthors().add(rjordan);

        authorRepository.save(rjordan);
        bookRepository.save(wot);

        Author fherbert = new Author("Frank", "Herbert");
        Book dune = new Book("Dune", "9780425027066");
        fherbert.getBooks().add(dune);
        dune.getAuthors().add(fherbert);

        authorRepository.save(fherbert);
        bookRepository.save(dune);

        Publisher penguin = new Publisher("Penguin House", "123 Main",
                "Altoona", "PA", "16602");

        publisherRepository.save(penguin);

        //associated books with publisher and vice versa
        dune.setPublisher(penguin);
        bookRepository.save(dune);

        penguin.getBooks().add(dune);
        publisherRepository.save(penguin);

        wot.setPublisher(penguin);
        bookRepository.save(wot);

        penguin.getBooks().add(wot);
        publisherRepository.save(penguin);

        System.out.println("Started in BootStrapData");
        System.out.println(("-----------------------"));
        System.out.println();

        List<Book>bookList = (List<Book>) bookRepository.findAll();
        for (Book book : bookList) {
            System.out.println(book.toString());
        }
        System.out.println();

        List<Author> authorList = (List<Author>) authorRepository.findAll();
        for (Author author : authorList) {
            System.out.println(author.toString());
        }
        System.out.println();

        List<Publisher> publisherList= (List<Publisher>) publisherRepository.findAll();
        for (Publisher publisher : publisherList) {
            System.out.println(publisher.toString());
        }
        System.out.println();

        System.out.println("Number of books in publisher: " + penguin.getBooks().size());
    }
}
