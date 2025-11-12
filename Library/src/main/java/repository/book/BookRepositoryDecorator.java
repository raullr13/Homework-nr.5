package repository.book;

public abstract class BookRepositoryDecorator implements BookRepository {

    protected BookRepository decoratorBookRepository;

    public BookRepositoryDecorator(BookRepository bookRepository) {
        decoratorBookRepository = bookRepository;
    }

}
