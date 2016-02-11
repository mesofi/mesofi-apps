package mx.com.mesofi.framework.context;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class ResourceScanner {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(ResourceScanner.class);

    private String basePackageName;
    public static final String DEFAULT_PACKAGE_SUFFIX = "controller";

    /**
     * @return the basePackageName
     */
    public String getBasePackageName() {
        return basePackageName;
    }

    /**
     * @param basePackageName
     *            the basePackageName to set
     */
    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public Set<Package> detectValidPackageNames() {
        Package[] packages = Package.getPackages();
        Set<Package> packagesFound = new HashSet<Package>();
        for (Package p : packages) {
            if (p.getName().startsWith(basePackageName) && p.getName().endsWith(DEFAULT_PACKAGE_SUFFIX)) {
                packagesFound.add(p);
            }
        }
        return packagesFound;
    }

    public Set<Class<? extends Object>> findClassesByPackage(Package package1,
            Class<? extends Annotation> annotatedClass) {
        Set<Class<? extends Object>> classesFound = new HashSet<Class<? extends Object>>();
        Set<Class<? extends Object>> classes = findClassesByPackage(package1);
        for (Class<? extends Object> clazz : classes) {
            if (clazz.isAnnotationPresent(annotatedClass)) {
                classesFound.add(clazz);
            }
        }
        return classesFound;
    }

    public Set<Class<? extends Object>> findClassesByPackage(Set<Package> packages) {
        Set<Class<? extends Object>> classes = new HashSet<Class<? extends Object>>();
        if (packages != null) {
            for (Package p : packages) {
                classes = findClassesByPackage(p);
            }
        }
        return classes;
    }

    public Set<Class<? extends Object>> findClassesByPackage(Set<Package> packages,
            Class<? extends Annotation> annotatedClass) {
        Set<Class<? extends Object>> classesFound = new HashSet<Class<? extends Object>>();
        for (Package package1 : packages) {
            classesFound.addAll(findClassesByPackage(package1, annotatedClass));
        }
        return classesFound;
    }

    public Set<Class<? extends Object>> findClassesByPackage(Package package1) {
        Set<Class<? extends Object>> classes = new HashSet<Class<? extends Object>>();
        try {
            // Get a File object for the package
            File directory = null;
            try {
                directory = new File(Thread.currentThread().getContextClassLoader()
                        .getResource(package1.getName().replace('.', '/')).getFile());
            } catch (Exception x) {
                String messageError = package1.getName() + " does not appear to be a valid package";
                log.error(messageError);
                throw new ClassNotFoundException(messageError);
            }
            if (directory.exists()) {
                // Get the list of the files contained in the package
                String[] files = directory.list();
                for (int i = 0; i < files.length; i++) {
                    // we are only interested in .class files
                    if (files[i].endsWith(".class")) {
                        // removes the .class extension
                        classes.add(Class.forName(package1.getName() + '.'
                                + files[i].substring(0, files[i].length() - 6)));
                    }
                }
            } else {
                log.warn("Skipping the following source due: " + package1.getName()
                        + " does not appear to be a valid package, review the classpath. "
                        + "The following directory does not exist [" + directory + "]");
            }
        } catch (Exception e) {
            throw new IllegalStateException("An error occured due to: " + e.getMessage());
        }
        return classes;
    }

}
