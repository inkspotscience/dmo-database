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
package co.inkspot.dmo.services;

import co.inkspot.dmo.dao.DMODataAccess;
import co.inkspot.dmo.model.Collection;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 * Service that is used to upload DMO data
 * @author hugo
 */
@Path("/dmos/upload")
public class DMOUploadService {
    @Inject
    DMODataAccess dmoAccess;
    
    @PUT
    @Path("/collections")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void updateCollection(Collection c) throws Exception {
    }
    
    @POST
    @Path("/collections")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public void createCollectionWithMetadata(String metadata) throws Exception {
        // Parse the metadata to extract details
        JSONObject parsedMetadata = new JSONObject(metadata);
        
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
        
        JSONObject deviceInfo = parsedMetadata.getJSONObject("device_info");
        JSONObject recordingDetails = parsedMetadata.getJSONObject("annotations");
        
        // ID of the collection using code,participant and stage
        String collectionId = recordingDetails.getString("project_code") + "." + recordingDetails.getString("participant_id") + "." + recordingDetails.getString("stage_name");
        
    }
}
