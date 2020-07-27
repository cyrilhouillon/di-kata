package fr.ath.kata.di;

import fr.ath.kata.di.annotations.Component;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationParameterValue;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.List;

public class BeansScanner {

    private ComponentRegistry registry = new ComponentRegistry();

    public void scan() {
        try (ScanResult scanResult =
                     new ClassGraph()
                             .enableAllInfo()             // Scan classes, methods, fields, annotations
                             .scan()) {                   // Start the scan
            registerScanResult(scanResult);
        }
    }

    public void scan(String packageName) {
        try (ScanResult scanResult =
                     new ClassGraph()
                             .enableAllInfo()             // Scan classes, methods, fields, annotations
                             .acceptPackages(packageName)         // Scan com.xyz and subpackages (omit to scan all packages)
                             .scan()) {                   // Start the scan
            registerScanResult(scanResult);
        }
    }

    private void registerScanResult(ScanResult scanResult) {
        for (ClassInfo routeClassInfo : scanResult.getClassesWithAnnotation(Component.class.getCanonicalName())) {
            registry.register(routeClassInfo.loadClass());
        }
    }

    public ComponentRegistry getRegistry() {
        return registry;
    }
}
