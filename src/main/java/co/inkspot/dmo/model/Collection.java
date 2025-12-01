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
package co.inkspot.dmo.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


/**
 * This class represents a collection session. It contains multiple days of data
 * a participant within a stage of a Study.
 * @author hugo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Collection.countForStudy", query = "SELECT COUNT(c.id) FROM Collection c WHERE c.studyCode=:studyCode")
})
public class Collection implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Database UUID for the collection
     */    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Basic
    private String participantId;
    
    @Lob
    private String comments;
    
    @Basic
    private String studyCode;
    
    @Basic
    private String deviceId;
    
    @Basic
    private String stageName;
    
    @Column(name = "startdate", columnDefinition = "DATE")
    private LocalDate startDate;
    
    @Column(name = "starttime", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "metadata", columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private String sensorMetadata;
    
    /**
     * Gets the Database UUID id for this data collection
     * @return UUID of the collection
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStudyCode() {
        return studyCode;
    }

    public void setStudyCode(String studyCode) {
        this.studyCode = studyCode;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getSensorMetadata() {
        return sensorMetadata;
    }

    public void setSensorMetadata(String sensorMetadata) {
        this.sensorMetadata = sensorMetadata;
    }
}