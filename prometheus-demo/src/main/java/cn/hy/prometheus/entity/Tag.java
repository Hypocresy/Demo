package cn.hy.prometheus.entity;

import lombok.Data;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/25 10:01
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Data
public class Tag {
        private String  tagName;
        private String  tagType;
        private String  ruleJson;
        private String  catalogId;
        private String  catalogFullPath;
        private String  customerTypeId;
        private String  isPrivate;
        private String  expireTime;
        private String  createType;
        private String  options;
        private String  rulesTxt;
        private String  desc;
        private String  descTechnology;
        private String  createUserId;
        private Tag tag;
}
