// 代码生成时间: 2025-09-21 10:38:08
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * Configuration Manager class to manage application configurations.
 * Provides methods to fetch configuration values.
 */
@ApplicationScoped
@RegisterForReflection(targets = {Config.class, ConfigProvider.class})
public class ConfigurationManager {

    private final Config config;

    // Constructor injection of MicroProfile Config
    public ConfigurationManager(Config config) {
        this.config = config;
    }

    /**
     * Fetches a single configuration value by key.
     * @param key         the key of the configuration value
     * @param defaultValue a default value to return if the key is not present
     * @return the configuration value if present, otherwise the default value
     */
    public String getConfigValue(String key, String defaultValue) {
        String value = config.getValue(key, String.class);
        return Optional.ofNullable(value).orElse(defaultValue);
    }

    /**
     * Fetches a Map of configuration values by prefix.
     * @param prefix the prefix of the configuration keys
     * @return a Map of configuration values for the given prefix
     */
    public Map<String, String> getConfigValuesByPrefix(String prefix) {
        Map<String, String> configMap = config.getPropertyNames()
            .stream()
            .filter(key -> key.startsWith(prefix))
            .collect(Collectors.toMap(
                key -> key,
                key -> config.getValue(key, String.class)
            ));
        return configMap;
    }

    /**
     * Error handling for the configuration fetch operation.
     * @return a message indicating that configuration is not available
     */
    public String handleConfigFetchError() {
        return "Configuration is not available.";
    }

    // Producer method for Config instance
    @Produces
    @Singleton
    @Named("ConfigInstance")
    public Config produceConfig() {
        return ConfigProviderResolver.instance().getConfig();
    }
}
