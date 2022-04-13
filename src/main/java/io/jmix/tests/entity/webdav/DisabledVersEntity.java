package io.jmix.tests.entity.webdav;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.webdav.annotation.WebdavSupport;
import io.jmix.webdav.entity.WebdavDocument;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "DISABLED_VERS_ENTITY", indexes = {
        @Index(name = "IDX_DISABLEDVERSENTITY", columnList = "FILE_ID")
})
@Entity
public class DisabledVersEntity {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @WebdavSupport(versioning = false)
    @JoinColumn(name = "FILE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private WebdavDocument file;

    public WebdavDocument getFile() {
        return file;
    }

    public void setFile(WebdavDocument file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}