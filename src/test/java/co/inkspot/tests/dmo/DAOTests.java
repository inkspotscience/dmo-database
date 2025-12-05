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

import co.inkspot.dmo.dao.DMODataAccess;
import co.inkspot.dmo.model.Collection;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Data Access Objects. 
 * @author hugo
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(Setup.class)
public class DAOTests {
    static DMODataAccess dao = new DMODataAccess();
    static String sessionId;
    static String sessionUUID;
    
    @BeforeAll
    public static void setUpClass() {

    }
    
    @Test
    public void Test000() throws Exception {
        sessionId = "EN.101.T1";
        Collection c = dao.getOrCreateCollectionFromSessionID(sessionId);
        assertNotNull(c);
        sessionUUID = c.getId();
    }
    
    @Test
    public void Test001() throws Exception {
        Collection c = dao.getCollection(sessionUUID);
        assertEquals(sessionId, c.getSessionId());
    }
    
    @Test
    public void Test002() throws Exception {
        JSONObject metadata = new JSONObject(new JSONTokener(getClass().getResourceAsStream("/data/metadata.json")));
        Collection c = dao.populateCollectionFromMobGapMetadata(metadata.toString());
        assertNotNull(c);
        
    }
            
}
