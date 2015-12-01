package com.selop;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite for all test classes.
 */
@RunWith(Suite.class)
@SuiteClasses({ BeanInjectorTest.class, ContainerTest.class, PackageScannerTest.class})
public class AllTests {
}
