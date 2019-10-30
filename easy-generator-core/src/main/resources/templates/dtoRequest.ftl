package ${targetPackage};

import lombok.Data;

import java.io.Serializable;

@Data
public class ${domainObjectClassName}Request implements Serializable {

    private long pageSize;
    private long pageNum;
    private String keyword;
    private String id;
    private String[] ids;
    private String[] excludeIds;

}
