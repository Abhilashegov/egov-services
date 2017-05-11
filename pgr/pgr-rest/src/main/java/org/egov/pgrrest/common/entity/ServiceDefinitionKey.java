package org.egov.pgrrest.common.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ServiceDefinitionKey implements Serializable {

    private String code;
    @Column(name = "tenantid", nullable = false)
    private String tenantId;
}
