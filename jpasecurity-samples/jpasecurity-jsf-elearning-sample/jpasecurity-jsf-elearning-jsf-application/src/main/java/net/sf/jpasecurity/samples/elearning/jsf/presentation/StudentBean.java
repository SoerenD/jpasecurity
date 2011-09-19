/*
 * Copyright 2011 Raffaela Ferrari open knowledge GmbH
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

import net.sf.jpasecurity.sample.elearning.domain.Course;
import net.sf.jpasecurity.sample.elearning.domain.Student;
import net.sf.jpasecurity.samples.elearning.jsf.service.ElearningRepository;

/**
 * @author Raffaela Ferrari
 */
@ManagedBean(name = "student")
public class StudentBean extends UserBean {

    @ManagedProperty(value = "#{elearningRepository}")
    private ElearningRepository elearningRepository;

    private Student student;

    public void setId(int id) {
        this.student = elearningRepository.findStudentById(id);
    }

    public int getId() {
        return student.getId();
    }

    public void setName(String name) {
        this.student.setName(name);
    }

    public String getName() {
        return this.student.getName();
    }

    public List<Course> getCourses() {
        return student.getCourses();
    }

    public void setElearningRepository(ElearningRepository elearningRepository) {
        this.elearningRepository = elearningRepository;
    }

    @PostConstruct
    private void init() {
        student = new Student();
    }
}