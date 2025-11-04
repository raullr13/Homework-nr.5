package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.awt.event.ActionListener;

public class BookController {

    private final BookView bookView;
    private final BookService bookService;
    public BookController(BookView bookView, BookService bookService) {
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());

    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            if (title.isEmpty() || author.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author or Title fields", "Cannot have an empty field (author or title)");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                if (savedBook) {
                    bookView.addDisplayAlertMessage("Save Success", "Book Saved", "Successfully Saved");
                    bookView.addBookToObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Save Error", "Problem at Title or Author fields", "Cannot save Book");
                }
            }
        }
    }
        private class DeleteButtonListener implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent actionEvent) {
                BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
                if(bookDTO != null) {

                    boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

                    if(deletionSuccessful) {
                        bookView.addDisplayAlertMessage("Delete Success", "Book Deleted", "Successfully Deleted");
                        bookView.removeBookFromObservableList(bookDTO);
                    }
                    else
                    {
                        bookView.addDisplayAlertMessage("Delete Error", "Problem with database", "Try again");
                    }
                }
                else
                {
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting", "Cannot delete Book");
                }
            }

        }


    }
