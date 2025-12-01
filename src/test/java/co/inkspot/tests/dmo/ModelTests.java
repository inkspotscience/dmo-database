/*
 * Copyright (C) 2025 Inkspot Science.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.inkspot.tests.dmo;

import co.inkspot.dmo.model.Collection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test basic DB model
 * @author hugo
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(Setup.class)
public class ModelTests {
    private static EntityManager em;    
    
    @BeforeAll
    public static void setUpClass() {
        em = TestSuite.em;
    }
    
    @BeforeEach
    public void setUp() {
        em.getTransaction().begin();
    }
    
    @AfterEach
    public void tearDown() {
        em.getTransaction().commit();
    }       
    
    private static String collectionId;
    @Test
    @DisplayName("Test creation of Collection")
    public void Test001() throws Exception {
        Collection c1 = new Collection();
        c1.setStudyCode("EN");
        c1.setParticipantId("235");
        c1.setStartDate(LocalDate.now());
        c1.setStartTime(LocalTime.now());
        
        em.persist(c1);
        collectionId = c1.getId();
                
    }
    
    @Test
    @DisplayName("Test Collection retieval")
    public void Test002() throws Exception {
        Collection retrieved = em.find(Collection.class, collectionId);
        assertNotNull(retrieved);
        assertEquals(collectionId, retrieved.getId());
    }
    
    @Test
    @DisplayName("Count collections")
    public void Test003() throws Exception {
        TypedQuery<Long> q = em.createNamedQuery("Collection.countForStudy", Long.class);
        q.setParameter("studyCode", "EN");
        assertTrue(q.getSingleResult().equals(1L));
    }
}
