package com.target.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "response_mapper")
public class ResponseMapper {

    @Id
    private Long id;

    @Column(name = "resp_code")
    private String responseCode;

    @Column(name = "resp_msg")
    private String respMsg;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
}
