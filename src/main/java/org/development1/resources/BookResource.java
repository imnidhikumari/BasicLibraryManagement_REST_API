package org.development1.resources;


import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.development1.dao.BookDAO;
import org.development1.entity.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final BookDAO bookDAO;
    private final Timer getAllBooksTimer;
    private final Timer getBooksByIdTimer;
    private final Timer getBooksByTitleTimer;
    private final Timer addBookTimer;


    public BookResource(BookDAO bookDAO, MetricRegistry metrics) {
        this.bookDAO = bookDAO;

        this.getAllBooksTimer=metrics.timer(MetricRegistry.name(BookResource.class,"getAllBooks"));
        this.getBooksByIdTimer=metrics.timer(MetricRegistry.name(BookResource.class,"getBookById"));
        this.getBooksByTitleTimer=metrics.timer(MetricRegistry.name(BookResource.class,"getBooksByTitle"));
        this.addBookTimer=metrics.timer(MetricRegistry.name(BookResource.class,"addBook"));
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book){
        final Timer.Context context = addBookTimer.time();
        try{
            bookDAO.addBook(book);
            return Response.status(Response.Status.CREATED).entity(book).build();
        }finally {
            context.stop();
        }
    }

    @GET
    public Response getAllBooks(){
        final Timer.Context context = getAllBooksTimer.time();
        try{
            List<Book> books = bookDAO.getAllBooks();
            return Response.ok(books).build();
        }finally {
            context.stop();
        }
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id){
        final Timer.Context context = getBooksByIdTimer.time();
        try{
            return bookDAO.getBookById(id)
                    .map(book -> Response.ok(book).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        }finally {
            context.stop();
        }
    }

    @GET
    @Path("/{title}")
    public Response getBookById(@PathParam("title") String title){
        final Timer.Context context = getBooksByTitleTimer.time();
        try{
            List<Book> books = bookDAO.getBooksByTitle(title);
            return Response.ok(books).build();
        }finally {
            context.stop();
        }
    }
}
