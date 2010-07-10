/*
 * Copyright 2008 Arne Limburg
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
package net.sf.jpasecurity.contacts;

import javax.persistence.EntityManager;

import net.sf.jpasecurity.contacts.acl.Contact;
import net.sf.jpasecurity.contacts.acl.ContactAclEntry;
import net.sf.jpasecurity.contacts.acl.User;

/**
 * @author Arne Limburg
 */
public class AclContactsTestData {

    protected User john;
    protected User mary;
    protected User admin;
    protected Contact johnsContact1;
    protected Contact johnsContact2;
    protected Contact marysContact1;
    protected Contact marysContact2;
    
    public AclContactsTestData(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        createTestData((EntityManager)entityManager.getDelegate());
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    protected void createTestData(EntityManager entityManager) {
        john = new User("John");
        entityManager.persist(john);
        mary = new User("Mary");
        entityManager.persist(mary);
        admin = new User("Admin");
        entityManager.persist(admin);
        johnsContact1 = new Contact(john, "john@jpasecurity.sf.net");
        entityManager.persist(johnsContact1);
        johnsContact2 = new Contact(john, "0 12 34 - 56 789");
        entityManager.persist(johnsContact2);
        marysContact1 = new Contact(mary, "mary@jpasecurity.sf.net");
        entityManager.persist(marysContact1);
        marysContact2 = new Contact(mary, "12 34 56 78 90");
        entityManager.persist(marysContact2);       
        entityManager.persist(new ContactAclEntry(johnsContact1));
        entityManager.persist(new ContactAclEntry(johnsContact2));
        entityManager.persist(new ContactAclEntry(marysContact1));
        entityManager.persist(new ContactAclEntry(marysContact2));
        entityManager.persist(new ContactAclEntry(johnsContact1, admin));
        entityManager.persist(new ContactAclEntry(johnsContact2, admin));
        entityManager.persist(new ContactAclEntry(marysContact1, admin));
        entityManager.persist(new ContactAclEntry(marysContact2, admin));
    }

    public User getJohn() {
        return john;
    }

    public User getMary() {
        return mary;
    }
    
    public User getAdmin() {
        return admin;
    }

    public Contact getJohnsContact1() {
        return johnsContact1;
    }

    public Contact getJohnsContact2() {
        return johnsContact2;
    }

    public Contact getMarysContact1() {
        return marysContact1;
    }

    public Contact getMarysContact2() {
        return marysContact2;
    }
    
    public void clear(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        clearTestData(entityManager);
        entityManager.getTransaction().commit();
        entityManager.close();        
    }
    
    protected void clearTestData(EntityManager entityManager) {
        entityManager.createQuery("delete from ContactAclEntry entry").executeUpdate();
        entityManager.createQuery("delete from Contact contact").executeUpdate();
        entityManager.createQuery("delete from User user").executeUpdate();
    }
}