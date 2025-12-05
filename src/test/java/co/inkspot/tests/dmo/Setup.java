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

import co.inkspot.tests.dmo.server.DummyServer;
import co.inkspot.dmo.dao.DMODataAccess;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Logger;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Set up / check test DB
 * @author hugo
 */
public class Setup implements BeforeAllCallback, ExtensionContext.Store.CloseableResource{
    private static Logger logger = Logger.getLogger(Setup.class.getName());
    private static volatile boolean started = false;
    public static EntityManager em;
    public static EntityManagerFactory emf;
    static DummyServer server;
    
    @Override
    public void beforeAll(ExtensionContext ec) throws Exception {
       if (!started) {
            try {
                logger.info("Started embedded PostgreSQL");
                emf = Persistence.createEntityManagerFactory("dmo");
                em = emf.createEntityManager();
                TestSuite.em = em;
                
                // Set the testing Entity Mnager
                DMODataAccess.setEntityManager(em);
                
                server = new DummyServer();
                server.start();
                started = true;
            } catch (Exception e) {
                started = false;
                e.printStackTrace();
            }
        }        
    }

    @Override
    public void close() throws Throwable {
        
    }
    
    public static void shutdown(){
        if(started){
            try {
                
                server.stop();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }    
    
}
