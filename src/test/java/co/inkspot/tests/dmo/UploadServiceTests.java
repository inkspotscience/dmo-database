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

import co.inkspot.dmo.api.DMOUploadService;
import co.inkspot.dmo.dao.DMODataAccess;
import co.inkspot.dmo.model.Collection;
import co.inkspot.osm.tools.client.ClientProxyFactory;
import co.inkspot.osm.tools.client.impl.ClientProxyFactoryImpl;
import static co.inkspot.tests.dmo.DAOTests.dao;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

/**
  Tests upload services
 * @author hugo
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(Setup.class)
public class UploadServiceTests {
    @BeforeEach
    public void setUp() {
        TestSuite.em.getTransaction().begin();
    }
    
    @AfterEach
    public void tearDown() {
        TestSuite.em.getTransaction().commit();
    }       
    
    @Test
    @DisplayName("Test upload of Metadata")
    public void Test000() throws Exception {
        ClientProxyFactory factory = new ClientProxyFactoryImpl();
        
        DMOUploadService svc = (DMOUploadService)factory.createProxy("http://localhost:8080/dmos/upload", DMOUploadService.class);
        JSONObject metadata = new JSONObject(new JSONTokener(getClass().getResourceAsStream("/data/metadata.json")));
        String id = svc.createCollectionWithMetadata(metadata.toString());
        System.out.println(id);
        
        Collection c = svc.getCollection(id);
    }
}
