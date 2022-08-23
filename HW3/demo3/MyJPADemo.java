package com.example.java17il2022.week4.demo3;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 *  pojo
 *
 *  1. map result to object/pojo
 *  2. m - m / 1 - m / 1 - 1 relations
 *      student m - m teacher
 *      student 1 - m student_teacher m - 1 teacher
 *
 *      departments 1 - m employees
 *      class Department {
 *          private List<Employee> emp;
 *      }
 *
 *      class Employee {
 *          private Department dept;
 *      }
 *  3. Eager => 1 query to db
 *      List<Student> stuList = em.createQuery(hql : select s from Student s);
 *      for(Student s: stuList) {
 *          List<Teacher_Student> tsList = s.getTeacherStudent();
 *      }
 *    Lazy loading => N + 1
 *      List<Student> stuList = em.createQuery(hql : select s from Student s);  => get N students info
 *      for(Student s: stuList) {
 *          List<Teacher_Student> tsList = s.getTeacherStudent();       => run another query : select xx from teacher_student where ts.stu_id =
 *      }
 *  4. hibernate  (SessionFactory, session, connection, datasource + hql)
 *     jpa (EntityManagerFactory, EntityManager, jpql)
 *  5. Spring data jpa
 *      interface StudentRepo extends JpaRepository<Student, String> {}
 *  6. persistent context(cache)
 *      1st level cache (session/entity manager)
 *      2nd level cache (session factory / entity manager factory)
 *  7. status of objects
 *      transient => new Instance();
 *      proxy =>
 *      unproxy / detached =>
 *
 *
 *  Homework:
 *      1. create author m - m book entities (no @ManyToMany)
 *      2. convert jdbc queries / native queries to jpql
 *      3. handle exception + rollback
 *      deadline : wed morning 10am cdt
 *  Tuesday:
 *      Spring IOC + AOP
 *      Spring boot
 *  Wednesday:
 *      Network
 *      http
 *  Thursday + Friday
 *      Restful api
 *
 *
 *  Today 2:30pm ~ 4:30pm cdt
 *
 *
 *
 *
 */
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
        properties.put( "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect" );
        properties.put( "hibernate.connection.driver_class", "org.postgresql.Driver" );
//        properties.put("hibernate.show_sql", "true");
        return properties;
    }

    private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties ){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com/example/java17il2022/week4/orm/demo3");
        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
        em.setJpaProperties( hibernateProperties );
        em.setPersistenceUnitName( "demo-unit" );
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }

    public static void main(String[] args) {
        MyJPADemo jpaDemo = new MyJPADemo();
        DataSource dataSource = jpaDemo.getDataSource();
        Properties properties = jpaDemo.getProperties();
        EntityManagerFactory entityManagerFactory = jpaDemo.entityManagerFactory(dataSource, properties);
        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();
//        insertToStudent(em);
        getStudentById(em);
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
        Student s = new Student();
        s.setName("Jerry");
        s.setId("17");
        em.merge(s);
        tx.commit();
    }

    private static void getStudentById(EntityManager em) {
        Query query = em.createQuery("select s from Student s left join fetch s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "17");
        Student s = (Student)query.getSingleResult();
        System.out.println(s);
    }

    private static void addToJunctionTable1(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student s = new Student();
        s.setName("1th stu");
        Teacher t = new Teacher();
        //persist t first to get new id
        em.persist(t);
        t.setName("1th teacher");
        //build connection between t and s
        Teacher_Student ts = new Teacher_Student();
        ts.setStu(s);
        ts.setTeacher(t);
        t.addTeacher_students(ts);

        em.persist(s);
        tx.commit();
    }

    private static void addToJunctionTable2(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery("INSERT INTO TEACHER_STUDENT (S_ID, T_ID) VALUES (?, ?)");
        query.setParameter(1, 4);
        query.setParameter(2, 4);
        query.executeUpdate();
        tx.commit();
    }

    private static void addToJunctionTable3(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student s = em.find(Student.class, "14");
        Teacher t = em.find(Teacher.class, "9");
        Teacher_Student ts = new Teacher_Student();
        ts.setStu(s);
        ts.setTeacher(t);
        em.persist(ts);
        tx.commit();
    }

    private static void notOrphanRemoveBiRelation(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join fetch s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "5");
        Student s = (Student) query.getSingleResult();
        Teacher t = em.find(Teacher.class, "3");
        List<Teacher_Student> teacher_students = new ArrayList<>();
        for(Teacher_Student ts: s.getTeacher_students()) {
            if(ts.getTeacher().getId().equals(t.getId())) {
                teacher_students.add(ts);
                em.remove(ts);
            }
        }
        s.getTeacher_students().removeAll(teacher_students);
        t.getTeacher_students().removeAll(teacher_students);
        tx.commit();
    }

    private static void notOrphanRemoveSingleRelation(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join fetch s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "5");
        Student s = (Student) query.getSingleResult();
        for(Teacher_Student ts: s.getTeacher_students()) {
            em.remove(ts);
        }
        s.setTeacher_students(new ArrayList<>());
        tx.commit();
    }

    private static void orphanRemove(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "14");
        Student s = (Student) query.getSingleResult();
        Iterator<Teacher_Student> itr = s.getTeacher_students().iterator();
        while(itr.hasNext()) {
            Teacher_Student ts = itr.next();
            if(ts.getTeacher().getId().equals("9")) {
                itr.remove();
            }
        }
        tx.commit();
    }


    private static void withoutOrphanRemove(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("select s from Student s join fetch s.teacher_students ts where s.id = ?1");
        query.setParameter(1, "14");
        Student s = (Student) query.getSingleResult();
        Iterator<Teacher_Student> itr = s.getTeacher_students().iterator();
        while(itr.hasNext()) {
            Teacher_Student ts = itr.next();
            if(ts.getTeacher().getId().equals("9")) {
                itr.remove();
                em.remove(ts);
            }
        }
        tx.commit();
    }
}