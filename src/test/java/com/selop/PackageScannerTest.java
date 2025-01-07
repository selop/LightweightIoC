package com.selop;

import com.selop.container.PackageScanner;
import com.selop.container.SimpleContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * See {@code PackageScanner} class.
 *
 * @author selop
 */
class PackageScannerTest {

    private static final String PACKAGE_ROOT = "com.selop.beans";

    // TODO: read those values before the tests
    private static final int BEANS_ANNOTATED_WITH_SINGLETON = 7;
    private static final int BEANS_ANNOTATED_WITH_NAMED = 3;

    private PackageScanner scanner;

    List<Class<?>> beanSingleton;
    List<Class<?>> beanNamed;

    @BeforeEach
    void before() {
        scanner = new PackageScanner();
        scanner.scan(PACKAGE_ROOT);

        beanSingleton = scanner.getBeans()
                .stream()
                .filter(bean -> bean.isAnnotationPresent(Singleton.class))
                .collect(Collectors.toList());

        beanNamed = scanner.getBeans()
                .stream()
                .filter(bean -> bean.isAnnotationPresent(Named.class))
                .collect(Collectors.toList());
    }

    @AfterEach
    void after() {
        SimpleContainer.getInstance().release();
    }

    @Test
    void scannerNotNull() {
        assertNotNull(scanner);
    }

    @Test
    void beanListSingletonNotNull() {
        assertNotNull(beanSingleton);
    }

    @Test
    void beanListNamedNotNull() {
        assertNotNull(beanNamed);
    }

    @Test
    void checkCountOfFoundBeansWithSingletonAnnotations() {
        assertEquals(beanSingleton.size(), BEANS_ANNOTATED_WITH_SINGLETON);
    }

    @Test
    void checkCountOfFoundBeansWithNamedAnnotations() {
        assertEquals(beanNamed.size(), BEANS_ANNOTATED_WITH_NAMED);
    }

}
