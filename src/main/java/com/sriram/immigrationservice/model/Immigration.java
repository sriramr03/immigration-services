package com.sriram.immigrationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Immigration")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Immigration {

    @Id
    private String id;
    private String receiptNumber;
    private String status;
    private String description;
}
