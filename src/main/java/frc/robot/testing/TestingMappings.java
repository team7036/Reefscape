package frc.robot.testing;

import java.util.HashMap;
import java.util.Map;

public class TestingMappings {
    private boolean isDefault;
    private Map<Integer, Integer> mappings;
    public static TestingMappings DEFAULT = new MappingsBuilder(true).build();

    public TestingMappings(Map<Integer, Integer> mappings, boolean isDefault) {
        this.mappings = mappings;
        this.isDefault = isDefault;
    }

    public boolean isDefault() {
        return isDefault;
    }
    public Map<Integer, Integer> getMappings() {
        return mappings;
    }
    public boolean hasMapping(int oldCanId) {
        return (mappings != null && mappings.get(oldCanId) != null);
    }
    public int getMapping(int oldCanId) {
        if(hasMapping(oldCanId)) {
            return mappings.get(oldCanId);
        }
        if(isDefault()) {
            return oldCanId;
        }
        throw new IllegalArgumentException("Can Id of: " + oldCanId + " doesn't have a test mapping");
    }

    public static class MappingsBuilder {
        private Map<Integer, Integer> mappings;
        private boolean isDefault;

        public MappingsBuilder(boolean isDefault) {
            this.mappings = new HashMap<>();
            this.isDefault = isDefault;
        }
        public MappingsBuilder addMapping(int oldCanId, int newCanId) {
            mappings.put(oldCanId, newCanId);
            return this;
        }
        public TestingMappings build() {
            if(isDefault) {
                return new TestingMappings(null, isDefault);
            } else {
                return new TestingMappings(mappings, isDefault);
            }
        }
    }
    
}
