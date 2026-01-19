package pharmacie.service;

import java.util.Map;

/**
 * Strategy interface for generating reports.
 */
public interface ReportStrategy {
    String generateReport();

    Map<String, Object> getData();
}
