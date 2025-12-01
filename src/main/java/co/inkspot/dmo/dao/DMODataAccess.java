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
package co.inkspot.dmo.dao;

import co.inkspot.dmo.model.Collection;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 * Code to access the DMO Database using standard JPA queries
 * @author hugo
 */
@ApplicationScoped
public class DMODataAccess {
    /**
     * Entity manager for storing data
     */
    @Inject
    @PersistenceUnit("dmo")
    protected EntityManager em;    
    
    public Collection getCollection(String id) throws Exception {
        return em.find(Collection.class, id);
    }
    
    public void storeCollection(Collection c) throws Exception {
        em.persist(c);
    }
}
