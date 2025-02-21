package com.codefrombasics.hibernatedemo.dao;

import com.codefrombasics.hibernatedemo.entity.Course;
import com.codefrombasics.hibernatedemo.entity.Instructor;
import com.codefrombasics.hibernatedemo.entity.InstructorDetails;
import com.codefrombasics.hibernatedemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDaoImpl implements AppDao{
   private EntityManager entityManager;
    @Autowired
    public AppDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    @Transactional
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructoryById(int id) {
        //fetch the instructor to delete
        Instructor theInstructor=entityManager.find(Instructor.class,id);


        // get the courses
        List<Course> courses = theInstructor.getCourses();

        // break association of all courses for the instructor
        for (Course tempCourse : courses) {
            tempCourse.setInstructor(null);
        }

        // delete the instructor
        entityManager.remove(theInstructor);


        //Delete the Instructor

        entityManager.remove(theInstructor);
    }

    @Override
    public InstructorDetails findInstructorDetailsById(int id) {
        return entityManager.find(InstructorDetails.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructoryDetailsById(int id) {
        //fetch the instructor details to delete
        InstructorDetails theInstructorDetails=entityManager.find(InstructorDetails.class,id);
        //Delete the Instructor
        theInstructorDetails.getInstructor().setInstructorDetails(null);
        entityManager.remove(theInstructorDetails);
    }

    @Override
    public List<Course> findCoursesForInstructor(int id) {

       TypedQuery<Course> query= entityManager.createQuery(
               //Course in the query is an Entity class name instructor variable name of class Course
                 "from Course where instructor.id=:data", Course.class);
       query.setParameter("data",id);
       List<Course> coursesList=query.getResultList();
       return coursesList;

    }
    @Override
    public Instructor findCoursesForInstructorByFetchJoin(int id) {

             TypedQuery<Instructor> query=entityManager.createQuery(
                     "select i from Instructor i " +
                     "JOIN FETCH i.courses "+
                     "JOIN FETCH i.instructorDetails "+
                     "where i.id=:data",Instructor.class);
                   query.setParameter("data",id);
             Instructor instructor=query.getSingleResult();
             return  instructor;
    }

    @Override
    @Transactional
    public void updateInstructorLastName(int id) {
      Instructor instructor=  entityManager.find(Instructor.class,id);
      instructor.setLastName("Baskar");
      entityManager.merge(instructor);
    }

    @Override
    @Transactional
    public void updateCourseTitle(int id) {
       Course course=entityManager.find(Course.class,id);
       course.setTitle("Data Science");
       entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class,theId);
    }
    @Override
    @Transactional
    public void deleteCourseById(int theId){
        Course course=findCourseById(theId);
        entityManager.remove(course);


    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }
    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {

        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                        + "JOIN FETCH c.reviews "
                        + "where c.id = :data", Course.class);

        query.setParameter("data", theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                        + "JOIN FETCH c.students "
                        + "where c.id = :data", Course.class);

        query.setParameter("data", theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

    @Override
    public Student findCourseAndStudentsByStudentId(int theId) {
        TypedQuery<Student> query = entityManager.createQuery(
                "select s from Student s "
                        + "JOIN FETCH s.courses "
                        + "where s.id = :data", Student.class);

        query.setParameter("data", theId);

        // execute query
        Student student = query.getSingleResult();

        return student;
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        Student student=entityManager.find(Student.class,id);
        entityManager.remove(student);
    }
}
