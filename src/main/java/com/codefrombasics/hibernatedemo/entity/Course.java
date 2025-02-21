package com.codefrombasics.hibernatedemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;

    @Column(name="title")
    private String title;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "instructor_id")//name of the colum in courses table
    private Instructor instructor;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private List<Review> reviews;


    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(
            name = "course_student",//name of the join table in database
            joinColumns = @JoinColumn(name = "course_id"),//used to fetch the id from id variable and store in the couser_student table
            inverseJoinColumns = @JoinColumn(name="student_id")

    )
    private List<Student> students;

    public Course() {
    }

    public Course(String title) {

        this.title = title;
    }

    //add convenience method

    public void addStudent(Student student){
        if(students==null){
            students=new ArrayList<>();

        }
        students.add(student);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    // add a convenience method

    public void addReview(Review theReview) {

        if (reviews == null) {
            reviews = new ArrayList<>();
        }

        reviews.add(theReview);
    }
    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title  +
                '}';
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }


}
