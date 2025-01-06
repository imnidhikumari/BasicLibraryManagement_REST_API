package org.development1.dao;

import org.development1.entity.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;


public class BookDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public BookDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void addBook(Book book){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) transaction.rollback();
            throw new RuntimeException("Error adding book: ",e);
        }
    }

    public List<Book> getAllBooks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book", Book.class).list();
        } catch (HibernateException e) {
            throw new RuntimeException("Error getting all books", e);
        }
    }

        public Optional<Book> getBookById(Long id){
            try(Session session=sessionFactory.openSession()){
                Book book = session.get(Book.class,id);
                return Optional.ofNullable(book);
            } catch (HibernateException e) {
                throw new RuntimeException("Error getting book by id:" + id, e);
            }
        }

        public List<Book> getBooksByTitle(String Title){
            try(Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM Book WHERE title= :Title", Book.class)
                        .setParameter("title",Title)
                        .list();
            } catch (Exception e) {
                throw new RuntimeException("Error getting books by title" + Title, e);
            }

        }

}
