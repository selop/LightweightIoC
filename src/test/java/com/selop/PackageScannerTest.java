package com.selop;

import com.selop.container.PackageScanner;
import com.selop.container.SimpleContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Named;
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
    // TODO: 27/11/15 not clean, make sure to read this via ReflectionsAPi
    private static final int BEANS_ANNOTATED_WITH_SINGLETON = 3;
    private static final int BEANS_ANNOTATED_WITH_NAMED = 3;

    private PackageScanner scanner;

    @Before
    public void before() {
        scanner = new PackageScanner();
    }

    @After
    public void after() {
        SimpleContainer.getInstance().release();
    }

    @Test
    public void scannerNotNull() {
        Assert.assertNotNull(scanner);
    }

    @Test
    public void checkCountOfFoundBeansWithAnnotations() {
        scanner.scan(PACKAGE_ROOT);

        List<Class<?>> beanSingleton = scanner.getBeans()
                .stream()
                .filter(bean -> bean.isAnnotationPresent(Singleton.class))
                .collect(Collectors.toList());

        List<Class<?>> beanNamed = scanner.getBeans()
                .stream()
                .filter(bean -> bean.isAnnotationPresent(Named.class))
                .collect(Collectors.toList());

        Assert.assertNotNull(beanSingleton);
        Assert.assertNotNull(beanNamed);

        Assert.assertEquals(beanSingleton.size(), BEANS_ANNOTATED_WITH_SINGLETON);
        Assert.assertEquals(beanNamed.size(), BEANS_ANNOTATED_WITH_NAMED);
    }
}
