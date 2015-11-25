package com.selop;

import com.selop.beans.MyBean;
import com.selop.container.PackageScanner;
import com.selop.container.SimpleContainer;
import com.selop.inject.BeanInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 * @author selop
 */
public class App {

    protected static Logger log = LoggerFactory.getLogger(App.class);

    private SimpleContainer container = SimpleContainer.getInstance();

    private static final String PACKAGE_ROOT = "com.selop.beans";

    public static void main( String[] args ) {

        log.info("Lightweight DI Container Test started.");

        PackageScanner scanner = new PackageScanner();
        BeanInjector injector = new BeanInjector();
        scanner.scan(PACKAGE_ROOT);

        try {
            MyBean testBean = new MyBean();
            injector.inject(testBean);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
