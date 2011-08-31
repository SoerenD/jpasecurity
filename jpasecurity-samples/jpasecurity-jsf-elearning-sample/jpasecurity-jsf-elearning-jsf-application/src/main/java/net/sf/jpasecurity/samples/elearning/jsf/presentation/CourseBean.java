/*
 * Copyright 2011 Arne Limburg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package net.sf.jpasecurity.samples.elearning.jsf.presentation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import net.sf.jpasecurity.sample.elearning.domain.Course;
import net.sf.jpasecurity.sample.elearning.domain.Student;
import net.sf.jpasecurity.sample.elearning.domain.Teacher;
import net.sf.jpasecurity.samples.elearning.jsf.service.PlatformService;

/**
 * @author Arne Limburg
 */
@ManagedBean(name = "course")
@SessionScoped
public class CourseBean extends EntityBean {

    @ManagedProperty(value = "#{platformServiceBean}")
    private PlatformService platformService;

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    private Course course;
    private String coursename;

    // create "new course"
    public String createCourse() {
        course = new Course();
        course.setTeacher(getCurrentTeacher());
        course.setName(coursename);
        course.setId(platformService.getNewId());
        platformService.addCourse(course);
        coursename = "";
        return "index.xhtml";
    }

    // add student to a course
    public String addStudentToCourse() {
        Student student = getCurrentStudent();
        course.addParticipant(student);
        return "index.xhtml";
    }

    // add student to a course
    public String removeStudentToCourse() {
        Student student = getCurrentStudent();
        course.removeParticipant(student);
        return "index.xhtml";
    }

    public boolean isStudentInCourse() {
        List<Student> courseStudents = course.getParticipants();
        Student currentStudent = getCurrentStudent();
        for (Student student : courseStudents) {
            if (student.equals(currentStudent)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return course.getName();
    }

    public void setName(String name) {
        course.setName(name);
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String name) {
        coursename = name;
    }

    public Teacher getTeacher() {
        Teacher teacher = course.getTeacher();
        if (teacher != null) {
            return teacher;
        }
        return null;
    }

    public void setTeacher(Teacher teacher) {
        course.setTeacher(teacher);
    }

    public List<Student> getStudents() {
        return course.getParticipants();
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public void setAuthenticationBean(AuthenticationBean aAuthenticationBean) {
        authenticationBean = aAuthenticationBean;
    }

    public void setId(int id) {
        this.course = this.platformService.findCourseById(id);
    }

    public int getId() {
        return course.getId();
    }

    public Teacher getCurrentTeacher() {
        if (authenticationBean.isAuthenticatedTeacher()) {
            int id = authenticationBean.getCurrentUser().getId();
            return platformService.findTeacherById(id);
        } else {
            return null;
        }
    }

    public Student getCurrentStudent() {
        if (authenticationBean.isAuthenticatedStudent()) {
            int id = authenticationBean.getCurrentUser().getId();
            return platformService.findStudentById(id);
        } else {
            return null;
        }
    }

    @PostConstruct
    private void init() {
        course = new Course();
    }
}
