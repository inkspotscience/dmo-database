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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 * Code to access the DMO Database using standard JPA queries
 *
 * @author hugo
 */
@ApplicationScoped
public class DMODataAccess {

    private static final Logger logger = Logger.getLogger(DMODataAccess.class.getName());

    @Inject
    @Any
    protected EntityManager em;
    
    private static EntityManager testEm;

    private EntityManager em(){
        if(testEm!=null){
            return testEm;
        } else {
            return em;
        }
    }
    public static void setEntityManager(EntityManager em) {
        logger.warning("Setting EntityManager externally - only valid in Unit Tests");
        DMODataAccess.testEm = em;
    }

    public List<Collection> listCollections() throws Exception {
        TypedQuery<Collection> q = em().createNamedQuery("Collection.list", Collection.class);
        return q.getResultList();
    }
    
    public Collection getCollection(String id) throws Exception {
        return em().find(Collection.class, id);
    }

    public Collection getOrCreateCollectionFromSessionID(String sessionId) throws Exception {
        TypedQuery<Collection> q = em().createNamedQuery("Collection.getBySessionId", Collection.class);
        q.setParameter("sessionid", sessionId);
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            Collection c = new Collection();
            c.setSessionId(sessionId);
            em().persist(c);
            return c;
        }

    }

    public void storeCollection(Collection c) throws Exception {
        em().persist(c);
    }

    public Collection populateCollectionFromMobGapMetadata(String metadataJson) throws Exception {
        // Parse the metadata to extract details
        JSONObject parsedMetadata = new JSONObject(metadataJson);

        // Make sure it's got the right fields
        /*
        {
          "device_info": {
            "sessionId": 1234,
            "deviceId": 39615
          },
          "sampling_config": {
            "gyro_range": 2000,
            "sampling_rate_hz": 100.0,
            "accel_unit": 4096,
            "num_axes": 6
          },
          "annotations": {
            "pc": "UCLH",
            "dbid": "1425",
            "host": "act-pd.inkspot.science",
            "tzo": "-60",
            "pid": "419107002",
            "prdbid": "786"
          },
        "recording_details": {
          "start_timestamp": 1758153600001,
          "start_day": "2025-09-18",
          "start_time": "00:00:00.001",
          "project_code": "UCLH",
          "participant_id": "419107001",
          "stage_name": "T1"
        },        
        }
        
        
        
         */
        JSONObject sensorMetadata = parsedMetadata.getJSONObject("sensor_metadata");
        JSONObject deviceInfo = sensorMetadata.getJSONObject("device_info");
        JSONObject annotations = sensorMetadata.getJSONObject("annotations");
        JSONObject recordingDetails = parsedMetadata.getJSONObject("recording_details");

        // ID of the collection using code,participant and stage
        String sessionId = recordingDetails.getString("project_code") + "." + recordingDetails.getString("participant_id") + "." + recordingDetails.getString("stage_name");

        // Create a new session or get a pre-exisitng one
        Collection c = getOrCreateCollectionFromSessionID(sessionId);

        // Set basic study details
        c.setStudyCode(recordingDetails.getString("project_code"));
        c.setParticipantId(recordingDetails.getString("participant_id"));
        c.setStageName(recordingDetails.getString("stage_name"));

        // Sensor details
        c.setDeviceId(deviceInfo.get("deviceId").toString());

        // Parse details
        Instant startInstant = Instant.ofEpochMilli(recordingDetails.getLong("start_timestamp"));

        ZoneOffset offset;
        if (annotations.has("tzo")) {
            // We have a timezone
            int offsetMinutes = Integer.parseInt(annotations.get("tzo").toString());
            offset = ZoneOffset.ofHours((int) (offsetMinutes / 60));
        } else {
            offset = ZoneOffset.ofHours(0);
        }
        c.setStartTimestamp(startInstant.toEpochMilli());



        // Save all of the available metadata
        c.setSensorMetadata(metadataJson);
        return c;
    }
}
