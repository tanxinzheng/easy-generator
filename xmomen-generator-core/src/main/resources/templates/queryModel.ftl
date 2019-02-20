package ${modulePackage}.model;

import lombok.Data;

import java.io.Serializable;


public @Data class ${domainObjectClassName}Query implements Serializable {

    private String keyword;
    private String id;
    private String[] ids;
    private String[] excludeIds;

}
