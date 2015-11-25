package com.selop;

import com.selop.container.PackageScanner;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

/**
 * See {@code PackageScanner} class.
 *
 * @author selop
 */
public class PackageScannerTest {

    private static final String PACKAGE_ROOT = "com.selop.beans";

    private static final int BEANS_ANNOTATED_WITH_SINGLETON = 2;

    @Test
    public void checkCountOfFoundBeansWithSingletonAnnotation() {
        PackageScanner scanner = new PackageScanner();

        scanner.scan(PACKAGE_ROOT);

        List<Class<?>> beans = scanner.getBeans()
                .stream()
                .filter(bean -> bean.isAnnotationPresent(Singleton.class))
                .collect(Collectors.toList());

        Assert.assertEquals(beans.size(), BEANS_ANNOTATED_WITH_SINGLETON);
    }

    // TODO: 25/11/15 restliche Tests
}
