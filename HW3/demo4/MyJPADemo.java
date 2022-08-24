package com.example.java17il2022.week4.demo4;

import com.example.java17il2022.week4.demo3.Student;
import com.example.java17il2022.week4.demo3.Teacher;
import com.example.java17il2022.week4.demo3.Teacher_Student;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.*;



public class MyJPADemo {
    private DataSource getDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setDatabaseName("OrmDemo");
        dataSource.setUser("root");
        dataSource.setPassword("yang520");
        dataSource.setUrl("jdbc:mysql://localhost:3306/sys");
        return dataSource;
    }

    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.put( "hibernate.dialect", "org.hibernate.dialect.mySQLDialect" );
        properties.put( "hibernate.connection.driver_class", "org.mysql.Driver" );
//        properties.put("hibernate.show_sql", "true");
        return properties;
    }

    private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties ){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("D:/Antra/Java batch/Java_0726-master/java-17-IL-2022-master/src/main/java/com/example/java17il2022/week4/demo4");
        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
        em.setJpaProperties( hibernateProperties );
        em.setPersistenceUnitName( "demo-unit" );
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }

    public static void main(String[] args) {
        com.example.java17il2022.week4.demo3.MyJPADemo jpaDemo = new com.example.java17il2022.week4.demo3.MyJPADemo();
        DataSource dataSource = jpaDemo.getDataSource();
        Properties properties = jpaDemo.getProperties();
        EntityManagerFactory entityManagerFactory = jpaDemo.entityManagerFactory(dataSource, properties);
        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();
//        insertToStudent(em);
        getAuthorById(em);
//        List<Teacher> tList = em.createQuery("select t from Teacher t join t.teacher_students ts").getResultList();
//        Teacher t = tList.get(0);
//        System.out.println("**************************************");
//        System.out.println("class is loaded : " + unitUtil.isLoaded(t));
//        System.out.println("collection is loaded : " + unitUtil.isLoaded(t, "teacher_students"));
//        List<Teacher_Student> teacher_students = t.getTeacher_students();
//        System.out.println("collection is loaded : " + unitUtil.isLoaded(teacher_students, "teacher_students"));
//        System.out.println(teacher_students);
//        System.out.println("collection is loaded : " + unitUtil.isLoaded(teacher_students, "teacher_students"));
//        System.out.println("**************************************");
    }

    private static void insertToStudent(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Author a = new Author();
        a.setName("Jerry");
        a.setAuthor_id(1231);
        em.merge(a);
        tx.commit();
    }

    private static void getAuthorById(EntityManager em) {
        Query query = em.createQuery("select a from Author a left join fetch a.author_book ab where a.author_id = ?1");
        query.setParameter(1, "1231");
       Author a = (Author) query.getSingleResult();
        System.out.println(a);
    }

    private static void addToJunctionTable1(EntityManager em) {
        EntityTransaction ab = em.getTransaction();
        ab.begin();
       Author s = new Author();
        s.setName("1th stu");
        Book b = new Book();
        //persist t first to get new id
        em.persist(b);
        b.setISBN(12315);
        //build connection between t and s
        Author_Book abb = new Author_Book();
        abb.setAuthor_book_table_id(1);
        abb.setAuthor(s);
        abb.addAuthor_Book((EntityTransaction) abb);

        em.persist(s);
        abb.commit();
    }

    private static void addToJunctionTable2(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery("INSERT INTO AUTHOR_BOOK (A_ID, B_ISBN) VALUES (?, ?)");
        query.setParameter(1, 4);
        query.setParameter(2, 4);
        query.executeUpdate();
        tx.commit();
    }

    private static void addToJunctionTable3(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Author a = em.find(Author.class, "14");
        Book b = em.find(Book.class, "9");
        Author_Book ts = new Author_Book();
        ts.setAuthor(a);
        ts.setBook(b);
        em.persist(ts);
        tx.commit();
    }

    private static void notOrphanRemoveBiRelation(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Author s join fetch s.author_book ts where s.author_id = ?1");
        query.setParameter(1, "5");
        Author s = (Author) query.getSingleResult();
       Book t = em.find(Book.class, "3");
        List<Author_Book> author_books = new ArrayList<>();
        for(Author_Book ts: s.getAuthor_book()) {
            if(Objects.equals(ts.getBook().getISBN(), t.getISBN())) {
                author_books.add(ts);
                em.remove(ts);
            }
        }
        s.getAuthor_book().removeAll(author_books);
        t.getAuthor_books().removeAll(author_books);
        tx.commit();
    }

    private static void notOrphanRemoveSingleRelation(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Author s join fetch s.author_book ts where s.author_id = ?1");
        query.setParameter(1, "5");
       Author s = (Author) query.getSingleResult();
        for(Author_Book ts: s.getAuthor_book()) {
            em.remove(ts);
        }
        s.setAuthor_book(new ArrayList<>());
        tx.commit();
    }

    private static void orphanRemove(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Author s join s.author_book ts where s.author_id = ?1");
        query.setParameter(1, "14");
        Author s = (Author) query.getSingleResult();
        Iterator<Author_Book> itr = s.getAuthor_book().iterator();
        while(itr.hasNext()) {
            Author_Book ts = itr.next();
            if(Objects.equals(ts.getBook().getISBN(), "9")) {
                itr.remove();
            }
        }
        tx.commit();
    }


    private static void withoutOrphanRemove(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Author s join fetch s.author_book ts where s.author_id = ?1");
        query.setParameter(1, "14");
        Author s = (Author) query.getSingleResult();
        Iterator<Author_Book> itr = s.getAuthor_book().iterator();
        while(itr.hasNext()) {
           Author_Book ts = itr.next();
            if(Objects.equals(ts.getBook().getISBN(), "9")) {
                itr.remove();
                em.remove(ts);
            }
        }
        tx.commit();
    }
}
