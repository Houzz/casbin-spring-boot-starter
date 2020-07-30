package org.casbin.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.casbin.exception.CasbinModelConfigNotFoundException;
import org.casbin.exception.CasbinPolicyConfigNotFoundException;
import org.casbin.utils.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.InputStream;

/**
 * @author fangzhengjin
 * @version V1.0
 * @title: CasbinProperties
 * @package org.casbin.spring.boot.autoconfigure.properties
 * @description:
 * @date 2019/4/2 15:25
 */
@Data
@ConfigurationProperties("casbin")
public class CasbinProperties {

    /**
     * enable Casbin or not
     */
    private boolean enableCasbin = true;

    /**
     * whether use synced Enforcer
     */
    private boolean useSyncedEnforcer = false;

    /**
     * local model file
     */
    private String model = "classpath:casbin/model.conf";

    /**
     * local policy file
     */
    private String policy = "classpath:casbin/policy.csv";

    /**
     * policy persistence strategy
     */
    private CasbinStoreType storeType = CasbinStoreType.JDBC;

    /**
     * table name for persisting policies
     */
    private String tableName = "casbin_rule";

    /**
     * Watcher sync strategy
     */
    private CasbinWatcherType watcherType = CasbinWatcherType.REDIS;

    /**
     * Watcher topic name
     */
    private String watcherTopic = "CASBIN_POLICY_TOPIC";

    /**
     * whether auto create table if not exists
     */
    private CasbinDataSourceInitializationMode initializeSchema = CasbinDataSourceInitializationMode.CREATE;

    /**
     * enable watcher or not
     */
    private boolean enableWatcher = false;

    /**
     * only valid when adapter supports auto save
     * can turn on with enforcer.enableAutoSave(true) as well
     */
    private boolean autoSave = true;

    /**
     * whether use default RBAC configs if model cannot be loaded
     */
    private boolean useDefaultModelIfModelNotSetting = true;

    public String getModelContext() {
        String text = FileUtils.getFileAsText(model);
        if (text == null) {
            throw new CasbinModelConfigNotFoundException();
        } else {
            return text;
        }
    }

    public InputStream getPolicyInputStream() {
        InputStream stream = FileUtils.getFileAsInputStream(policy);
        if (stream == null) {
            throw new CasbinPolicyConfigNotFoundException();
        } else {
            return stream;
        }
    }
}

